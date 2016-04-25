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
public class ESDS extends FullBox{
    //byte[] data = {(byte)0x03,  (byte)0x80, (byte)0x80, (byte)0x80, (byte)0x22, (byte)0x00, (byte)0x01, (byte)0x00, (byte)0x04, (byte)0x80, (byte)0x80, (byte)0x80, (byte)0x14, (byte)0x40, (byte)0x15, (byte)0x00 ,(byte)0x05, (byte)0x44,(byte) 0x00, (byte)0x06, (byte)0x5B, (byte)0xC0, (byte)0x00, (byte)0x05, (byte)0xD4, (byte)0x25,(byte) 0x05, (byte)0x80, (byte)0x80,(byte) 0x80 ,(byte)0x02,(byte) 0x11, (byte)0xB0,(byte) 0x06, (byte)0x80,(byte) 0x80, (byte)0x80,(byte) 0x01, (byte)0x02};
    byte[] data = {(byte)0x03,  (byte)0x80, (byte)0x80, (byte)0x80, (byte)0x22, (byte)0x00, (byte)0x01, (byte)0x00, (byte)0x04, (byte)0x80, (byte)0x80, (byte)0x80, (byte)0x14, (byte)0x40, (byte)0x15, (byte)0x00 ,(byte)0x00, (byte)0x00,(byte) 0x00, (byte)0x06, (byte)0x5B, (byte)0xC0, (byte)0x00, (byte)0x05, (byte)0xD4, (byte)0x24,(byte) 0x05, (byte)0x80, (byte)0x80,(byte) 0x80 ,(byte)0x02,(byte) 0x11, (byte)0xB0,(byte) 0x06, (byte)0x80,(byte) 0x80, (byte)0x80,(byte) 0x01, (byte)0x02};
                                                                                                                                                                                                                //00 00 00 | 00 06 5B C0 00 05 D4 24 05 80 80 80 02 11 B0 06 80 80 80 01 02
    
    public ESDS() {
        super(Box.ESDS);
    }

    @Override
    public byte[] toBinary() {
        try {
            byteStream.write(intToByteArray(12 + data.length));
            byteStream.write(intToByteArray(type));
            byteStream.write(intToByteArray(0));
            byteStream.write(data);
            
            
        } catch (IOException ex) {
            Logger.getLogger(ESDS.class.getName()).log(Level.SEVERE, null, ex);
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
