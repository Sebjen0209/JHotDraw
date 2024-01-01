package org.jhotdraw.samples.svg.bdd;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ScenarioStage;
import com.tngtech.jgiven.annotation.ScenarioState;
import org.jhotdraw.samples.svg.figures.SVGRectFigure;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class WhenDuplicateTest extends Stage<WhenDuplicateTest> {

    @ScenarioStage
    SVGRectFigure rectangle;

    @ScenarioStage
    int width;

    @ScenarioStage
    int height;
    @ScenarioState
    Graphics2D graphics;

    @ScenarioState
    Shape shape;
    public WhenDuplicateTest userDuplicates(){
        assertNotEquals(0, width);
        assertNotEquals(0, height);
        assertNotNull(rectangle);
        assertNotNull(graphics);

        shape = new Rectangle2D.Double(0,0,width,width);

        rectangle.clone();

        return this;
    }
}
