/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Iso14496;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mac
 */
public class IsoFile {
    byte[] fullData;
    int size;
    protected ArrayList<Box> boxes;
    
    
    
    public IsoFile(File videoFile) throws FileNotFoundException, IOException {
        boxes = new ArrayList<Box>();
        fullData = Files.readAllBytes(videoFile.toPath());
        loadData();
        displayBoxes();
    
    }
    
    private void loadData(){
        int boxType;
        int offset = 0;
        int boxSize;
        Box box = null;
        Class boxClass = null;
        
        
        do{
            boxSize = IsoReader.readIntAt(fullData , offset ); //get box size
            boxType = IsoReader.readIntAt(fullData , offset + 4); // get box code
            System.out.println(boxSize);
            //now lookup box code
            try {
                
                boxClass = Box.boxTable.get(boxType);
                if (boxClass != null) {

                    box = (Box) boxClass.newInstance();
                    box.setOffset(offset);
                    box.setFileData(fullData);
                    box.loadData();
                    
                    
                    boxes.add(box);
                }
                
            } catch (InstantiationException | IllegalAccessException ex) {
                System.out.println("box code not found");
            }
                
            
      
            
            //System.out.println("box length: " + boxSize + " box code:" + toASCII(boxCode));
            
            offset = offset + boxSize;
            
        }while(offset < fullData.length);
        
    }
    
    //FOR TESTING ONLY
    public static String toASCII(int value) {
        int length = 4;
        StringBuilder builder = new StringBuilder(length);
        for (int i = length - 1; i >= 0; i--) {
            builder.append((char) ((value >> (8 * i)) & 0xFF));
        }
        return builder.toString();
    }
    
    public void displayBoxes(){
        
        for( Box box: boxes){
            
            //System.out.println("box :" +  toASCII(box.getBoxType()));
            box.displayData();
            
        }
    
        
    }
    
}
