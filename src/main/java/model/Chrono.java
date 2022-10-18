package model;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Chrono {

  public IntegerProperty mm  = new SimpleIntegerProperty();
  public IntegerProperty ss  = new SimpleIntegerProperty();
  public IntegerProperty th  = new SimpleIntegerProperty();
  public IntegerProperty hd  = new SimpleIntegerProperty();
  public IntegerProperty cpt = new SimpleIntegerProperty();


  


  public Chrono() {

  }

  public void reset() {

    mm.set(0);
    ss.set(0);
    th.set(0);
    hd.set(0);
    cpt.set(0);
  }

  public void update() {
    cpt.set(cpt.get() + 2);
    hd.set(cpt.get() % 10);
    th.set((cpt.get() / 10) % 10);
    ss.set(((cpt.get() / 100) % 60));
    mm.set((cpt.get() / 6000) % 60);



  }

}
