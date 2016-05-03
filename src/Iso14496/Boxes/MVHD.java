/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Iso14496.Boxes;

import Iso14496.Box;
import Iso14496.FullBox;
import Iso14496.IsoFile;
import Iso14496.IsoReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mac NOW MATCHES
 */
public class MVHD extends FullBox {

    int creation_time = 0;//0x7C25B080;
    int modification_time = 0; //0xCFF05A35;
    int timescale = 0x000003E8;
    int duration = 0x000034C0;
    int rate = 0x00010000; // typically 1.0
    short volume = 0x0100;   // typically, full volume
    short reserved = 0;
    //int[]  reserved = {0,0};
    int[] matrix = {0x00010000, 0, 0, 0, 0x00010000, 0, 0, 0, 0x40000000};
    // Unity matrix
    int[] pre_defined = new int[6];
    int next_track_ID = 2;

    public MVHD() {
        super(Box.MVHD);
    }

    @Override
    public byte[] toBinary() {
        byteStream.reset();
        try {
            byteStream.write(intToByteArray(8 + 100)); //size
            byteStream.write(intToByteArray(type));
            byteStream.write(intToByteArray(0)); // version + flags

            byteStream.write(intToByteArray(creation_time));
            byteStream.write(intToByteArray(modification_time));
            byteStream.write(intToByteArray(timescale));
            byteStream.write(intToByteArray(duration));

            byteStream.write(intToByteArray(rate));

            byteStream.write(shortToByteArray(volume)); //short
            byteStream.write(shortToByteArray((short) 0)); // reserved short

            byteStream.write(intToByteArray(0)); // reserved 10
            byteStream.write(intToByteArray(0)); // reserved 11

            for (int n = 0; n < 9; n++) {
                byteStream.write(intToByteArray(matrix[n])); //20
            }

            for (int n = 0; n < 6; n++) {
                byteStream.write(intToByteArray(0)); //26
            }

            byteStream.write(intToByteArray(next_track_ID)); //27
            byteStream.flush();

        } catch (IOException ex) {
            Logger.getLogger(MVHD.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("test");
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

        creation_time = IsoReader.readIntAt(fileData, internalOffset + 12);
        modification_time = IsoReader.readIntAt(fileData, internalOffset + 16);
        timescale = IsoReader.readIntAt(fileData, internalOffset + 20);
        duration = IsoReader.readIntAt(fileData, internalOffset + 24);
        rate = IsoReader.readIntAt(fileData, internalOffset + 28);
        volume = IsoReader.readShortAt(fileData, internalOffset + 32);

        next_track_ID = IsoReader.readShortAt(fileData, internalOffset + 104);
    }

    public String toString() {

        return IsoFile.toASCII(type) + " Next Track : " + next_track_ID;

    }

}
