package at.technikum.tourplanner.database.fileServer;

import at.technikum.tourplanner.config.ConfigurationManagerImpl;
import at.technikum.tourplanner.models.Tour;
import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileAccessImpl implements FileAccess{
    private ConfigurationManagerImpl config = new ConfigurationManagerImpl();
    private String root;

    public FileAccessImpl() {
        this.root = config.getRoot();
    }

    private String GetFullPath(String filename){
        return Paths.get(root,filename).toString();
    }

    @Override
    public void createFolder(String folder){
        File currentFile = new File(GetFullPath(folder));
        currentFile.mkdirs();
    }

    @Override
    public File readFile(String filename){
        return new File(GetFullPath(filename));
    }

    @Override
    public Tour readTourFile(File file){
        Tour tour = new Tour();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
             tour = new Gson().fromJson(reader, Tour.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return tour;
    }

    @Override
    public File writeFile(String filename, byte[] text){
        File currentFile = new File(GetFullPath(filename));
        currentFile.getParentFile().mkdirs();

        try(FileOutputStream fileOutputStream = new FileOutputStream(GetFullPath(filename))){

            fileOutputStream.write(text);
            return new File(GetFullPath(filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public File writeFile(File file, byte[] text){

        Path path = Paths.get(file.getAbsolutePath());
        try {
            Files.write(path, text);
            return file;
        }
        catch (IOException ex) {
            System.out.print("Invalid Path");
        }

        return null;
    }

    @Override
    public boolean deleteFile(String filename){
        return new File(GetFullPath(filename)).delete();
    }


}
