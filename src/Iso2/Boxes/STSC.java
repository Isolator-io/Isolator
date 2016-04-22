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
public class STSC extends FullBox{
    int entry_count = 1;
    
    
    public STSC() {
        super(Box.STSC);
    }

    @Override
    public byte[] toBinary() {
        try {
            byteStream.write(intToByteArray(12+ 4 + (3 * entry_count * 4)));
            byteStream.write(intToByteArray(type));
            byteStream.write(intToByteArray(0));
            byteStream.write(intToByteArray(entry_count));
            
            byteStream.write(intToByteArray(1)); //first chunk
            byteStream.write(intToByteArray(633)); //samples per chunk
            byteStream.write(intToByteArray(1)); //first chunk index
            
            /*
            for(){
                
            }*/
        } catch (IOException ex) {
            Logger.getLogger(STSC.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return byteStream.toByteArray();
    }
    
}
