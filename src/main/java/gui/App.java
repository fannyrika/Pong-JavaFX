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
import javafx.scene.paint.Color;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.control.Button;
import java.awt.Dimension;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


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

        root.getChildren().add(music.mV);




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
        //Créer un Pane() pour la page d'instruction
        var rulesroot = new VBox();
        //Créer une Scene() pour la page d'instruction, avec les dimensions de l'écran
        var rules = new Scene(rulesroot,width,height);
        // Pour la personnalisation en css
        menuRoot.setId("menu");
        menuScene.getStylesheets().addAll(this.getClass().getResource("fond.css").toExternalForm());
        Button jouer = new Button("JOUER");
        jouer.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
              primaryStage.setScene(gameScene);
              gameView.animate(); }});
        
        Button retour = new Button("Retour");
        retour.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
              primaryStage.setScene(menuScene);
               }});
        
        Button instructions = new Button("Instructions");
        instructions.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
            	primaryStage.setScene(rules);
            	File bfile = new File("spacebackground.png");
            	String localUrl2 = bfile.toURI().toString();
            	Image img = new Image(localUrl2);
            	BackgroundImage bImg = new BackgroundImage(img,
            											   BackgroundRepeat.NO_REPEAT,
            										       BackgroundRepeat.NO_REPEAT,
            											   BackgroundPosition.DEFAULT,
            											   BackgroundSize.DEFAULT);
            	Background bGround = new Background(bImg);
            	rulesroot.setBackground(bGround);
            	Text regles = new Text("Regles du jeu : \n");
            	regles.setFont(Font.font(50));
            	regles.setFill(Color.WHITE);
                Text message = new Text("- Obtenir 3 points pour gagner la partie \n");
                message.setFont(Font.font(40));
            	message.setFill(Color.WHITE);
                rulesroot.getChildren().addAll(regles,message,retour);
                rulesroot.setAlignment(Pos.CENTER);
                
               
            	}});
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
        retour.setId("button");
        nom.setId("label1");
        titre.setId("label2");
        menuRoot.getChildren().addAll(nom, titre, jouer, instructions, options, quitter);
        menuRoot.setAlignment(Pos.CENTER);
        
        root.getChildren().addAll(retour);
        

        primaryStage.setScene(menuScene);
        primaryStage.show();
        //gameView2.animateBot();//test Bot;
    }

}
