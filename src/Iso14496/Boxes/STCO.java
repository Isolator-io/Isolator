/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Iso14496.Boxes;

import Iso14496.Box;
import Iso14496.FullBox;
import Iso14496.IsoReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mac
 */
public class STCO extends FullBox{
    int entry_count = 1;
    
    public STCO() {
        super(Box.STCO);
    }

    @Override
    public byte[] toBinary() {
        try {
            byteStream.write(intToByteArray(12 + 4 * entry_count + 4 ) );
            byteStream.write(intToByteArray(type));
            byteStream.write(intToByteArray(0));
            byteStream.write(intToByteArray(entry_count));
            
            byteStream.write(intToByteArray(40)); // first offset
            
        } catch (IOException ex) {
            Logger.getLogger(STSZ.class.getName()).log(Level.SEVERE, null, ex);
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
