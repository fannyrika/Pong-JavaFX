package gui;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.beans.binding.Bindings;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import java.io.File;
import java.net.MalformedURLException;
import javafx.scene.paint.ImagePattern;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
public class GameView {
    // class parameters
    private final Bot ModeBot;
    private final Court court;
    private final Pane gameRoot; // main node of the game
    private final double scale;
    private final double xMargin = 100.0, racketThickness = 25.0; // pixels

    // children of the game main node
    public Label timer;
    Chrono chronometer;
    public final Rectangle racketA, racketB ,bot;
    public final Circle ball;
    private boolean start;
    static boolean stop=false,pause=false,timerStop=false;
    static boolean afficheTimer=false,activeBoost=false;
    public static String style="terrain",styleBM="terrainBM";
    public Text l=new Text();
    public Text optionPartie = new Text();
    
    public Text textScoreP1, textScoreP2, instructionL,instructionR;
    public Text lastPoint=new Text("Balle de match");
    Boost boost;
    Button menu;
    /**
     * @param court le "modèle" de cette vue (le terrain de jeu de raquettes et tout ce qu'il y a dessus)
     * @param root  le nœud racine dans la scène JavaFX dans lequel le jeu sera affiché
     * @param scale le facteur d'échelle entre les distances du modèle et le nombre de pixels correspondants dans la vue
     */

    public GameView(Court court, Pane root, double scale) throws MalformedURLException{
        this.court = court;
	    this.ModeBot = null;
        this.gameRoot = root;
        this.scale = scale;
        
        root.setMinWidth(court.getWidth() * scale + 2 * xMargin);
        root.setMinHeight(court.getHeight() * scale);
		root.setMaxWidth(court.getWidth() * scale + 2 * xMargin);
		root.setMaxHeight(court.getHeight() * scale);

        timer();
        timer.setLayoutX(court.getWidth()/2-50);
        timer.setLayoutY(-50);
        menu = new Button("Retour au menu");  
        racketA = new Rectangle();
        racketA.setHeight(court.getRacketSize() * scale);
        racketA.setWidth(racketThickness);
        racketA.setFill(Color.WHITE);

        /*File fic = new File("joueuse.png");
        String s = fic.toURI().toURL().toString();
        Image joueuse = new Image(s);
        racketA.setFill(new ImagePattern(joueuse));*/

        racketA.setX(xMargin - racketThickness);
        racketA.setY(court.getRacketA() * scale);

        racketB = new Rectangle();
        racketB.setHeight(court.getRacketSize() * scale);
        racketB.setWidth(racketThickness);
        racketB.setFill(Color.WHITE);

        /*File fich = new File("joueur.png");
        String ss = fich.toURI().toURL().toString();
        Image joueur = new Image(ss);
        racketB.setFill(new ImagePattern(joueur));*/

        racketB.setX(court.getWidth() * scale + xMargin - 100);
        racketB.setY(court.getRacketB() * scale);
		    this.bot = null;


        ball = new Circle();
        ball.setRadius(court.getBallRadius());
        ball.setFill(Color.YELLOW);

        // Personnalisation de la balle avec une image
        File file = new File("balle3.jpg");
        String localUrl = file.toURI().toURL().toString();
        Image image = new Image(localUrl);
        ball.setFill(new ImagePattern(image));


        ball.setCenterX(court.getBallX() * scale + xMargin);
        ball.setCenterY(court.getBallY() * scale);

        textScoreP1 = new Text("0");
        textScoreP2 = new Text("0");
        
        textScoreP1.setStyle("-fx-text-fill: WHITE");
        textScoreP1.setFont(Font.font(50));
        textScoreP2.setFont(Font.font(50));

        textScoreP1.setX(court.getWidth()/2-150);
        textScoreP1.setY(80);

        textScoreP2.setX(court.getWidth()/2+150) ;
        textScoreP2.setY(80);





    }
	public boolean isStart() {
    	return start;
    }
    public void start(boolean b) {
    	 start=b;
    }
    public void addRoot() {
    	
    	  //Gére l'affichage des bonus des joueurs;
  	  Image imgL = new Image(new File("src/main/resources/gui/hitboxLeft.png").toURI().toString());
      Image imgR = new Image(new File("src/main/resources/gui/hitboxRight.png").toURI().toString());
  	  ImageView view = new ImageView(imgL);
  	  view.setFitHeight(200);
  	  view.setFitWidth(200);
  	  view.setPreserveRatio(true);
  	  Label caseBonusLeft=new Label();
  	  caseBonusLeft.setGraphic(view);
  	  caseBonusLeft.setTranslateX(-49);
  	  caseBonusLeft.setTranslateY(50);
  	  
  	 

  	  ImageView viewR=new ImageView(imgR);
  	  viewR.setFitHeight(200);
  	  viewR.setFitWidth(200);
  	  viewR.setPreserveRatio(true);
      Label caseBonusRight= new Label();
      caseBonusRight.setGraphic(viewR);
      caseBonusRight.setTranslateX(court.getWidth()-70);
  	  caseBonusRight.setTranslateY(50);
  	  String instructionLStr="up->Z down->S";
	  instructionL = new Text(instructionLStr);
	  instructionL.setFont(Font.font(15));
	  instructionL.setFill(Color.WHITE);
	  instructionL.setX(10);
	  instructionL.setY(30);

     String instructionRStr="up->UP down->DOWN";
     instructionR = new Text(instructionRStr);
     instructionR.setFont(Font.font(15));
     instructionR.setFill(Color.WHITE);
     instructionR.setX(court.getWidth()-70);
     instructionR.setY(30);

	  optionPartie.setFont(new Font("Arial",20));
      optionPartie.setX(court.getWidth()/2-400);
      optionPartie.setY(60);
	  if(court.isScoreLimit()) {
		  optionPartie.setText("Limite de score : "+court.score);
		  gameRoot.getChildren().add(optionPartie);
	  }
        if(afficheTimer) {
        
        	gameRoot.getChildren().add(timer);
        }
        if(activeBoost) {
        	gameRoot.getChildren().addAll(caseBonusLeft,caseBonusRight);
        }
        gameRoot.getChildren().addAll(instructionL,instructionR,racketA, racketB, ball,textScoreP1, textScoreP2);
    	
    }
   
    public GameView(Bot bot, Pane root, double scale) throws MalformedURLException{
        this.court = null;
	      this.ModeBot = bot;
        this.gameRoot = root;
        this.scale = scale;

        root.setMinWidth(bot.getWidth() * scale + 2 * xMargin);
        root.setMinHeight(bot.getHeight() * scale);

        timer();
        timer.setLayoutX(bot.getWidth()/2-50);
        timer.setLayoutY(-50);

        racketA = new Rectangle();
        racketA.setHeight(bot.getRacketSize() * scale);
        racketA.setWidth(racketThickness);
        racketA.setFill(Color.WHITE);

        /*File fic = new File("joueuse.png");
        String s = fic.toURI().toURL().toString();
        Image joueuse = new Image(s);
        racketA.setFill(new ImagePattern(joueuse));*/

        racketA.setX(xMargin - racketThickness);
        racketA.setY(bot.getRacketA() * scale);
	      this.racketB = null;

        this.bot = new Rectangle();
        this.bot.setHeight(bot.getRacketSize() * scale);
        this.bot.setWidth(racketThickness);
        this.bot.setFill(Color.WHITE);

        /*File fich = new File("joueur.png");
        String ss = fich.toURI().toURL().toString();
        Image joueur = new Image(ss);
        this.bot.setFill(new ImagePattern(joueur));*/

        this.bot.setX(bot.getWidth() * scale + xMargin -100);
        this.bot.setY(bot.getBot() * scale);

        ball = new Circle();
        ball.setRadius(bot.getBallRadius());
        ball.setFill(Color.YELLOW);

        File file = new File("balle3.jpg");
        String localUrl = file.toURI().toURL().toString();
        Image image = new Image(localUrl);
        ball.setFill(new ImagePattern(image));

        ball.setCenterX(bot.getBallX() * scale + xMargin-100);
        ball.setCenterY(bot.getBallY() * scale);

        textScoreP1 = new Text("0");
        textScoreP2 = new Text("0");
        textScoreP1.setFont(Font.font(50));
        textScoreP2.setFont(Font.font(50));

        textScoreP1.setX(bot.getWidth()/2-150);
        textScoreP1.setY(80);

        textScoreP2.setX(bot.getWidth()/2+150) ;
        textScoreP2.setY(80);







    }
    public void setBallepng(String s) throws MalformedURLException{
        File file = new File(s);
        String localUrl = file.toURI().toURL().toString();
        Image image = new Image(localUrl);
        ball.setFill(new ImagePattern(image));
      }
	 public void addRootBot() {
	      
	        instructionL = new Text();
	        instructionL.setText("up->Z down->S");
	        instructionL.setFont(Font.font(15));
	        instructionL.setFill(Color.WHITE);
	        instructionL.setX(10);
	        instructionL.setY(30);

		 if(afficheTimer) {
	        	gameRoot.getChildren().add(timer);
	        }
		
		 gameRoot.getChildren().addAll(racketA, this.bot, ball,textScoreP1, textScoreP2,instructionL);
    	}

	public void remove() {
		gameRoot.getChildren().clear();


    }


    //fonction qui gère l'implementation du timer
    public void timer() {
    	 chronometer =new Chrono();
        timer=new Label();
        timer.setFont(new Font("Arial",40));
        timer.setMinSize(100.00, 100.00);
        timer.setMinHeight(200);
    } 
    public void timerEnd(){
    	timerStop=true;
    	chronometer.cpt=Chrono.timeLimit;
    }
    
    public void endView1V1() {
    	timerStop=true;
    	chronometer.cpt=Chrono.timeLimit;
    	remove();
    	l.setStyle("-fx-font: 36 arial;");
    	l.setTranslateX(court.getWidth()/2-100);
    	l.setTranslateY(court.height/2);
    	if(court.player1Win()) {
    		l.setText("Le joueur de gauche a gagn\u00e9\nAppuyer sur R pour rejouer\nAppuyer sur F pour retourner au menu");
    	}
    	else if(court.player2Win()) {
    		l.setText("Le joueur de droite a gagn\u00e9\nAppuyer sur R pour rejouer\nAppuyer sur F pour retourner au menu");
    	}
    	else {
    		l.setText("Match Nul");
    	}
    	reset1V1();	  	 	
    	gameRoot.getChildren().addAll(l);
    	Button rejouer=new Button();
    	rejouer.setOnKeyPressed(ev -> {
            switch (ev.getCode()) {
            //Thème espace
	        	case R:
	        		remove();
	        		addRoot();
	             	gameRoot.setId(style);            
	             	start(true);
	      			animate();
	      			break;
	        		
	            }
  
   	 	
    	});
    	rejouer.setStyle("-fx-border-color: transparent;\r\n"
    			+ "    -fx-border-width: 0;\r\n"
    			+ "    -fx-background-radius: 0;\r\n"
    			+ "    -fx-background-color: transparent;\r\n"
    			+ "    -fx-font-family:\"Segoe UI\", Helvetica, Arial, sans-serif;\r\n"
    			+ "    -fx-font-size: 1em; /* 12 */\r\n"
    			+ "    -fx-text-fill: #828282;");
   	 	gameRoot.getChildren().add(rejouer);
    }
    public void endViewBot() {
    	timerStop=true;
    	chronometer.cpt=Chrono.timeLimit;
    	remove();
    	l.setStyle("-fx-font: 36 arial;");
    	l.setTranslateX(ModeBot.getWidth()/2-100);
    	l.setTranslateY(ModeBot.height/2);
    	if(ModeBot.player1Win()) {
    		l.setText("Le joueur 1 a gagn\u00e9\n-Appuyer sur R pour rejouer\n-Appuyer sur F pour retourner au menu");
    	}
    	else if(ModeBot.player2Win()) {
    		l.setText("L'ordinateur a gagn\u00e9\n-Appuyer sur R pour rejouer\n-Appuyer sur F pour retourner au menu");
    	}
    	else {
    		l.setText("Match Nul");
    	}
    	resetBot();	  	 	
    	gameRoot.getChildren().addAll(l);
    	Button rejouer=new Button();
    	rejouer.setOnKeyPressed(ev -> {
            switch (ev.getCode()) {
            //Thème espace
	        	case R:
	        		remove();
	        		addRootBot();
	             	gameRoot.setId(style);            
	             	start(true);
	      			animateBot();
	      			break;
	        		
	            }
  
   	 	
    	});
    	rejouer.setStyle("-fx-border-color: transparent;\r\n"
    			+ "    -fx-border-width: 0;\r\n"
    			+ "    -fx-background-radius: 0;\r\n"
    			+ "    -fx-background-color: transparent;\r\n"
    			+ "    -fx-font-family:\"Segoe UI\", Helvetica, Arial, sans-serif;\r\n"
    			+ "    -fx-font-size: 1em; /* 12 */\r\n"
    			+ "    -fx-text-fill: #828282;");
   	 	gameRoot.getChildren().add(rejouer);
    	}
    public void reset1V1() {
    	gameRoot.setId(style);
    	court.reset();
    	court.resetScore();
    	chronometer.reset();
    	activeBoost=false;

    }
    public void resetBot() {
    	gameRoot.setId(style);
    	ModeBot.reset();
    	ModeBot.resetScore();
    	chronometer.reset();
    }
    public void boost() {
   	 //toutes les 15 secondes fait spawn un boost si il y en a pas;
    	if(activeBoost) {
	   	 if(chronometer.ss%15==0&&chronometer.th==0&&chronometer.hd==0) {
	   		 if(boost!=null){
				gameRoot.getChildren().remove(boost.boost);
	         		boost=new Boost(court,chronometer,court.getBallSpeedX(),court.getBallSpeedY());
	         		gameRoot.getChildren().addAll(boost.boost);
			 }
			 else{
				boost=new Boost(court,chronometer,court.getBallSpeedX(),court.getBallSpeedY());
	         		gameRoot.getChildren().addAll(boost.boost);
			 }
	
	   	 }
	   	 
	   	 if(boost!=null&&boost.isBallTouchBoost()) {
	   		
	   		 MediaPlayer mp=new MediaPlayer(court.mediaBall);
	   		 mp.play();
	   		 if(court.ballSpeedX>0) {
	   			 court.p1.addBoostPlayer(true);
	   			 gameRoot.getChildren().addAll(court.p1.playerBoost);
	   		 }
	   		 if(court.ballSpeedX<0){
	   			 court.p2.addBoostPlayer(false);
	   			 gameRoot.getChildren().addAll(court.p2.playerBoost);
	   		 }
	   		 gameRoot.getChildren().remove(boost.boost);
	   		 boost=null;
	   	 }
	   	
	   	 
	
		 if(!court.p2.active&&!court.p1.active){  	
	   	 court.p1.activeBoost();
		 }
		 
	   	 if(!court.p2.active&&!court.p1.active){  	
	   	 court.p2.activeBoost();
		 }
		 if(court.p1.deleteBoost) {
	   		 gameRoot.getChildren().remove(court.p1.playerBoost);
	   		 court.p1.deleteBoost=false;
	   	 }
	   	 if(court.p2.deleteBoost) {
	   		 gameRoot.getChildren().remove(court.p2.playerBoost);
	   		 court.p2.deleteBoost=false;
	   	 }
	
	
	   	 court.p1.boostPlayer(chronometer.cpt);
	     court.p2.boostPlayer(chronometer.cpt);
    	}
   }
    public void lastPoint() {
    		gameRoot.setId(styleBM);
    		lastPoint.setStyle("-fx-font: 30 arial");
    		if(court!=null)lastPoint.setTranslateX(court.getWidth()/2-100);
    		else lastPoint.setTranslateX(ModeBot.getWidth()/2-100);
    		lastPoint.setTranslateY(50);
    		gameRoot.getChildren().add(lastPoint);
    		gameRoot.getChildren().remove(timer);
    	
    }
    public void animate() {
        new AnimationTimer() {
            long last = 0;

            @Override
            public void handle(long now) {
                if (last == 0) { // ignore the first tick, just compute the first deltaT
                    last = now;
                    return;
                }
                
                if(court.isScoreLimit()) {
                	
                	if(court.scoreLimitReach()) {
                		endView1V1();
                		stop();
                	}
                }
                if(court.isTimeLimit()) {
                	if(chronometer.cpt>0) {
                	chronometer.downTimer();
                	}
                	else if(court.scoreEgalite()) {
                			chronometer.downTimer();
                			if(!gameRoot.getChildren().contains(lastPoint)) {
                				lastPoint();
                			}
                			
                	}
                	else {
                		endView1V1();
                		stop();
                	}
               	}	
               
                else {
                	chronometer.update();
                }
                timer.textProperty().bind(Bindings.format("%02d:%02d:%d%d",  chronometer.mm, chronometer.ss, chronometer.th, chronometer.hd));;
                boost();
                court.update((now - last) * 1.0e-9); // convert nanoseconds to seconds
                last = now;
                racketA.setY(court.getRacketA() * scale);
                racketB.setY(court.getRacketB() * scale);
                ball.setCenterX(court.getBallX() * scale + xMargin);
                ball.setCenterY(court.getBallY() * scale);

                textScoreP1.setText(Integer.toString(court.getScoreP1()));
                textScoreP2.setText(Integer.toString(court.getScoreP2()));
			 if(pause) {
                	stop();
                }
                if(stop) {

                	stop();
                }
            }
        }.start();
    }
    public void animateBot() {
        new AnimationTimer() {
            long last = 0;

            @Override
            public void handle(long now) {
                if (last == 0) { // ignore the first tick, just compute the first deltaT
                    last = now;
                    return;
                }
                if(ModeBot.isScoreLimit()) {
                	
                	if(ModeBot.scoreLimitReach()) {
                		endViewBot();
                		stop();
                	}
                }
                if(ModeBot.isTimeLimit()) {
                	if(chronometer.cpt>0) {
                	chronometer.downTimer();
                	}
                	else if(ModeBot.scoreEgalite()) {
                			chronometer.downTimer();
                			if(!gameRoot.getChildren().contains(lastPoint)) {
                				lastPoint();
                			}
                			
                	}
                	else {
                		endViewBot();
               			stop();
                	}
               	}	
               
                else {
                	chronometer.update();
                }
            
                timer.textProperty().bind(Bindings.format("%02d:%02d:%d%d",  chronometer.mm, chronometer.ss, chronometer.th, chronometer.hd));;

                ModeBot.updateWithBot((now - last) * 1.0e-9); // convert nanoseconds to seconds
                last = now;
                racketA.setY(ModeBot.getRacketA() * scale);
                bot.setY(ModeBot.getBot() * scale);
                ball.setCenterX(ModeBot.getBallX() * scale + xMargin);
                ball.setCenterY(ModeBot.getBallY() * scale);
                textScoreP1.setText(Integer.toString(ModeBot.getScoreP1()));
                textScoreP2.setText(Integer.toString(ModeBot.getScoreP2()));
			 if(pause) {
                		stop();
                }
                if(stop) {
                		stop();
                }
            }
        }.start();
    }
}
