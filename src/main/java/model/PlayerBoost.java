package model;
import javafx.scene.image.Image;
import javafx.scene.media.*;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import java.io.File;
public class PlayerBoost{
	public Boost boost;
	public Court court;
	BallState player;
	
	public boolean active;//active nous permet de savoir si l'utilisateur a cliqué sur la touche boost
	public boolean deleteBoost;
	public int currentSS,currentTH;
	public double currentballSpeedX,currentballSpeedY;
	public Label playerBoost;
	
	public PlayerBoost(BallState player) {
		
		active=false;
		boost=null;
		this.player=player;
	}
	
	public void activeBoost() {
		if(boost!=null) {
		switch (player.getStateb()) {
        	case FAST:	
        	currentballSpeedX=court.ballSpeedX;
        	currentballSpeedY=court.ballSpeedY;
        	deleteBoost=true;
        	active=true;
        	MediaPlayer mp=new MediaPlayer(court.activationBoost);
        	mp.play();
        	currentSS=boost.chronometer.ss;
        	currentTH=boost.chronometer.th;
		boost=null;
			break;
        case IDLE:
            break;
		default:
			break;
			
		
		}
		}
	}
	void resetSpeedBall() {
		if(court.ballSpeedX<0) {
  			 court.ballSpeedX=-Math.abs(currentballSpeedX);
  		 }
  		 if(court.ballSpeedX>0) {
  			 court.ballSpeedX=Math.abs(currentballSpeedX);
  		 }
  		 if(court.ballSpeedY>0) {
  			 court.ballSpeedY=Math.abs(currentballSpeedY);
  		 }
  		 if(court.ballSpeedY<0) {
  			 court.ballSpeedY=-Math.abs(currentballSpeedY);
  		 }        
	}
	//fonction qui boost la balle pendant x seconde et x dixieme;
	public void boostPlayer(int seconde,int dixieme) {
		if(active) {
       	 
       	 if(seconde*10+dixieme<(currentSS+2)*10+currentTH) {
       			court.ballSpeedX*=1.02;
       			court.ballSpeedY*=1.02;
       	 }
       	 else {
       		 active=false;
       		 resetSpeedBall();
       	 }
        }
	}
	//ajoute le boost dans la case bonus du joueur;
	public void addBoostPlayer(boolean side) {
		if(side) {
			Image img = new Image(new File("src/main/resources/gui/boost.png").toURI().toString());
		    ImageView view = new ImageView(img);
		    view.setFitHeight(80);
		    view.setFitWidth(80);
		    view.setPreserveRatio(true);
		    playerBoost=new Label();
		    playerBoost.setGraphic(view);
		    playerBoost.setTranslateX(30);
		    playerBoost.setTranslateY(110);
		}
		else {
			Image img = new Image(new File("src/main/resources/gui/boost.png").toURI().toString());
		    ImageView view = new ImageView(img);
		    view.setFitHeight(80);
		    view.setFitWidth(80);
		    view.setPreserveRatio(true);
		    playerBoost=new Label();
		    playerBoost.setGraphic(view);
		    playerBoost.setTranslateX(court.width);
		    playerBoost.setTranslateY(110);
		}
	}
	
}
