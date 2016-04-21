/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Iso14496.Boxes;

import Iso14496.Box;

/**
 *
 * @author mac
 */
public class MediaDataBox extends Box{
    byte[] data;

    public MediaDataBox(int boxtype) {
        super(boxtype);
    }
    

    
}
