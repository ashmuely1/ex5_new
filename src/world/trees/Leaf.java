package world.trees;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import world.Block;
import world.Terrain;

import java.util.Random;

public class Leaf extends Block {
    private static final int FADEOUT_TIME = 7;
    private final int LEAF_CYCLE_LENGTH = 3;
    private final int LEAF_CHANGE_SIZE = 2;
    private final int LEAF_DEGREE_CHANGE = 7;
    private final int WND_BLOWING_TIME = 1;

    private int timeToFall;
    private Terrain terrain;
    private Transition<Float> trans1 = null;
    private Transition<Float> trans2 = null;
    private final Vector2 originalPlace;


    public Leaf(Vector2 topLeftCorner, Renderable renderable, Terrain terrain) {
        super(topLeftCorner, renderable);
        this.terrain = terrain;
        Random rand = new Random();
        timeToFall = rand.nextInt(200);
        new ScheduledTask(this, timeToFall, false, this::leafFall);
        float wait = rand.nextFloat() * 2;
        new ScheduledTask(this, wait, false, this::blowingWind);

        originalPlace = topLeftCorner;
    }



    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (trans1 != null) {
            this.removeComponent(trans1);
            trans1 = null;
        }
        if (trans2 != null) {
            this.removeComponent(trans2);
            trans2 = null;
        }
        Random random = new Random();
        int fadeTime = random.nextInt(FADEOUT_TIME) + FADEOUT_TIME;
        this.renderer().fadeOut(fadeTime, this::leafReborn);
    }

    private void leafFall(){
        trans1 = new Transition<Float>( this, place -> this.setTopLeftCorner(new Vector2(this.getTopLeftCorner().x(), place)),
                this.getTopLeftCorner().y(), terrain.groundHeightAt(this.getTopLeftCorner().x()), Transition.LINEAR_INTERPOLATOR_FLOAT, LEAF_CYCLE_LENGTH,
                Transition.TransitionType.TRANSITION_ONCE, null);
        trans2 = new Transition<Float>( this, place -> this.setTopLeftCorner(new Vector2(place, this.getTopLeftCorner().y())),
                this.getTopLeftCorner().x()-Block.SIZE, this.getTopLeftCorner().x() + Block.SIZE, Transition.CUBIC_INTERPOLATOR_FLOAT, LEAF_CYCLE_LENGTH/3,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH, null);
    }

    private void leafReborn(){
        Random random = new Random();
        int deathTime = random.nextInt(FADEOUT_TIME);
        new ScheduledTask(this, deathTime, false, this::leafAppears);
    }

    private void leafAppears(){
        setTopLeftCorner(originalPlace);
        this.renderer().setOpaqueness(1);
        new ScheduledTask(this, timeToFall, false, this::leafFall);
    }

    private void blowingWind(){
        renderer().setOpaqueness(1f);
        Random rand = new Random();
        int cycleLength = rand.nextInt(WND_BLOWING_TIME) + WND_BLOWING_TIME;
        float degreeChange = rand.nextInt(LEAF_DEGREE_CHANGE) + LEAF_DEGREE_CHANGE;
        int sizeChange = rand.nextInt(LEAF_CHANGE_SIZE) + 1;

        new Transition<Float>( this, timer -> renderer().setRenderableAngle(timer),
                0f, degreeChange, Transition.LINEAR_INTERPOLATOR_FLOAT, cycleLength,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH, null);
        new Transition<Float>( this, size -> setDimensions(new Vector2(size, size)),
                Block.SIZE, Block.SIZE + sizeChange,
                Transition.LINEAR_INTERPOLATOR_FLOAT, cycleLength,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH, null);

    }

}
