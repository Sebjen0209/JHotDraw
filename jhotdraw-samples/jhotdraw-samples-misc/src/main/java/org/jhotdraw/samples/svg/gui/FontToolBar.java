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
import org.jhotdraw.draw.event.FigureAttributeEditorHandler;
import org.jhotdraw.draw.event.SelectionComponentRepainter;
import org.jhotdraw.formatter.FontFormatter;
import org.jhotdraw.formatter.JavaNumberFormatter;
import org.jhotdraw.gui.action.ButtonFactory;
import org.jhotdraw.gui.plaf.palette.PaletteButtonUI;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import static org.jhotdraw.draw.AttributeKeys.FONT_FACE;
import static org.jhotdraw.draw.AttributeKeys.FONT_SIZE;

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
import org.jhotdraw.gui.plaf.palette.PaletteFormattedTextFieldUI;
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
    public JComponent createDisclosedComponent(int state) {
        JPanel p = initializePanel();

        if (editor == null) {
            return p;
        }

        switch (state) {
            case 1:
                buildState1UI(p);
                break;
            case 2:
                buildState2UI(p);
                break;
            default:
                System.out.println("Error");
                break;
        }

        return p;
    }

    private JPanel initializePanel() {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setBorder(new EmptyBorder(5, 5, 5, 8));
        return p;
    }

    private void buildState1UI(JPanel p) {
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.samples.svg.Labels");
        GridBagLayout layout = new GridBagLayout();
        p.setLayout(layout);

        GridBagConstraints gbc;
        AbstractButton btn;

        // Font face field and popup button
        JAttributeTextField<Font> faceField = createFontFaceField();
        gbc = createConstraints(0, 0, 2, GridBagConstraints.HORIZONTAL, 1f);
        p.add(faceField, gbc);

        btn = ButtonFactory.createFontButton(editor, FONT_FACE, labels, disposables);
        gbc = createConstraints(GridBagConstraints.REMAINDER, GridBagConstraints.WEST);
        p.add(btn, gbc);

        // Font size field with slider
        JAttributeTextField<Double> sizeField = createFontSizeField();
        gbc = createConstraints(0, 1, 2, GridBagConstraints.HORIZONTAL, 1f);
        p.add(sizeField, gbc);

        JPopupButton sizePopupButton = createSizePopupButton(sizeField);
        gbc = createConstraints(2, 1, GridBagConstraints.WEST, new Insets(3,0,0,0));
        p.add(sizePopupButton, gbc);

        // Font style buttons
        addFontStyleButtons(p, labels);
    }

    private JAttributeTextField<Font> createFontFaceField() {
        JAttributeTextField<Font> faceField = new JAttributeTextField<>();
        faceField.setColumns(2);
        faceField.setUI((PaletteFormattedTextFieldUI) PaletteFormattedTextFieldUI.createUI(faceField));
        faceField.setFormatterFactory(FontFormatter.createFormatterFactory());
        disposables.add(new FigureAttributeEditorHandler<Font>(FONT_FACE, faceField, editor));
        return faceField;
    }

    private JAttributeTextField<Double> createFontSizeField() {
        JAttributeTextField<Double> sizeField = new JAttributeTextField<>();
        sizeField.setColumns(1);
        sizeField.setUI((PaletteFormattedTextFieldUI) PaletteFormattedTextFieldUI.createUI(sizeField));
        sizeField.setFormatterFactory(JavaNumberFormatter.createFormatterFactory(0d, 1000d, 1d));
        disposables.add(new FigureAttributeEditorHandler<Double>(FONT_SIZE, sizeField, editor));
        sizeField.setHorizontalAlignment(JTextField.LEADING);
        return sizeField;
    }

    private JPopupButton createSizePopupButton(JAttributeTextField<Double> sizeField) {
        JPopupButton sizePopupButton = new JPopupButton();
        JAttributeSlider sizeSlider = createSizeSlider(sizeField);
        sizePopupButton.add(sizeSlider);
        return sizePopupButton;
    }

    private JAttributeSlider createSizeSlider(JAttributeTextField<Double> sizeField) {
        sizeField.setColumns(1);
        disposables.add(new FigureAttributeEditorHandler<Double>(FONT_SIZE, sizeField, editor));
        JAttributeSlider sizeSlider = new JAttributeSlider(JSlider.VERTICAL, 0, 100, 12);
        return sizeSlider;
    }

    private void addFontStyleButtons(JPanel p, ResourceBundleUtil labels) {
        AbstractButton btn;

        btn = ButtonFactory.createFontStyleBoldButton(editor, labels, disposables);
        addFontStyleButton(p, btn, 2, "first");

        btn = ButtonFactory.createFontStyleItalicButton(editor, labels, disposables);
        addFontStyleButton(p, btn, 3, "middle");

        btn = ButtonFactory.createFontStyleUnderlineButton(editor, labels, disposables);
        addFontStyleButton(p, btn, 4, "last");
    }

    private void addFontStyleButton(JPanel p, AbstractButton btn, int gridX, String segmentPosition) {
        btn.setUI((PaletteButtonUI) PaletteButtonUI.createUI(btn));
        btn.putClientProperty("Palette.Component.segmentPosition", segmentPosition);
        GridBagConstraints gbc = createConstraints(gridX, 2, GridBagConstraints.WEST, new Insets(3, 0, 0, 0));
        p.add(btn, gbc);
    }

    private void buildState2UI(JPanel p) {

    }

    private GridBagConstraints createConstraints(int gridx, int gridy, int anchor, Insets insets) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.anchor = anchor;
        gbc.insets = insets;
        return gbc;
    }

    private GridBagConstraints createConstraints(int gridx, int gridy, int gridwidth, int fill, float weightx) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = gridwidth;
        gbc.fill = fill;
        gbc.weightx = weightx;
        return gbc;
    }

    private GridBagConstraints createConstraints(int gridwidth, int anchor) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = gridwidth;
        gbc.anchor = anchor;
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
