package at.technikum.tourplanner.database.fileServer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public interface FileAccess {
    File readFile(String filename);
    File writeFile(String filename, byte[] text);
    boolean deleteFile(String filename);

}
