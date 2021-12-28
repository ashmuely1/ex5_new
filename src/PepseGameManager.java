import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import world.Block;
import world.Sky;
import world.Terrain;
import world.daynight.Night;
import world.daynight.Sun;
import world.daynight.SunHalo;

import java.awt.*;

public class PepseGameManager extends GameManager {

    private static final int BlocksInSeason = 100;
    private static final int DAY_LENGTH = 20;

    private static final int GROUND_LAYER = Layer.STATIC_OBJECTS;
    private static final int TREE_LAYER = Layer.BACKGROUND + 5;
    private static final int LEAF_LAYER = TREE_LAYER + 1;


    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);


        Sky.create(gameObjects(), windowController.getWindowDimensions(), Layer.BACKGROUND);


        Terrain terr = new Terrain(gameObjects(), Layer.STATIC_OBJECTS,
                windowController.getWindowDimensions());
        terr.createInRange(0, (int) (BlocksInSeason * Block.SIZE));

        Night.create(gameObjects(), Layer.FOREGROUND, windowController.getWindowDimensions(), DAY_LENGTH);

        GameObject sun = Sun.create( gameObjects(), Layer.BACKGROUND, windowController.getWindowDimensions(),
                DAY_LENGTH) ;

        SunHalo.create(gameObjects(), Layer.BACKGROUND + 10, sun, new Color(255, 255, 0, 20));

        this.gameObjects().layers().shouldLayersCollide(GROUND_LAYER, LEAF_LAYER, true);

    }

    public static void main(String[] args) {
        new PepseGameManager().run();
    }

}