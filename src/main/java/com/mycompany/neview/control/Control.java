/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.neview.control;

import com.mycompany.neview.model.FileInParser;
import com.mycompany.neview.view.MainFrame;

/**
 *
 * @author mugarov
 */
public class Control {
    
    private final MainFrame frame;
    private final FileInParser data;
    
    public Control(MainFrame frame, String[] nameAndFilepath){
        this.frame = frame;
        this.data = new FileInParser(nameAndFilepath);
        this.frame.setValues(data.getDots(), data.getLines(), data.getName());
        this.frame.initFileExport(new ExportControl(this.frame, this.data));
    }
}
