/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Iso14496;

import static Iso14496.Box.*;
import Iso2.Boxes.*;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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
    public static final int MDHD = 0x6D646864;
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
    public static final int MP4A = 0x6D703461;
    public static final int EDTS = 0x65647473;
    public static final int ELST = 0x656C7374;
 

     
    protected ByteArrayOutputStream byteStream;       
    protected ArrayList<Box> children;
    protected Box container = null;
    protected int internalSize;
    protected long internalExtendedSize;
    protected int internalOffset;
    protected int type;
    protected byte[] fileData = null; //temp
    protected boolean isExtendedSize = false;
    protected int headerOffset;
    
    public static final Map<Integer, Class> boxTable = initializeTable();
    
    private static Map<Integer, Class> initializeTable() {

        Map<Integer, Class> table = new HashMap<Integer, Class>();

        try {

            table.put(FTYP, Iso2.Boxes.FTYP.class);
            table.put(FREE, Iso2.Boxes.FREE.class);
            table.put(MDAT, Iso2.Boxes.MDAT.class);
            table.put(MOOV, Iso2.Boxes.MOOV.class);
            table.put(MVHD, Iso2.Boxes.MVHD.class);
            table.put(TRAK, Iso2.Boxes.TRAK.class);
            table.put(TKHD, Iso2.Boxes.TKHD.class);
            table.put(MDIA, Iso2.Boxes.MDIA.class);
            table.put(MDHD, Iso2.Boxes.MDHD.class);
            table.put(HDLR, Iso2.Boxes.HDLR.class);
            //table.put(SOUN, Iso2.Boxes.SOUN.class);
            table.put(MINF, Iso2.Boxes.MINF.class);
            table.put(SMHD, Iso2.Boxes.SMHD.class);
            table.put(DINF, Iso2.Boxes.DINF.class);
            table.put(DREF, Iso2.Boxes.DREF.class);
            table.put(URL, Iso2.Boxes.URL.class);
            table.put(STBL, Iso2.Boxes.STBL.class);
            table.put(STSD, Iso2.Boxes.STSD.class);
            table.put(ESDS, Iso2.Boxes.ESDS.class);
            table.put(STTS, Iso2.Boxes.STTS.class);
            table.put(STSC, Iso2.Boxes.STSC.class);
            table.put(STSZ, Iso2.Boxes.STSZ.class);
            table.put(STCO, Iso2.Boxes.STCO.class);
            table.put(MP4A, Iso2.Boxes.MP4A.class);
            table.put(EDTS, Iso2.Boxes.EDTS.class);
            table.put(ELST, Iso2.Boxes.ELST.class);
            


 


        } catch (Exception e) {
            
            System.out.println("couldnt add " + e.getMessage());
        }

        return Collections.unmodifiableMap(table);

    }
    
  
    public Box(int boxType){
        children = new ArrayList<Box>();
        byteStream = new ByteArrayOutputStream();
        type = boxType;
        headerOffset = 8;
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
    
    public byte[] intToByteArray(int input){
        return ByteBuffer.allocate(4).putInt(input).array();
    }
    
    public byte[] shortToByteArray(short input){
        return ByteBuffer.allocate(2).putShort(input).array();
    }
    
    public abstract byte[] toBinary();
    
    public int getBoxType(){
        return type;
    }
    
    public abstract void loadData();
            
    public void setSize(int size){
        internalSize = size;
    }
    
    public void setOffset(int offset){
        internalOffset = offset;
    }
    
    public void setFileData(byte[] data){
        fileData = data;
    }
    
    public int getDepth(){
        if(container == null){
            return 1;
        }else{
            return 1 + container.getDepth();
        }
    }
    
    public void displayData() {
        int n = getDepth();
        char[] chars = new char[n];
        Arrays.fill(chars, '-');
        String indent = new String(chars);
        System.out.println(indent+ "> " + toString());
    }
    
    public String toString(){
        return IsoFile.toASCII(type);
    }
    
    /*
    public Box(String boxtype){
        byte[] bytes = boxtype.getBytes(StandardCharsets.UTF_8);
        type = ByteBuffer.wrap(bytes).getInt();
    }
*/

}
