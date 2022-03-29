package com.example.groupworkgame;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;

public class LoadingScreenController extends Application {
    Scene s1;
    Stage stg;
    private Scene thread1Scene;

    public static void main(String[] args) {
        Application.launch(args);
    }
    @Override
    public void start(Stage stage) throws IOException{
        stg = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Game.class.getResource("LoadingScreen.fxml"));
        Parent root = fxmlLoader.load();
        s1 = new Scene(root);
        stage.setFullScreen(true);
        stage.setScene(s1);
        stage.show();
    }



    //SWITCH TO GAME
    @FXML
    private void goToGame(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader (getClass().getResource("game-scene.fxml"));
        Parent root = loader.load();
        Scene s2 = new Scene(root);
        Stage window = (Stage)(((Node)event.getSource()).getScene().getWindow());
        window.setFullScreen(true);
        window.setScene(s2);
        window.show();

}
//EXIT GAME
    @FXML
    private Button exitButton;
    @FXML
    private AnchorPane scenePane;

    Stage stage;

    public void exitGame(ActionEvent Event) {
        stage = (Stage) scenePane.getScene().getWindow();
        System.out.println("you logged out");
        stage.close();
    }
}
