/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.neview.view;

import java.awt.Component;
import javax.swing.JSplitPane;

/**
 *
 * @author mugarov
 */
public class SplitetPane extends JSplitPane{
    private boolean widthIsSet;
    
    public SplitetPane(Component west, Component east){
        super(JSplitPane.HORIZONTAL_SPLIT, west, east);
        this.widthIsSet = false;
        this.setDoubleBuffered(true);
        this.setDividerLocation(this.getWidth()-100);
        super.setOneTouchExpandable(true);
    }
    
    public void checkWidth(){
        if(!this.widthIsSet){
            this.updateUI();
            this.setDividerLocation((this.getWidth()/3)*2);
//            System.out.println("Setting width to "+this.getDividerLocation());
            this.updateUI();
            this.widthIsSet = true;
        }
    }
}
