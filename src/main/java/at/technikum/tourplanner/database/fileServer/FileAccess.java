package at.technikum.tourplanner.database.fileServer;

import at.technikum.tourplanner.models.Tour;

import java.io.File;

public interface FileAccess {

    void createFolder(String folder);

    File readFile(String filename);

    Tour readTourFile(File file);

    File writeFile(String filename, byte[] text);

    File writeFile(File filename, byte[] text);

    boolean deleteFile(String filename);

}
