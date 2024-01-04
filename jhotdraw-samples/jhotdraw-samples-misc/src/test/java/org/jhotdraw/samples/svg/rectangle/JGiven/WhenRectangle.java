package org.jhotdraw.samples.svg.rectangle.JGiven;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import org.jhotdraw.draw.DefaultDrawing;
import org.jhotdraw.draw.DefaultDrawingView;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.samples.svg.figures.SVGRectFigure;

public class WhenRectangle extends Stage<WhenRectangle> {
    
    @ExpectedScenarioState
    private DefaultDrawingView view;
    @ProvidedScenarioState
    private Drawing drawing;
    public WhenRectangle() {
        this.drawing = new DefaultDrawing();
    }
    public WhenRectangle theUserDrawsRectangle() {
        SVGRectFigure rectangle= new SVGRectFigure(10, 10, 50, 50);
        drawing.add(rectangle);
        view.setDrawing(drawing);
        view.repaint();
        return self();
    }
}
