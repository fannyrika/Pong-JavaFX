package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.control.Label;
import javafx.scene.media.*;
import java.io.File;


public class Boost {
	public final double currentBallSpeedX,currentBallSpeedY;
	public int currentT;
	public double x,y;
	public Court court;
	public Label boost;
	public Label boostPlayer;
	public Chrono chronometer;
	
	
	
	public Boost(Court court,Chrono chronometer,double ballSpeedX,double ballSpeedY){
		this.currentBallSpeedX=ballSpeedX;
		this.currentBallSpeedY=ballSpeedY;
		this.court=court;
		this.chronometer=chronometer;
		
		x=(Math.random()*court.width*3/4-court.width/4+1)+court.width/4;
		y=(Math.random()*(court.height-200-40+1)+40);
		
		
		Image img = new Image(new File("src/main/resources/gui/boost.png").toURI().toString());
	    ImageView view = new ImageView(img);
	    view.setFitHeight(150);
	    view.setFitWidth(150);
	    view.setPreserveRatio(true);
	    boost=new Label();
	    boost.setGraphic(view);
	    boost.setTranslateX(x);
	    boost.setTranslateY(y);
	    
	}	
	
	
	public boolean isBallTouchBoost() {
		if(court.ballX<=x&&court.ballX>=x-150&&court.ballY>=y+20&&court.ballY<=y+130) {
		        
			if(court.ballSpeedX>0) {
				if(court.p1.boost!=null)return false;
				
				court.p1.boost=new Boost(court, chronometer,currentBallSpeedX, currentBallSpeedY);
				court.p1.court=court;
				
				return true;
				
				
			}
			if(court.ballSpeedX<0) {
				if(court.p2.boost!=null)return false;
				
				court.p2.boost=new Boost(court, chronometer,currentBallSpeedX, currentBallSpeedY);
				court.p2.court=court;
				return true;
			}
			
			
		
		}
		return false;	
	}
}
