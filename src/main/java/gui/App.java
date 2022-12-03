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
import javafx.scene.input.KeyCode;
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

import javafx.scene.control.TextFormatter;
import javafx.util.converter.IntegerStringConverter;
import java.util.regex.Pattern;
import java.awt.Dimension;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.awt.*;
import java.awt.Dimension;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.beans.*;
import javafx.scene.paint.ImagePattern;
import java.awt.event.KeyEvent;
import javafx.scene.control.Slider;


public class App extends Application {



    @Override
    public void start(Stage primaryStage) throws MalformedURLException {

    	//ajouter une icone pour la fenêtre
		File file = new File("pongicon.png");
		String localUrl = file.toURI().toURL().toString();
		Image image = new Image(localUrl);

		//Récupérer les dimensions de l'écran
		Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();

		//Fenêtre de jeu
		var root = new Pane();
		var gameScene = new Scene(root, width, height);

		class Player implements RacketController,BallState {
            State state = State.IDLE;
            StateBall stateb=StateBall.IDLE;
            @Override
            public State getState() {
                return state;
            }
            public StateBall getStateb() {
            	return stateb;
        	}
        }

        var playerA = new Player();
        var playerB = new Player();


        var bot = new Bot(playerA,1,1.0001);//test bot;
        var court = new Court(playerA,playerB,1.0001,playerA,playerB);
        var gameView = new GameView(court, root, 1.0);
        var gameView2 = new GameView(bot, root, 1.0);//test Bot;
        primaryStage.setTitle("nootynootnoot PONG");
        primaryStage.getIcons().add(image);


        //variables nécessaire dans le reste de la classe
        //Il y a deux boutons quitter en tout "quitterpause" et "quittermenu"
        var nom = new Label("nootynootnoot");
        nom.setFont(new Font("Arial",20));
        nom.setTextFill(Color.BLACK);
        var titre = new Label("PONG\n\n");
        titre.setFont(new Font("Arial",80));
        titre.setTextFill(Color.BLACK);
        var jouer = new Label("_Jouer\n");
        jouer.setMnemonicParsing(true);
        jouer.setId("tennismnemonic");
        jouer.setFont(new Font("Arial",40));
        jouer.setTextFill(Color.BLACK);
        var instructions = new Label("_Instructions\n");
        instructions.setMnemonicParsing(true);
        instructions.setId("tennismnemonic");
        instructions.setFont(new Font("Arial",40));
        instructions.setTextFill(Color.BLACK);
        var options = new Label("_Options\n");
        options.setMnemonicParsing(true);
        options.setId("tennismnemonic");
        options.setFont(new Font("Arial",40));
        options.setTextFill(Color.BLACK);

      //ce bouton quitter est celui que se trouve dans la page menu
        var quittermenu = new Label("_Quitter");
        quittermenu.setMnemonicParsing(true);
        quittermenu.setId("tennismnemonic");
        quittermenu.setFont(new Font("Arial",40));
        quittermenu.setTextFill(Color.BLACK);

        var rappel = new Text("\n\n(N'oubliez pas que seule les touches du clavier sont utilisables.)");
        rappel.setFont(new Font("Arial",20));
        rappel.setFill(Color.BLACK);


      //Création du menu
        var bvnRoot = new VBox();
        var bvnScene = new Scene(bvnRoot, width, height);
        var bvn = new Text("Bienvenu sur Pong !\n");
        bvn.setFont(new Font("Arail",50));
        bvn.setFill(Color.BLACK);
        var msg = new Text("L'application s'utilise exclusivement au clavier.\n\nChaque premi\u00e8re lettre soulign\u00e9e "
        		+ "repr\u00e9sente la touche \u00e0 utiliser pour valider l'action.\n\n"
        		+ "Vous pouvez quitter l'application \u00e0 tout moment avec la touche '\u00c9chap'.\n\n"
        		+ "(Presser entr\u00e9e pour continuer)");
        msg.setFont(new Font("Arial",30));
        msg.setFill(Color.BLACK);
        bvnRoot.setId("mode");
        bvnScene.getStylesheets().addAll(this.getClass().getResource("fond.css").toExternalForm());
        bvnRoot.getChildren().addAll(bvn,msg);//ajout des labels
        bvnRoot.setAlignment(Pos.CENTER);


      //Création du menu
        var menuRoot = new VBox();
        var menuScene = new Scene(menuRoot, width, height);
        menuRoot.setId("menu");
        menuScene.getStylesheets().addAll(this.getClass().getResource("fond.css").toExternalForm());
        menuRoot.getChildren().addAll(nom,titre,jouer,instructions,options,quittermenu,rappel);//ajout des labels
        menuRoot.setAlignment(Pos.CENTER);

      //Page des modes
        var modeRoot = new VBox();
   	   	var modeScene = new Scene(modeRoot, width, height);
        modeRoot.setSpacing(20.0);
        modeRoot.setId("mode");
        modeScene.getStylesheets().addAll(this.getClass().getResource("fond.css").toExternalForm());
        var tmp = new HBox();
        tmp.setSpacing(15.0);
        Label choix = new Label("\n\nChoisissez la difficult\u00e9 du bot :");
        choix.setFont(new Font("Arial",30));
        choix.setTextFill(Color.BLACK);
        choix.setId("choix");
        var settingRoot=new VBox();
        var settingScene=new Scene(settingRoot,width, height);
        settingRoot.setId("mode");
        settingScene.getStylesheets().addAll(this.getClass().getResource("fond.css").toExternalForm());
        Label setting = new Label("_Param\u00e8tres");
        setting.setMnemonicParsing(true);
        setting.setId("tennismnemonic");
        setting.setFont(new Font("Arial",30));
        setting.setTextFill(Color.BLACK);
        //Page qui explique au(x) joueur(s) comment déplacer les raquettes avant la partie
        //Il y a 5 fonctions. Une pour chaque niveau de jeu.
        //Pour le mode 1v1
        var instruroot = new VBox();
        var instruScene = new Scene(instruroot,width,height);
        var touche = new Text("Joueur de gauche :\n -D\u00e9placement haut : 'Z'\n -D\u00e9placement bas : 'S'\n\nJoueur de droite :\n"
        		+ "-D\u00e9placement haut : fl\u00e8che du haut\n-D\u00e9placement bas : fl\u00e8che du bas"
        		+"\n\n (Presser entr\u00e9e pour continuer ou 'R' pour revenir en arri\u00e8re)");
        touche.setFont(new Font("Arial", 50));
        touche.setFill(Color.BLACK);
        instruroot.setId("mode");
        instruScene.setOnKeyPressed(ev -> {
            switch (ev.getCode()) {
                case ENTER:
                	gameView.start(true);
              		gameView.addRoot();
              		gameView.animate();
              		primaryStage.setScene(gameScene);
                    break;
                case R:
                	primaryStage.setScene(modeScene);
                	break;
                case ESCAPE:
                	primaryStage.close();
                	break;
                }});

        instruScene.getStylesheets().addAll(this.getClass().getResource("fond.css").toExternalForm());
    	instruroot.getChildren().addAll(touche);
    	instruroot.setAlignment(Pos.CENTER);

    	//Pour le mode facile
    	var instrurootF = new VBox();
        var instruSceneF = new Scene(instrurootF,width,height);
        var toucheF = new Text("Vous \u00eates le joueur de gauche :\n\n -D\u00e9placement haut : 'Z'\n -D\u00e9placement bas : 'S'\n\n"
        		+ "(Presser entr\u00e9e pour continuer ou 'R' pour revenir en arri\u00e8re)");
        toucheF.setFont(new Font("Arial", 50));
        toucheF.setFill(Color.BLACK);
        instrurootF.setId("mode");
        instruSceneF.setOnKeyPressed(ev -> {
            switch (ev.getCode()) {
                case ENTER:
                	bot.setDifficulty(0);
         	  	   	gameView2.start(true);
         	  	   	gameView2.addRootBot();
         	  	   	gameView2.animateBot();
         	  	   	primaryStage.setScene(gameScene);
         	  	   	modeRoot.getChildren().removeAll(tmp,choix);
                    break;
                case R:
                	primaryStage.setScene(modeScene);
                	break;
                case ESCAPE:
                	primaryStage.close();
                	break;
                }});

        instruSceneF.getStylesheets().addAll(this.getClass().getResource("fond.css").toExternalForm());
    	instrurootF.getChildren().addAll(toucheF);
    	instrurootF.setAlignment(Pos.CENTER);

    	//Pour le mode moyen
    	var instrurootM = new VBox();
        var instruSceneM = new Scene(instrurootM,width,height);
        var toucheM = new Text("Vous \u00eates le joueur de gauche :\n\n -D\u00e9placement haut : 'Z'\n -D\u00e9placement bas : 'S'\n\n"
        		+ "(Presser entr\u00e9e pour continuer ou 'R' pour revenir en arri\u00e8re)");
        toucheM.setFont(new Font("Arial", 50));
        toucheM.setFill(Color.BLACK);
        instrurootM.setId("mode");
        instruSceneM.setOnKeyPressed(ev -> {
            switch (ev.getCode()) {
                case ENTER:
                	bot.setDifficulty(1);
         	  	   	gameView2.start(true);
         	  	   	gameView2.addRootBot();
         	  	   	gameView2.animateBot();
         	  	   	primaryStage.setScene(gameScene);
         	  	   	modeRoot.getChildren().removeAll(tmp,choix);
                    break;
                case R:
                	primaryStage.setScene(modeScene);
                	break;
                case ESCAPE:
                	primaryStage.close();
                	break;
                }});

        instruSceneM.getStylesheets().addAll(this.getClass().getResource("fond.css").toExternalForm());
    	instrurootM.getChildren().addAll(toucheM);
    	instrurootM.setAlignment(Pos.CENTER);

    	//Pour le mode difficile
    	var instrurootD = new VBox();
        var instruSceneD = new Scene(instrurootD,width,height);
        var toucheD = new Text("Vous \u00eates le joueur de gauche :\n\n -D\u00e9placement haut : 'Z'\n -D\u00e9placement bas : 'S'\n\n"
        		+ "(Presser entr\u00e9e pour continuer ou 'R' pour revenir en arri\\u00e8re v)");
        toucheD.setFont(new Font("Arial", 50));
        toucheD.setFill(Color.BLACK);
        instrurootD.setId("mode");
        instruSceneD.setOnKeyPressed(ev -> {
            switch (ev.getCode()) {
                case ENTER:
                	bot.setDifficulty(2);
         	  	   	gameView2.start(true);
         	  	   	gameView2.addRootBot();
         	  	   	gameView2.animateBot();
         	  	   	primaryStage.setScene(gameScene);
         	  	   	modeRoot.getChildren().removeAll(tmp,choix);
                    break;
                case R:
                	primaryStage.setScene(modeScene);
                	break;
                case ESCAPE:
                	primaryStage.close();
                	break;
                }});

        instruSceneD.getStylesheets().addAll(this.getClass().getResource("fond.css").toExternalForm());
    	instrurootD.getChildren().addAll(toucheD);
    	instrurootD.setAlignment(Pos.CENTER);

    	//Pour le mode expert
    	var instrurootE = new VBox();
        var instruSceneE = new Scene(instrurootE,width,height);
        var toucheE = new Text("Vous \u00eates le joueur de gauche :\n\n -D\u00e9placement haut : 'Z'\n -D\u00e9placement bas : 'S'\n\n"
        		+ "(Presser entr\u00e9e pour continuer ou 'R' pour revenir en arri\u00e8re)");
        toucheE.setFont(new Font("Arial", 50));
        toucheE.setFill(Color.BLACK);
        instrurootE.setId("mode");
        instruSceneE.setOnKeyPressed(ev -> {
            switch (ev.getCode()) {
                case ENTER:
                	bot.setDifficulty(3);
         	  	   	gameView2.start(true);
         	  	   	gameView2.addRootBot();
         	  	   	gameView2.animateBot();
         	  	   	primaryStage.setScene(gameScene);
         	  	   	modeRoot.getChildren().removeAll(tmp,choix);
                    break;
                case R:
                	primaryStage.setScene(modeScene);
                	break;
                case ESCAPE:
                	primaryStage.close();
                	break;
                }});

        instruSceneE.getStylesheets().addAll(this.getClass().getResource("fond.css").toExternalForm());
    	instrurootE.getChildren().addAll(toucheE);
    	instrurootE.setAlignment(Pos.CENTER);

        //les variables de choix de jeu
        var vs = new Label("_Jouer en 1 vs 1");
        vs.setMnemonicParsing(true);
        vs.setId("tennismnemonic");
		vs.setFont(new Font("Arial",30));
		vs.setTextFill(Color.BLACK);
		var contreordi = new Label("_Contre l'ordi");
        contreordi.setMnemonicParsing(true);
        contreordi.setId("tennismnemonic");
		contreordi.setFont(new Font("Arial",30));
		contreordi.setTextFill(Color.BLACK);
		var facile = new Label("_Facile");
		facile.setMnemonicParsing(true);
		facile.setId("tennismnemonic");
		facile.setFont(new Font("Arial",30));
		facile.setTextFill(Color.BLACK);
		var moyen = new Label("_Moyen");
        moyen.setMnemonicParsing(true);
        moyen.setId("tennismnemonic");
		moyen.setFont(new Font("Arial",30));
		moyen.setTextFill(Color.BLACK);
		var difficile = new Label("_Difficile");
        difficile.setMnemonicParsing(true);
        difficile.setId("tennismnemonic");
		difficile.setFont(new Font("Arial",30));
		difficile.setTextFill(Color.BLACK);
		var expert = new Label("_Expert");
        expert.setMnemonicParsing(true);
        expert.setId("tennismnemonic");
		expert.setFont(new Font("Arial",30));
		expert.setTextFill(Color.BLACK);

		//Une touche pour chaque action dans la page des modes
		modeScene.setOnKeyPressed(ev -> {
            switch (ev.getCode()) {
            	case P: 
            		primaryStage.setScene(settingScene);
            		break;
                case J:
              		modeRoot.getChildren().removeAll(tmp,choix);
              		primaryStage.setScene(instruScene);
                    break;
                case C:
                	modeRoot.getChildren().removeAll(choix, tmp);
                    modeRoot.getChildren().addAll(choix, tmp);
                    if(tmp.getChildren().indexOf(facile)==-1) {
                    tmp.getChildren().addAll(facile,moyen,difficile,expert);
                    }
                    tmp.setAlignment(Pos.CENTER);
                    break;
                case F:
                	modeRoot.getChildren().removeAll(tmp,choix);
              		primaryStage.setScene(instruSceneF);
            		break;
                case M:
                	modeRoot.getChildren().removeAll(tmp,choix);
              		primaryStage.setScene(instruSceneM);
         		   	break;
                case D:
                	modeRoot.getChildren().removeAll(tmp,choix);
              		primaryStage.setScene(instruSceneD);
         	  	   	break;
                case E:
                	modeRoot.getChildren().removeAll(tmp,choix);
              		primaryStage.setScene(instruSceneE);
                    break;
                case R:
                	primaryStage.setScene(menuScene);
                	break;
                case ESCAPE:
                	primaryStage.close();
                	break;
                }});



		//Fenêtre pause
		var rootPause = new VBox();
		var pauseScene = new Scene(rootPause, width, height);
		rootPause.setId("pause");

		//d'autres variables utiles
		var reprendre = new Label("_Reprendre");
        reprendre.setMnemonicParsing(true);
        reprendre.setId("tennismnemonic");
        reprendre.setFont(new Font("Arial",30));
        reprendre.setTextFill(Color.BLACK);
        var retour = new Label("_Retour");
        retour.setMnemonicParsing(true);
        retour.setId("tennismnemonic");
        retour.setFont(new Font("Arial",30));
        retour.setTextFill(Color.BLACK);
        var retourmenu = new Label("_Aller au menu");
        retourmenu.setMnemonicParsing(true);
        retourmenu.setId("tennismnemonic");
        retourmenu.setFont(new Font("Arial",30));
        retourmenu.setTextFill(Color.BLACK);
        var nouvellepartie = new Label("_Nouvelle Partie");
        nouvellepartie.setMnemonicParsing(true);
        nouvellepartie.setId("tennismnemonic");
        nouvellepartie.setFont(new Font("Arial",30));
        nouvellepartie.setTextFill(Color.BLACK);
        var musique = new Label("_Musique");
        musique.setMnemonicParsing(true);
        musique.setId("tennismnemonic");
        musique.setFont(new Font("Arial",30));
        musique.setTextFill(Color.BLACK);

       //ce bouton quitter est celui que se trouve dans la page pause
        var quitterpause = new Label("_Quitter");
        quitterpause.setMnemonicParsing(true);
        quitterpause.setId("tennismnemonic");
        quitterpause.setFont(new Font("Arial",30));
        quitterpause.setTextFill(Color.BLACK);

		pauseScene.getStylesheets().addAll(this.getClass().getResource("fond.css").toExternalForm());
		//ajout des variables à la page pause
		rootPause.getChildren().addAll(reprendre,retourmenu,nouvellepartie,musique,quitterpause);
		rootPause.setAlignment(Pos.CENTER);


        // ajout d'une image de fond
        root.setId("terrain");
        gameScene.getStylesheets().addAll(this.getClass().getResource("fond.css").toExternalForm());

        //music
        var music = new Music();
        root.getChildren().addAll(music.mV);

        //Création de la page de musique avec un slider
      var sonRoot = new VBox();
      sonRoot.setPadding(new Insets(height/2, 450,0, height/2));
      var sonScene = new Scene(sonRoot, width, height);
      var volumeSlider = new Slider(0.0, 100, 0.0);
      volumeSlider.setId("slider");
      volumeSlider.lookup(".track");
      volumeSlider.setValue(music.mP.getVolume() * 70);

      Button mutebis = new Button(" ");
        mutebis.setFocusTraversable(false);
        mutebis.setId("boutonMute2");

      Button mute = new Button(" ");
        mute.setFocusTraversable(false);
        mute.setId("boutonMute2");

      var volumeSliderO = new Slider(0.0, 100, 0.0);
      volumeSliderO.setId("slider");
      volumeSliderO.lookup(".track");
      volumeSliderO.setValue(music.mP.getVolume() * 70);
      volumeSliderO.valueProperty().addListener(new InvalidationListener() {
      public void invalidated(Observable observable) {
      music.mP.setVolume(volumeSliderO.getValue() / 100);
      if(volumeSliderO.getValue() == 0){ mute.setId("boutonMute1"); mutebis.setId("boutonMute1");}
      else{mute.setId("boutonMute2"); mutebis.setId("boutonMute2");}
      volumeSlider.setValue(volumeSliderO.getValue()); } });

      mute.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent event) {
        if(volumeSlider.getValue() == 0){ mute.setId("boutonMute2"); volumeSlider.setValue(50);  mutebis.setId("boutonMute2"); volumeSliderO.setValue(50);}
        else{ mute.setId("boutonMute1"); volumeSlider.setValue(0); mutebis.setId("boutonMute1"); volumeSliderO.setValue(0);} }});




        //Modifier le volume de la musique
        volumeSlider.valueProperty().addListener(new InvalidationListener() {
        public void invalidated(Observable observable) {
        music.mP.setVolume(volumeSlider.getValue() / 100);
        if(volumeSlider.getValue() == 0){ mute.setId("boutonMute1"); mutebis.setId("boutonMute1");}
        else{mute.setId("boutonMute2"); mutebis.setId("boutonMute2");}
        volumeSliderO.setValue(volumeSlider.getValue()); }});
        var volume = new Text("Volume");
        volume.setFont(Font.font(30));
		Button son = new Button("Musique et son");
		son.setFocusTraversable(false);
		son.setId("boutonsP");
		var fleche = new Label("(Pour r\u00e9gler le son utiliser les fl\u00e8ches droite et gauche)\n");
		fleche.setFont(new Font("Arial", 20));
		fleche.setTextFill(Color.BLACK);
		var retourpause = new Label("_Retour");
		retourpause.setMnemonicParsing(true);
		retourpause.setId("tennismnemonic");
		retourpause.setFont(new Font("Arial",30));
		retourpause.setTextFill(Color.BLACK);

		sonRoot.setId("menu");
		sonScene.getStylesheets().addAll(this.getClass().getResource("fond.css").toExternalForm());
		sonRoot.getChildren().addAll(mute,volume,volumeSlider,fleche,retourpause);
		sonRoot.setAlignment(Pos.TOP_CENTER);

		//la touche R sert à revenir en arrière lorsqu'on est dans la page musique
		sonScene.setOnKeyPressed(ev -> {
            switch (ev.getCode()) {
                case R:
                    primaryStage.setScene(pauseScene);
                    music.mP.pause();
                    break;
                case ESCAPE:
                	primaryStage.close();
                	break;
                }});


        //Page d'instructions dans le menu
        var rulesroot = new VBox();
        var rules = new Scene(rulesroot,width,height);
      	Text regles = new Text("R\u00e8gles du jeu : \n");
        regles.setFont(Font.font(35));
        regles.setFill(Color.GREEN);
        Text message = new Text("- Contr\u00f4ler la raquette gauche avec Z et S \n\n- Contr\u00f4ler la raquette droite avec UP et DOWN \n\n- Le but du jeu est d'envoyer la balle dans le camp adverse en mettant \nson adversaire dans l'incapacit\u00e9 de la renvoyer. \n\n- Obtenir 3 points pour gagner la partie \n\n-- Vous pouvez choisir un mode de jeu sp\u00e9cifique\nen d\u00e9but de partie : \nLa difficult\u00e9 de l'ordinateur repr\u00e9sente son efficacit\u00e9 \u00e0 rattraper les balles;\nplus c'est difficile, moins il y a de chance qu'il les rate !\n\n-- Un boost (repr\u00e9sent\u00e9 par un cercle rouge avec un \u00e9clair jaune) sera affich\u00e9 sur votre terrain\nen cours de partie; si vous l'attrapez, activez le avec les boutons CTRL ou E pour acc\u00e9lerer la balle\n\n(Presser 'R' pour revenir en arri\u00e8re)");
        message.setFont(Font.font(25));
        message.setFill(Color.DARKRED);
        rulesroot.setId("regles");
        rulesroot.getChildren().addAll(regles,message);
        rulesroot.setAlignment(Pos.CENTER);
      	rules.getStylesheets().addAll(this.getClass().getResource("fond.css").toExternalForm());

      //Page d'instructions dans la partie
        var rulesroot2 = new VBox();
        var rules2 = new Scene(rulesroot2,width,height);
      	Text regles2 = new Text("R\u00e8gles du jeu : \n");
        regles2.setFont(Font.font(35));
        regles2.setFill(Color.GREEN);
        var h = new Text("(Presser 'H' pour revenir en arri\u00e8re)");
        h.setFont(new Font("Arail",20));
        h.setFill(Color.BLACK);
        Text message2 = new Text("- Contr\u00f4ler la raquette gauche avec Z et S \n\n- Contr\u00f4ler la raquette droite avec UP et DOWN \n\n- Le but du jeu est d'envoyer la balle dans le camp adverse en mettant \nson adversaire dans l'incapacit\u00e9 de la renvoyer. \n\n- Obtenir 3 points pour gagner la partie \n\n-- Vous pouvez choisir un mode de jeu sp\u00e9cifique\nen d\u00e9but de partie : \nLa difficult\u00e9 de l'ordinateur repr\u00e9sente son efficacit\u00e9 \u00e0 rattraper les balles;\nplus c'est difficile, moins il y a de chance qu'il les rate !\n\n-- Un boost (repr\u00e9sent\u00e9 par un cercle rouge avec un \u00e9clair jaune) sera affich\u00e9 sur votre terrain\nen cours de partie; si vous l'attrapez, activez le avec les boutons CTRL ou E pour acc\u00e9lerer la balle\n");
        message2.setFont(Font.font(25));
        message2.setFill(Color.DARKRED);
        rulesroot2.setId("regles");
        rulesroot2.getChildren().addAll(regles2,message2,h);
        rulesroot2.setAlignment(Pos.CENTER);
      	rules2.getStylesheets().addAll(this.getClass().getResource("fond.css").toExternalForm());

		var optionsRoot = new VBox();

    mutebis.setOnAction(new EventHandler<ActionEvent>() {
    public void handle(ActionEvent event) {
      if(volumeSliderO.getValue() == 0){ mutebis.setId("boutonMute2"); volumeSliderO.setValue(50); mute.setId("boutonMute2"); volumeSlider.setValue(50);}
      else{ mutebis.setId("boutonMute1"); volumeSliderO.setValue(0); mute.setId("boutonMute1"); volumeSlider.setValue(0);} }});
      var volumeO = new Text("Volume :");
      volumeO.setFont(Font.font(30));
      var flecheO = new Label("(Pour r\u00e9gler le son utiliser les fl\u00e8ches droite et gauche)\n");
      flecheO.setFont(new Font("Arial", 20));
      flecheO.setTextFill(Color.BLACK);

		var optionsScene = new Scene(optionsRoot, width, height);
		var space = new Label("_Space");
        space.setMnemonicParsing(true);
        space.setId("tennismnemonic");
        space.setFont(new Font("Arial",40));
        space.setTextFill(Color.BLACK);
        var tennis = new Label("_Tennis");
        tennis.setMnemonicParsing(true);
        tennis.setId("tennismnemonic");
        tennis.setFont(new Font("Arial",40));
        tennis.setTextFill(Color.BLACK);
        var neon = new Label("_N\u00e9on");
        neon.setMnemonicParsing(true);
        neon.setId("tennismnemonic");
        neon.setFont(new Font("Arial",40));
        neon.setTextFill(Color.BLACK);
		optionsRoot.setId("menu");
		optionsScene.getStylesheets().addAll(this.getClass().getResource("fond.css").toExternalForm());
    var optionson = new VBox();
    optionson.setPadding(new Insets(150, 450,50, height/2));
    var optiontheme = new VBox(10);
    var theme = new Label("Th\u00e8mes :\n");
    theme.setFont(new Font("Arial",50));
    theme.setTextFill(Color.BLACK);
    var msq = new Label("Musique :\n");
    msq.setFont(new Font("Arial",50));
    msq.setTextFill(Color.BLACK);
    var mutetext = new Label("Mute :");
    mutetext.setFont(new Font("Arial",30));
    mutetext.setTextFill(Color.BLACK);
    optiontheme.setAlignment(Pos.CENTER);
    var presserR = new Label("(Presser 'R' pour revenir en arri\u00e8re)");
    presserR.setFont(new Font("Arail",20));
    presserR.setTextFill(Color.BLACK);
    optiontheme.getChildren().addAll(theme,space,tennis,neon,presserR);
    optionson.getChildren().addAll(msq,mutetext,mutebis,volumeO,volumeSliderO,flecheO);
    optionson.setAlignment(Pos.TOP_CENTER);
    optionsRoot.getChildren().addAll(optionson,optiontheme);

		//ajout des variables à la page mode
		modeRoot.getChildren().addAll(presserR,vs,contreordi,setting);
        modeRoot.setAlignment(Pos.CENTER);


		bvnScene.setOnKeyPressed(ev -> {
            switch (ev.getCode()) {
                case ENTER:
                	primaryStage.setScene(menuScene);
                	break;
                case ESCAPE:
                	primaryStage.close();
                	break;
                }});

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
                case E:
                	playerA.stateb=BallState.StateBall.FAST;
                	break;
                case CONTROL:
                	playerB.stateb =BallState.StateBall.FAST;
                	break;
                case P:
			        GameView.pause=true;
			        music.mP.pause();
                    primaryStage.setScene(pauseScene);
                    break;
                case F :
                	if(!gameView.isStart()||!gameView2.isStart()) {
                		gameView.remove();
                		gameView2.remove();
                		primaryStage.setScene(menuScene);
                		
                	}
                case R:
                	
	      			break;
	        		
	            
                case M:
                	if(volumeSlider.getValue() == 0){ mute.setId("boutonMute2"); volumeSlider.setValue(50);}
                    else{ mute.setId("boutonMute1"); volumeSlider.setValue(0);}
                	break;
                case H:
                    GameView.pause=true;
                    primaryStage.setScene(rules2);
                    //rulesroot.getChildren().add(reprendre);
                    break;
                case ESCAPE:
                	primaryStage.close();
                	break;
                }});

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
                case E:
                    if (playerA.stateb == BallState.StateBall.FAST) playerA.stateb = BallState.StateBall.IDLE;
                    break;
              
                case CONTROL:
                    if (playerB.stateb == BallState.StateBall.FAST) playerB.stateb = BallState.StateBall.IDLE;
                    break;
                case ESCAPE:
                	primaryStage.close();
                	break;
            }
        });


        menuScene.setOnKeyPressed(ev -> {
            switch (ev.getCode()) {
              case J:
                  primaryStage.setScene(modeScene);
                  break;
              case I:
            	  primaryStage.setScene(rules);
                  break;
                case O:
                     primaryStage.setScene(optionsScene);
                  break;
                case Q:
                	primaryStage.close();
                 break;
                case M:
                	if(volumeSlider.getValue() == 0){ mute.setId("boutonMute2"); volumeSlider.setValue(50);}
                    else{ mute.setId("boutonMute1"); volumeSlider.setValue(0);}
                	break;
                case ESCAPE:
                	primaryStage.close();
                	break;
                 }});

        //une touche pour chaque action dans la page pause
        pauseScene.setOnKeyPressed(ev -> {
            switch (ev.getCode()) {
            	case R:

        			GameView.pause=false;
        			if(gameView.isStart()){
        			gameView.animate();
        			}
        			else {
        				gameView2.animateBot();
        			}
        			primaryStage.setScene(gameScene);
        			music.mP.play();
        			break;
            	case A:
            		GameView.stop=false;
            		GameView.pause=false;
            		gameView.remove();
            		gameView2.remove();
            		gameView.reset1V1();
            		gameView2.resetBot();
            		gameView.start(false);
            		gameView2.start(false);
                    primaryStage.setScene(menuScene);
                    music.mP.play();
                    break;
            	case N:
            		GameView.stop=false;
            		GameView.pause=false;
            		gameView.remove();
            		gameView2.remove();
            		gameView.reset1V1();
            		gameView2.resetBot();
            		gameView.start(false);
            		gameView2.start(false);
                    primaryStage.setScene(modeScene);
                    music.mP.play();
                    break;
                case P:
                  GameView.pause=false;
                  music.mP.play();
                  if(gameView.isStart()){
                  gameView.animate();
                  }
                  else {
                    gameView2.animateBot();
                  }
                  primaryStage.setScene(gameScene);
              	  break;
                case M:
                	primaryStage.setScene(sonScene);
                	music.mP.play();
                	break;
                case Q:
                	primaryStage.close();
                	break;
                case ESCAPE:
                	primaryStage.close();
                	break;
                }});

        //une touche R dans la page instructions du menu pour revenir en arrière
        rules.setOnKeyPressed(ev -> {
            switch (ev.getCode()) {
            	case R:
            		primaryStage.setScene(menuScene);
            		break;
            	case ESCAPE:
                	primaryStage.close();
                	break;
                }});

        //une touche H dans la page d'instruction de la partie pour revenir en arrière
        rules2.setOnKeyPressed(ev -> {
            switch (ev.getCode()) {
            	case H:
            		GameView.pause=false;
                    music.mP.play();
                    if(gameView.isStart()){
                    gameView.animate();
                    }
                    else {
                      gameView2.animateBot();
                    }
                    primaryStage.setScene(gameScene);
                	  break;
            	case ESCAPE:
                	primaryStage.close();
                	break;
                }});
        //Setting scene;
        Label instructionSet=new Label("Utiliser la touche 'espace' pour valider une option et la touche 'entr\u00e9' pour passer \u00e0 la suivante :");
        instructionSet.setTranslateY(-100);
        instructionSet.setFont(new Font("Arial",20));
        instructionSet.setTextFill(Color.BLACK);
        RadioButton boost=new RadioButton("Activer le boost (PRESS-ESPACE)");
        boost.setFont(new Font("Arial",20));
        boost.setTextFill(Color.BLACK);
        RadioButton timer=new RadioButton("Afficher le timer (PRESS-ESPACE)");
        timer.setFont(new Font("Arial",20));
        timer.setTextFill(Color.BLACK);
        GridPane gScore=new GridPane();
        Label scoreInst=new Label("Entrer un score au clavier (limite : 9999) : ");
        scoreInst.setFont(new Font("Arial",20));
        scoreInst.setTextFill(Color.BLACK);
        TextField score = new TextField("");
        gScore.addColumn(0,scoreInst);
        gScore.addColumn(1,score);
        gScore.setTranslateX(width/2-160);
        
        score.setPromptText("score");
        
        Pattern validText = Pattern.compile("[1-9][0-9]{0,3}|[1-9]?");

        TextFormatter<Integer> textFormatter = new TextFormatter<Integer>(new IntegerStringConverter(),null, 
            change -> {
                String newText = change.getControlNewText() ;
                if (validText.matcher(newText).matches()) {
                    return change ;
                } else return null ;
            });

        score.setTextFormatter(textFormatter);
        score.setMaxWidth(50);
        ComboBox<Integer> timeLimitM=new ComboBox<Integer>();
		ComboBox<Integer> timeLimitS=new ComboBox<Integer>();
		Label indication=new Label("Parametrer la dur\u00e9e de la partie (mm:ss) en utilisant UP et DOWN: ");
		indication.setFont(new Font("Arial",20));
		indication.setTextFill(Color.BLACK);
		GridPane gTime=new GridPane();
		gTime.addColumn(0,indication);
        gTime.addColumn(1, timeLimitM);
        gTime.addColumn(2, timeLimitS);
        gTime.setTranslateX(width/2-160);
		timeLimitS.setPromptText("ss");
        timeLimitM.setPromptText("mm");
		timeLimitM.setId("time-select");
		timeLimitS.setId("time-select");
		
		Label valider=new Label("_Valider");
		valider.setMnemonicParsing(true);
        valider.setId("tennismnemonic");
        valider.setFont(new Font("Arial",20));
        valider.setTextFill(Color.BLACK);
		timer.setOnKeyPressed(event -> {
            if(event.getCode().equals(KeyCode.ENTER)){
                boost.requestFocus();
            }
        });
		Label effacer=new Label("_Effacer");
		effacer.setMnemonicParsing(true);
        effacer.setId("tennismnemonic");
        effacer.setFont(new Font("Arial",20));
        effacer.setTextFill(Color.BLACK);
        
        Label retourmode =new Label("_Retour");
		retourmode.setMnemonicParsing(true);
        retourmode.setId("tennismnemonic");
        retourmode.setFont(new Font("Arial",20));
        retourmode.setTextFill(Color.BLACK);
        GridPane g2=new GridPane();
        g2.addRow(0, valider);
        g2.addRow(1, effacer);
        g2.addRow(2, retourmode);
        g2.setAlignment(Pos.CENTER);
		boost.setOnKeyPressed(event -> {
            if(event.getCode().equals(KeyCode.ENTER)){
               score.requestFocus();
            }
        });
		for(int i=0;i<60;i++) {
			timeLimitM.getItems().add(i);
			timeLimitS.getItems().add(i);
		}
	
		timeLimitM.setOnAction(event->{
			int x=timeLimitM.getSelectionModel().getSelectedItem().intValue();
		});
		
		Label ss=new Label();
		timeLimitS.setOnAction(event->{
			int x=timeLimitS.getSelectionModel().getSelectedItem().intValue();
			//mm.setText(x+"mm");
		});     
        Label erreurTime =new Label("Veuillez entrer les deux param\u00e9tre de temps");
  
        settingRoot.getChildren().addAll(instructionSet,timer,boost,gScore,gTime,g2);
        settingRoot.setAlignment(Pos.CENTER);
		score.setOnKeyPressed(event -> {
            if(event.getCode().equals(KeyCode.ENTER)){
                timeLimitM.requestFocus();
            }
            if(event.getCode().equals(KeyCode.V)){
            	if(timer.isSelected()) {
            		GameView.afficheTimer=true;
            	}
            	else {
            		GameView.afficheTimer=false;
            	}
            	if(boost.isSelected()) {
            		GameView.activeBoost=true;
            	}
            	else {
            		GameView.activeBoost=false;
            	}
            	if(score.toString()!=null) {
            		if(score.getText().matches("[0-9]+")) {
	                		court.setScoreLimit(Integer.parseInt(score.getText()));
	                		court.scoreLimit(true);
	                		bot.setScoreLimit(Integer.parseInt(score.getText()));
	                		bot.scoreLimit(true);
	                		
	            		}
	            	}
            	if(timeLimitM.getValue()!=null&&timeLimitS.getValue()!=null&&timeLimitM.getValue()+timeLimitS.getValue()!=0) {
	            		court.setTimeLimit(timeLimitM.getValue(),timeLimitS.getValue());
	            		court.limitTime(true);
	            		bot.setTimeLimit(timeLimitM.getValue(),timeLimitS.getValue());
	            		bot.limitTime(true);
	            		gameView.chronometer.setStart(timeLimitM.getValue()*6000+timeLimitS.getValue()*100);
	            		gameView2.chronometer.setStart(timeLimitM.getValue()*6000+timeLimitS.getValue()*100);
	            		Chrono.timeLimit=timeLimitM.getValue()*6000+timeLimitS.getValue()*100;
	            		settingRoot.getChildren().remove(erreurTime);
	          			primaryStage.setScene(modeScene);	
	            	}
	            	else if(timeLimitM.getValue()!=null&&timeLimitS.getValue()==null ||timeLimitM.getValue()==null&&timeLimitS.getValue()!=null) {
	            		settingRoot.getChildren().add(erreurTime);
	            		}
	            	else {
	            		primaryStage.setScene(modeScene);	
	            	}
            }
            
        });
		timeLimitM.setOnKeyPressed(event -> {
            if(event.getCode().equals(KeyCode.ENTER)){
                timeLimitS.requestFocus();
            }
        });
		timeLimitS.setOnKeyPressed(event -> {
            if(event.getCode().equals(KeyCode.ENTER)){
            	timer.requestFocus();
            }
        });
		
        settingScene.setOnKeyPressed(ev -> {
            switch (ev.getCode()) {
                case V:
                	if(timer.isSelected()) {
                		GameView.afficheTimer=true;
                	}
                	else {
                		GameView.afficheTimer=false;
                	}
                	if(boost.isSelected()) {
                		GameView.activeBoost=true;
                	}
                	else {
                		GameView.activeBoost=false;
                	}
                	if(score.toString()!=null) {
                		if(score.getText().matches("[0-9]+")) {
                    		court.setScoreLimit(Integer.parseInt(score.getText()));
                    		court.scoreLimit(true);
                    		bot.setScoreLimit(Integer.parseInt(score.getText()));
                    		bot.scoreLimit(true);
                		}
                		
                	}
                	if(timeLimitM.getValue()!=null&&timeLimitS.getValue()!=null&&timeLimitM.getValue()+timeLimitS.getValue()!=0) {
                		court.setTimeLimit(timeLimitM.getValue(),timeLimitS.getValue());
                		court.limitTime(true);
                		bot.setTimeLimit(timeLimitM.getValue(),timeLimitS.getValue());
                		bot.limitTime(true);
                		gameView.chronometer.setStart(timeLimitM.getValue()*6000+timeLimitS.getValue()*100);
                		gameView2.chronometer.setStart(timeLimitM.getValue()*6000+timeLimitS.getValue()*100);
                		Chrono.timeLimit=timeLimitM.getValue()*6000+timeLimitS.getValue()*100;
                		settingRoot.getChildren().remove(erreurTime);
            		
              			primaryStage.setScene(modeScene);	
                	}
                	else if(timeLimitM.getValue()!=null&&timeLimitS.getValue()==null ||timeLimitM.getValue()==null&&timeLimitS.getValue()!=null) {
                		settingRoot.getChildren().add(erreurTime);
                		}
                	else {
	            		primaryStage.setScene(modeScene);	
	            	}	
                    break;
                case E:
                	score.clear();
                	timer.setSelected(false);
                	boost.setSelected(false);
                	timeLimitM.setValue(0);
                	timeLimitS.setValue(0);
                    break;
                case R:
                	primaryStage.setScene(modeScene);
                	break;
                case ESCAPE:
                	primaryStage.close();
                	break;
                }});
        optionsScene.setOnKeyPressed(ev -> {
            switch (ev.getCode()) {
                //Thème espace
            	case S:
            		GameView.style="spacebg";
            		GameView.styleBM="spaceMB";
            		menuRoot.setId("spacebg");
                    modeRoot.setId("spacemode");
                    instruroot.setId("spacemode");
                    instrurootF.setId("spacemode");
                    instrurootM.setId("spacemode");
                    instrurootD.setId("spacemode");
                    instrurootE.setId("spacemode");
                    settingRoot.setId("spacemode");
            		rootPause.setId("spacepause");
                    root.setId("spacebg");
            		sonRoot.setId("spacebg");
                    rulesroot.setId("spacebg");
                    rulesroot2.setId("spacebg");
            		optionsRoot.setId("spacebg");
            		
            		scoreInst.setTextFill(Color.WHITE);
            	    erreurTime.setTextFill(Color.WHITE);
            	    boost.setTextFill(Color.WHITE);
            	    timer.setTextFill(Color.WHITE);
            	    indication.setTextFill(Color.WHITE);
            	    
            		nom.setTextFill(Color.WHITE);
                    titre.setTextFill(Color.WHITE);
                    jouer.setTextFill(Color.WHITE);
                    instructions.setTextFill(Color.WHITE);
                    options.setTextFill(Color.WHITE);
                    quittermenu.setTextFill(Color.WHITE);
                    quitterpause.setTextFill(Color.WHITE);
            		vs.setTextFill(Color.WHITE);
            		contreordi.setTextFill(Color.WHITE);
            		facile.setTextFill(Color.WHITE);
            		moyen.setTextFill(Color.WHITE);
            		difficile.setTextFill(Color.WHITE);
            		expert.setTextFill(Color.WHITE);
                    reprendre.setTextFill(Color.WHITE);
                    retour.setTextFill(Color.WHITE);
                    retourmenu.setTextFill(Color.WHITE);
                    nouvellepartie.setTextFill(Color.WHITE);
                    musique.setTextFill(Color.WHITE);
            		fleche.setTextFill(Color.WHITE);
            		flecheO.setTextFill(Color.WHITE);
            		retourpause.setTextFill(Color.WHITE);
            		regles.setFill(Color.WHITE);
                    regles2.setFill(Color.WHITE);
                    message.setFill(Color.WHITE);
                    message2.setFill(Color.WHITE);
                    space.setTextFill(Color.WHITE);
                    tennis.setTextFill(Color.WHITE);
                    neon.setTextFill(Color.WHITE);
                    volume.setFill(Color.WHITE);
                    volumeO.setFill(Color.WHITE);
                    h.setFill(Color.WHITE);
                    rappel.setFill(Color.WHITE);
                    message.setFill(Color.WHITE);
                    message2.setFill(Color.WHITE);
                    toucheM.setFill(Color.WHITE);
                    toucheE.setFill(Color.WHITE);
                    toucheD.setFill(Color.WHITE);
                    toucheF.setFill(Color.WHITE);
                    touche.setFill(Color.WHITE);
                    choix.setTextFill(Color.WHITE);
                    theme.setTextFill(Color.WHITE);
                    msq.setTextFill(Color.WHITE);
                    mutetext.setTextFill(Color.WHITE);
                    presserR.setTextFill(Color.WHITE);
                    setting.setTextFill(Color.WHITE);
                    instructionSet.setTextFill(Color.WHITE);
                    boost.setTextFill(Color.WHITE);
                    timer.setTextFill(Color.WHITE);
                    scoreInst.setTextFill(Color.WHITE);
            		indication.setTextFill(Color.WHITE);
            		valider.setTextFill(Color.WHITE);
                    effacer.setTextFill(Color.WHITE);
                    retourmode.setTextFill(Color.WHITE);
                    gameView.l.setFill(Color.WHITE);
                    gameView2.l.setFill(Color.WHITE);
                    gameView.textScoreP1.setFill(Color.WHITE);
                    gameView2.textScoreP1.setFill(Color.WHITE);
                    gameView.textScoreP2.setFill(Color.WHITE);
                    gameView2.textScoreP2.setFill(Color.WHITE);
                    gameView.optionPartie.setFill(Color.WHITE);
                    gameView2.optionPartie.setFill(Color.WHITE);

                  //couleur du soulignement de la première lettre
                    jouer.setId("spacemnemonic");
                    instructions.setId("spacemnemonic");
                    options.setId("spacemnemonic");
                    quittermenu.setId("spacemnemonic");
                    quitterpause.setId("spacemnemonic");
            		vs.setId("spacemnemonic");
            		contreordi.setId("spacemnemonic");
            		facile.setId("spacemnemonic");
            		moyen.setId("spacemnemonic");
            		difficile.setId("spacemnemonic");
            		expert.setId("spacemnemonic");
                    reprendre.setId("spacemnemonic");
                    retour.setId("spacemnemonic");
                    retourmenu.setId("spacemnemonic");
                    nouvellepartie.setId("spacemnemonic");
                    musique.setId("spacemnemonic");
            		fleche.setId("spacemnemonic");
            		flecheO.setId("spacemnemonic");
            		retourpause.setId("spacemnemonic");
                    regles.setId("spacemnemonic");
                    message.setId("spacemnemonic");
                    space.setId("spacemnemonic");
                    tennis.setId("spacemnemonic");
                    neon.setId("spacemnemonic");
                    valider.setId("spacemnemonic");
                    effacer.setId("spacemnemonic");
                    retourmode.setId("spacemnemonic");
                    setting.setId("spacemnemonic");
                    break;
                //Thème tennis
            	case T:
            		GameView.style="terrain";
            		GameView.styleBM="terrainBM";
            		menuRoot.setId("tennisbg");
                    modeRoot.setId("mode");
                    instruroot.setId("mode");
                    instrurootF.setId("mode");
                    instrurootM.setId("mode");
                    instrurootD.setId("mode");
                    instrurootE.setId("mode");
            		rootPause.setId("pause");
                    root.setId("terrain");
            		sonRoot.setId("terrain");
                    rulesroot.setId("regles");
                    rulesroot2.setId("regles");
            		optionsRoot.setId("tennisbg");
            		settingRoot.setId("mode");
            		
            		scoreInst.setTextFill(Color.BLACK);
            	    erreurTime.setTextFill(Color.BLACK);
            	    valider.setTextFill(Color.BLACK);
            	    boost.setTextFill(Color.BLACK);
            	    timer.setTextFill(Color.BLACK);
            	    indication.setTextFill(Color.BLACK);
            		nom.setTextFill(Color.BLACK);
                    titre.setTextFill(Color.BLACK);
                    jouer.setTextFill(Color.BLACK);
                    instructions.setTextFill(Color.BLACK);
                    options.setTextFill(Color.BLACK);
                    quitterpause.setTextFill(Color.BLACK);
                    quittermenu.setTextFill(Color.BLACK);
            		vs.setTextFill(Color.BLACK);
            		contreordi.setTextFill(Color.BLACK);
            		facile.setTextFill(Color.BLACK);
            		moyen.setTextFill(Color.BLACK);
            		difficile.setTextFill(Color.BLACK);
            		expert.setTextFill(Color.BLACK);
                    reprendre.setTextFill(Color.BLACK);
                    retour.setTextFill(Color.BLACK);
                    retourmenu.setTextFill(Color.BLACK);
                    nouvellepartie.setTextFill(Color.BLACK);
                    musique.setTextFill(Color.BLACK);
            		fleche.setTextFill(Color.BLACK);
            		fleche.setTextFill(Color.BLACK);
            		retourpause.setTextFill(Color.BLACK);
            		regles.setFill(Color.GREEN);
                    regles2.setFill(Color.GREEN);
                    message.setFill(Color.DARKRED);
                    message2.setFill(Color.DARKRED);
                    space.setTextFill(Color.BLACK);
                    tennis.setTextFill(Color.BLACK);
                    neon.setTextFill(Color.BLACK);
                    volume.setFill(Color.BLACK);
                    flecheO.setTextFill(Color.BLACK);
                    h.setFill(Color.BLACK);
                    rappel.setFill(Color.BLACK);
                    toucheM.setFill(Color.BLACK);
                    toucheE.setFill(Color.BLACK);
                    toucheD.setFill(Color.BLACK);
                    toucheF.setFill(Color.BLACK);
                    touche.setFill(Color.BLACK);
                    choix.setTextFill(Color.BLACK);
                    theme.setTextFill(Color.BLACK);
                    msq.setTextFill(Color.BLACK);
                    mutetext.setTextFill(Color.BLACK);
                    presserR.setTextFill(Color.BLACK);
                    setting.setTextFill(Color.BLACK);
                    instructionSet.setTextFill(Color.BLACK);
                    boost.setTextFill(Color.BLACK);
                    timer.setTextFill(Color.BLACK);
                    scoreInst.setTextFill(Color.BLACK);
            		indication.setTextFill(Color.BLACK);
            		valider.setTextFill(Color.BLACK);
                    effacer.setTextFill(Color.BLACK);
                    retourmode.setTextFill(Color.BLACK);
                    gameView.l.setFill(Color.BLACK);
                    gameView2.l.setFill(Color.BLACK);
                    gameView.textScoreP1.setFill(Color.BLACK);
                    gameView2.textScoreP1.setFill(Color.BLACK);
                    gameView.textScoreP2.setFill(Color.BLACK);
                    gameView2.textScoreP2.setFill(Color.BLACK);

                  //couleur du soulignement de la première lettre
                    jouer.setId("tennismnemonic");
                    instructions.setId("tennismnemonic");
                    options.setId("tennismnemonic");
                    quitterpause.setId("tennismnemonic");
                    quittermenu.setId("tennismnemonic");
            		vs.setId("tennismnemonic");
            		contreordi.setId("tennismnemonic");
            		facile.setId("tennismnemonic");
            		moyen.setId("tennismnemonic");
            		difficile.setId("tennismnemonic");
            		expert.setId("tennismnemonic");
                    reprendre.setId("tennismnemonic");
                    retour.setId("tennismnemonic");
                    retourmenu.setId("tennismnemonic");
                    nouvellepartie.setId("tennismnemonic");
                    musique.setId("tennismnemonic");
            		fleche.setId("tennismnemonic");
            		flecheO.setId("tennismnemonic");
            		retourpause.setId("tennismnemonic");
                    regles.setId("tennismnemonic");
                    message.setId("tennismnemonic");
                    space.setId("tennismnemonic");
                    tennis.setId("tennismnemonic");
                    neon.setId("tennismnemonic");
                    valider.setId("tennismnemonic");
                    effacer.setId("tennismnemonic");
                    retourmode.setId("tennismnemonic");
                    setting.setId("tennismnemonic");
                    break;
                //Thème néon
            	case N:
            		GameView.style="ledbggame";
            		GameView.styleBM="ledBM";
            		menuRoot.setId("ledbg");
                    modeRoot.setId("ledmode");
                    instruroot.setId("ledmode");
                    instrurootF.setId("ledmode");
                    instrurootM.setId("ledmode");
                    instrurootD.setId("ledmode");
                    instrurootE.setId("ledmode");
                    settingRoot.setId("ledmode");
            		rootPause.setId("ledpause");
                    root.setId("ledbggame");
            		sonRoot.setId("ledbg");
                    rulesroot.setId("ledbg");
                    rulesroot2.setId("ledbg");
            		optionsRoot.setId("ledbg");

            		//couleur des labels
            		scoreInst.setTextFill(Color.PINK);
            	    erreurTime.setTextFill(Color.PINK);
            	    boost.setTextFill(Color.PINK);
            	    timer.setTextFill(Color.PINK);
            	    indication.setTextFill(Color.PINK);
            		nom.setTextFill(Color.PINK);
                    titre.setTextFill(Color.PINK);
                    jouer.setTextFill(Color.PINK);
                    instructions.setTextFill(Color.PINK);
                    options.setTextFill(Color.PINK);
                    quitterpause.setTextFill(Color.PINK);
                    quittermenu.setTextFill(Color.PINK);
            		vs.setTextFill(Color.PINK);
            		contreordi.setTextFill(Color.PINK);
            		facile.setTextFill(Color.PINK);
            		moyen.setTextFill(Color.PINK);
            		difficile.setTextFill(Color.PINK);
            		expert.setTextFill(Color.PINK);
                    reprendre.setTextFill(Color.PINK);
                    retour.setTextFill(Color.PINK);
                    retourmenu.setTextFill(Color.PINK);
                    nouvellepartie.setTextFill(Color.PINK);
                    musique.setTextFill(Color.PINK);
            		fleche.setTextFill(Color.PINK);
            		flecheO.setTextFill(Color.PINK);
            		retourpause.setTextFill(Color.PINK);
            		regles.setFill(Color.PINK);
                    regles2.setFill(Color.PINK);
                    message.setFill(Color.PINK);
                    message2.setFill(Color.PINK);
                    space.setTextFill(Color.PINK);
                    tennis.setTextFill(Color.PINK);
                    neon.setTextFill(Color.PINK);
                    volume.setFill(Color.PINK);
                    volumeO.setFill(Color.PINK);
                    h.setFill(Color.PINK);
                    rappel.setFill(Color.PINK);
                    toucheM.setFill(Color.PINK);
                    toucheE.setFill(Color.PINK);
                    toucheD.setFill(Color.PINK);
                    toucheF.setFill(Color.PINK);
                    touche.setFill(Color.PINK);
                    choix.setTextFill(Color.PINK);
                    theme.setTextFill(Color.PINK);
                    msq.setTextFill(Color.PINK);
                    mutetext.setTextFill(Color.PINK);
                    presserR.setTextFill(Color.PINK);
                    setting.setTextFill(Color.PINK);
                    instructionSet.setTextFill(Color.PINK);
                    boost.setTextFill(Color.PINK);
                    timer.setTextFill(Color.PINK);
                    scoreInst.setTextFill(Color.PINK);
            		indication.setTextFill(Color.PINK);
            		valider.setTextFill(Color.PINK);
                    effacer.setTextFill(Color.PINK);
                    retourmode.setTextFill(Color.PINK);
                    gameView.l.setFill(Color.PINK);
                    gameView2.l.setFill(Color.PINK);
                    gameView.textScoreP1.setFill(Color.PINK);
                    gameView2.textScoreP1.setFill(Color.PINK);
                    gameView.textScoreP2.setFill(Color.PINK);
                    gameView2.textScoreP2.setFill(Color.PINK);

                    //couleur du soulignement de la première lettre
                    jouer.setId("neonmnemonic");
                    instructions.setId("neonmnemonic");
                    options.setId("neonmnemonic");
                    quitterpause.setId("neonmnemonic");
                    quittermenu.setId("neonmnemonic");
            		vs.setId("neonmnemonic");
            		contreordi.setId("neonmnemonic");
            		facile.setId("neonmnemonic");
            		moyen.setId("neonmnemonic");
            		difficile.setId("neonmnemonic");
            		expert.setId("neonmnemonic");
                    reprendre.setId("neonmnemonic");
                    retour.setId("neonmnemonic");
                    retourmenu.setId("neonmnemonic");
                    nouvellepartie.setId("neonmnemonic");
                    musique.setId("neonmnemonic");
            		fleche.setId("neonmnemonic");
            		flecheO.setId("spacemnemonic");
            		retourpause.setId("neonmnemonic");
                    regles.setId("neonmnemonic");
                    message.setId("neonmnemonic");
                    space.setId("neonmnemonic");
                    tennis.setId("neonmnemonic");
                    neon.setId("neonmnemonic");
            		valider.setId("neonmnemonic");
                    effacer.setId("neonmnemonic");
                    retourmode.setId("neonmnemonic");
                    setting.setId("neonmnemonic");
                    break;
            	case R:
            		primaryStage.setScene(menuScene);
            		break;
            	case ESCAPE:
                	primaryStage.close();
                	break;
                }});


        primaryStage.setScene(bvnScene);
        primaryStage.show();
    }

}