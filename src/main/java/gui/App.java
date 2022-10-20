package gui;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.*;
import model.RacketController;
import javafx.stage.StageStyle;
import java.io.File;
import java.net.MalformedURLException;
import javafx.scene.image.Image;
import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.awt.GridLayout;
import java.awt.*;
import javax.swing.*;



public class App extends Application {

  public static void f(){

  			  // On crée la fenêtre
  			    JFrame fenetre = new JFrame("Menu PONG");
  			  // On la personnalise
  			    fenetre.setSize(700,700);
  			    fenetre.setLayout(null); // On définit la disposition sur null
  			      fenetre.setLocationRelativeTo(null);
  			      //fenetre.setOpacity(0.1f);
  			    JPanel p = new JPanel();
  			    //p.setBounds(150,70,150,150);
  			    //p.setSize(500,30);
  			    Color lavande = new Color(216,191,216);

  			    //p.setBackground(lavande);
  			    //fenetre.setOpacity(0.5f);


  			  // On crée un label pour afficher le titre du jeu sur la page du menu
  			    JLabel pong = new JLabel("PONG");
  			    pong.setFont(new Font("Arial", Font.BOLD, 50));
  			   // p.add(pong);

  			  // Demander le nom du joueur
  			    JTextField nom = new JTextField("Votre nom",15);
  			    nom.setSize(10,15);
  			    //fenetre.getContentPane().add(nom);
  			  //  p.add(nom);

  			 // Pour arranger l'affichage
  			   GridLayout g = new GridLayout(12,3); // (nbr de lignes,colonnes)
  			   g.setVgap(20); // (distance entre les lignes)
  			   p.setLayout(g);


  			  // On crée les boutons


  			   // Premier bouton : jouer, qui ramène à la fenêtre du jeu
  			    JButton a = new JButton("Jouer");
  			    	a.setFont(new Font("Yrsa Medium", Font.ITALIC, 30));
  			    	a.setBorder(BorderFactory.createEmptyBorder(1,3,2,5));
  				    Color purple = new Color(124, 55, 9);
  				    a.setForeground(purple); // Couleur du texte
  				    //a.setContentAreaFilled(false);
  				    a.setLayout(null);
  				    a.setBounds(200, 100, 100, 60);
  				    p.add(a);

  				 /*  a.addActionListener(new ActionListener()

  				    {
  				      public void actionPerformed(ActionEvent e)
  				 */
  				    a.addActionListener(e -> {
  				    	JFrame maNewFrame = new JFrame(); // fenêtre du jeu
  				    	  maNewFrame.setBounds( 0, 0, 200, 200 );
  				    	  maNewFrame.setVisible(true);
  				    	  fenetre.dispose();
  				       });


  			    // Deuxième bouton : qui ramène à une fenêtre qui explique les instructions du jeu
  			    JButton b = new JButton("Instructions");
  			    	b.setFont(new Font("Yrsa Medium", Font.ITALIC, 30));
  			    	b.setBorder(BorderFactory.createEmptyBorder(1,3,2,5));
  				    b.setForeground(purple);
  				    b.setLayout(null);
  				    b.setBounds(40, 100, 100, 60);
  				    //fenetre.getContentPane().add(b);
  				  //  p.add(b);
  				    // On crée la page des instructions
  				    b.addActionListener(e -> {
  				    	JFrame instructions = new JFrame();
  					    instructions.setSize(500,500);
  					    instructions.setTitle("PONG Comment jouer");
  					    JPanel p1 = new JPanel();
  					    instructions.setContentPane(p1);
  					    p1.setBackground(lavande);
  					    JButton retour = new JButton("Retour au menu");
  					    retour.setForeground(purple);
  					    instructions.getContentPane().add(retour);
  					    instructions.setVisible(true);
  					    retour.addActionListener(ee -> {
  					    	fenetre.setVisible(true);
  					    	instructions.dispose();
  					    });
  				    	 instructions.setVisible(true);
  				    	 fenetre.dispose(); // mouais ?
  				       });



  				// Troisième bouton : ramène à une fenêtre où on choisit le mode de jeu
  				    // + *********** on pourra aussi choisir la vitesse du bot/de la balle
  			    JButton c = new JButton("Mode");
  			    	c.setFont(new Font("Yrsa Medium", Font.ITALIC, 30));
  			    	c.setBorder(BorderFactory.createEmptyBorder(1,3,2,5));
  				    c.setForeground(purple);
  				    //fenetre.getContentPane().add(c);
  				//    p.add(c);
  				    c.addActionListener(e -> {
  				    	JFrame mode = new JFrame();
  					    mode.setSize(500,500);
  					    mode.setTitle("PONG Comment jouer");
  					    JPanel p1 = new JPanel();
  					    mode.setContentPane(p1);
  					    p1.setBackground(lavande);
  					    JButton retour = new JButton("Retour au menu");
  					    retour.setForeground(purple);
  					    mode.getContentPane().add(retour);
  					    mode.setVisible(true);
  					    retour.addActionListener(ee -> {
  					    	fenetre.setVisible(true);
  					    	mode.dispose();
  					    });
  				    	 mode.setVisible(true);
  				    	 fenetre.dispose(); // mouais ?
  				       });


  				// Quatrième bouton : ferme la fenêtre du menu
  			    JButton q = new JButton("Quitter");
  			    	q.setFont(new Font("Yrsa Medium", Font.ITALIC, 25));
  			    	q.setBorder(BorderFactory.createEmptyBorder(1,3,2,5));
  				    q.setForeground(purple);
  				    //fenetre.getContentPane().add(q);
  				    //p.add(q);
  			      q.addActionListener(e -> {
  			          fenetre.dispose();
  			       });

  			    //fenetre.add(p);
  			      p.add(pong);
  			      //p.add(nom); // inutile
  			      p.add(a);
  			      p.add(b);
  			      p.add(c);
  			      p.add(q);
  			      fenetre.setContentPane(p);
  			    fenetre.setVisible(true); // Rend la fenêtre et tout son contenu visibles
  			  }

    @Override
    public void start(Stage primaryStage) throws MalformedURLException {
        var root = new Pane();
        var gameScene = new Scene(root);


        //add an icon for the window
        File file = new File("pongicon.png");
        String localUrl = file.toURI().toURL().toString();
        Image image = new Image(localUrl);


      /*  Media media = new Media(new File("pongmusic.mp3").toURI().toString());
        MediaPlayer mP = new MediaPlayer(media);
        mP.setCycleCount(mP.INDEFINITE);
        mP.setAutoPlay(true);

        MediaView mV = new MediaView(mP);
        root.getChildren().add(mV);*/




        class Player implements RacketController {
            State state = State.IDLE;

            @Override
            public State getState() {
                return state;
            }
        }
        var playerA = new Player();
        var playerB = new Player();
        gameScene.setOnKeyPressed(ev -> {
            switch (ev.getCode()) {
                case Z:
                    playerA.state = RacketController.State.GOING_UP;
                    break;
                case S:
                    playerA.state = RacketController.State.GOING_DOWN;
                    break;
                case UP:
                    playerB.state = RacketController.State.GOING_UP;
                    break;
                case DOWN:
                    playerB.state = RacketController.State.GOING_DOWN;
                    break;
            }
        });
        gameScene.setOnKeyReleased(ev -> {
            switch (ev.getCode()) {
                case Z:
                    if (playerA.state == RacketController.State.GOING_UP) playerA.state = RacketController.State.IDLE;
                    break;
                case S:
                    if (playerA.state == RacketController.State.GOING_DOWN) playerA.state = RacketController.State.IDLE;
                    break;
                case UP:
                    if (playerB.state == RacketController.State.GOING_UP) playerB.state = RacketController.State.IDLE;
                    break;
                case DOWN:
                    if (playerB.state == RacketController.State.GOING_DOWN) playerB.state = RacketController.State.IDLE;
                    break;
            }
        });
        var bot = new Bot(playerA,1,1.000001);//test bot;
        var court = new Court(playerA,playerB,1.000001);
        var gameView = new GameView(court, root, 1.0);
        //var gameView2 = new GameView(bot, root, 1.0);//test Bot;
        primaryStage.setTitle("Pong");
        primaryStage.getIcons().add(image);
        primaryStage.setScene(gameScene);
        primaryStage.show();
        gameView.animate();
        //gameView2.animateBot();//test Bot;
    }
}
