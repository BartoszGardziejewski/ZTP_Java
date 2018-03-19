package files;

import application.ApplicationManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;

import java.io.File;
import java.util.ArrayList;

public class FileManager {

    private ApplicationManager manager;

    private DirectoryLoader superDirectory;
    private DirectoryLoader currentDirectory;
    private ArrayList<DirectoryLoader> directorys;


    public ArrayList<Node> getCurrentContent(){

        ArrayList<Node> content = new ArrayList<>();

        if(superDirectory!=null) {
            Button superButton = new Button(superDirectory.getFile().getName());
            superButton.getStyleClass().add("image-tile");

            superButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    moveToSuperDirectory();
                }
            });
            content.add(superButton);
        }

        try {
            currentDirectory.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        content.addAll(currentDirectory.getElements());

        for (DirectoryLoader directory: directorys) {
            Button directoryButton = new Button(directory.getFile().getName());
            directoryButton.getStyleClass().add("image-tile");

            directoryButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    moveToDirectory(directory);
                }
            });

            content.add(directoryButton);
        }

        return  content;
    }

    public FileManager(String path,ApplicationManager manager){
        DirectoryLoader.setMnager(this);
        this.manager = manager;
        currentDirectory = new DirectoryLoader(new File(path));
        currentDirectory.start();

        if(currentDirectory.getFile().getParentFile()!=null) {
            superDirectory = new DirectoryLoader(currentDirectory.getFile().getParentFile());
            superDirectory.start();
        }
        else {
            superDirectory = null;
        }

        directorys = new ArrayList<>();
        File[] list = currentDirectory.getFile().listFiles();
        for ( File f : list ) {
            if ( f.isDirectory() ) {
                DirectoryLoader tmpDir = new DirectoryLoader(f.getAbsoluteFile());
                tmpDir.start();
                directorys.add(tmpDir);
            }
        }

    }

    public void loadImageEditor(File image){
        manager.loadImageEditor(image);
    }

    public void moveToSuperDirectory(){

        currentDirectory = superDirectory;

        if(currentDirectory.getFile().getParentFile() != null)
        {
            superDirectory = new DirectoryLoader(currentDirectory.getFile().getParentFile());
            superDirectory.start();
        }
        else{
            superDirectory = null;
        }

        try {
            for (DirectoryLoader loader: directorys) {
                loader.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        directorys = new ArrayList<>();

        File[] list = currentDirectory.getFile().listFiles();
        for ( File f : list ) {
            if ( f.isDirectory() ) {
                DirectoryLoader tmpDir = new DirectoryLoader(f);
                tmpDir.start();
                directorys.add(tmpDir);
            }
        }

        manager.moveToDirectory();
    }

    public void moveToDirectory( DirectoryLoader directory ) {

        try {
            if(superDirectory!=null) {
                superDirectory.join();
            }
            superDirectory = currentDirectory;
            currentDirectory = directory;

            for (DirectoryLoader loader: directorys) {
                    loader.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        directorys = new ArrayList<>();

        File[] list = currentDirectory.getFile().listFiles();
        for ( File f : list ) {
            if ( f.isDirectory() ) {
                DirectoryLoader tmpDir = new DirectoryLoader(f.getAbsoluteFile());
                tmpDir.start();
                directorys.add(tmpDir);
            }
        }

        manager.moveToDirectory();
    }

    public DirectoryLoader getCurrenDirectory(){
        return currentDirectory;
    }

    public void refreshCurrentDirectory(){

        try {
            currentDirectory.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        currentDirectory = new DirectoryLoader(currentDirectory.getFile());
        currentDirectory.start();
    }

}
