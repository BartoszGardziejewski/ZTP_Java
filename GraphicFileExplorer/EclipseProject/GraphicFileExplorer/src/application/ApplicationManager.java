package application;

import files.FileManager;
import javafx.scene.control.Button;
import loaders.PluginsLoader;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ApplicationManager extends Application {

    private FileManager fileManager;
    private Stage currentStage;
    private ApplicationController controller;
    private String rootPath;

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {


        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception{

        rootPath = "D:\\";

        currentStage = primaryStage;

        fileManager = new FileManager(rootPath,this);

        loadMainWindow();
    }

    private void exitApplication(){
        currentStage.close();
        try {
            this.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadMainWindow() throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
        Parent root  = loader.load();
        controller = loader.getController();

        currentStage.setTitle("Graphic file exporter");
        currentStage.setScene(new Scene(root));
        currentStage.show();

        setExitButton();

        viewCurrentDirectoryContent();
    }

    public void moveToDirectory(){
        viewCurrentDirectoryContent();
    }

    private void viewCurrentDirectoryContent(){
        try {
            fileManager.getCurrenDirectory().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        rootPath = fileManager.getCurrenDirectory().getFile().getAbsolutePath();
        controller.MainPane.getChildren().clear();
        controller.upperPane.getChildren().clear();

        controller.upperPane.getChildren().add(controller.pathLabel);

        controller.pathLabel.setText(rootPath);
        controller.MainPane.getChildren().addAll(fileManager.getCurrentContent());
    }

    public void loadImageEditor(File image){
        Image mainImage = new Image("File:"+image.getAbsolutePath());
        ImageView mainView = new ImageView(mainImage);

        //currentStage.setWidth(mainImage.getWidth());
        //currentStage.setHeight(mainImage.getHeight());

        rootPath = image.getAbsolutePath();
        controller.MainPane.getChildren().clear();
        controller.upperPane.getChildren().clear();

        Button grayscaleButton = new Button("greyscale color");
        grayscaleButton.getStyleClass().add("button-plugin");
        Button invertButton = new Button("invert color");
        invertButton.getStyleClass().add("button-plugin");
        Button invertRedButton = new Button("invert red color");
        invertRedButton.getStyleClass().add("button-plugin");

        invertButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                try {
                    loadInvertPlugin(image);
                    refreshImage(image);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        grayscaleButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                try {
                    loadGrayscalePlugin(image);
                    refreshImage(image);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        invertRedButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                try {
                    loadInvertRedPlugin(image);
                    refreshImage(image);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        controller.upperPane.getChildren().add(invertButton);
        controller.upperPane.getChildren().add(grayscaleButton);
        controller.upperPane.getChildren().add(invertRedButton);
        controller.upperPane.getChildren().add(controller.pathLabel);

        controller.pathLabel.setText(rootPath);
        controller.MainPane.getChildren().add(mainView);

        setBackButton();
    }

    public void refreshImage(File image){
        Image mainImage = new Image("File:"+image.getAbsolutePath());
        ImageView mainView = new ImageView(mainImage);

        controller.MainPane.getChildren().clear();
        controller.MainPane.getChildren().add(mainView);

        fileManager.refreshCurrentDirectory();
    }

    public void loadInvertRedPlugin(File image) throws Exception {

        PluginsLoader pLoader = new PluginsLoader(ApplicationManager.class.getClassLoader());
        Class inverter = pLoader.loadClass("plugins.ColorInvertRed");

        Class argType[] = new Class[] { String.class };
        Method invert = inverter.getMethod("invertRedImage", argType);
        Object argsArray[] = { image.getAbsolutePath() };
        invert.invoke(null,argsArray);
    }

    public void loadInvertPlugin(File image) throws Exception {

        PluginsLoader pLoader = new PluginsLoader(ApplicationManager.class.getClassLoader());
        Class inverter = pLoader.loadClass("plugins.ColorInversion");

        Class argType[] = new Class[] { String.class };
        Method invert = inverter.getMethod("invertImage", argType);
        Object argsArray[] = { image.getAbsolutePath() };
        invert.invoke(null,argsArray);
    }

    public void loadGrayscalePlugin(File image) throws Exception {

        PluginsLoader pLoader = new PluginsLoader(ApplicationManager.class.getClassLoader());
        Class inverter = pLoader.loadClass("plugins.ColorGrayscale");

        Class argType[] = new Class[] { String.class };
        Method invert = inverter.getMethod("grayscaleImage", argType);
        Object argsArray[] = { image.getAbsolutePath() };
        invert.invoke(null,argsArray);
    }

    private void setExitButton(){
        controller.exitButton.setText("Exit");
        controller.exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                exitApplication();
            }
        });
    }

    private void setBackButton(){
        controller.exitButton.setText("Back");
        controller.exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                viewCurrentDirectoryContent();
                setExitButton();
            }
        });
    }

}
