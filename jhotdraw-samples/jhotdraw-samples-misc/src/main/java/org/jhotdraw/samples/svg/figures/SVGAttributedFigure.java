/*
 * @(#)SVGAttributedFigure.java
 *
 * Copyright (c) 1996-2010 The authors and contributors of JHotDraw.
 * You may not use, copy or modify this file, except in compliance with the
 * accompanying license terms.
 */
package org.jhotdraw.samples.svg.figures;

import org.jhotdraw.draw.figure.AbstractAttributedFigure;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.util.*;
import javax.swing.*;
import org.jhotdraw.draw.*;
import static org.jhotdraw.draw.AttributeKeys.STROKE_WIDTH;
import static org.jhotdraw.draw.AttributeKeys.TRANSFORM;
import org.jhotdraw.samples.svg.SVGAttributeKeys;
import static org.jhotdraw.samples.svg.SVGAttributeKeys.*;
import org.jhotdraw.util.*;

/**
 * SVGAttributedFigure.
 *
 * @author Werner Randelshofer
 * @version $Id$
 */
public abstract class SVGAttributedFigure extends AbstractAttributedFigure {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a new instance.
     */
    public SVGAttributedFigure() {
    }

    @Override
    public void draw(Graphics2D g) {
        double opacity = get(OPACITY);
        if (opacity <= 0) {
            return; // Nothing to draw if opacity is zero or negative
        }

        Rectangle2D.Double drawingArea = getDrawingArea();
        Rectangle2D clipBounds = g.getClipBounds();

        if (clipBounds != null) {
            Rectangle2D.intersect(drawingArea, clipBounds, drawingArea);
        }

        if (drawingArea.isEmpty()) {
            return; // Nothing to draw if the drawing area is empty
        }

        BufferedImage buf = createBufferedImage(g, drawingArea);
        if (opacity != 1.0) {
            compositeAndDrawImage(g, buf, drawingArea, opacity);
        } else {
            g.drawImage(buf, (int) drawingArea.x, (int) drawingArea.y,
                    (int) drawingArea.width + 2, (int) drawingArea.height + 2, null);
        }
    }

    private BufferedImage createBufferedImage(Graphics2D g, Rectangle2D.Double drawingArea) {
        int bufWidth = Math.max(1, (int) ((2 + drawingArea.width) * g.getTransform().getScaleX()));
        int bufHeight = Math.max(1, (int) ((2 + drawingArea.height) * g.getTransform().getScaleY()));
        BufferedImage buf = new BufferedImage(bufWidth, bufHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gr = buf.createGraphics();

        gr.scale(g.getTransform().getScaleX(), g.getTransform().getScaleY());
        gr.translate((int) -drawingArea.x, (int) -drawingArea.y);
        gr.setRenderingHints(g.getRenderingHints());

        drawFigure(gr);

        gr.dispose();
        return buf;
    }

    private void compositeAndDrawImage(Graphics2D g, BufferedImage buf, Rectangle2D.Double drawingArea, double opacity) {
        Composite savedComposite = g.getComposite();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) opacity));
        g.drawImage(buf, (int) drawingArea.x, (int) drawingArea.y,
                (int) drawingArea.width + 2, (int) drawingArea.height + 2, null);
        g.setComposite(savedComposite);
    }

    /**
     * This method is invoked before the rendered image of the figure is
     * composited.
     */
    public void drawFigure(Graphics2D g) {
        AffineTransform savedTransform = null;
        if (get(TRANSFORM) != null) {
            savedTransform = g.getTransform();
            g.transform(get(TRANSFORM));
        }
        Paint paint = SVGAttributeKeys.getFillPaint(this);
        if (paint != null) {
            g.setPaint(paint);
            drawFill(g);
        }
        paint = SVGAttributeKeys.getStrokePaint(this);
        if (paint != null && get(STROKE_WIDTH) > 0) {
            g.setPaint(paint);
            g.setStroke(SVGAttributeKeys.getStroke(this, 1.0));
            drawStroke(g);
        }
        if (get(TRANSFORM) != null) {
            g.setTransform(savedTransform);
        }
    }

    @Override
    public <T> void set(AttributeKey<T> key, T newValue) {
        if (key == TRANSFORM) {
            invalidate();
        }
        super.set(key, newValue);
    }

    @Override
    public Collection<Action> getActions(Point2D.Double p) {
        LinkedList<Action> actions = new LinkedList<Action>();
        if (get(TRANSFORM) != null) {
            ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.samples.svg.Labels");
            actions.add(new AbstractAction(labels.getString("edit.removeTransform.text")) {
                private static final long serialVersionUID = 1L;

                @Override
                public void actionPerformed(ActionEvent evt) {
                    willChange();
                    fireUndoableEditHappened(
                            TRANSFORM.setUndoable(SVGAttributedFigure.this, null)
                    );
                    changed();
                }
            });
        }
        return actions;
    }
}
