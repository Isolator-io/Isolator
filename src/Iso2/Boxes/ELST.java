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
public class ELST extends FullBox{
    int entry_count =1;
    int segment_duration =13504;
    int media_time =0;
    short media_rate_integer = 1;
    short media_rate_fraction = (short) 0;

    public ELST() {
        super(Box.ELST);
    }

    @Override
    public byte[] toBinary() {
    
        try {
            byteStream.write(intToByteArray(28));
            byteStream.write(intToByteArray(type));
            byteStream.write(intToByteArray(0));
            
            byteStream.write(intToByteArray(entry_count));
            byteStream.write(intToByteArray(segment_duration));
            byteStream.write(intToByteArray(media_time));
            
            byteStream.write(shortToByteArray(media_rate_integer));
            byteStream.write(shortToByteArray(media_rate_fraction));
            
            
        } catch (IOException ex) {
            Logger.getLogger(ELST.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return byteStream.toByteArray();
        
    }
    
}
