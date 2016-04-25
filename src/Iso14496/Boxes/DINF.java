/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Iso14496.Boxes;

import Iso14496.Box;
import Iso14496.IsoReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mac
 */
public class DINF extends Box{

    public DINF() {
        super(Box.DINF);
    }
    
        @Override
    public byte[] toBinary() {
        ByteArrayOutputStream tempStream = new ByteArrayOutputStream();
        if (children.size() > 0) {
            for (Box box : children) {
                try {
                    tempStream.write(box.toBinary());
                } catch (IOException ex) {
                    Logger.getLogger(MOOV.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        try {
            byteStream.write(intToByteArray(8 + tempStream.size()));
            byteStream.write(intToByteArray(type));
            byteStream.write(tempStream.toByteArray());
            
        } catch (IOException ex) {
            Logger.getLogger(MOOV.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return byteStream.toByteArray();
    }

    @Override
    public void loadData() {
        
        int boxType;
        int offset = 0;
        int boxSize;
        Box box = null;
        Class boxClass = null;

        internalSize = IsoReader.readIntAt(fileData, internalOffset + offset); //get box size
    }
    
}
