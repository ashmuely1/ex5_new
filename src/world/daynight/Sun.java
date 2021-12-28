package world.daynight;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.*;

public class Sun {

    private static final float SUN_RADIUS = 100;



    public static GameObject create(GameObjectCollection gameObjects, int layer,
                                    Vector2 windowDimensions, float cycleLength){

        Renderable renderable = new OvalRenderable(Color.YELLOW);
        GameObject sun = new GameObject(Vector2.ZERO, new Vector2(SUN_RADIUS, SUN_RADIUS), renderable);
        sun.setCenter(new Vector2(windowDimensions.x()/2, windowDimensions.y()/4));
        sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects.addGameObject(sun, layer);
        sun.setTag("sun");

        new Transition<Float>(
                sun, //the game object being changed
                aFloat -> sun.setCenter(new Vector2((windowDimensions.x()/2)+
                        ((windowDimensions.x()/2)*((float)Math.cos((aFloat + 270) * (Math.PI/180)))),
                        (windowDimensions.x()/2) + ((windowDimensions.x()/2)*
                                ((float)Math.sin((aFloat + 270) * (Math.PI/180)))))),  //the method to call
                0f,    //initial transition value
                360f,   //final transition value
                Transition.LINEAR_INTERPOLATOR_FLOAT,  //use a cubic interpolator
                cycleLength,   //transition fully over half a day
                Transition.TransitionType.TRANSITION_LOOP,
                null);  //nothing further to execute upon reaching final value
        return sun;
    }
}
