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
public class URL extends FullBox{
    byte[] location = {};
    public URL() {
        super(Box.URL);
    }

    @Override
    public byte[] toBinary() {
        
        try {
            //byteStream.write(intToByteArray(12 + location.length)); //size
            byteStream.write(intToByteArray(12 )); //size
            byteStream.write(intToByteArray(type)); //type 
            byteStream.write(intToByteArray(1)); //type 
            //byteStream.write(location); //type 
        } catch (IOException ex) {
            Logger.getLogger(URL.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return byteStream.toByteArray();
    }
    
}
