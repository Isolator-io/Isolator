/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Iso2.Boxes;

import Iso14496.Box;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mac
 */
public class MP4A extends Box{
    byte[] reserved = new byte[6];
    short data_reference_index = (byte)1;
    
    
    public MP4A() {
        super(Box.MP4A);
    }
/*
    @Override
    public byte[] toBinary() {
        try {
            byteStream.write(intToByteArray(8 + 6 + 2));
            byteStream.write(intToByteArray(type));
            byteStream.write(reserved);
            byteStream.write(shortToByteArray(data_reference_index));
        } catch (IOException ex) {
            Logger.getLogger(MP4A.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return byteStream.toByteArray();
    }
*/
    
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
                 
            byteStream.write(intToByteArray(36 + tempStream.size()));
            byteStream.write(intToByteArray(type));
            
            byteStream.write(intToByteArray(0));//00 00 00 00
            byteStream.write(shortToByteArray((short)0));//00 00
            byteStream.write(shortToByteArray(data_reference_index));// 00 01
            
            byteStream.write(intToByteArray(0));//00 00 00 00
            byteStream.write(intToByteArray(0));//00 00 00 00
            byteStream.write(intToByteArray(0x00020010));//00 02 00 10
            
            byteStream.write(shortToByteArray((short)0)); //00 00
            byteStream.write(intToByteArray(0x0000BB80));//sample rate
            byteStream.write(shortToByteArray((short)0));//00 00
            
            
            byteStream.write(tempStream.toByteArray());
            
        } catch (IOException ex) {
            Logger.getLogger(MOOV.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return byteStream.toByteArray();
    }
    
}
