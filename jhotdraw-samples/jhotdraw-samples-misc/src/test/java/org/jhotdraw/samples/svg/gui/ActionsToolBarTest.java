package org.jhotdraw.samples.svg.gui;

import junit.framework.TestCase;
import org.jhotdraw.gui.JPopupButton;
import org.jhotdraw.samples.svg.figures.SVGRectFigure;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ActionsToolBarTest extends TestCase {
    private SVGRectFigure figure;
    private ActionsToolBar ATB;

    public ActionsToolBarTest(ActionsToolBar atb) {
        ATB = atb;
    }

    @Before
    public void setUp(){
        double x = 11.1;
        double y = 12.2;
        double width = 9.9;
        double height = 13.3;
        double rx = 1d;
        double ry = 1d;
        figure = new SVGRectFigure(x, y, width, height, rx, ry);
    }

    @Test
    public void testCreateDisclosedComponent() {
        int state = 1;
        JComponent result = ATB.createDisclosedComponent(state);

        assertNotNull(result);
        assertTrue(result instanceof JPanel);

        JPanel panel = (JPanel) result;
        Component[] components = panel.getComponents();

        // Check if the expected buttons are added to the panel
        assertEquals(5, components.length);
        assertTrue(components[0] instanceof JButton);
        assertTrue(components[1] instanceof JButton);
        assertTrue(components[2] instanceof JButton);
        assertTrue(components[3] instanceof JButton);
        assertTrue(components[4] instanceof JPopupButton);
    }


}