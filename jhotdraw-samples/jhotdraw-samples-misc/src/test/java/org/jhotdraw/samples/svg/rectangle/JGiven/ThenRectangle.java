package org.jhotdraw.samples.svg.rectangle.JGiven;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import org.jhotdraw.draw.DefaultDrawingView;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.figure.Figure;
import org.jhotdraw.samples.svg.figures.SVGRectFigure;

import java.util.List;

public class ThenRectangle extends Stage<ThenRectangle> {
    
    @ExpectedScenarioState
    private DefaultDrawingView view;

    @ExpectedScenarioState
    private Drawing drawingView;

    public ThenRectangle TheRectangleShouldBeDisplayedOnTheView() {

        drawingView = view.getDrawing();

        List<Figure> figures = drawingView.getChildren();

        boolean rectDrawn = figures.stream().anyMatch(figure -> figure instanceof SVGRectFigure);

        assert rectDrawn : "No rectangle present";

        return self();
    }
}

