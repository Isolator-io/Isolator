/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Iso14496;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 *
 * @author mac
 */
public abstract class Box {
    
    public static final int FTYP = 0x66747970;
    public static final int FREE = 0x66726565;
    public static final int MDAT = 0x6D646174;
    public static final int MOOV = 0x6D6F6F76;
    public static final int MVHD = 0x6D766864;
    public static final int TRAK = 0x7472616B;
    public static final int TKHD = 0x746B6864;
    public static final int MDIA = 0x6D646961;
    public static final int MDHD = 0x6D646961;
    public static final int HDLR = 0x68646C72;
    public static final int SOUN = 0x736F756E;
    public static final int MINF = 0x6D696E66;
    public static final int SMHD = 0x736D6864;
    public static final int DINF = 0x64696E66;
    public static final int DREF = 0x64726566;
    public static final int URL = 0x75726C20;
    public static final int STBL = 0x7374626C;
    public static final int STSD = 0x73747364;
    public static final int ESDS = 0x65736473;
    public static final int STTS = 0x73747473;
    public static final int STSC = 0x73747363;
    public static final int STSZ = 0x7374737A;
    public static final int STCO = 0x7374636F;
     
    ByteArrayOutputStream byteStream;       
    ArrayList<Box> children;
    int internalSize;
    int type;
    
  
    public Box(int boxtype){
        children = new ArrayList<Box>();
        byteStream = new ByteArrayOutputStream();
        
    }
    
    public void addBox(Box boxToAdd){
        children.add(boxToAdd);
    }
    
    public int getSize() {
        int totalSize = internalSize;
        if (children.size() > 0) {
            for (Box box : children) {
                totalSize = box.getSize();
            }
        }
        return totalSize;
    }
    
    
    /*
    public Box(String boxtype){
        byte[] bytes = boxtype.getBytes(StandardCharsets.UTF_8);
        type = ByteBuffer.wrap(bytes).getInt();
    }
    */
    //Page 6
    /**
     * Boxes start with a header which gives both size and type. The header permits compact or extended size (32 or 64 bits) and compact or extended types (32 bits or full Universal Unique IDentifiers, i.e. UUIDs). The standard boxes all use compact types (32‐bit) and most boxes will use the compact (32‐bit) size. Typically only the Media Data Box(es) need the 64‐bit size.
The size is the entire size of the box, including the size and type header, fields, and all contained boxes. This facilitates general parsing of the file.
     */
    
}
