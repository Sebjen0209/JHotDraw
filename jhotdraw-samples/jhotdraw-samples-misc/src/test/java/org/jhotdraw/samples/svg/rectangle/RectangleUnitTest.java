package org.jhotdraw.samples.svg.rectangle;

import org.jhotdraw.samples.svg.figures.SVGRectFigure;
import org.junit.Test;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;


public class RectangleUnitTest {
    AffineTransform tx;

    @Test
    public void RectangleCreation(){
        SVGRectFigure rectangle = new SVGRectFigure(50,50,20,20);

        assertNotNull(rectangle);
        
        //Check position is correct
        assertEquals(50, rectangle.getX(), 0);
        assertEquals(50, rectangle.getY(), 0);
        
        //Check size is correct
        assertEquals(20, rectangle.getWidth(), 0);
        assertEquals(20, rectangle.getHeight(), 0);
    }


    @Test
    public void RectangleEdit(){
        SVGRectFigure rectangle = new SVGRectFigure(50,50,20,20);

        tx = new AffineTransform();
        tx.translate(10, 10);
    
        
        rectangle.transform(tx);

        //Checks if coordinates has been transformed
        assertEquals(60, rectangle.getX(), 0);
        assertEquals(60, rectangle.getY(), 0);
    }

    @Test
    public void rectangleDrawStroke(){
        SVGRectFigure rectFigure = new SVGRectFigure(10, 10, 50, 30, 5, 5);

        BufferedImage buf = new BufferedImage(150, 150, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = buf.createGraphics();

        //Check if the pixel color is white 
        int x = 3;
        int height = 8;
        assertEquals("Initial pixel color should be white", 0, buf.getRGB(x, (height / 2)));

        //Set pixel color to black
        buf.setRGB(x, (height / 2), Color.BLACK.getRGB());

        //Check if pixel color is black
        assertEquals("Pixel color should be black", Color.BLACK.getRGB(), buf.getRGB(x, (height / 2)));

        //Draw the stroke
        rectFigure.drawStroke(g);

        //Check if pixel color has changed after drawing
        assertNotEquals("Pixel color should have changed after drawing", 0, buf.getRGB(x, (height / 2)));
    }
}
