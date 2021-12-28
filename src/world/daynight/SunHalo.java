package world.daynight;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.*;

public class SunHalo {


    private static final float SUNHALO_RADIUS = 300;

    public static GameObject create(GameObjectCollection gameObjects, int layer, GameObject sun,
                                    Color color) {
        Renderable renderable = new OvalRenderable(color);
        GameObject sunHalo = new GameObject(Vector2.ZERO, new Vector2(SUNHALO_RADIUS, SUNHALO_RADIUS), renderable);
        sunHalo.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects.addGameObject(sunHalo, layer);
        sun.setTag("sunHalo");

        sunHalo.addComponent(deltaTime -> sunHalo.setCenter(sun.getCenter()));

        return sunHalo;

    }

}
