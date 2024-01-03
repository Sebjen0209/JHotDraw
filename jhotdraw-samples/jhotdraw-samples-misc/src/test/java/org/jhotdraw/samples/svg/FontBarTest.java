package org.jhotdraw.samples.svg;

import org.jhotdraw.draw.DefaultDrawingEditor;
import org.jhotdraw.draw.figure.TextAreaFigure;
import org.jhotdraw.samples.svg.gui.FontToolBar;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.swing.*;
import java.awt.*;

import static org.jhotdraw.draw.AttributeKeys.FONT_UNDERLINE;

public class FontBarTest {
    private static FontToolBar fontToolBar;
    private static SVGDrawingPanel drawingPanel;
    private static final String ITALIC_TEXT = "Italic";
    private static final String UNDERLINE_TEXT = "Underline";
    private static final String BOLD_TEXT = "Bold";


    @BeforeAll
    public static void setUpClass() {
        drawingPanel = new SVGDrawingPanel();
        drawingPanel.setEditor(new DefaultDrawingEditor());

        findFontToolBar(drawingPanel);

        if (fontToolBar == null) {
            throw new RuntimeException("Could not locate the FontToolBar class");
        }
    }

    @BeforeEach
    public void setUp() {
        drawingPanel.getDrawing().removeAllChildren();
    }

    private static boolean findFontToolBar(JComponent component) {
        for (Component c : component.getComponents()) {
            if (c instanceof FontToolBar) {
                fontToolBar = (FontToolBar) c;
                return true;
            }
            if (c instanceof JComponent) {
                boolean found = findFontToolBar((JComponent) c);
                if (found) {
                    return true;
                }
            }
        }
        return false;
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    public void testItalicButton() {
        JPanel panel = (JPanel) fontToolBar.createDisclosedComponent(1);
        JButton italicButton = getButton(panel, ITALIC_TEXT);

        TextAreaFigure textAreaFigure = addSelectedTextAreaToDrawing();

        boolean isItalic = textAreaFigure.getFont().isItalic();
        if (isItalic) {
            throw new RuntimeException("Font is already italic");
        }
        italicButton.doClick();

        Assertions.assertTrue(textAreaFigure.getFont().isItalic());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    public void testUnderlineButton() {
        JPanel panel = (JPanel) fontToolBar.createDisclosedComponent(1);
        JButton underlineButton = getButton(panel, UNDERLINE_TEXT);

        TextAreaFigure textAreaFigure = addSelectedTextAreaToDrawing();

        boolean isUnderlined = textAreaFigure.get(FONT_UNDERLINE);
        if (isUnderlined) {
            throw new RuntimeException("Font is already underlined");
        }

        underlineButton.doClick();

        Assertions.assertTrue(textAreaFigure.get(FONT_UNDERLINE));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    public void testBoldButton() {
        JPanel panel = (JPanel) fontToolBar.createDisclosedComponent(1);
        JButton boldButton = getButton(panel, BOLD_TEXT);

        TextAreaFigure textAreaFigure = addSelectedTextAreaToDrawing();

        boolean isBold = textAreaFigure.getFont().isBold();
        if (isBold) {
            throw new RuntimeException("Font is already bold");
        }

        boldButton.doClick();

        Assertions.assertTrue(textAreaFigure.getFont().isBold());
    }

    private static TextAreaFigure addSelectedTextAreaToDrawing() {
        return addSelectedTextAreaToDrawing(drawingPanel);
    }

    public static TextAreaFigure addSelectedTextAreaToDrawing(SVGDrawingPanel drawingPanel) {
        TextAreaFigure textAreaFigure = new TextAreaFigure();
        drawingPanel.getDrawing().add(textAreaFigure);

        drawingPanel.getView().selectAll();
        return textAreaFigure;
    }

    private static JButton getButton(JPanel panel, String toolTipText) {
        for (Component c : panel.getComponents()) {
            if (!(c instanceof JButton)) {
                continue;
            }
            JButton button = (JButton) c;
            if (button.getToolTipText() != null && button.getToolTipText().contains(toolTipText)) {
                return button;
            }
        }

        throw new RuntimeException("Could not find " + toolTipText + " button");
    }
}