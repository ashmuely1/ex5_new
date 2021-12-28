package world;

import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import util.ColorSupplier;
import world.trees.Tree;

import java.awt.*;
import java.util.Random;

public class Terrain {
    private static final int TERRAIN_DEPTH = 20;
    private static final Color BASE_GROUND_COLOR = new Color(212, 123, 74);

    private final GameObjectCollection gameObjects;
    private final int groundLayer;
    private float groundHeightAtX0;


    public Terrain(GameObjectCollection gameObjects,
                   int groundLayer,
                   Vector2 windowDimensions) {

        this.gameObjects = gameObjects;
        this.groundLayer = groundLayer;
        this.groundHeightAtX0 = windowDimensions.y();
        this.groundHeightAtX0 = windowDimensions.y() * 2 / 3;
    }

    public float groundHeightAt(float x) {
        return (int)((groundHeightAtX0 + (float) Math.sin(x/5) * Block.SIZE * 2)/30) * 30;
    }

    public void createInRange(int minX, int maxX) {
        Random rand = new Random();
        for (int i = (int)((minX / Block.SIZE) * Block.SIZE); i < maxX; i += Block.SIZE){
            for (int j = 0; j < TERRAIN_DEPTH; j++) {
                Renderable ground = new RectangleRenderable(ColorSupplier.approximateColor(BASE_GROUND_COLOR));
                Block block = new Block(new Vector2(i, groundHeightAt(i) + j*Block.SIZE), ground);
                block.setTag("earth");
                gameObjects.addGameObject(block, groundLayer);
            }
            if (rand.nextInt(10) == 0)
                new Tree(gameObjects, i, this, Layer.BACKGROUND + 5);

        }
    }


}