/*
 * @(#)StrokeToolBar.java
 *
 * Copyright (c) 2007-2008 The authors and contributors of JHotDraw.
 * You may not use, copy or modify this file, except in compliance with the
 * accompanying license terms.
 */
package org.jhotdraw.samples.svg.gui;

import dk.sdu.mmmi.featuretracer.lib.FeatureEntryPoint;
import org.jhotdraw.draw.AttributeKey;
import org.jhotdraw.gui.action.ButtonFactory;
import org.jhotdraw.gui.plaf.palette.PaletteButtonUI;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import static org.jhotdraw.draw.AttributeKeys.FONT_FACE;
import org.jhotdraw.draw.figure.CompositeFigure;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.figure.Figure;
import org.jhotdraw.draw.figure.TextHolderFigure;
import org.jhotdraw.draw.event.SelectionComponentDisplayer;
import org.jhotdraw.draw.gui.JAttributeSlider;
import org.jhotdraw.draw.gui.JAttributeTextField;
import org.jhotdraw.draw.tool.TextAreaCreationTool;
import org.jhotdraw.draw.tool.TextCreationTool;
import org.jhotdraw.gui.JFontChooser;
import org.jhotdraw.gui.JPopupButton;
import org.jhotdraw.util.*;

/**
 * StrokeToolBar.
 *
 * @author Werner Randelshofer
 * @version $Id$
 */
public class FontToolBar extends AbstractToolBar {

    private static final long serialVersionUID = 1L;
    private SelectionComponentDisplayer displayer;

    /**
     * Creates new instance.
     */
    public FontToolBar() {
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.samples.svg.Labels");
        setName(labels.getString("font.toolbar"));
        JFontChooser.loadAllFonts();
        setDisclosureStateCount(3);
    }

    @Override
    public void setEditor(DrawingEditor newValue) {
        if (displayer != null) {
            displayer.dispose();
            displayer = null;
        }
        super.setEditor(newValue);
        if (newValue != null) {
            displayer = new SelectionComponentDisplayer(editor, this) {
                @Override
                public void updateVisibility() {
                    boolean newValue = editor != null
                            && editor.getActiveView() != null
                            && (isVisibleIfCreationTool && ((editor.getTool() instanceof TextCreationTool) || editor.getTool() instanceof TextAreaCreationTool)
                            || containsTextHolderFigure(editor.getActiveView().getSelectedFigures()));
                    JComponent component = getComponent();
                    if (component == null) {
                        dispose();
                        return;
                    }
                    component.setVisible(newValue);
                    // The following is needed to trick BoxLayout
                    if (newValue) {
                        component.setPreferredSize(null);
                    } else {
                        component.setPreferredSize(new Dimension(0, 0));
                    }
                    component.revalidate();
                }

                private boolean containsTextHolderFigure(Collection<Figure> figures) {
                    for (Figure f : figures) {
                        if (f instanceof TextHolderFigure) {
                            return true;
                        } else if (f instanceof CompositeFigure) {
                            if (containsTextHolderFigure(((CompositeFigure) f).getChildren())) {
                                return true;
                            }
                        }
                    }
                    return false;
                }
            };
        }
    }

    @Override
    @FeatureEntryPoint("FontToolBar")
    protected JComponent createDisclosedComponent(int state) {
        JPanel p = null;

        switch (state) {
            case 1:
                p = createStatePanel(2);
                break;
            case 2:
                p = createStatePanel(3);
                break;
            default:
                System.out.println("Error");
                break;
        }

        return p;
    }

    private JPanel createStatePanel(int gridY) {
        JPanel p = createBasePanel();
        if (editor == null) {
            return p;
        }

        addFontComponentsToPanel(p, gridY);

        return p;
    }

    private JPanel createBasePanel() {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setBorder(new EmptyBorder(5, 5, 5, 8));
        return p;
    }

    @FeatureEntryPoint("FontToolBar")
    private void addFontComponentsToPanel(JPanel p, int gridY) {
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.samples.svg.Labels");
        GridBagLayout layout = new GridBagLayout();
        p.setLayout(layout);

        GridBagConstraints gbc;

        // Font face field and popup button
        JAttributeTextField<Font> faceField = createFontFaceField();
        gbc = createGridBagConstraints(0, 0, 2, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL);
        p.add(faceField, gbc);

        AbstractButton fontButton = createFontButton(editor, FONT_FACE, labels);
        gbc = createGridBagConstraints(0, gridY, 2, GridBagConstraints.REMAINDER, GridBagConstraints.WEST);
        p.add(fontButton, gbc);

        // Font size field with slider
        JAttributeTextField<Double> sizeField = createFontSizeField();
        gbc = createGridBagConstraints(0, gridY, 2, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL);
        p.add(sizeField, gbc);

        JPopupButton sizePopupButton = createSizePopupButton(sizeField);
        gbc = createGridBagConstraints(2, gridY,2, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.EAST);
        p.add(sizePopupButton, gbc);

        // Font style buttons
        addFontStyleButtonsToPanel(p, gridY);

        // Add other components as needed
    }

    private JAttributeTextField<Font> createFontFaceField() {
        JAttributeTextField<Font> faceField = new JAttributeTextField<>();
        faceField.setColumns(2);
        // Customize font face field
        return faceField;
    }

    private AbstractButton createFontButton(DrawingEditor editor, AttributeKey<Font> fontAttribute, ResourceBundleUtil labels) {
        AbstractButton btn = ButtonFactory.createFontButton(editor, fontAttribute, labels, disposables);
        btn.setUI((PaletteButtonUI) PaletteButtonUI.createUI(btn));
        return btn;
    }

    private JAttributeTextField<Double> createFontSizeField() {
        JAttributeTextField<Double> sizeField = new JAttributeTextField<>();
        sizeField.setColumns(1);
        return sizeField;
    }

    private JPopupButton createSizePopupButton(JAttributeTextField<Double> sizeField) {
        JPopupButton sizePopupButton = new JPopupButton();
        JAttributeSlider sizeSlider = new JAttributeSlider(JSlider.VERTICAL, 0, 100, 12);
        sizePopupButton.add(sizeSlider);
        return sizePopupButton;
    }

    @FeatureEntryPoint("FontToolBar")
    private void addFontStyleButtonsToPanel(JPanel p, int gridY) {
        AbstractButton btn;
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.samples.svg.Labels");

        btn = ButtonFactory.createFontStyleBoldButton(editor, labels, disposables);
        btn.setUI((PaletteButtonUI) PaletteButtonUI.createUI(btn));
        btn.putClientProperty("Palette.Component.segmentPosition", "first");
        GridBagConstraints gbc = createGridBagConstraints(0, gridY, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL);
        p.add(btn, gbc);

        btn = ButtonFactory.createFontStyleItalicButton(editor, labels, disposables);
        btn.setUI((PaletteButtonUI) PaletteButtonUI.createUI(btn));
        btn.putClientProperty("Palette.Component.segmentPosition", "middle");
        gbc = createGridBagConstraints(1, gridY, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL);
        p.add(btn, gbc);

        btn = ButtonFactory.createFontStyleUnderlineButton(editor, labels, disposables);
        btn.setUI((PaletteButtonUI) PaletteButtonUI.createUI(btn));
        btn.putClientProperty("Palette.Component.segmentPosition", "last");
        gbc = createGridBagConstraints(2, gridY, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL);
        p.add(btn, gbc);
    }

    private GridBagConstraints createGridBagConstraints(int x, int y, int gridwidth, int anchor, int fill) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = gridwidth;
        gbc.anchor = anchor;
        gbc.fill = fill;
        return gbc;
    }

    @Override
    protected String getID() {
        return "font";
    }

    @Override
    protected int getDefaultDisclosureState() {
        return 1;
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
