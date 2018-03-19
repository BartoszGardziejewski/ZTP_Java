package files;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class DirectoryLoader extends Thread{

    private static FileManager manager;

    private File directory;
    private ArrayList<Node> elements;

    public static void setMnager(FileManager manager){
        DirectoryLoader.manager = manager;
    }


    public DirectoryLoader(File directory){
        this.directory = directory;
        elements = new ArrayList<>();
    }

    public File getFile(){
        return directory;
    }

    @Override
    public void run() {

        if (directory == null){
            return;
        }

        File[] files = directory.listFiles();

        if (files == null){
            return;
        }

        for (File file:files) {
            if ( file.isDirectory() ) {
                //addDirectory(file);
            } else if( file.isFile() && ( file.getName().contains(".png") || file.getName().contains(".jpg") )){
                addImage(file);
            }
        }
    }


    private void addImage(File image){

        StackPane basePane = new StackPane();

        basePane.setPrefWidth(150);
        basePane.setPrefHeight(150);
        basePane.setMaxWidth(150);
        basePane.setMaxHeight(150);
        basePane.setClip(new Rectangle(150, 150));


        Button imageButton = new Button();
        imageButton.setPrefWidth(150);
        imageButton.setPrefHeight(150);
        imageButton.setStyle("-fx-background-color:transparent");

        imageButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loadImageEditor(image);
            }
        });

        WeakReference wr = new WeakReference(new Image("file:"+image));
        Image miniimage = (Image) wr.get();
        //Image miniimage = new Image("file:"+image);

        ImageView imageView = new ImageView(miniimage);

        imageView.getStyleClass().add("image-tile");

        double imHeight = miniimage.getHeight();
        double imWidth = miniimage.getWidth();

        double param = imHeight/150;
        param = imWidth/param;

        imageView.maxWidth(150);
        imageView.maxHeight(150);


        imageView.setFitWidth(150); // tmp not param
        imageView.setFitHeight(150);

        imageView.setClip(new Rectangle(150, 150));

        basePane.getChildren().add(imageView);
        basePane.getChildren().add(imageButton);

        elements.add(basePane);
    }

public void loadImageEditor(File image){
        manager.loadImageEditor(image);
    }

    public ArrayList<Node> getElements() {
        return elements;
    }

}
