/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Iso2.Boxes;

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
public class SMHD extends FullBox{

    short balance = 0;
 
    public SMHD() {
        super(Box.SMHD);
    }

    @Override
    public byte[] toBinary() {
        try {
            byteStream.write(intToByteArray(16));
            byteStream.write(intToByteArray(type));
            byteStream.write(intToByteArray(0)); //Full Box Headers
            
            byteStream.write(shortToByteArray(balance)); //Full Box Headers
            byteStream.write(shortToByteArray((short)0)); //Full Box Headers
            
            
        } catch (IOException ex) {
            Logger.getLogger(SMHD.class.getName()).log(Level.SEVERE, null, ex);
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
