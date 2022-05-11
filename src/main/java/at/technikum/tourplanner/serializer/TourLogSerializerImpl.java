package at.technikum.tourplanner.serializer;

import at.technikum.tourplanner.database.fileServer.FileAccess;
import at.technikum.tourplanner.database.fileServer.FileAccessImpl;
import at.technikum.tourplanner.models.Tour;
import at.technikum.tourplanner.models.TourLog;
import com.google.gson.Gson;

import java.io.File;
import java.nio.charset.StandardCharsets;
// TODO: 11.05.2022 @Checked
public class TourLogSerializerImpl implements TourLogSerializer{

    @Override
    public void saveTourLogAsJSON(File file, TourLog tourLog) {
        FileAccess fileAccess = new FileAccessImpl();
        String jsonString = convertTourLogToJsonString(tourLog);
        fileAccess.writeFile(file,jsonString.getBytes(StandardCharsets.UTF_8));
    }

    private String convertTourLogToJsonString(TourLog tourLog){
        return new Gson().toJson(tourLog);
    }
}
