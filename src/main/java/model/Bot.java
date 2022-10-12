package model;
import java.util.Random;
public class Bot extends Court{

    double bot;
    private int difficulty;
    public Bot(RacketController playerA,double width,double height,int difficulty){
      super(playerA,null,width,height);
      this.difficulty=difficulty;
    }



    public double getBot(){
      return bot;
    }
    public void setBot(double x){
      bot=x;
    }

    public void updateWithBot(double deltaT,int difficulty) {

        switch (this.getPlayerA().getState()) {
            case GOING_UP:
                this.setRacketA(this.getRacketA()-this.getRacketSpeed() * deltaT);
                if (this.getRacketA() < 0.0) this.setRacketA(0.0);
                break;
            case IDLE:
                break;
            case GOING_DOWN:
            this.setRacketA(this.getRacketA()+this.getRacketSpeed() * deltaT);

                if (this.getRacketA() + this.getRacketSize() > this.getHeight())   this.setRacketA(this.getHeight()-this.getRacketSize());
                break;
        }
        if(difficulty==0){
            if(this.getBallSpeedX()>0){
              Random x= new Random();
              int alea=x.nextInt(20);


              if(alea>=4&&this.getBallSpeedY()<0){
                  bot += this.getBallSpeedY() * deltaT;
                  if (bot< 0.0) bot = 0.0;
                }
              if(this.getBallSpeedY()>0&&alea>=4){
                    bot+= this.getBallSpeedY() * deltaT;
                  if (bot + this.getRacketSize() > this.getHeight()) bot = this.getHeight() - this.getRacketSize();
                }
              }

        }
        if(difficulty==1){
            if(this.getBallSpeedX()>0){
              Random x= new Random();
              int alea=x.nextInt(20);


              if(alea>=3&&this.getBallSpeedY()<0){
                bot += this.getBallSpeedY() * deltaT;
                if (bot< 0.0) bot = 0.0;
                }
              if(this.getBallSpeedY()>0&&alea>=4){
                bot+= this.getBallSpeedY() * deltaT;
              if (bot + this.getRacketSize() > this.getHeight()) bot = this.getHeight() - this.getRacketSize();
                }
              }

        }
        if(difficulty==2){
            if(this.getBallSpeedX()>0){
              Random x= new Random();
              int alea=x.nextInt(30);


              if(alea>=2&&this.getBallSpeedY()<0){
                bot += this.getBallSpeedY() * deltaT;
                if (bot< 0.0) bot = 0.0;
                }
              if(this.getBallSpeedY()>0&&alea>=4){
                bot+= this.getBallSpeedY() * deltaT;
              if (bot + this.getRacketSize() > this.getHeight()) bot = this.getHeight() - this.getRacketSize();
                }
              }
              

        }
        if(this.difficulty==3){
            if(this.getBallSpeedX()>0){
              if(this.getBallSpeedY()<0){
                bot += this.getBallSpeedY() * deltaT;
                if (bot< 0.0) bot = 0.0;
                }
              if(this.getBallSpeedY()>0){
                bot+= this.getBallSpeedY() * deltaT;
              if (bot + this.getRacketSize() > this.getHeight()) bot = this.getHeight() - this.getRacketSize();
                }
              }

        }
        if (updateBallWithBot(deltaT)) reset();
    }

    private boolean updateBallWithBot(double deltaT){
      double nextBallX = this.getBallX() + deltaT * this.getBallSpeedX();
      double nextBallY = this.getBallY() + deltaT * this.getBallSpeedY();
      // next, see if the ball would meet some obstacle
      if (nextBallY < 0 || nextBallY > this.getHeight()) {
          this.setBallSpeedY(this.getBallSpeedY()-2.0*this.getBallSpeedY());
          nextBallY = this.getBallY() + deltaT * this.getBallSpeedY();
      }
      if ((nextBallX < 0 && nextBallY > this.getRacketA() && nextBallY < this.getRacketA() + this.getRacketSize())
              || (nextBallX > this.getWidth() && nextBallY > bot && nextBallY < bot + this.getRacketSize())) {
          this.setBallSpeedX(this.getBallSpeedX()-2*this.getBallSpeedX());
          nextBallX = this.getBallX() + deltaT * this.getBallSpeedX();
      } else if (nextBallX < 0) {
          return true;
      } else if (nextBallX > this.getWidth()) {
          return true;
      }
      this.setBallx(nextBallX);
      this.setBallY(nextBallY);
      return false;
    }
    void reset() {
        this.setRacketA(this.getHeight()/2);
        this.setBallSpeedX(200.0);
        this.setBallSpeedY(400.0);
        this.setBallx(this.getWidth()/2);
        this.setBallY(this.getHeight()/2);


    }

}
