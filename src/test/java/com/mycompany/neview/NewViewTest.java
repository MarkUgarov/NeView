/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.neview;

import com.mugarov.neview.NewView;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mugarov
 */
public class NewViewTest {
    
    public NewViewTest() {
    }
    

    /**
     * Test of main method, of class NewView.
     */
    @org.junit.Test
    public void testMain() {
        System.out.println("main");
        String dir = "/vol/ampipe/data/mugarov/output/Miseq___Data/Newbler_M240_TGACCA_L001/";
        String[] args = new String[]{"Test", dir+"454AllContigs.fna", dir+"454Scaffolds.txt"};
        NewView.main(args);
        try {
            Thread.sleep(100000);
        } catch (InterruptedException ex) {
            Logger.getLogger(NewViewTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
