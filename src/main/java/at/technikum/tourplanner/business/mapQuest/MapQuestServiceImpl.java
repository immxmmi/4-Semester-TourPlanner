package at.technikum.tourplanner.business.mapQuest;

import at.technikum.tourplanner.business.net.NetworkCommunicationService;
import at.technikum.tourplanner.business.net.NetworkCommunicationServiceImpl;
import at.technikum.tourplanner.business.config.ConfigurationManager;
import at.technikum.tourplanner.database.dao.RouteImageDao;
import at.technikum.tourplanner.database.fileServer.FileAccess;
import at.technikum.tourplanner.database.fileServer.FileAccessImpl;
import at.technikum.tourplanner.database.sqlServer.RouteImageDaoImpl;
import at.technikum.tourplanner.models.RouteImage;
import at.technikum.tourplanner.models.Route;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.image.Image;

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

    private RouteImageDao routeImageDao = new RouteImageDaoImpl();

    // 1. SEARCH
    @Override
    public Route searchRoute(String from, String to){

        Route currentRoute = this.routeBuilder(from,to);

        if (currentRoute == null){
            return null;
        }

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(currentRoute.getUrlRoute()))
                    .build();

            CompletableFuture<HttpResponse<String>> response = client.sendAsync(request,HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper = new ObjectMapper();

            String body = response.thenApply(HttpResponse::body).get(5, TimeUnit.SECONDS);


            if (!mapper.readTree(body).get("route").get("routeError").get("errorCode").toString().equals("-400")){
                return null;
            }

            currentRoute.setRouteBody(mapper.readTree(body).get("route"));
            currentRoute.setDistance(Double.valueOf(mapper.readTree(body).get("route").get("distance").toString()));
            currentRoute.setTime(Time.valueOf(mapper.readTree(body).get("route").get("formattedTime").toString().replace("\"","")));

            currentRoute.setSessionID("&session="+currentRoute.getRouteBody().get("sessionId").toString().replace("\"",""));
            String lr = currentRoute.getRouteBody().get("boundingBox").get("lr").get("lng").toString() + "," + currentRoute.getRouteBody().get("boundingBox").get("lr").get("lat").toString();
            String ul = currentRoute.getRouteBody().get("boundingBox").get("ul").get("lng").toString() + "," + currentRoute.getRouteBody().get("boundingBox").get("lr").get("lat").toString();
            currentRoute.setBoundingBox("&boundingBox="+lr + "," + ul);

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
    private Route routeBuilder(String from, String to){
        Route route = Route.builder()
                .key("?key=" + ConfigurationManager.getConfigPropertyValue("mapQuest_id"))
                .from(from)
                .to(to)
                .build();

        route.setUrlRoute("http://www.mapquestapi.com/directions/v2/route"+route.getKey()+"&from="+route.getFrom()+"&to="+route.getTo());
        return route;
    }

    //3. SET IMAGE SETTINGS
    @Override
    public Route setImageSettingsToRoute(Route currentRoute){
        currentRoute.setSize("&size="+currentRoute.getRouteImage().getWidth()+","+ currentRoute.getRouteImage().getHeight());
        currentRoute.setUrlMap(createDownloadURL(currentRoute,true,false));
        return currentRoute;
    }

    //4. CREATE URL
    private String createDownloadURL(Route currentRoute,boolean defaultMarker,boolean zoom){
        String params = currentRoute.getKey()+currentRoute.getSize()+currentRoute.getSessionID();
        if(!defaultMarker){params+=currentRoute.getRouteImage().getDefaultMarker();}
        if(zoom){params+=currentRoute.getRouteImage().getZoom();}
        return "http://www.mapquestapi.com/staticmap/v5/map"+params;
    }

    //5. SAVE DATA TO IMAGE
    @Override
    public Route copyRouteDataToImage(Route currentRoute){
        String filename = currentRoute.getFrom()+"-"+currentRoute.getTo();
        currentRoute.getRouteImage().setImageID(filename);
        currentRoute.getRouteImage().setDownloadURL(currentRoute.getUrlRoute());
        currentRoute.getRouteImage().setFrom(currentRoute.getFrom());
        currentRoute.getRouteImage().setTo(currentRoute.getTo());
        currentRoute.getRouteImage().setFilePath(ConfigurationManager.getConfigPropertyValue("image")+filename+".jpg");

        return currentRoute;
    }

    //5.1 SAVE IMAGE
    @Override
    public RouteImage saveImageOnline(RouteImage currentRouteImage){
        return routeImageDao.insert(currentRouteImage);
    }

    //5.2 LOAD IMAGE FROM INTERNET
    private byte[] loadRouteImage(String downloadURL){
        NetworkCommunicationService networkCommunicationService = new NetworkCommunicationServiceImpl();
        return networkCommunicationService.loadImageByLink(downloadURL);
    }

    //6 UPDATE
    @Override
    public RouteImage updateImageOnline(RouteImage currentRouteImage){
        return routeImageDao.update(currentRouteImage);
    }



    // CREATE IMAGE
    @Override
    public Route startRoute(String from, String to){
        RouteImage routeImageSettings = RouteImage.builder()
                //.height()
                //.width()
                //.zoom()
                //.defaultMarker()
                .build();
        // SET ROUTE + IMAGE
        Route route = this.searchRoute(from,to);

        if(route == null){
            return null;
        }

        route.setRouteImage(routeImageSettings);

        if(route == null){return null;}
        route = this.setImageSettingsToRoute(route);
        route = this.copyRouteDataToImage(route);
        this.saveImageOnline(route.getRouteImage());
        downloadImage(route);
        return route;
    }


    @Override
    public Image showRouteImage(RouteImage routeImage){
        FileAccess fileAccess = new FileAccessImpl();
        File file = fileAccess.readFile(routeImage.getImageID()+".jpg");
        return new Image(file.getAbsolutePath());
    }


    //6. DOWNLOAD IMAGE 
    // TODO: 30.03.2022 IF Bedingung einbauen und Dataenbank updaten
    @Override
    public RouteImage downloadImage(Route route){
        FileAccess fileAccess = new FileAccessImpl();
        fileAccess.writeFile(route.getRouteImage().getImageID()+".jpg",loadRouteImage(route.getUrlMap()));
        route.getRouteImage().setLocal(true);
        routeImageDao.update(route.getRouteImage());
        return route.getRouteImage();
    }

    //7. RELODE IMAGE
    @Override
    public RouteImage reloadImage(RouteImage routeImage){

        System.out.println(routeImage);
        Route route = this.searchRoute(routeImage.getFrom(), routeImage.getTo());
        route.setRouteImage(routeImage);
        if(route == null){return null;}
        route = this.setImageSettingsToRoute(route);
        route = this.copyRouteDataToImage(route);


        RouteImage newRouteImage = this.updateImageOnline(route.getRouteImage());

        System.out.println(newRouteImage);

        routeImage = routeImageDao.getItemById(routeImage.getImageID());

        //downloadImage(image);
        return routeImage;
    }
}
