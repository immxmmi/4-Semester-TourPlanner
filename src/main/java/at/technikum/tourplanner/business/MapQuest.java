package at.technikum.tourplanner.business;

import at.technikum.tourplanner.models.Image;

public interface MapQuest {
    public Image searchRoute(String from, String to);
}
