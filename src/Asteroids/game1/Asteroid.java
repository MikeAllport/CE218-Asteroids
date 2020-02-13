package Asteroids.game1;

import Asteroids.utilities.RotatableImage;
import Asteroids.utilities.RotatableShape;
import Asteroids.utilities.Sprite;
import Asteroids.utilities.Vector2D;

import java.awt.*;

import static Asteroids.game1.Constants.*;

public class Asteroid extends GameObject {
    public static final double MAX_SPEED = 100; // maximum speed an asteroid can have
    private Vector2D rotationalVec;
    public Asteroid[] child = new Asteroid[3];
    public boolean killedByPlayer = false;

    private RotatableImage img;
    private Sprite sp;

//    private LinkedList<Vector2D> texture; // old rotating texture

    /**
     * Standard constructor for an asteroid
     *
     * @param x
     * @param y  Positional value to instantiate an asteroid with
     * @param vx
     * @param vy Velocity values to instantiate an asteroid with
     */
    public Asteroid(double x, double y, double vx, double vy, int rad) {
        super(new Vector2D(x, y), new Vector2D(vx, vy), rad);
        rotationalVec = (new Vector2D(vx, vy));
        // sets sprite accordingly to radius of asteroid with random picture
        switch (rad)
        {
            case 20:
                img = new RotatableImage(String.format("resources/Ast%d.png", (int) (Math.random() * 7) + 1));
                img.setScale((double) 40 / img.getWidth(), (double) 48 / img.getHeight());
                break;
            case 33:
                img = new RotatableImage(String.format("resources/Ast%d.png", (int) (Math.random() * 7) + 1));
                img.setScale((double) 66 / img.getWidth(), (double) 66 / img.getHeight());
                break;
            case 42:
            default:
                img = new RotatableImage(String.format("resources/Ast%d.png", (int) (Math.random() * 7) + 1));
                img.setScale((double) 84 / img.getWidth(), (double) 84 / img.getHeight());
                break;
        }
    }

    public void hit()
    {
        this.alive = false;
        // creates 3 new child asteroids off 1 radius smaller then existing dead one
        // with velocity equal to 1.5 times faster in a random angle
        for (int i = 0; i < 3; i++) {
            double randAngle = Math.toRadians(Math.random() * 360);
            Vector2D newV = Vector2D.polar(randAngle, velocity.mag() * 1.2);
            switch ((int) RADIUS)
            {
                case 20:
                default:
                    break;
                case 33:
                    child[i] = new Asteroid(this.position.x, this.position.y,
                            newV.x, newV.y, 20);
                    break;
                case 42:
                    child[i] = new Asteroid(this.position.x, this.position.y,
                            newV.x, newV.y, 33);
                    break;
            }
        }
    }

    /**
     * makeRandomAsteroid's main purpose it to create an asteroid with random positional
     * and velocity values utilizing Math.random
     *
     * @return
     */
    public static Asteroid makeRandomAsteroid() {
        int[] radii = {20, 33, 42};
        int radius = radii[(int) (Math.random() * 3)]; // random radius between 12-24 in increments of 6 (3 different
                                                        // sizes of asteroids)
        double x = Math.random() * WORLD_WIDTH;
        double y = Math.random() * WORLD_HEIGHT;
        double vx = (Math.random() * MAX_SPEED * 2) - MAX_SPEED;
        double vy = (Math.random() * MAX_SPEED * 2) - MAX_SPEED;
//        System.out.printf("posx= %3.2f, posy= %3.2f\nspeedx= %3.2f, speedy = %3.2f\n",
//                x, y, vx, vy);
        return new Asteroid(x, y, vx, vy, radius);
    }

    /**
     * getShapeCord's purpose is to generate x/y coordinates for a slightly random shape given it's radius
     * @return
     *      int[][], [0][..] and [1][..] being randomly generated x and y coordinates respectively
     */
    public RotatableShape makeShape()
    {
        // init shape
        RotatableShape shape = new RotatableShape(((int) RADIUS * 2) - (2* ((int) RADIUS / 3)));
//        texture = new LinkedList<>();

        // Adds coordinates for top half of circle, slightly randomized y coord +/- 3pixels
        for (int xCoord = (int) -RADIUS; xCoord < RADIUS; xCoord+=3)
        {
            double x = xCoord;
            double y = (Math.random()*8-4) + Math.sqrt(RADIUS * RADIUS - xCoord * xCoord);
            shape.pushCoords(x, y);
        }

        // Adds coordinates for bottom half of circle
        for (int xCoord = (int) -RADIUS; xCoord < RADIUS; xCoord+=3)
        {
            double x = xCoord;
            double y = (Math.random()*8-4) + Math.sqrt(RADIUS * RADIUS - xCoord * xCoord);
            shape.pushCoords(-x, -y);
        }
        return shape;
//        for (int i = 0; i < 100; i++)
//        {
//            texture.add(new Vector2D(Math.random()*50 - RADIUS, Math.random()*50-RADIUS));
//        }
    }

    /**
     * update's purpose is to update the asteroids position given it's velocity
     * and change in time DT
     */
    public void update() {
        super.update();
        final double angle = rotationalVec.angle() * 0.02;
        // rotates the shape and image
        img.setRotate(angle);
    }

    /**
     * draw's purpose is to draw this asteroid on a give Graphics2D object utilizing populated
     * positional vectors in it's shape vector2D array
     * @param g
     *      Graphics2D, the jswing graphics object to draw unto
     */
    public void draw(Graphics2D g) {

        img.paintIcon(g, (int) position.x, (int) position.y);
    }

    @Override
    public String toString() {
        return String.format("I am an asteroid, my positional vector is\n%s", position);
    }
}