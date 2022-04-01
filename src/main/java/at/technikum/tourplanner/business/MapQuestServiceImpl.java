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
            currentRoute.setDistance(Double.valueOf(mapper.readTree(body).get("route").get("distance").toString()));
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
    public Route setImageSettingsToRoute(Route currentRoute){
        currentRoute.setSize("&size="+currentRoute.getImage().getHeight()+","+ currentRoute.getImage().getWidth());
        currentRoute.setUrlMap(createDownloadURL(currentRoute,true,false));
        return currentRoute;
    }

    //4. CREATE URL
    private String createDownloadURL(Route currentRoute,boolean defaultMarker,boolean zoom){
        String params = currentRoute.getKey()+currentRoute.getSize()+currentRoute.getSessionID();
                if(!defaultMarker){params+=currentRoute.getImage().getDefaultMarker();}
                if(zoom){params+=currentRoute.getImage().getZoom();}
        return "http://www.mapquestapi.com/staticmap/v5/map"+params;
    }

    //5. SAVE DATA TO IMAGE
    @Override
    public Route copyRouteDataToImage(Route currentRoute){
        String filename = currentRoute.getFrom()+"-"+currentRoute.getTo();
        currentRoute.getImage().setImageID(filename);
        currentRoute.getImage().setImageData(loadRouteImage(currentRoute));
        currentRoute.getImage().setDownloadURL(currentRoute.getUrlMap());
        currentRoute.getImage().setFrom(currentRoute.getFrom());
        currentRoute.getImage().setTo(currentRoute.getTo());
        currentRoute.getImage().setFilePath("tours/"+filename+".jpg");
        return currentRoute;
    }

    //5.1 SAVE IMAGE
    @Override
    public Image saveImageOnline(Image currentImage){
        return imageDao.insert(currentImage);
    }

    //5.2 LOAD IMAGE FROM INTERNET
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

    //6 UPDATE
    @Override
    public Image updateImageOnline(Image currentImage){
        return imageDao.update(currentImage);
    }



    // CREATE IMAGE
    @Override
    public Route startRoute(String from, String to){
        Image imageSettings = Image.builder()
                //.height()
                //.width()
                //.zoom()
                //.defaultMarker()
                .build();
        // SET ROUTE + IMAGE
        Route route = this.searchRoute(from,to);
        route.setImage(imageSettings);
        if(route == null){return null;}
        route = this.setImageSettingsToRoute(route);
        route = this.copyRouteDataToImage(route);
        this.saveImageOnline(route.getImage());
        return route;
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

        System.out.println(image);
        Route route = this.searchRoute(image.getFrom(), image.getTo());
        route.setImage(image);
        if(route == null){return null;}
        route = this.setImageSettingsToRoute(route);
        route = this.copyRouteDataToImage(route);


        Image newImage = this.updateImageOnline(route.getImage());

        System.out.println(newImage);

        image = imageDao.getItemById(image.getImageID());

        //downloadImage(image);
        return image;
    }
}
