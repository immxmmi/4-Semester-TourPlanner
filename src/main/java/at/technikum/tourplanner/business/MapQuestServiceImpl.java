package at.technikum.tourplanner.business;

import at.technikum.tourplanner.database.dao.ImageDao;
import at.technikum.tourplanner.database.fileServer.FileAccess;
import at.technikum.tourplanner.database.fileServer.FileAccessImpl;
import at.technikum.tourplanner.database.sqlServer.ImageDaoImpl;
import at.technikum.tourplanner.models.Image;
import at.technikum.tourplanner.models.Route;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
public class MapQuestServiceImpl implements MapQuestService {

    private ImageDao imageDao = new ImageDaoImpl();

    // 1. SEARCH
    @Override
    public Route searchRoute(String from, String to){

        Route currentRoute = this.routeBuilder(from,to);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(currentRoute.getUrlRoute()))
                .build();

        CompletableFuture<HttpResponse<String>> response = client.sendAsync(request,HttpResponse.BodyHandlers.ofString());
        ObjectMapper mapper = new ObjectMapper();

        try {
            String body = response.thenApply(HttpResponse::body).get(5, TimeUnit.SECONDS);
            currentRoute.setRouteBody(mapper.readTree(body).get("route"));

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

        currentRoute.setSessionID("&session="+currentRoute.getRouteBody().get("sessionId").toString().replace("\"",""));
        String lr = currentRoute.getRouteBody().get("boundingBox").get("lr").get("lng").toString() + "," + currentRoute.getRouteBody().get("boundingBox").get("lr").get("lat").toString();
        String ul = currentRoute.getRouteBody().get("boundingBox").get("ul").get("lng").toString() + "," + currentRoute.getRouteBody().get("boundingBox").get("lr").get("lat").toString();
        currentRoute.setBoundingBox("&boundingBox="+lr + "," + ul);

        return currentRoute;

    }

    //2. BUILD
    private Route routeBuilder(String from, String to){
        Route route = Route.builder()
                .key("?key=" + ConfigurationManager.GetConfigPropertyValue("mapQuest_id"))
                .from(from)
                .to(to)
                .build();

        route.setUrlRoute("http://www.mapquestapi.com/directions/v2/route"+route.getKey()+"&from="+route.getFrom()+"&to="+route.getTo());
        return route;
    }

    //3. SET IMAGE SETTINGS
    @Override
    public Route setImageSettingsToRoute(Route currentRoute, Image emptyImage){
        currentRoute.setSize("&size="+emptyImage.getHeight()+","+ emptyImage.getWidth());
        currentRoute.setZoom(emptyImage.getZoom());
        currentRoute.setDefaultMarker(emptyImage.getDefaultMarker());
        currentRoute.setUrlMap(createDownloadURL(currentRoute,true,false));
        return currentRoute;
    }

    //4. CREATE URL
    private String createDownloadURL(Route currentRoute,boolean defaultMarker,boolean zoom){
        String params = currentRoute.getKey()+currentRoute.getSize()+currentRoute.getSessionID();
                if(!defaultMarker){params+=currentRoute.getDefaultMarker();}
                if(zoom){params+=currentRoute.getZoom();}
        return "http://www.mapquestapi.com/staticmap/v5/map"+params;
    }

    //5. SAVE DATA TO IMAGE
    @Override
    public Image copyRouteDataToImage(Image currentImage, Route currentRoute){
        String filename = currentRoute.getFrom()+"-"+currentRoute.getTo();
        currentImage.setImageID(filename);
        currentImage.setImageData(loadRouteImage(currentRoute));
        currentImage.setDownloadURL(currentRoute.getUrlMap());
        currentImage.setFrom(currentRoute.getFrom());
        currentImage.setTo(currentRoute.getTo());
        currentImage.setFilePath("tours/"+currentImage.getImageID()+".jpg");
        //imageDao.insert(currentImage);

        return currentImage;
    }
    private byte[] loadRouteImage(Route currentRoute){

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(currentRoute.getUrlMap()))
                .build();
        try {
            HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());
            return response.body();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    //6. DOWNLOAD IMAGE 
    // TODO: 30.03.2022 IF Bedingung einbauen und Dataenbank updaten
    @Override
    public Image downloadImage(Image currentImage){

        FileAccess fileAccess = new FileAccessImpl();
        fileAccess.readFile(currentImage.getFilePath());
        fileAccess.writeFile(currentImage.getFilePath(), currentImage.getImageData());
        
        currentImage.setLocal(true);
       // imageDao.update(currentImage);
        return currentImage;
    }

    //7. RELODE IMAGE
    @Override
    public Image reloadImage(Image image){
        Route currentRoute = this.searchRoute(image.getFrom(),image.getTo());
        currentRoute = setImageSettingsToRoute(currentRoute,image);
        image = copyRouteDataToImage(image,currentRoute);

       // imageDao.update(image);
        //downloadImage(image);
        //return imageDao.getItemById(image.getImageName());
        return image;
    }
}
