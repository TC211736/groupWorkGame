package com.example.groupworkgame;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Pair;
import java.io.IOException;

public class Game extends Application {

    Player player = new Player(false, 0, 0, false);

    int counter;
    int score;

    public void initialiseArrayLists(Parent root, GameController controller){

        //initialise background arraylist
        for (int i = 0; i < root.getChildrenUnmodifiable().size(); i++) {
            if (root.getChildrenUnmodifiable().get(i).getId().contains("background")) {
                controller.backgrounds.add((ImageView) root.getChildrenUnmodifiable().get(i));
            }
        }

        //initialise objects arraylist
        for (int i = 0; i < root.getChildrenUnmodifiable().size(); i++) {
            if (root.getChildrenUnmodifiable().get(i).getId().contains("obj")) {
                controller.objects.add((ImageView) root.getChildrenUnmodifiable().get(i));
            }
        }
    }


    @Override
    public void start(Stage stage) throws IOException {

        //initialise stage & objects
        FXMLLoader fxmlLoader = new FXMLLoader(Game.class.getResource("game-scene.fxml"));
        Parent root = fxmlLoader.load();
        GameController controller = fxmlLoader.getController();
        Scene scene = new Scene(root);
        stage.setFullScreen(true);
        scene.setOnMousePressed(mouseEvent -> player.setUp(mouseEvent.isPrimaryButtonDown()));
        scene.setOnMouseReleased(mouseEvent -> player.setUp(false));
        stage.setScene(scene);
        initialiseArrayLists(root, controller);
        controller.initialiseObjects();
        stage.show();

        //game loop
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!player.isDead) {
                    counter = updateCounter(counter);
                    updateEntities();
                    if (controller.checkCollisions()) {
                        System.out.println("you got: " + score);
                        player.setDead(true);
                    }
                }
            }

            //updates the counter variable
            public int updateCounter(int counter){
                score++;
                switch (counter) {

                    //creates an object at the top of the screen every 100 counter ticks, offset by 50 ticks
                    case 25 -> controller.createTopObject();

                    //creates an object at the bottom of screen, resets counter when it = 100
                    case 50 -> {
                        controller.createBottomObject();
                        counter = 0;
                    }
                }

                //increments counter
                counter++;
                return counter;
            }

            //updates all the entities on screen
            public void updateEntities(){
                controller.cycleBackground();
                controller.moveObjects();
                Pair<Float, Float> newPlayerPos = controller.updatePlayerPos(player.isUp(), player.getVelY(), player.getRotY());
                player.setVelY(newPlayerPos.getKey());
                player.setRotY(newPlayerPos.getValue());
            }

        };
        timer.start();
    }
    public static void main(String[] args) {
        launch();
    }
}