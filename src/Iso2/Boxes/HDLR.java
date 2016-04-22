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
public class HDLR extends FullBox{
   int  pre_defined = 0;
   int  handler_type;
   String   name;
   byte[] soundHandler = {0x53 , 0x6F ,  0x75 ,  0x6E,  0x64 , 0x48, 0x61, 0x6E ,0x64 , 0x6C ,0x65, 0x72, 0x00};
    
    
    public HDLR() {
        super(Box.HDLR);
    }

    @Override
    public byte[] toBinary() {
       try {
            byteStream.write(intToByteArray((8*4) + soundHandler.length));
            byteStream.write(intToByteArray(type));
            byteStream.write(intToByteArray(0)); //Full Box Headers
            
            byteStream.write(intToByteArray(0));
            byteStream.write(intToByteArray(Box.SOUN)); //handler type

            
            byteStream.write(intToByteArray(0));
            byteStream.write(intToByteArray(0));
            byteStream.write(intToByteArray(0));
            
            
            byteStream.write(soundHandler);
            //byteStream.write(shortToByteArray((short)0)); //reserved
            
            
            
       } catch (IOException ex) {
           Logger.getLogger(HDLR.class.getName()).log(Level.SEVERE, null, ex);
       }
          
            
            return byteStream.toByteArray();
    }
    
}
