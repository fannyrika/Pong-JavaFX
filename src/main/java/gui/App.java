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

			//add an icon for the window
			File file = new File("pongicon.png");
			String localUrl = file.toURI().toURL().toString();
			Image image = new Image(localUrl);

			// Fenêtre de jeu
			var root = new Pane();
			var gameScene = new Scene(root);
			Button pause = new Button("pause");
			root.getChildren().addAll(pause);
			var rootPause = new VBox();

							// Récupérer les dimensions de l'écran
							Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
							double width = screenSize.getWidth();
							double height = screenSize.getHeight();

			// On crée la scene()
			var pauseScene = new Scene(rootPause, width, height);
			// On lui applique le css
			rootPause.setId("pause");
			pauseScene.getStylesheets().addAll(this.getClass().getResource("fond.css").toExternalForm());
			// On y met le bouton 'pause'
			pause.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
					primaryStage.setScene(pauseScene); }});


        // ajout d'une image de fond
        root.setId("terrain");
        gameScene.getStylesheets().addAll(this.getClass().getResource("fond.css").toExternalForm());

    //    root.getChildren().add(music.mV);




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
        primaryStage.setTitle("nootynootnoot PONG");
        primaryStage.getIcons().add(image);


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

        Button retour = new Button("Retour au menu");
        retour.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
              primaryStage.setScene(menuScene);
               }});

        Button instructions = new Button("Instructions");
        instructions.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
            	primaryStage.setScene(rules);
							rulesroot.setId("regles");
							rules.getStylesheets().addAll(this.getClass().getResource("fond.css").toExternalForm());
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
			var optionsRoot = new Pane();
			var optionsScene = new Scene(optionsRoot, width, height);
			optionsRoot.setId("options");
			optionsScene.getStylesheets().addAll(this.getClass().getResource("fond.css").toExternalForm());
		  Button retourO = new Button("Retour au menu");
			retourO.setId("boutons");
			retourO.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
			                primaryStage.setScene(menuScene); }});
			optionsRoot.getChildren().addAll(retourO);
			options.setOnAction(new EventHandler<ActionEvent>() {
			    public void handle(ActionEvent event) {
			            primaryStage.setScene(optionsScene); }});

			// On crée les boutons de la page 'pause'
			Button reprendre = new Button("Reprendre le jeu");
			reprendre.setOnAction(new EventHandler<ActionEvent>() {
			    public void handle(ActionEvent event) {
						primaryStage.setScene(gameScene); }});

			Button retourP = new Button("Retour au menu");
			retourP.setOnAction(new EventHandler<ActionEvent>() {
				  public void handle(ActionEvent event) {
							primaryStage.setScene(menuScene); }});

			Button son = new Button("Son et musiques");

			Button bye = new Button("Quitter le jeu");
			bye.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
							primaryStage.close(); }});

									rootPause.getChildren().addAll(reprendre, retourP, son, bye);
									rootPause.setAlignment(Pos.CENTER);
									reprendre.setId("boutons");
									retourP.setId("boutons");
									son.setId("boutons");
									bye.setId("boutons");

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


        primaryStage.setScene(menuScene);
        primaryStage.show();
        //gameView2.animateBot();//test Bot;
    }

}
