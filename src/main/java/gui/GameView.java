package gui;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.*;
import javafx.scene.control.Label;
import javafx.beans.binding.Bindings;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.File;

public class GameView {
    // class parameters
    private final Bot ModeBot;
    private final Court court;
    private final Pane gameRoot; // main node of the game
    private final double scale;
    private final double xMargin = 50.0, racketThickness = 20.0; // pixels

    // children of the game main node
    private Label timer;
    Chrono chronometer;
    private final Rectangle racketA, racketB ,bot;
    private final Circle ball;
    Boost boost;
    private Text textScoreP1, textScoreP2;

    /**
     * @param court le "modèle" de cette vue (le terrain de jeu de raquettes et tout ce qu'il y a dessus)
     * @param root  le nœud racine dans la scène JavaFX dans lequel le jeu sera affiché
     * @param scale le facteur d'échelle entre les distances du modèle et le nombre de pixels correspondants dans la vue
     */
    public GameView(Court court, Pane root, double scale) {
        this.court = court;
	      this.ModeBot = null;
        this.gameRoot = root;
        this.scale = scale;

        root.setMinWidth(court.getWidth() * scale + 2 * xMargin);
        root.setMinHeight(court.getHeight() * scale);

        timer();
        timer.setLayoutX(court.getWidth()/2-50);

        racketA = new Rectangle();
        racketA.setHeight(court.getRacketSize() * scale);
        racketA.setWidth(racketThickness);
        racketA.setFill(Color.BLACK);

        racketA.setX(xMargin - racketThickness);
        racketA.setY(court.getRacketA() * scale);

        racketB = new Rectangle();
        racketB.setHeight(court.getRacketSize() * scale);
        racketB.setWidth(racketThickness);
        racketB.setFill(Color.BLACK);

        racketB.setX(court.getWidth() * scale + xMargin);
        racketB.setY(court.getRacketB() * scale);
		    this.bot = null;


        ball = new Circle();
        ball.setRadius(court.getBallRadius());
        ball.setFill(Color.BLACK);

        ball.setCenterX(court.getBallX() * scale + xMargin);
        ball.setCenterY(court.getBallY() * scale);

        textScoreP1 = new Text("0");
        textScoreP2 = new Text("0");
        textScoreP1.setFont(Font.font(25));
        textScoreP2.setFont(Font.font(25));

        textScoreP1.setX(xMargin - racketThickness + 10);
        textScoreP1.setY(court.getRacketA() * scale -200);

        textScoreP2.setX(court.getWidth() * scale + xMargin -10) ;
        textScoreP2.setY(court.getRacketB() * scale -200);

	  //Gére l'affichage des bonus des joueurs;
	  Image img = new Image(new File("src/main/resources/gui/Bonus.png").toURI().toString());
	  ImageView view = new ImageView(img);
	  view.setFitHeight(200);
	  view.setFitWidth(200);
	  view.setPreserveRatio(true);
	  Label caseBonusLeft=new Label();
	  caseBonusLeft.setGraphic(view);
	  caseBonusLeft.setTranslateX(-49);
	  caseBonusLeft.setTranslateY(-30);
	    
	  ImageView viewR=new ImageView(img);
	  viewR.setFitHeight(200);
	  viewR.setFitWidth(200);
	  viewR.setPreserveRatio(true);
        Label caseBonusRight= new Label();
        caseBonusRight.setGraphic(viewR);
        caseBonusRight.setTranslateX(court.getWidth()-70);
	  caseBonusRight.setTranslateY(-30);
       
        
        gameRoot.getChildren().addAll(racketA, racketB, ball,timer,caseBonusLeft,caseBonusRight);


    }
    public GameView(Bot bot, Pane root, double scale) {
        this.court = null;
	      this.ModeBot = bot;
        this.gameRoot = root;
        this.scale = scale;

        root.setMinWidth(bot.getWidth() * scale + 2 * xMargin);
        root.setMinHeight(bot.getHeight() * scale);

        timer();
        timer.setLayoutX(bot.getWidth()/2-50);

        racketA = new Rectangle();
        racketA.setHeight(bot.getRacketSize() * scale);
        racketA.setWidth(racketThickness);
        racketA.setFill(Color.GREEN);

        racketA.setX(xMargin - racketThickness);
        racketA.setY(bot.getRacketA() * scale);
	      this.racketB = null;

        this.bot = new Rectangle();
        this.bot.setHeight(bot.getRacketSize() * scale);
        this.bot.setWidth(racketThickness);
        this.bot.setFill(Color.RED);

        this.bot.setX(bot.getWidth() * scale + xMargin);
        this.bot.setY(bot.getBot() * scale);

        ball = new Circle();
        ball.setRadius(bot.getBallRadius());
        ball.setFill(Color.BLACK);

        ball.setCenterX(bot.getBallX() * scale + xMargin);
        ball.setCenterY(bot.getBallY() * scale);

        textScoreP1 = new Text("0");
        textScoreP2 = new Text("0");
        textScoreP1.setFont(Font.font(25));
        textScoreP2.setFont(Font.font(25));

        textScoreP1.setX(xMargin - racketThickness + 10);
        textScoreP1.setY(bot.getRacketA() * scale -200);

        textScoreP2.setX(bot.getWidth() * scale + xMargin -10) ;
        textScoreP2.setY(bot.getBot() * scale -200);



        gameRoot.getChildren().addAll(racketA, this.bot, ball, textScoreP1, textScoreP2,timer);


    }
    //fonction qui gère l'implementation du timer
    public void timer() {
    	 chronometer =new Chrono();
        timer=new Label();
        timer.setFont(new Font("Arial",40));
        timer.setMinSize(100.00, 100.00);
        timer.setMinHeight(200);

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
                if((now - last)%50==0) {
                    chronometer.update();
                    timer.textProperty().bind(Bindings.format("%02d:%02d:%d%d",  chronometer.mm, chronometer.ss, chronometer.th, chronometer.hd));;
                    }
		    boost();
                court.update((now - last) * 1.0e-9); // convert nanoseconds to seconds
                last = now;
                racketA.setY(court.getRacketA() * scale);
                racketB.setY(court.getRacketB() * scale);
                ball.setCenterX(court.getBallX() * scale + xMargin);
                ball.setCenterY(court.getBallY() * scale);
                textScoreP1.setText(Integer.toString(court.getScoreP1()));
                textScoreP2.setText(Integer.toString(court.getScoreP2()));
            }
        }.start();
    }
   	
   public void boost() {
    	 //toutes les 15 secondes fait spawn un boost si il y en a pas;
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


       court.p1.boostPlayer(chronometer.ss,chronometer.th);
       court.p2.boostPlayer(chronometer.ss,chronometer.th);
       
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
                if((now - last)%20==0) {
                    chronometer.update();
                    timer.textProperty().bind(Bindings.format("%02d:%02d:%d%d",  chronometer.mm, chronometer.ss, chronometer.th, chronometer.hd));;
                  }
                ModeBot.updateWithBot((now - last) * 1.0e-9); // convert nanoseconds to seconds
                last = now;
                racketA.setY(ModeBot.getRacketA() * scale);
                bot.setY(ModeBot.getBot() * scale);
                ball.setCenterX(ModeBot.getBallX() * scale + xMargin);
                ball.setCenterY(ModeBot.getBallY() * scale);
                textScoreP1.setText(Integer.toString(ModeBot.getScoreP1()));
                textScoreP2.setText(Integer.toString(ModeBot.getScoreP2()));
            }
        }.start();
    }
}
