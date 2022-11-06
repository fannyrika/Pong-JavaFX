package gui;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.*;
import javafx.scene.control.Label;
import javafx.beans.binding.Bindings;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.image.Image;
import java.io.File;
import java.net.MalformedURLException;
import javafx.scene.paint.ImagePattern;

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
    static boolean stop=false,pause=false;
    private boolean start;
    private Text textScoreP1, textScoreP2;

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

        timer();
        timer.setLayoutX(court.getWidth()/2-50);
        timer.setLayoutY(-50);

        racketA = new Rectangle();
        racketA.setHeight(court.getRacketSize() * scale);
        racketA.setWidth(racketThickness);
        racketA.setFill(Color.DARKGRAY);

        racketA.setX(xMargin - racketThickness);
        racketA.setY(court.getRacketA() * scale);

        racketB = new Rectangle();
        racketB.setHeight(court.getRacketSize() * scale);
        racketB.setWidth(racketThickness);
        racketB.setFill(Color.DARKGRAY);

        racketB.setX(court.getWidth() * scale + xMargin);
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
        textScoreP1.setFont(Font.font(50));
        textScoreP2.setFont(Font.font(50));

        textScoreP1.setX(xMargin - racketThickness + 10);
        textScoreP1.setY(court.getRacketA() * scale -200);

        textScoreP2.setX(court.getWidth() * scale + xMargin -10) ;
        textScoreP2.setY(court.getRacketB() * scale -200);




    }
	public boolean isStart() {
    	return start;
    }
    public void start(boolean b) {
    	 start=b;
    }
    public void addRoot1V1() {

    	gameRoot.getChildren().addAll(racketA, racketB, ball,timer,textScoreP1, textScoreP2);
    }
    public void remove1v1() {
    	gameRoot.getChildren().remove(racketA);
 	   gameRoot.getChildren().remove(ball);
 	   gameRoot.getChildren().remove(racketB);
 	   gameRoot.getChildren().remove(timer);
	   gameRoot.getChildren().remove(textScoreP1);
	   gameRoot.getChildren().remove(textScoreP2);
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
        ball.setFill(Color.YELLOW);

        File file = new File("balle3.jpg");
        String localUrl = file.toURI().toURL().toString();
        Image image = new Image(localUrl);
        ball.setFill(new ImagePattern(image));

        ball.setCenterX(bot.getBallX() * scale + xMargin);
        ball.setCenterY(bot.getBallY() * scale);

        textScoreP1 = new Text("0");
        textScoreP2 = new Text("0");
        textScoreP1.setFont(Font.font(50));
        textScoreP2.setFont(Font.font(50));

        textScoreP1.setX(xMargin - racketThickness + 10);
        textScoreP1.setY(bot.getRacketA() * scale -200);

        textScoreP2.setX(bot.getWidth() * scale + xMargin -10) ;
        textScoreP2.setY(bot.getBot() * scale -200);






    }

	 public void addRoootBot() {
    	 gameRoot.getChildren().addAll(racketA, this.bot, ball,timer,textScoreP1, textScoreP2);
    	}

	public void removeBot() {
	   gameRoot.getChildren().remove(racketA);
	   gameRoot.getChildren().remove(ball);
	   gameRoot.getChildren().remove(this.bot);
	   gameRoot.getChildren().remove(timer);
  	   gameRoot.getChildren().remove(textScoreP1);
	   gameRoot.getChildren().remove(textScoreP2);

    }


    //fonction qui gère l'implementation du timer
    public void timer() {
    	 chronometer =new Chrono();
        timer=new Label();
        timer.setFont(new Font("Arial",40));
        timer.setMinSize(100.00, 100.00);
        timer.setMinHeight(200);
    }
    public void reset1V1() {
    	court.reset();
	court.resetScore();
    	chronometer.reset();

    }
    public void resetBot() {
    	ModeBot.reset();
	ModeBot.resetScore();
    	chronometer.reset();
    }
    public void animate() {
        new AnimationTimer() {
            long last = 0;

            @Override
            public void handle(long now) {
                if (last == 0) { // ignore the first tick, just compute the first deltaT
                    if((now - last)%65==0) {
                        chronometer.update();
                        timer.textProperty().bind(Bindings.format("%02d:%02d:%d%d",  chronometer.mm, chronometer.ss, chronometer.th, chronometer.hd));;
                    }
                      last = now;
                    return;
                }

                chronometer.update();
                timer.textProperty().bind(Bindings.format("%02d:%02d:%d%d",  chronometer.mm, chronometer.ss, chronometer.th, chronometer.hd));;

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

                chronometer.update();
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
