package at.technikum.tourplanner.business.net;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class NetworkCommunicationServiceImpl implements NetworkCommunicationService {

    //LOGGER
    private final static Logger log = LogManager.getLogger(NetworkCommunicationServiceImpl.class.getName());

    //LOAD IMAGE FROM NET
    @Override
    public byte[] loadImageByLink(String downloadURL) {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(downloadURL))
                .build();
        try {
            HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());
            return response.body();

        } catch (IOException e) {
            e.printStackTrace();
            log.error(e);
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.error(e);
        }

        return null;
    }

    @Override
    public String getResponseBodyByURL(String url) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        CompletableFuture<HttpResponse<String>> response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        try {
            return response.thenApply(HttpResponse::body).get(40, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.error(e);
        } catch (ExecutionException e) {
            e.printStackTrace();
            log.error(e);
        } catch (TimeoutException e) {
            e.printStackTrace();
            log.error(e);
        }
        return "";
    }

}

