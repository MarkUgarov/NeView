/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.neview.control;

import com.mycompany.neview.model.FileParser;
import com.mycompany.neview.view.MainFrame;

/**
 *
 * @author mugarov
 */
public class Control {
    
    private final MainFrame frame;
    private FileParser data;
    
    public Control(MainFrame frame, String[] NameAndFilePaths){
        this.frame = frame;
        this.data = new FileParser(NameAndFilePaths);
        this.frame.setValues(data.getDots(), data.getLines(), data.getName());
    }
}
