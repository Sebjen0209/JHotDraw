package org.jhotdraw.samples.svg.figures.ellipse.JGiven;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import org.jhotdraw.draw.DefaultDrawingView;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.figure.Figure;
import org.jhotdraw.samples.svg.figures.SVGEllipseFigure;

import java.util.List;

public class ThenEllipse extends Stage<ThenEllipse> {

    @ExpectedScenarioState
    private DefaultDrawingView view;

    @ExpectedScenarioState
    private Drawing drawing;

    public ThenEllipse TheEllipseShouldBeDisplayedOnTheView() {

        drawing = view.getDrawing();

        List<Figure> figures = drawing.getChildren();

        boolean ellipseDrawn = figures.stream().anyMatch(figure -> figure instanceof SVGEllipseFigure);

        assert ellipseDrawn : "Ellipse is not present in the drawing";

        return self();
    }
}
