/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.neview;

import com.mugarov.neview.control.Control;
import com.mugarov.neview.view.MainFrame;

/**
 *
 * @author mugarov
 */
public class NewView {
    
    public static void main(String[] args){
        boolean cont = true;
        if(args==null || args.length == 0){
            System.err.println("Please give atleast one fasta file to read from. For more choose '-help'.");
            cont = false;
        }
        else{
            for(String s:args){
                if(s.equals("-help")){
                    StringBuilder out = new StringBuilder();
                    out.append("Please open NeView like this:\n");
                    out.append("java -jar NeView-0.0.1.jar ");
                    out.append("<Name> <file1> <file2> <file3> .....\n");
                    out.append("where the files");
                    out.append("\n - should at least contain one fasta file (*.fa, *.fasta, *.fna...)");
                    out.append("\n - can contain text files describing the belonging of contigs to scaffolds (name of the scaffold in the first column, name of the contig in the sixth, distinguished by whitespaces)");
                    out.append("\n Please note: the first arguement will allways be taken as the name.");
                    System.out.println(out.toString());
                    cont = false;        
                }
            }
        }
        if(cont){
            MainFrame frame = new MainFrame();
            Control ctrl = new Control(frame, args);
        }
       
    }
}
