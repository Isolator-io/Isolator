/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Iso14496;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author mac
 */
public class Box {
    int size;
    int type;
    
    /*
    public Box(int boxtype){
        
    }
    
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
