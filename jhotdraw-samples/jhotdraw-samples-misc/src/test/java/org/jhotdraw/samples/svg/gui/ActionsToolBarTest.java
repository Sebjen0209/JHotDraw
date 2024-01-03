package org.jhotdraw.samples.svg.gui;

import junit.framework.TestCase;
import org.jhotdraw.gui.JPopupButton;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;

public class ActionsToolBarTest extends TestCase {
    private ActionsToolBar ATB;
    @Before
    public void setUp(){
        ATB = new ActionsToolBar();
    }
    @Test
    public void testCreateDisclosedComponent() {
        int state = 1;
        JComponent result = ATB.createDisclosedComponent(state);
        assertNotNull(result);
        assertTrue(result instanceof JPanel);
        JPanel panel = (JPanel) result;
        Component[] components = panel.getComponents();
        assertEquals(5, components.length);
        assertTrue(components[0] instanceof JButton);
        assertTrue(components[1] instanceof JButton);
        assertTrue(components[2] instanceof JButton);
        assertTrue(components[3] instanceof JButton);
        assertTrue(components[4] instanceof JPopupButton);
    }
}