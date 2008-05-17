/*
 * @(#)ClearSelectionAction.java  1.0  2008-05-17
 *
 * Copyright (c) 2008 by the original authors of JHotDraw
 * and all its contributors.
 * All rights reserved.
 *
 * The copyright of this software is owned by the authors and  
 * contributors of the JHotDraw project ("the copyright holders").  
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * the copyright holders. For details see accompanying license terms. 
 */

package org.jhotdraw.app.action;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import java.beans.*;
import java.util.*;
import org.jhotdraw.util.*;
import org.jhotdraw.app.EditableComponent;

/**
 * ClearSelectionAction.
 *
 * @author Werner Randelshofer.
 * @version 1.0 2008-05-17 Created.
 */
public class ClearSelectionAction extends AbstractAction {
    public final static String ID = "selectionClear";
    
    /** Creates a new instance. */
    public ClearSelectionAction() {
        ResourceBundleUtil labels = ResourceBundleUtil.getLAFBundle("org.jhotdraw.app.Labels");
        labels.configureAction(this, ID);
    }
    
    public void actionPerformed(ActionEvent evt) {
        Component focusOwner = KeyboardFocusManager.
                getCurrentKeyboardFocusManager().
                getPermanentFocusOwner();
        if (focusOwner != null) {
            if (focusOwner instanceof EditableComponent) {
                ((EditableComponent) focusOwner).clearSelection();
            } else if (focusOwner instanceof JTextComponent) {
               JTextComponent tc = ((JTextComponent) focusOwner);
               tc.select(tc.getSelectionStart(), tc.getSelectionStart());
            } else {
                focusOwner.getToolkit().beep();
            }
        }
    }
}