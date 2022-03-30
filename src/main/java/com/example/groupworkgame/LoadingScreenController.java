package com.example.groupworkgame;

import javafx.animation.AnimationTimer;
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
import javafx.util.Pair;

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
        GameController controller = loader.getController();
        Game.initialiseArrayLists(root, controller);
        controller.initialiseObjects();
        s2.setOnMousePressed(mouseEvent -> Game.player.setUp(mouseEvent.isPrimaryButtonDown()));
        s2.setOnMouseReleased(mouseEvent -> Game.player.setUp(false));
        //GAME LOOP
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!Game.player.isDead) {
                    Game.counter = updateCounter(Game.counter);
                    updateEntities();
                    if (controller.checkCollisions()) {
                        System.out.println("you got: " + Game.score);
                        Game.player.setDead(true);
                    }
                }
            }
            public int updateCounter(int counter){
                Game.score++;
                switch (counter) {
                    case 25 -> controller.createTopObject();
                    case 50 -> {
                        controller.createBottomObject();
                        counter = 0;
                    }
                }
                counter++;
                return counter;
            }
            public void updateEntities(){
                controller.cycleBackground();
                controller.moveObjects();
                Pair<Float, Float> newPlayerPos = controller.updatePlayerPos(Game.player.isUp(), Game.player.getVelY(), Game.player.getRotY());
                Game.player.setVelY(newPlayerPos.getKey());
                Game.player.setRotY(newPlayerPos.getValue());
            }

        };
            timer.start();
            window.setFullScreen(true);
            window.setScene(s2);
            window.show();

    }
//EXIT GAME
    @FXML
    private AnchorPane scenePane;

    Stage stage;

    public void exitGame(ActionEvent Event) {
        stage = (Stage) scenePane.getScene().getWindow();
        System.out.println("you logged out");
        stage.close();
    }
}
