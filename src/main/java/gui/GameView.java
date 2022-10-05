package gui;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Court;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class GameView {
    // class parameters
    private final Court court;
    private final Pane gameRoot; // main node of the game
    private final double scale;
    private final double xMargin = 50.0, racketThickness = 10.0; // pixels

    // children of the game main node
    private final Rectangle racketA, racketB;
    private final Circle ball;
    
    private Text textScoreA, textScoreB;
    //private final Text textScoreB;
    

    /**
     * @param court le "modèle" de cette vue (le terrain de jeu de raquettes et tout ce qu'il y a dessus)
     * @param root  le nœud racine dans la scène JavaFX dans lequel le jeu sera affiché
     * @param scale le facteur d'échelle entre les distances du modèle et le nombre de pixels correspondants dans la vue
     */
    public GameView(Court court, Pane root, double scale) {
        this.court = court;
        this.gameRoot = root;
        this.scale = scale;

        root.setMinWidth(court.getWidth() * scale + 2 * xMargin);
        root.setMinHeight(court.getHeight() * scale);

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

        ball = new Circle();
        ball.setRadius(court.getBallRadius());
        ball.setFill(Color.BLACK);

        ball.setCenterX(court.getBallX() * scale + xMargin);
        ball.setCenterY(court.getBallY() * scale);

        //textScoreA = new Text(String.valueOf(court.getScoreA()));
        textScoreA = new Text("Joueur A");
        //textScoreA = new Text(court.getTextScoreA());
        textScoreA.setX(50);
        textScoreA.setY(50);
        textScoreA.setFont(Font.font("Verdana",50));
        textScoreA.setFill(Color.BLACK);

        //textScoreB = new Text(String.valueOf(court.getScoreB()) );
        textScoreB = new Text("Joueur B");
        //textScoreB = new Text(court.getTextScoreB());
        textScoreB.setX(court.getWidth()-200);
        textScoreB.setY(50);
        textScoreB.setFont(Font.font("Verdana",50));
        textScoreB.setFill(Color.BLACK);
        /*
         * StackPane stp= new StackPane();
            Rectangle date = new Rectangle();
            Text h = new Text(LocalDate.now().toString());
            date.setHeight(40);
            date.setWidth(120);
            h.setTextFill(Color.WHITE);
            stp.getChildren().addAll(date,h);
         */

        gameRoot.getChildren().addAll(racketA, racketB, ball, textScoreA, textScoreB);


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
                court.update((now - last) * 1.0e-9); // convert nanoseconds to seconds
                last = now;
                racketA.setY(court.getRacketA() * scale);
                racketB.setY(court.getRacketB() * scale);
                ball.setCenterX(court.getBallX() * scale + xMargin);
                ball.setCenterY(court.getBallY() * scale);
            }
        }.start();
    }
}
