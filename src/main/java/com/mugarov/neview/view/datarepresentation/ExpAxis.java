/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.neview.view.datarepresentation;

import java.text.NumberFormat;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;

/**
 *
 * @author mugarov
 */
public class ExpAxis extends NumberAxis{
    
    public ExpAxis(String name){
        super(name);
        super.setNumberFormatOverride(new ExpFormat());
    }
}
