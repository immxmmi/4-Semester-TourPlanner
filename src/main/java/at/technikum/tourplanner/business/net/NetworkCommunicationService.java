package at.technikum.tourplanner.business.net;

public interface NetworkCommunicationService {

    byte[] loadImageByLink(String downloadURL);

    String getResponseBodyByURL(String url);

}
