/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Iso14496;

import java.nio.ByteBuffer;

/**
 *
 * @author mac
 */
public class IsoReader {

    public static int readIntAt(byte[] fullData, int i) {
        byte[] bytes = new byte[4];
        bytes[0] = fullData[i];
        bytes[1] = fullData[i+1];
        bytes[2] = fullData[i+2];
        bytes[3] = fullData[i+3];
        
       return ByteBuffer.wrap(bytes).getInt();
        
    }
    
}
