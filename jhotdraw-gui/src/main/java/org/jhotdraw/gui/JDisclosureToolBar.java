/**
 * @(#)JDisclosureToolBar.java
 *
 * Copyright (c) 2008 The authors and contributors of JHotDraw.
 * You may not use, copy or modify this file, except in compliance with the
 * accompanying license terms.
 */
package org.jhotdraw.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import org.jhotdraw.gui.plaf.palette.PaletteButtonUI;
import org.jhotdraw.gui.plaf.palette.PaletteToolBarUI;

/**
 * A ToolBar with disclosure functionality.
 *
 * @author Werner Randelshofer
 * @version $Id$
 */
public class JDisclosureToolBar extends JToolBar {

    private static final long serialVersionUID = 1L;
    private JButton disclosureButton;
    public static final String DISCLOSURE_STATE_PROPERTY = "disclosureState";
    public static final String DISCLOSURE_STATE_COUNT_PROPERTY = "disclosureStateCount";

    /**
     * Creates new form.
     */
    public JDisclosureToolBar() {
        setUI(PaletteToolBarUI.createUI(this));
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JButton disclosureButton = createDisclosureButton();
        gbc.gridx = 0;
        gbc.insets = new Insets(0, 1, 0, 1);
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weighty = 1d;
        gbc.weightx = 1d;
        add(disclosureButton, gbc);

        configureToolBarProperties();
    }

    private JButton createDisclosureButton() {
        JButton btn = (disclosureButton == null) ? createNewButton() : disclosureButton;

        btn.addActionListener(e -> {
            int newState = ((Integer) btn.getClientProperty(DisclosureIcon.CURRENT_STATE_PROPERTY) + 1) %
                    (Integer) btn.getClientProperty(DisclosureIcon.STATE_COUNT_PROPERTY);
            setDisclosureState(newState);
        });

        return btn;
    }

    private JButton createNewButton() {
        JButton btn = new JButton();
        btn.setUI((PaletteButtonUI) PaletteButtonUI.createUI(btn));
        btn.setBorderPainted(false);
        btn.setIcon(new DisclosureIcon());
        btn.setOpaque(false);
        btn.putClientProperty(DisclosureIcon.CURRENT_STATE_PROPERTY, 1);
        btn.putClientProperty(DisclosureIcon.STATE_COUNT_PROPERTY, 2);

        disclosureButton = btn;
        return btn;
    }

    private void configureToolBarProperties() {
        putClientProperty(PaletteToolBarUI.TOOLBAR_INSETS_OVERRIDE_PROPERTY, new Insets(0, 0, 0, 0));
        putClientProperty(PaletteToolBarUI.TOOLBAR_ICON_PROPERTY, new EmptyIcon(10, 8));
    }

    public void setDisclosureStateCount(int newValue) {
        int oldValue = getDisclosureStateCount();
        disclosureButton.putClientProperty(DisclosureIcon.STATE_COUNT_PROPERTY, newValue);
        firePropertyChange(DISCLOSURE_STATE_COUNT_PROPERTY, oldValue, newValue);
    }

    public void setDisclosureState(int newValue) {
        int oldValue = getDisclosureState();
        disclosureButton.putClientProperty(DisclosureIcon.CURRENT_STATE_PROPERTY, newValue);

        removeAll();

        JComponent disclosedComponent = getDisclosedComponent(newValue);
        GridBagConstraints gbc = createDefaultGridBagConstraints();

        if (disclosedComponent != null) {
            addDisclosedComponent(newValue, disclosedComponent, gbc);
        } else {
            addDisclosureButtonWithoutComponent(gbc);
        }

        validateAndRepaint();
        firePropertyChange(DISCLOSURE_STATE_PROPERTY, oldValue, newValue);
    }

    private GridBagConstraints createDefaultGridBagConstraints() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1d;
        gbc.weighty = 1d;
        gbc.insets = new Insets(0, 1, 0, 1);
        return gbc;
    }

    private void addDisclosedComponent(int newValue, JComponent disclosedComponent, GridBagConstraints gbc) {
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.WEST;
        add(disclosedComponent, gbc);

        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        add(disclosureButton, gbc);
    }

    private void addDisclosureButtonWithoutComponent(GridBagConstraints gbc) {
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        add(disclosureButton, gbc);
    }

    private void validateAndRepaint() {
        invalidate();
        Container parent = findValidParentContainer();
        parent.validate();
        repaint();
    }

    private Container findValidParentContainer() {
        Container parent = getParent();
        while (parent.getParent() != null && !parent.getParent().isValid()) {
            parent = parent.getParent();
        }
        return parent;
    }


    public int getDisclosureStateCount() {
        Integer value = (Integer) disclosureButton.getClientProperty(DisclosureIcon.STATE_COUNT_PROPERTY);
        return (value == null) ? 2 : value;
    }

    public int getDisclosureState() {
        Integer value = (Integer) disclosureButton.getClientProperty(DisclosureIcon.CURRENT_STATE_PROPERTY);
        return (value == null) ? 1 : value;
    }

    protected JComponent getDisclosedComponent(int state) {
        return new JLabel(Integer.toString(state));
    }
    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     * /
       // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
    }// </editor-fold>//GEN-END:initComponents
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
