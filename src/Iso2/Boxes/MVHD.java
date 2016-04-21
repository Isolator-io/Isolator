/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Iso2.Boxes;

import Iso14496.Box;
import Iso14496.FullBox;

/**
 *
 * @author mac
 */
public class MVHD extends FullBox{
    int  creation_time;
    int  modification_time;
    int  timescale;
    int  duration;
    int  rate = 0x00000001; // typically 1.0
    short  volume = 0x0100;   // typically, full volume
    short  reserved = 0;
    //int[]  reserved = {0,0};
    int[]  matrix = { 0x00010000,0,0,0,0x00010000,0,0,0,0x40000000 };
      // Unity matrix
    int[]  pre_defined = new int[6];
    int  next_track_ID;
      
    public MVHD() {
        super(Box.MVHD);
    }
    
}
