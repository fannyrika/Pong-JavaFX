package model;

public class Chrono {

  public int mm  = 0;
  public int ss  = 0;
  public int th  = 0;
  public int hd  = 0;
  public int cpt = 0;





  public Chrono() {

  }

  public void reset() {
    mm=0;
    ss=0;
    th=0;
    hd=0;
    cpt=0;
  }

  public void update() {
    cpt=cpt + 1;
    hd=cpt % 10;
    th=(cpt / 10) % 10;
    ss=(((cpt/ 100) % 60));
    mm=((cpt / 6000) % 60);
  }

}
