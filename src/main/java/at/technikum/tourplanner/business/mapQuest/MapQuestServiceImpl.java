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
import at.technikum.tourplanner.models.Transporter;
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
    private NetworkCommunicationService net = new NetworkCommunicationServiceImpl();
    private Tour currentTour;


    // 1. SEARCH
    @Override
    public Route searchRoute(String from, String to, Transporter transporter) {
        // Create a new Route - with Builder
        Route currentRoute = this.routeBuilder(from, to, transporter);
        return RouteMapper(currentRoute);
    }
    //1.1 BUILD - KEY  + FROM, TO
    private Route routeBuilder(String from, String to, Transporter transporter) {
        Route route = Route.builder()
                .key("?key=" + config.getMapQuestID())
                .from(from)
                .to(to)
                .build();

        route.setUrlRoute("http://www.mapquestapi.com/directions/v2/route" + route.getKey() + "&from=" + route.getFrom() + "&to=" + route.getTo() + "&routeType=" + transporter.toString() + "&unit=k");

        //System.out.println(route.getUrlRoute());
        return route;
    }
    // 1.2 Mapper --> search in the INTERNET
    private Route RouteMapper(Route currentRoute){
        try {
            ObjectMapper mapper = new ObjectMapper();
            String body = net.getResponseBodyByURL(currentRoute.getUrlRoute());

            if (body.equals("")) {
                return null;
            }

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
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    //2. SET IMAGE SETTINGS
    private Route setImageSettingsToRoute(Route currentRoute) {
        currentRoute.setSize("&size=" + currentRoute.getRouteImage().getWidth() + "," + currentRoute.getRouteImage().getHeight());
        currentRoute.setUrlMap(createDownloadURL(currentRoute, true, false));
       // currentRoute = copyRouteDataToImage(currentRoute);
        return copyRouteDataToImage(currentRoute);
    }
    //2.1 CREATE IMG-URL
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
    //2.2 SAVE DATA TO IMAGE
    private Route copyRouteDataToImage(Route currentRoute) {
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

    //3 SAVE IMAGE in DATABASE
    private RouteImage saveImageOnline(RouteImage currentRouteImage) {
        return routeImageDao.insert(currentRouteImage);
    }
    //4.1 LOAD IMAGE FROM INTERNET
    private byte[] loadRouteImage(String downloadURL) {
        return net.loadImageByLink(downloadURL);
    }

    //5 UPDATE
    private RouteImage updateImage(RouteImage currentRouteImage) {
        return routeImageDao.update(currentRouteImage);
    }

    //start Image
    @Override
    public Route startRoute(String from, String to, Transporter transporter) {
        final Route[] route = new Route[1];
        // SET ROUTE + IMAGE
        route[0] = searchRoute(from, to, transporter);
        if (route[0] == null) {return null;}
        //  set image setting
        route[0] = setImageSettingsToRoute(route[0]);
        // save in DataBase
        saveImageOnline(route[0].getRouteImage());
        // DOWNLOAD  IMAGE
        saveImageDataOnline(route[0]);
        return route[0];
    }
    //check Route
    @Override
    public Route startRoute(Tour tour) {
        currentTour = tour;
        return startRoute(tour.getFrom(), tour.getTo(), tour.getTransporter());
    }


    // CREATE IMAGE
    private Image showLocalRouteImage(RouteImage routeImage) {
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


        FileAccess fileAccess = new FileAccessImpl();
        //fileAccess.writeFile(route.getRouteImage().getImageID() + ".jpg", currentImage);

        routeImageDao.updateImageData(route.getRouteImage());
        routeImageDao.update(route.getRouteImage());

        return route.getRouteImage();
    }


    @Override
    public RouteImage saveImageDataOnline(Route route){
        byte[] currentImage = loadRouteImage(route.getUrlMap());
        route.getRouteImage().setData(currentImage);
        route.getRouteImage().setLocal(false);
        routeImageDao.updateImageData(route.getRouteImage());
        routeImageDao.update(route.getRouteImage());

        return route.getRouteImage();
    }

    // TODO: 09.05.2022 noch nicht in Verwendung
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
