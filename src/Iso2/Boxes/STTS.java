/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Iso2.Boxes;

import Iso14496.Box;
import Iso14496.FullBox;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mac
 */
public class STTS extends FullBox{
    int entry_count = 1;
    
    
    public STTS() {
        super(Box.STTS);
    }

    @Override
    public byte[] toBinary() {
        
        try {
            //hardcode for now
            byteStream.write(intToByteArray(24));
            byteStream.write(intToByteArray(type));
            byteStream.write(intToByteArray(0));
            byteStream.write(intToByteArray(entry_count));
            byteStream.write(intToByteArray(633));
            byteStream.write(intToByteArray(1024));
            
        } catch (IOException ex) {
            Logger.getLogger(STTS.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return byteStream.toByteArray();
    }
    
}
