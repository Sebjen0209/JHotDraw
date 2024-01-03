package org.jhotdraw.samples.svg.figures.ellipse.JGiven;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import org.jhotdraw.draw.DefaultDrawing;
import org.jhotdraw.draw.DefaultDrawingView;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.samples.svg.figures.SVGEllipseFigure;


public class WhenEllipse extends Stage<WhenEllipse> {

    @ExpectedScenarioState
    private DefaultDrawingView view;
    @ProvidedScenarioState
    private Drawing drawing;
    public WhenEllipse() {
        this.drawing = new DefaultDrawing();
    }
    public WhenEllipse theUserDrawsTheEllipse() {
        SVGEllipseFigure ellipse = new SVGEllipseFigure(10, 10, 15, 50);
        drawing.add(ellipse);
        view.setDrawing(drawing);
        view.repaint();
        return self();
    }
}


