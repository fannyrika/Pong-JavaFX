package model;

public class Court {
    // instance parameters
    private final RacketController playerA, playerB;
    private final double width, height; // m
    private final double racketSize = 100.0; // m
    private final double ballRadius = 10.0; // m
    // instance state
    private double racketA; // m
    private double racketB; // m
    private double ballX, ballY; // m
    private double ballSpeedX, ballSpeedY, racketSpeed, acceleration; // m

    public Court(RacketController playerA, RacketController playerB, double width, double height, double acceleration) {
        this.playerA = playerA;
        this.playerB = playerB;
        this.width = width;
        this.height = height;
        this.acceleration= acceleration;
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
        // first, compute possible next position if nothing stands in the way
        double nextBallX = ballX + deltaT * ballSpeedX;
        double nextBallY = ballY + deltaT * ballSpeedY;
        // next, see if the ball would meet some obstacle
        if (nextBallY < 0 || nextBallY > height) {
            ballSpeedY = -ballSpeedY;
            nextBallY = ballY + deltaT * ballSpeedY;
        }
        if ((nextBallX < 0 && nextBallY > racketA && nextBallY < racketA + racketSize)
                || (nextBallX > width && nextBallY > racketB && nextBallY < racketB + racketSize)) {
            ballSpeedX = -ballSpeedX;
            nextBallX = ballX + deltaT * ballSpeedX;
        } else if (nextBallX < 0) {
            return true;
        } else if (nextBallX > width) {
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
    

    void reset() {
        this.racketA = height / 2;
        this.racketB = height / 2;
        this.racketSpeed = 300.0;
        this.ballSpeedX = 200.0;
        this.ballSpeedY = 200.0;
        this.ballX = width / 2;
        this.ballY = height / 2;
    }
}
