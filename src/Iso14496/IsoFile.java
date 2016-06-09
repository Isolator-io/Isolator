/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Iso14496;

import Iso14496.Boxes.DINF;
import Iso14496.Boxes.DREF;
import Iso14496.Boxes.EDTS;
import Iso14496.Boxes.ELST;
import Iso14496.Boxes.ESDS;
import Iso14496.Boxes.FREE;
import Iso14496.Boxes.FTYP;
import Iso14496.Boxes.HDLR;
import Iso14496.Boxes.MDAT;
import Iso14496.Boxes.MDHD;
import Iso14496.Boxes.MDIA;
import Iso14496.Boxes.MINF;
import Iso14496.Boxes.MOOV;
import Iso14496.Boxes.MP4A;
import Iso14496.Boxes.MVHD;
import Iso14496.Boxes.SMHD;
import Iso14496.Boxes.STBL;
import Iso14496.Boxes.STCO;
import Iso14496.Boxes.STSC;
import Iso14496.Boxes.STSD;
import Iso14496.Boxes.STSZ;
import Iso14496.Boxes.STTS;
import Iso14496.Boxes.TKHD;
import Iso14496.Boxes.TRAK;
import Iso14496.Boxes.URL;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.util.ArrayList;

/**
 *
 * @author mac
 */
public class IsoFile {

    byte[] fullData;
    int size;
    long fileSize;
    protected ArrayList<Box> boxes;
    protected ByteArrayOutputStream byteStream;
    RandomAccessFile randomAccessFile;

    public IsoFile() {
        byteStream = new ByteArrayOutputStream();

    }

    public IsoFile(File videoFile) throws FileNotFoundException, IOException {

        byteStream = new ByteArrayOutputStream();
        randomAccessFile = new RandomAccessFile(videoFile.getPath(), "r");
        boxes = new ArrayList<Box>();
        fullData = Files.readAllBytes(videoFile.toPath());
        loadData();
        displayBoxes();

    }

    private void loadData() throws IOException {

        fileSize = randomAccessFile.length();
        int boxType;
        long offset = 0;
        int boxSize;
        Box box = null;
        Class boxClass = null;

        //Read all top level boxes
        do {

            randomAccessFile.seek(offset);
            boxSize = randomAccessFile.readInt();
            boxType = randomAccessFile.readInt();

            //now lookup box code
            try {

                boxClass = Box.boxTable.get(boxType);
                if (boxClass != null) {

                    box = (Box) boxClass.newInstance();
                    box.setOffset((int) offset);
                    box.setFileData(fullData);
                    box.loadData();

                    boxes.add(box);
                }

            } catch (InstantiationException | IllegalAccessException ex) {
                System.out.println("box code not found");
            }

            offset = (int) randomAccessFile.getFilePointer() + boxSize;

        } while (offset < fileSize);

    }

    //FOR TESTING ONLY
    public static String toASCII(int value) {
        int length = 4;
        StringBuilder builder = new StringBuilder(length);
        for (int i = length - 1; i >= 0; i--) {
            builder.append((char) ((value >> (8 * i)) & 0xFF));
        }
        return builder.toString();
    }

    public void displayBoxes() {

        for (Box box : boxes) {

            //System.out.println("box :" +  toASCII(box.getBoxType()));
            box.displayData();

        }

    }

    public void addBox(Box box) {
        boxes.add(box);
    }

    public TRAK getAudioTrack() {
        TRAK audioTrack = null;

        for (Box box : boxes) {

            if (box.getBoxType() == Box.MOOV) {
                audioTrack = ((MOOV) box).getAudioTrack();
                break;
            }

        }
        return audioTrack;
    }

    public static IsoFile ripAudio(IsoFile inputFile) {

        ByteArrayOutputStream byteStreamBuilder = new ByteArrayOutputStream();

        TRAK audioTrack = inputFile.getAudioTrack();

        IsoFile isoFile = new IsoFile();
        System.out.println("ripped chunk count : " + audioTrack.getChunkCount());

        int stcoChunkCount = audioTrack.getChunkCount();
        int[][] rippedBinary = new int[stcoChunkCount][3];

        int[] offsets = audioTrack.getChunkOffsets();

        for (int n = 0; n < stcoChunkCount; n++) {
            rippedBinary[n][0] = offsets[n];
        }

        /*
                    for (int n = 0; n < stcoChunkCount; n++) {
         
                

                byteStreamBuilder.write(fullData, totalData[n][0] , totalData[n][2]);
            
            }
        
         */

 /*
        FREE free = new FREE();
        //free.setData(new byte[4]);

        FTYP ftyp = new FTYP();
        ftyp.setMajorBrand(0x69736F6D);
        ftyp.setMinorVersion(0x00000200);
        ftyp.setCompatibleBrands(new int[]{0x69736F6D, 0x69736F32});

        MDAT mdat = new MDAT();
        mdat.setData(new byte[3]);

        MOOV moov = new MOOV();
        MVHD mvhd = new MVHD();
        TRAK trak = new TRAK();
        TKHD tkhd = new TKHD();
        EDTS edts = new EDTS();
        ELST elst = new ELST();
        MDIA mdia = new MDIA();
        MDHD mdhd = new MDHD();
        HDLR hdlr = new HDLR();
        MINF minf = new MINF();
        SMHD smhd = new SMHD();
        DINF dinf = new DINF();
        DREF dref = new DREF();
        URL url = new URL();
        STBL stbl = new STBL();
        STSD stsd = new STSD();
        MP4A mp4a = new MP4A();
        ESDS esds = new ESDS();

        STTS stts = new STTS();
        STSC stsc2 = new STSC();
        STSZ stsz2 = new STSZ(new int[4]); // sampleSizes
        STCO stco2 = new STCO();

        //ADD TOP LEVEL BOXES
        isoFile.addBox(free);
        isoFile.addBox(ftyp);
        isoFile.addBox(mdat);
        isoFile.addBox(moov);

        moov.addBox(mvhd);
        moov.addBox(trak);
        trak.addBox(tkhd);
        trak.addBox(edts);
        edts.addBox(elst);
        trak.addBox(mdia);
        mdia.addBox(mdhd);
        mdia.addBox(hdlr);
        mdia.addBox(minf);
        minf.addBox(smhd);
        minf.addBox(dinf);
        dinf.addBox(dref);
        dref.addBox(url);
        minf.addBox(stbl);
        stbl.addBox(stsd);
        stsd.addBox(mp4a);
        mp4a.addBox(esds);
        stbl.addBox(stts);
        stbl.addBox(stsc2);
        stbl.addBox(stsz2);
        stbl.addBox(stco2);
         */
        return isoFile;
    }

    public byte[] toBinary() throws IOException {

        for (Box box : boxes) {
            byteStream.write(box.toBinary());
        }
        return byteStream.toByteArray();
    }

}
