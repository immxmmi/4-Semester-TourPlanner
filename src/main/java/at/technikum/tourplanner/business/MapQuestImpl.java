package at.technikum.tourplanner.business;

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
public class MapQuestImpl implements MapQuest{



    private Route routeBuilder(String from, String to){
        Route route = Route.builder()
                .key("?key=" + ConfigurationManager.GetConfigPropertyValue("mapQuest_id"))
                .size("&size=225,160")
                .defaultMarker("&defaultMarker=none")
                .zoom("&zoom=11")
                .from("&from="+from)
                .to("&to="+to)
                .build();

        route.setUrlRoute("http://www.mapquestapi.com/directions/v2/route"+route.getKey()+route.getFrom()+route.getTo());
        return route;
    }
    @Override
    public Image searchRoute(String from, String to){

        Route currentRoute = this.routeBuilder(from,to);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(currentRoute.getUrlRoute()))
                .build();

        CompletableFuture<HttpResponse<String>> response = client.sendAsync(request,HttpResponse.BodyHandlers.ofString());
        ObjectMapper mapper = new ObjectMapper();

        try {
            String body = response.thenApply(HttpResponse::body).get(5, TimeUnit.SECONDS);
            currentRoute.setBody(mapper.readTree(body).get("route"));

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

        Image currentImage = Image.builder()
                .name(from + "-"+to)
                .from(from)
                .to(to)
                .image(loadRouteImage(currentRoute))
                .build();

        return currentImage;

    }

    private byte[] loadRouteImage(Route currentRoute){

        String rand;
        currentRoute.setSession("&session="+currentRoute.getBody().get("sessionId").toString().replace("\"",""));
        String lr = currentRoute.getBody().get("boundingBox").get("lr").get("lng").toString() + "," + currentRoute.getBody().get("boundingBox").get("lr").get("lat").toString();
        String ul = currentRoute.getBody().get("boundingBox").get("ul").get("lng").toString() + "," + currentRoute.getBody().get("boundingBox").get("lr").get("lat").toString();
        currentRoute.setBoundingBox("&boundingBox="+lr + "," + ul);

        String params = currentRoute.getKey() +
                currentRoute.getSize() +
                currentRoute.getDefaultMarker() +
                currentRoute.getZoom()+
                currentRoute.getSession()+
                currentRoute.getBoundingBox() +
                currentRoute.getTo();
        currentRoute.setUrlMap("http://www.mapquestapi.com/staticmap/v5/map"+params);

        System.out.println(currentRoute.getUrlMap());


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
}
