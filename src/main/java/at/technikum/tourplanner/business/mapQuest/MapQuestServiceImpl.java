package at.technikum.tourplanner.business.mapQuest;

import at.technikum.tourplanner.business.config.ConfigurationManager;
import at.technikum.tourplanner.business.net.NetworkCommunicationService;
import at.technikum.tourplanner.business.net.NetworkCommunicationServiceImpl;
import at.technikum.tourplanner.business.config.ConfigurationManagerImpl;
import at.technikum.tourplanner.database.dao.RouteImageDao;
import at.technikum.tourplanner.database.fileServer.FileAccess;
import at.technikum.tourplanner.database.fileServer.FileAccessImpl;
import at.technikum.tourplanner.database.sqlServer.RouteImageDaoImpl;
import at.technikum.tourplanner.models.RouteImage;
import at.technikum.tourplanner.models.Route;
import at.technikum.tourplanner.models.Tour;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Time;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MapQuestServiceImpl implements MapQuestService {

    private static ConfigurationManager config = new ConfigurationManagerImpl();
    private RouteImageDao routeImageDao = new RouteImageDaoImpl();
    private Tour currentTour;

    // 1. SEARCH
    @Override
    public Route searchRoute(String from, String to) {

        Route currentRoute = this.routeBuilder(from, to);

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(currentRoute.getUrlRoute()))
                    .build();

            CompletableFuture<HttpResponse<String>> response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper = new ObjectMapper();

            String body = response.thenApply(HttpResponse::body).get(5, TimeUnit.SECONDS);


            if (!mapper.readTree(body).get("route").get("routeError").get("errorCode").toString().equals("-400")) {
                return null;
            }


            currentRoute.setRouteBody(mapper.readTree(body).get("route"));
            currentRoute.setDistance(Double.valueOf(mapper.readTree(body).get("route").get("distance").toString()));
            currentRoute.setTime(Time.valueOf(mapper.readTree(body).get("route").get("formattedTime").toString().replace("\"", "")));
            currentRoute.setSessionID("&session=" + currentRoute.getRouteBody().get("sessionId").toString().replace("\"", ""));
            String lr = currentRoute.getRouteBody().get("boundingBox").get("lr").get("lng").toString() + "," + currentRoute.getRouteBody().get("boundingBox").get("lr").get("lat").toString();
            String ul = currentRoute.getRouteBody().get("boundingBox").get("ul").get("lng").toString() + "," + currentRoute.getRouteBody().get("boundingBox").get("lr").get("lat").toString();
            currentRoute.setBoundingBox("&boundingBox=" + lr + "," + ul);

            return currentRoute;

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

    //2. BUILD
    private Route routeBuilder(String from, String to) {
        Route route = Route.builder()
                .key("?key=" + config.getMapQuestID())
                .from(from)
                .to(to)
                .build();

        route.setUrlRoute("http://www.mapquestapi.com/directions/v2/route" + route.getKey() + "&from=" + route.getFrom() + "&to=" + route.getTo());
        return route;
    }

    //3. SET IMAGE SETTINGS
    @Override
    public Route setImageSettingsToRoute(Route currentRoute) {
        currentRoute.setSize("&size=" + currentRoute.getRouteImage().getWidth() + "," + currentRoute.getRouteImage().getHeight());
        currentRoute.setUrlMap(createDownloadURL(currentRoute, true, false));
        return currentRoute;
    }

    //4. CREATE URL
    private String createDownloadURL(Route currentRoute, boolean defaultMarker, boolean zoom) {
        String params = currentRoute.getKey() + currentRoute.getSize() + currentRoute.getSessionID();
        if (!defaultMarker) {
            params += currentRoute.getRouteImage().getDefaultMarker();
        }
        if (zoom) {
            params += currentRoute.getRouteImage().getZoom();
        }
        return "http://www.mapquestapi.com/staticmap/v5/map" + params;
    }

    //5. SAVE DATA TO IMAGE
    @Override
    public Route copyRouteDataToImage(Route currentRoute) {
        String filename = currentRoute.getFrom() + "-" + currentRoute.getTo();
        if (currentTour.getTourID() != null) {
            currentRoute.getRouteImage().setImageID(currentTour.getTourID());
        } else {
            currentRoute.getRouteImage().setImageID(filename);
        }
        currentRoute.getRouteImage().setDownloadURL(currentRoute.getUrlRoute());
        currentRoute.getRouteImage().setFrom(currentRoute.getFrom());
        currentRoute.getRouteImage().setTo(currentRoute.getTo());
        currentRoute.getRouteImage().setFilePath(config.getImage() + currentRoute.getRouteImage().getImageID() + ".jpg");

        return currentRoute;
    }

    //5.1 SAVE IMAGE
    @Override
    public RouteImage saveImageOnline(RouteImage currentRouteImage) {
        return routeImageDao.insert(currentRouteImage);
    }

    //5.2 LOAD IMAGE FROM INTERNET
    private byte[] loadRouteImage(String downloadURL) {
        NetworkCommunicationService networkCommunicationService = new NetworkCommunicationServiceImpl();
        return networkCommunicationService.loadImageByLink(downloadURL);
    }

    //6 UPDATE
    @Override
    public RouteImage updateImageOnline(RouteImage currentRouteImage) {
        return routeImageDao.update(currentRouteImage);
    }

    //start Image
    @Override
    public Route startRoute(String from, String to) {
        final Route[] route = new Route[1];
        RouteImage routeImageSettings = RouteImage.builder().build();

        // SET ROUTE + IMAGE

        route[0] = searchRoute(from, to);
        if (route[0] == null) {
            return null;
        }
        route[0].setRouteImage(routeImageSettings);


        // copy image and set settings

        route[0] = setImageSettingsToRoute(route[0]);
        route[0] = copyRouteDataToImage(route[0]);


        // save in DataBase
        saveImageOnline(route[0].getRouteImage());
        // DOWNLOAD  IMAGE
        downloadImage(route[0]);


        return route[0];
    }

    @Override
    public Route startRoute(Tour tour) {
        currentTour = tour;
        return startRoute(tour.getFrom(), tour.getTo());
    }

    // CREATE IMAGE
    @Override
    public Image showLocalRouteImage(RouteImage routeImage) {
        FileAccess fileAccess = new FileAccessImpl();
        File file = fileAccess.readFile(routeImage.getImageID() + ".jpg");
        return new Image(file.getAbsolutePath());
    }

    @Override
    public Image showOnlineRouteImage(RouteImage routeImage) {
        byte[] image = routeImage.getData();
        return new Image(new ByteArrayInputStream(image));
    }



    //6. DOWNLOAD IMAGE 
    // TODO: 30.03.2022 IF Bedingung einbauen und Datenbank updaten
    @Override
    public RouteImage downloadImage(Route route) {

        byte[] currentImage = loadRouteImage(route.getUrlMap());
        route.getRouteImage().setData(currentImage);



        /*
        FileAccess fileAccess = new FileAccessImpl();
        fileAccess.writeFile(route.getRouteImage().getImageID() + ".jpg", currentImage);
        route.getRouteImage().setLocal(true);
        */
        routeImageDao.updateImageData(route.getRouteImage());

        return route.getRouteImage();
    }

    //7. RELOAD IMAGE
    @Override
    public RouteImage reloadImage(RouteImage routeImage) {
        //Route route = this.searchRoute(routeImage.getFrom(), routeImage.getTo());
        //route.setRouteImage(routeImage);
        //if (route == null) {return null;}
        // route = this.setImageSettingsToRoute(route);
        // route = this.copyRouteDataToImage(route);
        // RouteImage newRouteImage = this.updateImageOnline(route.getRouteImage());

        // routeImage = routeImageDao.getItemById(routeImage.getImageID());

        //downloadImage(image);
        return routeImage;
    }
}
