/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Iso14496.Boxes;

import Iso14496.Box;
import Iso14496.IsoReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mac
 */
public class MINF extends Box{

    public MINF() {
        super(Box.MINF);
    }

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
            byteStream.write(intToByteArray(8 + tempStream.size()));
            byteStream.write(intToByteArray(type));
            byteStream.write(tempStream.toByteArray());
            
        } catch (IOException ex) {
            Logger.getLogger(MOOV.class.getName()).log(Level.SEVERE, null, ex);
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

        offset += 8; //Account for head length
        //Now Find boxes inside MOOV
        do {
            boxSize = IsoReader.readIntAt(fileData, internalOffset + offset); //get box size
            boxType = IsoReader.readIntAt(fileData, internalOffset + offset + 4); // get box code
            //System.out.println(IsoFile.toASCII(boxType));
            //System.out.println(boxSize);

            //now lookup box code
            try {

                boxClass = Box.boxTable.get(boxType);
                if (boxClass != null) {

                    box = (Box) boxClass.newInstance();
                    box.setOffset(internalOffset + offset);
                    box.setFileData(fileData);
                    box.setContainer(this);
                    box.loadData();

                    children.add(box);
                }

            } catch (InstantiationException | IllegalAccessException ex) {
                System.out.println("box code not found");
            }

            //System.out.println("box length: " + boxSize + " box code:" + toASCII(boxCode));
            offset = offset + boxSize;

        } while (offset < internalSize);
    }
    
    
    public int getChunkCount() {
        for (Box box : children) {

            if (box.getBoxType() == Box.STBL) {

                return ((STBL) box).getChunkCount();

            }

        }

        return 0;
    }
    
    
    public int[] getChunkOffsets() {
        for (Box box : children) {

            if (box.getBoxType() == Box.STBL) {

                return ((STBL) box).getChunkOffsets();

            }

        }

        return null;
    }

}
