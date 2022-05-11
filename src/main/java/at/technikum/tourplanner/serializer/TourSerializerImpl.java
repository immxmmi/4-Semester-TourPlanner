package at.technikum.tourplanner.serializer;

import at.technikum.tourplanner.database.fileServer.FileAccess;
import at.technikum.tourplanner.database.fileServer.FileAccessImpl;
import at.technikum.tourplanner.models.Tour;
import com.google.gson.Gson;

import java.io.File;
import java.nio.charset.StandardCharsets;
// TODO: 11.05.2022 @Checked
public class TourSerializerImpl implements TourSerializer{

    @Override
    public void saveTourAsJSON(File file, Tour tour) {
        FileAccess fileAccess = new FileAccessImpl();
        String jsonString = convertTourToJsonString(tour);
        fileAccess.writeFile(file,jsonString.getBytes(StandardCharsets.UTF_8));
    }

    private String convertTourToJsonString(Tour tour){
        return new Gson().toJson(tour);
    }

}
