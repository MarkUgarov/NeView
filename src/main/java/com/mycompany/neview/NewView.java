/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.neview;

import com.mycompany.neview.control.Control;
import com.mycompany.neview.view.MainFrame;

/**
 *
 * @author mugarov
 */
public class NewView {
    
    public static void main(String[] args){
        MainFrame frame = new MainFrame();
        
        /**
         * For Testing only!
         */
        if(args.length == 0){
            args = new String[]{"Test", "/vol/ampipe/data/ExampleInputNeView/454AllContigs.fna"};
        }
        Control ctrl = new Control(frame, args);
        
        
    }
}
