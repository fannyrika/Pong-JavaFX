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
import java.awt.Dimension;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;


public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws MalformedURLException {

      var root = new Pane();
      var gameScene = new Scene(root);
      var music = new Music();

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
        root.getChildren().add(music.mV);*/




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

        // Récupérer les dimensions de l'écran
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        // Créer un Pane() pour le menu
        var menuRoot = new VBox();
        // Créer une Scene() pour le menu, avec les dimensions de l'écran
        var menuScene = new Scene(menuRoot, width, height);
        // Pour la personnalisation en css
        menuRoot.setId("menu");
        menuScene.getStylesheets().addAll(this.getClass().getResource("fond.css").toExternalForm());
        Button jouer = new Button("JOUER");
        jouer.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
              primaryStage.setScene(gameScene);
              gameView.animate(); }});

        Button instructions = new Button("Instructions");
        instructions.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
            /* -----------A AJOUTER-------------- */ }});

        Button options = new Button("Options/Mode");
        options.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
            /* ---------A AJOUTER------------- */ }});

        Button quitter = new Button("Quitter");
        quitter.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
              primaryStage.close(); }});

        Label nom = new Label("nootynootnoot");
        Label titre = new Label("PONG\n\n");
        jouer.setId("boutons");
        instructions.setId("boutons");
        options.setId("boutons");
        quitter.setId("boutons");
        nom.setId("label1");
        titre.setId("label2");
        menuRoot.getChildren().addAll(nom, titre, jouer, instructions, options, quitter);
        menuRoot.setAlignment(Pos.CENTER);

        primaryStage.setScene(menuScene);
        primaryStage.show();
        //gameView2.animateBot();//test Bot;
    }

}
