package at.technikum.tourplanner.database.fileServer;

import at.technikum.tourplanner.models.Tour;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public interface FileAccess {

    void createFolder(String folder);
    File readFile(String filename);
    File writeFile(String filename, byte[] text);

    File writeFile(File filename, byte[] text);

    boolean deleteFile(String filename);

}
