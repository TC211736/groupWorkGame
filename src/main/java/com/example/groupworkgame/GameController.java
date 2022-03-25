package com.example.groupworkgame;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Random;

public class GameController {
    @FXML
    private ImageView background;
    @FXML
    private ImageView background2;
    @FXML
    private ImageView playerSprite;
    @FXML
    private AnchorPane scene;
    @FXML
    public ArrayList<ImageView> objects = new ArrayList<>();
    @FXML
    public ArrayList<ImageView> backgrounds = new ArrayList<>();
    
    public double playerY;
    public float gameSpeed = 15;
    public int prevHeight = 0;
    public Random random = new Random();


    //put objects in correct positions at the start of the program
    public void initialiseObjects() {
        playerSprite.setX(900);
        for (ImageView object : objects) {
            object.setX(-500);
        }
        background.setX(0);
        background2.setX(1919);
    }


    //player movement logic
    public void movePlayer(float velY, float rotY) {
        playerY -= velY;
        playerSprite.setY(playerY);
        playerSprite.setRotate(rotY);

    }

    public Pair<Float, Float> updatePlayerPos(boolean up, float velY, float rotY) {

        if (playerY - 2 * velY >= 0 && playerY - 2 * velY < 1020) {
            if (up && velY + 1.1 < 12) velY += 1.1;
            if (!up && velY - 0.6 > -10) velY -= 0.6;
            rotY = 4 * -velY;
        }
        if (playerY - 2 * velY < 0) {
            velY = 0;

        }
        if (playerY - 2 * velY > 1020) {
            velY = 0;
        }
        movePlayer(velY, rotY);

        return new Pair<>(velY, rotY);
    }


    //move objects across the screen
    public void moveObjects() {
        for (ImageView object : objects) {
            if (isOnScreen(object, scene)) {
                object.setX(object.getX() - gameSpeed);
            }
        }
    }

    //makes sure background is visible and cycles them
    public void cycleBackground() {
        for (ImageView imageView : backgrounds) {
            if (!isOnScreen(imageView, scene)) {
                imageView.setX(1919);
            } else {
                imageView.setX(imageView.getX() - gameSpeed/1.5);
            }
        }
    }


    //code used to create new objects at the top or bottom of screen
    public void createTopObject() {
        for (ImageView object : objects) {
            if (!isOnScreen(object, scene)) {
                object.setX(1919);
                object.setY(-700+prevHeight);
                object.setRotate(0);
                return;
            }
        }
    }


    public void createBottomObject() {
        for (ImageView object : objects) {
            if (!isOnScreen(object, scene)) {
                prevHeight = (random.nextInt(300)-150);
                object.setX(1919);
                object.setY(scene.getPrefHeight()-object.getFitHeight()+600+prevHeight);
                object.setRotate(180);
                return;
            }
        }
    }


    //checks if player is colliding with any objects
    public boolean checkCollisions() {
        for (ImageView object : objects) {
            if (isOnScreen(object, scene)) {
                if (collidesWith(playerSprite, object)) {
                    return true;
                }
            }
        }
        return false;
    }


    //collision logic

    //checks object on object collision
    public boolean collidesWith(ImageView one, ImageView two) {
        return one.getX() < two.getX() + two.getFitWidth() &&
                one.getX() + one.getFitWidth() > two.getX() &&
                one.getY() < two.getY() + two.getFitHeight() &&
                one.getFitHeight() + one.getY() > two.getY();
    }

    //checks if an object is on screen
    public boolean isOnScreen(ImageView one, AnchorPane two) {
        return one.getX() < two.getPrefWidth() &&
                one.getX() + one.getFitWidth() > 0 &&
                one.getY() < two.getPrefHeight() &&
                one.getFitHeight() + one.getY() > 0;
    }


}


