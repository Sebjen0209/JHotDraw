package org.jhotdraw.samples.svg.rectangle;

import static org.junit.Assert.assertNotNull;

import org.jhotdraw.samples.svg.rectangle.JGiven.GivenRectangle;
import org.jhotdraw.samples.svg.rectangle.JGiven.ThenRectangle;
import org.jhotdraw.samples.svg.rectangle.JGiven.WhenRectangle;
import org.junit.Test;

import com.tngtech.jgiven.junit.ScenarioTest;

public class RectangleBDDTest extends ScenarioTest<GivenRectangle, WhenRectangle, ThenRectangle>{

     @Test
    public final void DrawRectangle() {
        given().anEmptyView();
        when().theUserDrawsRectangle();
        then().TheRectangleShouldBeDisplayedOnTheView();
    }
}
