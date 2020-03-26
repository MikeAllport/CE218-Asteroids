package Model;

import View.ImgManag;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import Controller.Controller;
import Controller.HlAiController;
import View.SoundsManag;
import Controller.Game;

import static Model.Constants.FRAME_WIDTH;

public class EnemyShip extends Ship {
    public boolean killedByPlayer = false;

    public EnemyShip(Controller ctrl, Vector2D pos)
    {
        super(pos, new Vector2D(0, 0), 25, ctrl);
        sp = new Sprite(position, direction, RADIUS * 2, RADIUS * 2, ImgManag.getImage("EnemyrShip.png")
                , genShape());
        thrustSp = new Sprite(position, direction, RADIUS * 2, RADIUS * 2 + 30, ImgManag.getImage("EnemyShipThrust.png")
                , genShape());
        ((HlAiController) ctrl).setOwner(this);
        myAcc = MAG_ACC;
        myFire = 1 + ((Player.difficulty / 3) * 0.2);
        mySteer = STEER_RATE * Math.PI;
        mySpeed = MAX_SPEED;
    }

    public boolean canHit(GameObject other)
    {
        return  other instanceof Bullet || other instanceof PlayerShip;
    }

    @Override
    public void hit(GameObject other) {
        super.hit(other);
        if (other instanceof PlayerBullet)
            killedByPlayer = true;
        alive = false;
        if (position.dist(Game.player.playerShip.position) <= FRAME_WIDTH + (FRAME_WIDTH / 2))
            SoundsManag.shipExp();
    }

    @Override
    public Path2D genShape()
    {
        Path2D shape = new Path2D.Double();
        shape.append(new Ellipse2D.Double(-RADIUS/2,-RADIUS/2,RADIUS * 2, RADIUS * 2), true);
        return shape;
    }

    @Override
    public void mkBullet() {
        bullet = new EnemyBullet(this);
        SoundsManag.enemyFire();
    }
}