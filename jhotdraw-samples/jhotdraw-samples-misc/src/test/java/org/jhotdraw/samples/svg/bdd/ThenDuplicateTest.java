package org.jhotdraw.samples.svg.bdd;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ScenarioStage;
import com.tngtech.jgiven.annotation.ScenarioState;
import org.jhotdraw.samples.svg.figures.SVGRectFigure;
import org.mockito.Mockito;

import java.awt.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ThenDuplicateTest extends Stage<ThenDuplicateTest> {

    @ExpectedScenarioState
    Graphics2D graphics;

    @ExpectedScenarioState
    Shape shape;

    @ExpectedScenarioState
    SVGRectFigure rectangle;

    public ThenDuplicateTest userGetsADuplicate(){
        assertNotNull(graphics);
        assertNotNull(shape);
        assertNotNull(rectangle);

        assertEquals(100, rectangle.getWidth(), 0.001);
        assertEquals(100, rectangle.getHeight(), 0.001);

        Mockito.verify(rectangle,Mockito.times(5)).clone();
        return this;
    }
}
