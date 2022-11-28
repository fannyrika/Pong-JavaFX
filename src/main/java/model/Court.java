package model;

import javafx.geometry.Rectangle2D;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import java.io.File;
import java.util.Random;

import javafx.stage.Screen;
public class Court {
    // instance parameters
    private final RacketController playerA, playerB;
    public final double width, height; // m
    private final double racketSize = 200.0; // m
    private final double ballRadius = 20.0; // m
    // instance state
    public PlayerBoost p1,p2;
    private double racketA; // m
    private double racketB; // m
    public double ballX, ballY; // m
    public double ballSpeedX, ballSpeedY, racketSpeed, acceleration; // m
    private int scoreP1 = 0;
    private int scoreP2 = 0;
    public Media mediaBall;
    public Media activationBoost;
    boolean limitTime, scoreLimit;
    public int timeMM,timeSS,score;


    Rectangle2D screen = Screen.getPrimary().getVisualBounds();
    public Court(RacketController playerA, RacketController playerB, double acceleration,BallState playerABall,BallState playerBball) {
        this.playerA = playerA;
        this.playerB = playerB;
        this.width = screen.getWidth()-racketSize/2;//visual bounds
        this.height = screen.getHeight();//visual bounds
        this.acceleration= acceleration;
        mediaBall = new Media(new File("src/main/resources/gui/AudioBoost.mp3").toURI().toString());
        activationBoost=new Media(new File("src/main/resources/gui/AudioBoostActive.mp3").toURI().toString());
        p1=new PlayerBoost(playerABall);
        p2=new PlayerBoost(playerBball);

        reset();
    }
    public double getRacketSpeed(){
      return this.racketSpeed;
    }
    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getRacketSize() {
        return racketSize;
    }

    public RacketController getPlayerA(){
      return playerA;
    }
    public double getRacketA() {
        return racketA;
    }
    public void setRacketA(double racketA){

      this.racketA=racketA;
    }

    public double getRacketB() {
        return racketB;
    }

    public double getBallX() {
        return ballX;
    }

    public double getBallY() {
        return ballY;
    }

     public double getBallSpeedX(){
       return ballSpeedX;
     }
     public void setBallx(double x){
       ballX=x;
     } public void setBallY(double x){
         ballY=x;
       }
     public double getBallSpeedY(){
       return ballSpeedY;
     }
     public void setBallSpeedX(double x){
       ballSpeedX=x;
     }
     public void setBallSpeedY(double x){
       ballSpeedY=x;
     }
     public void setScoreP1(int s){
        scoreP1=s;
     }
     public void setScoreP2(int s){
        scoreP2=s;
     }
     public int getScoreP1(){
        return scoreP1;
     }
     public int getScoreP2(){
        return scoreP2;
     }
     public void setRacketSpeed(double s){
     	racketSpeed=s;
     }
    public void update(double deltaT) {

        switch (playerA.getState()) {
            case GOING_UP:
                racketA -= racketSpeed * deltaT;
                if (racketA < 0.0) racketA = 0.0;
                break;
            case IDLE:
                break;
            case GOING_DOWN:
                racketA += racketSpeed * deltaT;
                if (racketA + racketSize > height) racketA = height - racketSize;
                break;
        }
        switch (playerB.getState()) {
            case GOING_UP:
                racketB -= racketSpeed * deltaT;
                if (racketB < 0.0) racketB = 0.0;
                break;
            case IDLE:
                break;
            case GOING_DOWN:
                racketB += racketSpeed * deltaT;
                if (racketB + racketSize > height) racketB = height - racketSize;
                break;
        }
        if (updateBall(deltaT)) reset();
    }


    /**
     * @return true if a player lost
     */
    private boolean updateBall(double deltaT) {
        Media media = new Media(new File("bruitagerqt.mp3").toURI().toString());
        MediaPlayer mP = new MediaPlayer(media);
        MediaView mV = new MediaView(mP);
        Media media2 = new Media(new File("but.mp3").toURI().toString());
        MediaPlayer mP2 = new MediaPlayer(media2);
        MediaView mV2 = new MediaView(mP2);
        // first, compute possible next position if nothing stands in the way
        double nextBallX = ballX + deltaT * ballSpeedX;
        double nextBallY = ballY + deltaT * ballSpeedY;
        // next, see if the ball would meet some obstacle
        if (nextBallY < 0 || nextBallY > height) {
            ballSpeedY = -ballSpeedY;
            nextBallY = ballY + deltaT * ballSpeedY;
        }
        if ((nextBallX < 0 && nextBallY > racketA && nextBallY < racketA + racketSize)
                || (nextBallX > width-100 && nextBallY > racketB && nextBallY < racketB + racketSize)) {
            mP.setAutoPlay(true);
            ballSpeedX = -ballSpeedX;
            nextBallX = ballX + deltaT * ballSpeedX;
        } else if (nextBallX < 0) {
            mP2.setAutoPlay(true);
            scoreP2++;
            return true;
        } else if (nextBallX > width-100) {
            mP2.setAutoPlay(true);
            scoreP1++;
            return true;
        }
        ballX = nextBallX;
        ballY = nextBallY;
        this.updateSpeed();
        return false;
    }

    public double getBallRadius() {
        return ballRadius;
    }


    public void updateSpeed() {
    	racketSpeed= racketSpeed * acceleration;
    	ballSpeedX= ballSpeedX * acceleration;
    	ballSpeedY= ballSpeedY * acceleration;
    }
    public void setTimeLimit(int mm,int ss) {
    	this.timeMM=mm;
    	this.timeSS=ss;
    }
    public boolean isTimeLimit() {
    	return limitTime;
    }
    public void limitTime(boolean b) {
    	limitTime=b;
    }
    public boolean timeEnd(int mm,int ss) {
    	return mm==timeMM && ss==timeSS;
    }
    
    public boolean isScoreLimit() {
    	return scoreLimit;
    }
    public void scoreLimit(boolean b) {
    	scoreLimit=b;
    }
    public void setScoreLimit(int score) {
    	this.score=score;
    }
    public boolean scoreLimitReach() {
    	return scoreP1==score||scoreP2==score;
    }
    public boolean scoreEgalite() {
    	return scoreP1==scoreP2;
    }
    public boolean player1Win() {
    	return scoreP1>scoreP2;
    }
    public boolean player2Win() {
    	return scoreP1<scoreP2;
    }
    public void resetScore(){
	 scoreP1 = 0;
   	 scoreP2 = 0;
	}

    public void reset() {
        this.racketA = height / 2;
        this.racketB = height / 2;
        this.racketSpeed = 300.0;
    	this.racketA = height / 2;
        this.racketB = height / 2;
        Random rd=new Random();
        int x=rd.nextInt(4);
        int y=rd.nextInt(200)+100;
	   	if(x==0) {
		        this.ballSpeedX=-200-y%200;
		        this.ballSpeedY=-y;
	   	}
	   	else if(x==1) {
	   		this.ballSpeedX=200+y%200;
	        this.ballSpeedY=-y;
	   	}
		else if(x==2) {
	   		this.ballSpeedX=-200-y%200;
	        this.ballSpeedY=y;
	   	}
		else {
	   		this.ballSpeedX=200+y%200;
	        this.ballSpeedY=y;
	   	}

        this.ballX = width / 2;
        this.ballY = Math.random()*((height-100))+50;
    }
}
