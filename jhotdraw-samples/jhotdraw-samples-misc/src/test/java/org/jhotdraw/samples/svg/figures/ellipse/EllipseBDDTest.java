package org.jhotdraw.samples.svg.figures.ellipse;

import com.tngtech.jgiven.junit.ScenarioTest;
import org.jhotdraw.samples.svg.figures.ellipse.JGiven.GivenEllipse;
import org.jhotdraw.samples.svg.figures.ellipse.JGiven.ThenEllipse;
import org.jhotdraw.samples.svg.figures.ellipse.JGiven.WhenEllipse;
import org.junit.Test;

public class EllipseBDDTest extends ScenarioTest<GivenEllipse, WhenEllipse, ThenEllipse> {

    @Test
    public final void DrawEllipse() {
        given().anEmptyView();
        when().theUserDrawsTheEllipse();
        then().TheEllipseShouldBeDisplayedOnTheView();
    }

}
