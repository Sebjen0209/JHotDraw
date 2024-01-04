package org.jhotdraw.samples.svg.rectangle.JGiven;
import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import org.jhotdraw.draw.DefaultDrawing;
import org.jhotdraw.draw.DefaultDrawingView;


public class GivenRectangle extends Stage<GivenRectangle> {
    
    @ProvidedScenarioState
    private DefaultDrawingView drawingView;

    public GivenRectangle anEmptyView(){
        drawingView = new DefaultDrawingView();
        drawingView.setDrawing(new DefaultDrawing());
        return self();
    }
}
