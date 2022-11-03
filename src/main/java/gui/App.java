package gui;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.*;
import model.RacketController;
import javafx.stage.StageStyle;
import java.io.File;
import java.net.MalformedURLException;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import javafx.scene.control.Button;


public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws MalformedURLException {

      var root = new Pane();
      var gameScene = new Scene(root);

        //add an icon for the window
        File file = new File("pongicon.png");
        String localUrl = file.toURI().toURL().toString();
        Image image = new Image(localUrl);


        // ajout d'une image de fond
        root.setId("terrain");
        gameScene.getStylesheets().addAll(this.getClass().getResource("fond.css").toExternalForm());

      /*  Media media = new Media(new File("pongmusic.mp3").toURI().toString());
        MediaPlayer mP = new MediaPlayer(media);
        mP.setCycleCount(mP.INDEFINITE);
        mP.setAutoPlay(true);

        MediaView mV = new MediaView(mP);
        root.getChildren().add(mV);*/




        class Player implements RacketController {
            State state = State.IDLE;

            @Override
            public State getState() {
                return state;
            }
        }
        var playerA = new Player();
        var playerB = new Player();
        gameScene.setOnKeyPressed(ev -> {
            switch (ev.getCode()) {
                case Z:
                    playerA.state = RacketController.State.GOING_UP;
                    break;
                case S:
                    playerA.state = RacketController.State.GOING_DOWN;
                    break;
                case UP:
                    playerB.state = RacketController.State.GOING_UP;
                    break;
                case DOWN:
                    playerB.state = RacketController.State.GOING_DOWN;
                    break;
            }
        });
        gameScene.setOnKeyReleased(ev -> {
            switch (ev.getCode()) {
                case Z:
                    if (playerA.state == RacketController.State.GOING_UP) playerA.state = RacketController.State.IDLE;
                    break;
                case S:
                    if (playerA.state == RacketController.State.GOING_DOWN) playerA.state = RacketController.State.IDLE;
                    break;
                case UP:
                    if (playerB.state == RacketController.State.GOING_UP) playerB.state = RacketController.State.IDLE;
                    break;
                case DOWN:
                    if (playerB.state == RacketController.State.GOING_DOWN) playerB.state = RacketController.State.IDLE;
                    break;
            }
        });
        var bot = new Bot(playerA,1,1.0001);//test bot;
        var court = new Court(playerA,playerB,1.0001);
        var gameView = new GameView(court, root, 1.0);
        //var gameView2 = new GameView(bot, root, 1.0);//test Bot;
        primaryStage.setTitle("Pong");
        primaryStage.getIcons().add(image);

        var menuRoot = new Pane();
        var menuScene = new Scene(menuRoot);
        //menuScene.setOpacity(0.5f);
        Button jouer = new Button("Start");
        menuRoot.getChildren().addAll(jouer);

        primaryStage.setScene(gameScene);
        primaryStage.show();
        gameView.animate();
        //gameView2.animateBot();//test Bot;
    }

}
