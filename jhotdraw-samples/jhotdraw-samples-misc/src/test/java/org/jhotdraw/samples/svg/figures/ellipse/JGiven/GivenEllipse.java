package org.jhotdraw.samples.svg.figures.ellipse.JGiven;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import org.jhotdraw.draw.DefaultDrawing;
import org.jhotdraw.draw.DefaultDrawingView;
public class GivenEllipse extends Stage<GivenEllipse> {

    @ProvidedScenarioState
    private DefaultDrawingView view;

    public GivenEllipse anEmptyView() {
        view = new DefaultDrawingView();
        view.setDrawing(new DefaultDrawing());
        return self();
    }

}
