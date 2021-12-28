package world.trees;

import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import util.ColorSupplier;
import world.Block;
import world.Terrain;

import java.awt.*;
import java.util.Random;

public class Tree {

    public Tree(GameObjectCollection gameObjects, int x, Terrain terr, int layer){

        float base_y = terr.groundHeightAt(x);
        Random rand = new Random();
        int treeHeight = rand.nextInt(6) + 5;
        for (int i = 0; i < treeHeight; i++){
            Vector2 place = new Vector2(x, base_y - ((i+1)* Block.SIZE));
            Renderable renderable = new RectangleRenderable(ColorSupplier.approximateColor(new Color(100,
                    50, 20)));
            gameObjects.addGameObject(new Block(place, renderable), layer);
        }
        int leafRadius =  rand.nextInt(treeHeight/3) + 1;
        for (int i = -leafRadius; i <= leafRadius; i++){
            for (int j = -leafRadius; j <= leafRadius; j++) {
                if (rand.nextInt(15) != 0) {
                    Vector2 place = new Vector2(x + i * Block.SIZE, base_y - (treeHeight * Block.SIZE) - j * Block.SIZE);
                    Renderable renderable =
                            new RectangleRenderable(ColorSupplier.approximateColor(new Color(50, 200, 30)));
                    gameObjects.addGameObject(new Leaf(place, renderable, terr), layer + 1);
                }
            }
        }
    }

}
