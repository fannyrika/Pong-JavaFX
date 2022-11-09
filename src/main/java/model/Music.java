

package model;


import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;


public class Music {

	public Media media;
    public MediaPlayer mP;
    public MediaView mV;

	public Music(){
    	media = new Media(new File("fallen_leaves.mp3").toURI().toString());
    	mP = new MediaPlayer(media);
        mP.setCycleCount(mP.INDEFINITE);
        mP.setAutoPlay(true);

        mV = new MediaView(mP);
    }

}
