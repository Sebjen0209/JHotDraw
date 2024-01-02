package org.jhotdraw.gui;

import static org.junit.jupiter.api.Assertions.*;

import org.jhotdraw.gui.JDisclosureToolBar;
import org.junit.jupiter.api.Test;

import javax.swing.*;

public class JDisclosureToolBarTest {

    @Test
    public void testSetDisclosureStateCount() {
        JDisclosureToolBar toolBar = new JDisclosureToolBar();
        int newValue = 3;

        toolBar.setDisclosureStateCount(newValue);

        assertEquals(newValue, toolBar.getDisclosureStateCount());
    }

    @Test
    public void testSetDisclosureState() {
        JDisclosureToolBar toolBar = new JDisclosureToolBar();
        int newValue = 1;

        toolBar.setDisclosureState(newValue);

        assertEquals(newValue, toolBar.getDisclosureState());
    }

    @Test
    public void testGetDisclosedComponent() {
        JDisclosureToolBar toolBar = new JDisclosureToolBar();
        int state = 1;

        JComponent disclosedComponent = toolBar.getDisclosedComponent(state);

        assertNotNull(disclosedComponent);
    }

}
