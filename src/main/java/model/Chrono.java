package model;

public class Chrono {

	  public static int timeLimit=0;
	  public int mm  = 0;
	  public int ss  = 0;
	  public int th  = 0;
	  public int hd  = 0;
	  public int cpt = 0;
	  public int secondeTimer=0;
	  
	  




	  public Chrono() {

	  }
	  public void setStart(int n) {
		  cpt=n;
	  }
	  public void downTimer() {
		  	cpt=cpt - 2;
		    secondeTimer=cpt/100;
		    hd=cpt % 10;
		    th=(cpt / 10) % 10;
		    ss=(((cpt/ 100) % 60));
		    mm=((cpt / 6000) % 60);
	  }
	  public void reset() {
	    mm=0;
	    ss=0;
	    th=0;
	    hd=0;
	    cpt=timeLimit;
	    secondeTimer=0;
	  }

	  public void update() {
	    cpt=cpt + 2;
	    secondeTimer=cpt/100;
	    hd=cpt % 10;
	    th=(cpt / 10) % 10;
	    ss=(((cpt/ 100) % 60));
	    mm=((cpt / 6000) % 60);
	  }

}

