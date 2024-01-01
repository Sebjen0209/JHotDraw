package org.jhotdraw.samples.svg.bdd;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ScenarioStage;
import com.tngtech.jgiven.annotation.ScenarioState;
import org.jhotdraw.samples.svg.figures.SVGRectFigure;
import org.mockito.Mockito;

import java.awt.*;

public class GivenDuplicateTest extends Stage<GivenDuplicateTest> {

    @ScenarioStage
    SVGRectFigure rectangle;

    @ScenarioStage
    int width;

    @ScenarioStage
    int height;
    @ScenarioState
    Graphics2D graphics;

    public GivenDuplicateTest userWantsToDuplicate(){
        width = 200;
        height = 150;
        rectangle = new SVGRectFigure(20,10,width,height);

        graphics = Mockito.mock(Graphics2D.class);
        return this;
    }
}
