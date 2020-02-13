package Asteroids.utilities;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * RotatableImage's purpose is to extend awt Icon class, overriding functionality to paint the image associated
 * with to an instance, and adding functionality to rotate and scale the image
 */
public class RotatableImage{

    public BufferedImage img;
    public double degrees; // angle to be rotated by
    public double scaleX; // scaling factor on x component
    public double scaleY; // scaling factor on y component

    /**
     * Standard constructor associating image icon to filename argument
     * @param fname
     *      String, file and path for the image
     */
    public RotatableImage(String fname)
    {
        try {
            img = ImageIO.read(new File(fname));
        }
        catch (IOException e)
        {
            throw new RuntimeException(String.format("Failed to load Image '%s'", fname));
        }
       degrees = 0;
       this.scaleX = this.scaleY = 1;
    }

    public static RotatableImage builder(String fname)
    {
        return new RotatableImage(fname);
    }

    /**
     * setRotate's purpose is to increment/decrement angle of rotation by given degrees
     * @param degrees
     *      double, value of angle to be changed by
     */
    public RotatableImage setRotate(double degrees)
    {
        this.degrees = this.degrees + degrees;
        return this;
    }

    /**
     * setScale's purpose is to set the scaling factor
     * @param x
     *      double, x factor
     * @param y
     *      double, y factor
     */
    public void setScale(double x, double y)
    {
        this.scaleX = x;
        this.scaleY = y;
    }

    /**
     * paintIcon's purpose is to override the superclasses paint method, painting a scaled and rotated
     * image onto given graphics component
     * @param g
     *      Graphics object, to be painted on
     * @param x
     *      int, x positional value for 0,0
     * @param y
     *      int, y positional value for 0,0
     */
    public void paintIcon(Graphics2D g, int x, int y) {
        AffineTransform init = g.getTransform();
        AffineTransform at = new AffineTransform();
        at.rotate(degrees, 0,0);
        at.scale(scaleX, scaleY);
        at.translate(-getWidth()/2, -getHeight()/2);
        g.translate(x, y);
        g.drawImage(img, at, null);
        g.setTransform(init);
    }

    public int getWidth() {
        return img.getWidth();
    }

    public int getHeight() {
        return img.getHeight();
    }

    public double getRotatedWidth()
    {
        double sin = Math.sin(degrees);
        double cos = Math.cos(degrees);
        return Math.round(getWidth() * cos - getHeight() * sin);
    }

    public double getRotatedHeight()
    {
        double sin = Math.sin(degrees);
        double cos = Math.cos(degrees);
        return (int) Math.round(getWidth() * sin + img.getHeight() * cos);
    }
}
