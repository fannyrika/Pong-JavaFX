package gui;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameView {
    // class parameters
    private final Bot ModeBot;
    private final Court court;
    private final Pane gameRoot; // main node of the game
    private final double scale;
    private final double xMargin = 50.0, racketThickness = 10.0; // pixels

    // children of the game main node
    private final Rectangle racketA, racketB ,bot;
    private final Circle ball;

    /**
     * @param court le "modèle" de cette vue (le terrain de jeu de raquettes et tout ce qu'il y a dessus)
     * @param root  le nœud racine dans la scène JavaFX dans lequel le jeu sera affiché
     * @param scale le facteur d'échelle entre les distances du modèle et le nombre de pixels correspondants dans la vue
     */
    public GameView(Court court, Pane root, double scale) {
        this.court = court;
		this.ModeBot = null;
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
		this.bot = null;
		

        ball = new Circle();
        ball.setRadius(court.getBallRadius());
        ball.setFill(Color.BLACK);

        ball.setCenterX(court.getBallX() * scale + xMargin);
        ball.setCenterY(court.getBallY() * scale);

        gameRoot.getChildren().addAll(racketA, racketB, ball);


    }
    public GameView(Bot bot, Pane root, double scale) {
        this.court = null;
		this.ModeBot = bot;
        this.gameRoot = root;
        this.scale = scale;

        root.setMinWidth(bot.getWidth() * scale + 2 * xMargin);
        root.setMinHeight(bot.getHeight() * scale);

        racketA = new Rectangle();
        racketA.setHeight(bot.getRacketSize() * scale);
        racketA.setWidth(racketThickness);
        racketA.setFill(Color.BLACK);

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
        ball.setFill(Color.BLACK);

        ball.setCenterX(bot.getBallX() * scale + xMargin);
        ball.setCenterY(bot.getBallY() * scale);




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
    public void animateBot() {
        new AnimationTimer() {
            long last = 0;

            @Override
            public void handle(long now) {
                if (last == 0) { // ignore the first tick, just compute the first deltaT
                    last = now;
                    return;
                }
                ModeBot.updateWithBot((now - last) * 1.0e-9); // convert nanoseconds to seconds
                last = now;
                racketA.setY(ModeBot.getRacketA() * scale);
                bot.setY(ModeBot.getBot() * scale);
                ball.setCenterX(ModeBot.getBallX() * scale + xMargin);
                ball.setCenterY(ModeBot.getBallY() * scale);
            }
        }.start();
    }
}
