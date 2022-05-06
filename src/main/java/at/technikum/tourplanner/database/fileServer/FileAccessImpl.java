package at.technikum.tourplanner.database.fileServer;

import at.technikum.tourplanner.business.config.ConfigurationManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;

public class FileAccessImpl implements FileAccess{
    private ConfigurationManager config = new ConfigurationManager();
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
    public boolean deleteFile(String filename){
        return new File(GetFullPath(filename)).delete();
    }


}
