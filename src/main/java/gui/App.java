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
import java.awt.*;
import java.awt.Dimension;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.beans.*;


public class App extends Application {



    @Override
    public void start(Stage primaryStage) throws MalformedURLException {

    	//add an icon for the window
		File file = new File("pongicon.png");
		String localUrl = file.toURI().toURL().toString();
		Image image = new Image(localUrl);

		// Récupérer les dimensions de l'écran
		Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();

		// Fenêtre de jeu
		var root = new Pane();
		var gameScene = new Scene(root, width, height);

		//Fenêtre pause
		var rootPause = new VBox();
		// On crée la scene()
		var pauseScene = new Scene(rootPause, width, height);
		// On lui applique le css
		rootPause.setId("pause");
		pauseScene.getStylesheets().addAll(this.getClass().getResource("fond.css").toExternalForm());


        // ajout d'une image de fond
        root.setId("terrain");
        gameScene.getStylesheets().addAll(this.getClass().getResource("fond.css").toExternalForm());

        //music
        var music = new Music();
        root.getChildren().addAll(music.mV);


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

        // Ajout d'une page 'choix de mode' (avec ou sans bot)
        var modeRoot = new VBox();
  	   var modeScene = new Scene(modeRoot, width, height);
       modeRoot.setSpacing(20.0);
        modeRoot.setId("mode");
        modeScene.getStylesheets().addAll(this.getClass().getResource("fond.css").toExternalForm());
        //Créer un Pane() pour le menu
        var menuRoot = new VBox();
        //Créer une Scene() pour le menu, avec les dimensions de l'écran
        var menuScene = new Scene(menuRoot, width, height);


        Button retour = new Button("Retour au menu");
        retour.setFocusTraversable(false);
        retour.setId("boutons");
        retour.setOnAction(new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event) {
		GameView.stop=false;
		GameView.pause=false;
		gameView.remove1v1();
		gameView2.removeBot();
		gameView.reset1V1();
		gameView2.resetBot();
		gameView.start(false);
		gameView2.start(false);
          primaryStage.setScene(menuScene);
         }});


         Button retourP = new Button("Aller au menu");
         retourP.setFocusTraversable(false);
         retourP.setId("boutonsP");
         retourP.setOnAction(new EventHandler<ActionEvent>() {
         public void handle(ActionEvent event) {
 		GameView.stop=false;
 		GameView.pause=false;
 		gameView.remove1v1();
 		gameView2.removeBot();
 		gameView.reset1V1();
 		gameView2.resetBot();
 		gameView.start(false);
 		gameView2.start(false);
           primaryStage.setScene(menuScene);
           music.mP.play();
          }});




        Button retourmode = new Button("Nouvelle partie");
        retourmode.setFocusTraversable(false);
        retourmode.setId("boutonsP");
        retourmode.setOnAction(new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event) {
	     	GameView.stop=false;
		GameView.pause=false;
		gameView.remove1v1();
		gameView2.removeBot();
		gameView.reset1V1();
		gameView2.resetBot();
		gameView.start(false);
		gameView2.start(false);
          primaryStage.setScene(modeScene);
          music.mP.play();
         }});

        //Retour à la page pause
        Button retourpause = new Button("Retour");
        retourpause.setFocusTraversable(false);
        retourpause.setId("boutonsP");
        retourpause.setOnAction(new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event) {
          music.mP.pause();
          primaryStage.setScene(pauseScene);
         }});

        retourpause.setId("boutonsP");


        var tmp = new HBox();
        tmp.setSpacing(15.0);

        Label choix = new Label("\n\nChoisissez la difficult\u00e9 du bot (s\u00e9l\u00e9ctionner avec ENTRER)");
        choix.setId("ligne");

        Button facile = new Button("Facile");
        facile.setFocusTraversable(false);
        facile.setOnAction(new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event) {
            bot.setDifficulty(0);
        		gameView2.start(true);
        		gameView2.addRoootBot();
        		gameView2.animateBot();
           primaryStage.setScene(gameScene);
           modeRoot.getChildren().removeAll(tmp,choix);  }});


        Button moyen = new Button("Moyenne");
        moyen.setFocusTraversable(false);
        moyen.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event){
              bot.setDifficulty(1);
        		   gameView2.start(true);
        		   gameView2.addRoootBot();
        		   gameView2.animateBot();
              primaryStage.setScene(gameScene);
              modeRoot.getChildren().removeAll(tmp,choix);   }});


        Button difficile = new Button("Difficile");
        difficile.setFocusTraversable(false);
        difficile.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                bot.setDifficulty(2);
        	  	   gameView2.start(true);
        		   gameView2.addRoootBot();
        		   gameView2.animateBot();
              primaryStage.setScene(gameScene);
              modeRoot.getChildren().removeAll(tmp,choix); }});


          Button expert = new Button("Expert");
          expert.setFocusTraversable(false);
          expert.setOnAction(new EventHandler<ActionEvent>() {
              public void handle(ActionEvent event) {
                bot.setDifficulty(3);
                gameView2.start(true);
                gameView2.addRoootBot();
                gameView2.animateBot();
                primaryStage.setScene(gameScene);
                modeRoot.getChildren().removeAll(tmp,choix);  }});


        facile.setId("diffSelec");
        moyen.setId("difficulte");
        difficile.setId("difficulte");
        expert.setId("difficulte");

        Button opt1 = new Button("Jouer en 1v1");
        opt1.setFocusTraversable(false);
        opt1.setOnAction(new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event) {
      		gameView.start(true);
      		gameView.addRoot1V1();
      		gameView.animate();
           primaryStage.setScene(gameScene);
         modeRoot.getChildren().removeAll(tmp,choix); }});

        Button opt2 = new Button("Jouer contre l'ordinateur");
        opt2.setFocusTraversable(false);
        tmp.getChildren().addAll(facile,moyen,difficile,expert);
        tmp.setAlignment(Pos.CENTER);
        opt2.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
              modeRoot.getChildren().removeAll(choix, tmp);
              modeRoot.getChildren().addAll(choix, tmp); }});

        opt1.setId("modesSelec");
        opt2.setId("modes");

        modeRoot.getChildren().addAll(opt1,opt2,retour);
        modeRoot.setAlignment(Pos.CENTER);


        menuRoot.setId("menu");
        menuScene.getStylesheets().addAll(this.getClass().getResource("fond.css").toExternalForm());
        Button jouer = new Button("JOUER");
        jouer.setFocusTraversable(false);
        jouer.setOnAction(new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event) {
          primaryStage.setScene(modeScene); }});


        //Page d'instructions
        var rulesroot = new VBox();
        var rules = new Scene(rulesroot,width,height);

		// On crée les boutons de la page 'pause'
		Button reprendre = new Button("Reprendre");
    reprendre.setFocusTraversable(false);
		reprendre.setOnAction(new EventHandler<ActionEvent>() {
		public void handle(ActionEvent event) {
			rulesroot.getChildren().remove(reprendre);
			GameView.pause=false;
			if(gameView.isStart()){
			gameView.animate();
			}
			else {
				gameView2.animateBot();
			}
			primaryStage.setScene(gameScene);
			music.mP.play();}});

                Button retourI = new Button("Retour au menu");
                retourI.setFocusTraversable(false);
                retourI.setId("retourI");
                retourI.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
        		GameView.stop=false;
        		GameView.pause=false;
        		gameView.remove1v1();
        		gameView2.removeBot();
        		gameView.reset1V1();
        		gameView2.resetBot();
        		gameView.start(false);
        		gameView2.start(false);
        		rulesroot.getChildren().remove(reprendre);
                primaryStage.setScene(menuScene);
                 }});

        Button instructions = new Button("Instructions");
        instructions.setFocusTraversable(false);
      	Text regles = new Text("R\u00e8gles du jeu : \n");
        regles.setFont(Font.font(35));
        regles.setFill(Color.GREEN);
        Text message = new Text("- Contr\u00f4ler la raquette gauche avec Z et S \n\n- Contr\u00f4ler la raquette droite avec UP et DOWN \n\n- Le but du jeu est d'envoyer la balle dans le camp adverse en mettant \nson adversaire dans l'incapacit\u00e9 de la renvoyer. \n\n- Obtenir 3 points pour gagner la partie \n\n-- Vous pouvez choisir un mode de jeu sp\u00e9cifique\nen d\u00e9but de partie : \nLa difficult\u00e9 de l'ordinateur repr\u00e9sente son efficacit\u00e9 \u00e0 rattraper les balles;\nplus c'est difficile, moins il y a de chance qu'il les rate !\n\n-- Un boost (repr\u00e9sent\u00e9 par un cercle rouge avec un \u00e9clair jaune) sera affich\u00e9 sur votre terrain\nen cours de partie; si vous l'attrapez, activez le avec les boutons CTRL ou E pour acc\u00e9lerer la balle\n");
        message.setFont(Font.font(25));
        message.setFill(Color.DARKRED);
        rulesroot.setId("regles");
        rulesroot.getChildren().addAll(regles,message,retourI);
        rulesroot.setAlignment(Pos.CENTER);
      	rules.getStylesheets().addAll(this.getClass().getResource("fond.css").toExternalForm());
        instructions.setOnAction(new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event) {
        	primaryStage.setScene(rules); }});


        	//Page d'options

			Button options = new Button("Options");
      options.setFocusTraversable(false);
			Button spacebg = new Button("Space");
      spacebg.setFocusTraversable(false);
			Button tennisbg = new Button("Tennis Court");
      tennisbg.setFocusTraversable(false);
			options.setId("boutonsP");
			spacebg.setId("boutonsP");
			tennisbg.setId("boutonsP");
			var optionsRoot = new VBox();
			var optionsScene = new Scene(optionsRoot, width, height);
			optionsRoot.setId("menu");
			optionsScene.getStylesheets().addAll(this.getClass().getResource("fond.css").toExternalForm());
			optionsRoot.getChildren().addAll(spacebg,tennisbg,retour);
			optionsRoot.setAlignment(Pos.CENTER);


			options.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
			    primaryStage.setScene(optionsScene);
	            }});



			//Page son
			var sonRoot = new VBox();
			sonRoot.setPadding(new Insets(height/2, 450,0, height/2));
	        var sonScene = new Scene(sonRoot, width, height);
	        var volumeSlider = new Slider(0.0, 100, 0.0);
	        volumeSlider.setId("slider");
	        volumeSlider.lookup(".track");
	        volumeSlider.setValue(music.mP.getVolume() * 70);
		  // Arrêter la musique (mute)
         		 Button mute = new Button(" ");
             mute.setFocusTraversable(false);
        	      mute.setId("boutonMute2");
                 mute.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
              if(volumeSlider.getValue() == 0){ mute.setId("boutonMute2"); volumeSlider.setValue(50);}
              else{ mute.setId("boutonMute1"); volumeSlider.setValue(0);} }});
          // Modifier le volume de la musique
	        volumeSlider.valueProperty().addListener(new InvalidationListener() {
	        	public void invalidated(Observable observable) {
	        		music.mP.setVolume(volumeSlider.getValue() / 100);} });
	        var volume = new Text("Volume");
	        volume.setFont(Font.font(30));
			Button son = new Button("Musique et son");
      son.setFocusTraversable(false);
      son.setId("boutonsP");
			son.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
          music.mP.play();
					primaryStage.setScene(sonScene); }});

			sonRoot.setId("menu");
			sonScene.getStylesheets().addAll(this.getClass().getResource("fond.css").toExternalForm());
			sonRoot.getChildren().addAll(mute,volume,volumeSlider,retourpause);
			sonRoot.setAlignment(Pos.TOP_CENTER);

			//Space theme
			spacebg.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					rootPause.setId("spacebg");
			        root.setId("spacebg");
			        modeRoot.setId("spacebg");
			        menuRoot.setId("spacebg");
			        rulesroot.setId("spacebg");
					optionsRoot.setId("spacebg");
					sonRoot.setId("spacebg");
					pauseScene.getStylesheets().addAll(this.getClass().getResource("fond.css").toExternalForm());
			        gameScene.getStylesheets().addAll(this.getClass().getResource("fond.css").toExternalForm());
			        modeScene.getStylesheets().addAll(this.getClass().getResource("fond.css").toExternalForm());
			        menuScene.getStylesheets().addAll(this.getClass().getResource("fond.css").toExternalForm());
			      	rules.getStylesheets().addAll(this.getClass().getResource("fond.css").toExternalForm());
					optionsScene.getStylesheets().addAll(this.getClass().getResource("fond.css").toExternalForm());
				    primaryStage.setScene(optionsScene);
		            }});

			//Tennis court theme
			tennisbg.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					rootPause.setId("pause");
			        root.setId("terrain");
			        modeRoot.setId("mode");
			        menuRoot.setId("menu");
			        rulesroot.setId("terrain");
					optionsRoot.setId("terrain");
					sonRoot.setId("terrain");
					pauseScene.getStylesheets().addAll(this.getClass().getResource("fond.css").toExternalForm());
			        gameScene.getStylesheets().addAll(this.getClass().getResource("fond.css").toExternalForm());
			        modeScene.getStylesheets().addAll(this.getClass().getResource("fond.css").toExternalForm());
			        menuScene.getStylesheets().addAll(this.getClass().getResource("fond.css").toExternalForm());
			      	rules.getStylesheets().addAll(this.getClass().getResource("fond.css").toExternalForm());
					optionsScene.getStylesheets().addAll(this.getClass().getResource("fond.css").toExternalForm());
				    primaryStage.setScene(optionsScene);
		            }});

			//Bouton pour quitter le jeu
			Button bye = new Button("Quitter le jeu");
      bye.setFocusTraversable(false);
			bye.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				primaryStage.close(); }});


				rootPause.getChildren().addAll(reprendre,retourP,retourmode, son, bye);
				rootPause.setAlignment(Pos.CENTER);
				reprendre.setId("boutonsSelec");
				son.setId("boutonsP");
				bye.setId("boutonsP");


		//Bouton pour quitter le jeu
        Button quitter = new Button("Quitter");
        quitter.setFocusTraversable(false);
        quitter.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
              primaryStage.close(); }});

        Label nom = new Label("nootynootnoot");
        Label titre = new Label("PONG");
        jouer.setId("boutonsSelec");
        instructions.setId("boutons");
        options.setId("boutons");
        quitter.setId("boutons");
        retour.setId("boutons");
        nom.setFont(Font.font(30));
        titre.setId("label2");
        menuRoot.getChildren().addAll(nom, titre, jouer, instructions, options, quitter);
        menuRoot.setAlignment(Pos.CENTER);

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
                case M:
                	if(volumeSlider.getValue() == 0){ mute.setId("boutonMute2"); volumeSlider.setValue(50);}
                    else{ mute.setId("boutonMute1"); volumeSlider.setValue(0);}
                	break;
                case H:
                    GameView.pause=true;
                    primaryStage.setScene(rules);
                    //rulesroot.getChildren().add(reprendre);
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
            }
        });


        // touche 'p' pour revenir sur le jeu depuis la page 'pause'
        pauseScene.setOnKeyPressed(ev -> {
            switch (ev.getCode()) {
                case DOWN:
                  if(reprendre.getId() == "boutonsSelec"){
                    reprendre.setId("boutonsP");
                    retourP.setId("boutonsSelec");
                  }
                  else if(retourP.getId() == "boutonsSelec"){
                    retourP.setId("boutonsP");
                    retourmode.setId("boutonsSelec");
                  }
                  else if(retourmode.getId() == "boutonsSelec"){
                    retourmode.setId("boutonsP");
                    son.setId("boutonsSelec");
                  }
                  else if(son.getId() == "boutonsSelec"){
                    son.setId("boutonsP");
                    bye.setId("boutonsSelec");
                  }
                  break;
                case UP:
                  if(bye.getId() == "boutonsSelec"){
                    bye.setId("boutonsP");
                    son.setId("boutonsSelec");
                  }
                  else if(son.getId() == "boutonsSelec"){
                    son.setId("boutonsP");
                    retourmode.setId("boutonsSelec");
                  }
                  else if(retourmode.getId() == "boutonsSelec"){
                    retourmode.setId("boutonsP");
                    retourP.setId("boutonsSelec");
                  }
                  else if(retourP.getId() == "boutonsSelec"){
                    retourP.setId("boutonsP");
                    reprendre.setId("boutonsSelec");
                  }
                  break;
                case SPACE:
                  if(reprendre.getId() == "boutonsSelec"){
                    GameView.pause=false;
                    music.mP.play();
                    if(gameView.isStart()){
                    gameView.animate();
                    }
                    else {
                      gameView2.animateBot();
                    }
                    primaryStage.setScene(gameScene);
                  }
                  else if(retourP.getId() == "boutonsSelec"){
                    GameView.stop=false;
                    GameView.pause=false;
                    gameView.remove1v1();
                    gameView2.removeBot();
                    gameView.reset1V1();
                    gameView2.resetBot();
                    gameView.start(false);
                    gameView2.start(false);
                    primaryStage.setScene(menuScene);
                    music.mP.play();
                  }
                  else if(retourmode.getId() == "boutonsSelec"){
                    GameView.stop=false;
                    GameView.pause=false;
                    gameView.remove1v1();
                    gameView2.removeBot();
                    gameView.reset1V1();
                    gameView2.resetBot();
                    gameView.start(false);
                    gameView2.start(false);
                    primaryStage.setScene(modeScene);
                    music.mP.play();
                  }
                  else if(son.getId() == "boutonsSelec"){
                    primaryStage.setScene(sonScene);
                    music.mP.play();
                  }
                  else if(bye.getId() == "boutonsSelec"){
                    primaryStage.close();
                  }
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
                }});

        menuScene.setOnKeyPressed(ev -> {
            switch (ev.getCode()) {
              case DOWN:
                  if(jouer.getId() == "boutonsSelec"){
                    jouer.setId("boutons");
                    instructions.setId("boutonsSelec");
                  }
                  else if(instructions.getId() == "boutonsSelec"){
                    instructions.setId("boutons");
                    options.setId("boutonsSelec");
                  }
                  else if(options.getId() == "boutonsSelec"){
                    options.setId("boutons");
                    quitter.setId("boutonsSelec");
                  }
                  break;
              case UP:
                  if(quitter.getId() == "boutonsSelec"){
                    quitter.setId("boutons");
                    options.setId("boutonsSelec");
                  }
                  else if(options.getId() == "boutonsSelec"){
                    options.setId("boutons");
                    instructions.setId("boutonsSelec");
                  }
                  else if(instructions.getId() == "boutonsSelec"){
                    instructions.setId("boutons");
                    jouer.setId("boutonsSelec");
                  }
                  break;
                case SPACE:
                  if(jouer.getId() == "boutonsSelec"){
                     primaryStage.setScene(modeScene);
                  }
                  else if(instructions.getId() == "boutonsSelec"){
                     primaryStage.setScene(rules);
                  }
                  else if(options.getId() == "boutonsSelec"){
                     primaryStage.setScene(optionsScene);
                  }
                  else if(quitter.getId() == "boutonsSelec"){
                     primaryStage.close();
                  }
                  break;
                case M:
                	if(volumeSlider.getValue() == 0){ mute.setId("boutonMute2"); volumeSlider.setValue(50);}
                    else{ mute.setId("boutonMute1"); volumeSlider.setValue(0);}
                	break;
                 }});

       optionsScene.setOnKeyPressed(ev -> {
             switch (ev.getCode()) {
                case R:
                	primaryStage.setScene(menuScene);
                  break; }});

        rules.setOnKeyPressed(ev -> {
            switch (ev.getCode()) {
                case M:
                	if(volumeSlider.getValue() == 0){ mute.setId("boutonMute2"); volumeSlider.setValue(50);}
                    else{ mute.setId("boutonMute1"); volumeSlider.setValue(0);}
                	break;
                case H:
                    if(gameView.isStart() || gameView2.isStart()){
                    	if(gameView.isStart()) gameView.animate();
                    	else gameView2.animateBot();
                    	primaryStage.setScene(gameScene);
                      GameView.pause=false;
                        }
                    else {
                     	rulesroot.getChildren().remove(reprendre);
                     	primaryStage.setScene(menuScene);
                    }
                    break;
                 }});

        sonScene.setOnKeyPressed(ev -> {
          switch (ev.getCode()){
            case S:
              music.mP.pause();
              primaryStage.setScene(pauseScene);
              break;
            case M:
              if(volumeSlider.getValue() == 0){ mute.setId("boutonMute2"); volumeSlider.setValue(50);}
              else{ mute.setId("boutonMute1"); volumeSlider.setValue(0);}
              break;
            case RIGHT:
              mute.setId("boutonMute2");
              if(volumeSlider.getValue() != 100) volumeSlider.setValue(volumeSlider.getValue()+5);
              break;
            case LEFT:
              if(volumeSlider.getValue() > 0) volumeSlider.setValue(volumeSlider.getValue()-5);
              if(volumeSlider.getValue() == 0) mute.setId("boutonMute1");
              break;
          }});

        modeScene.setOnKeyPressed(ev -> {
            switch (ev.getCode()) {
                case M:
                	if(volumeSlider.getValue() == 0){ mute.setId("boutonMute2"); volumeSlider.setValue(50);}
                    else{ mute.setId("boutonMute1"); volumeSlider.setValue(0);}
                	break;
                case DOWN:
                  if(opt1.getId() == "modesSelec"){
                    opt1.setId("modes");
                    opt2.setId("modesSelec");
                  }
                  break;
                case UP:
                  if(opt2.getId() == "modesSelec"){
                    opt2.setId("modes");
                    opt1.setId("modesSelec");
                  }
                  break;
                case RIGHT:
                  if(facile.getId() == "diffSelec"){
                    facile.setId("difficulte");
                    moyen.setId("diffSelec");
                  }
                  else if(moyen.getId() == "diffSelec"){
                    moyen.setId("difficulte");
                    difficile.setId("diffSelec");
                  }
                  else if(difficile.getId() == "diffSelec"){
                    difficile.setId("difficulte");
                    expert.setId("diffSelec");
                  }
                  break;
                case LEFT:
                  if(expert.getId() == "diffSelec"){
                    expert.setId("difficulte");
                    difficile.setId("diffSelec");
                  }
                  else if(difficile.getId() == "diffSelec"){
                    difficile.setId("difficulte");
                    moyen.setId("diffSelec");
                  }
                  else if(moyen.getId() == "diffSelec"){
                    moyen.setId("difficulte");
                    facile.setId("diffSelec");
                  }
                break;
                case SPACE:
                  if(opt1.getId() == "modesSelec"){
                    gameView.start(true);
                    gameView.addRoot1V1();
                    gameView.animate();
                    primaryStage.setScene(gameScene);
                    modeRoot.getChildren().removeAll(tmp,choix);
                  }
                  else if(opt2.getId() == "modesSelec"){
                    modeRoot.getChildren().addAll(choix,tmp);
                    opt2.setId("modes");
                  }
                  break;
                case ENTER:
                  if(facile.getId() == "diffSelec"){
                    bot.setDifficulty(0);
                    gameView2.start(true);
                    gameView2.addRoootBot();
                    gameView2.animateBot();
                    primaryStage.setScene(gameScene);
                    modeRoot.getChildren().removeAll(tmp,choix);
                  }
                  else if(moyen.getId() == "diffSelec"){
                    bot.setDifficulty(1);
                   gameView2.start(true);
                   gameView2.addRoootBot();
                   gameView2.animateBot();
                   primaryStage.setScene(gameScene);
                   modeRoot.getChildren().removeAll(tmp,choix);
                  }
                  else if(difficile.getId() == "diffSelec"){
                    bot.setDifficulty(2);
                   gameView2.start(true);
                   gameView2.addRoootBot();
                   gameView2.animateBot();
                   primaryStage.setScene(gameScene);
                   modeRoot.getChildren().removeAll(tmp,choix);
                  }
                  else if(expert.getId() == "diffSelec"){
                    gameView2.start(true);
                    gameView2.addRoootBot();
                    gameView2.animateBot();
                    primaryStage.setScene(gameScene);
                    modeRoot.getChildren().removeAll(tmp,choix);
                  }
                  break;  }});

        primaryStage.setScene(menuScene);
        primaryStage.show();
    }

}
