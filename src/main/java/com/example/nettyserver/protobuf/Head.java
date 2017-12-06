package com.example.nettyserver.protobuf;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 2017/11/13.
 */
public class Head {
    public static final int Header_Length = 32;

    private byte[] headpackage;

    private int commandCode;
    private int bodylength;

    private int version = 10;
    private int vendorid = 99;
    private int marketid = 11;
    private int is_checksum = 1;
    private int check_sum = 0;
    private int extend = 0;

    public Head(int bodylength, int commandCode, int marketid){
        this.commandCode = commandCode;
        this.bodylength = bodylength;
        this.marketid = marketid;
    }

    protected byte[] bulidPackage() throws Exception{
        List<byte[]>byteArray = new ArrayList<byte[]>();

        byte[] version = ByteArrayUtils.intToByteArray(this.version);
        byte[] length = ByteArrayUtils.intToByteArray(Header_Length + this.bodylength);
        byte[] command = ByteArrayUtils.intToByteArray(this.commandCode);
        byte[] vendor_id = ByteArrayUtils.intToByteArray(this.vendorid);
        byte[] market_id = ByteArrayUtils.intToByteArray(this.marketid);
        byte[] ischecksum = ByteArrayUtils.intToByteArray(this.is_checksum);
        byte[] checksum = ByteArrayUtils.intToByteArray(this.check_sum);
        byte[] extend = ByteArrayUtils.intToByteArray(this.extend);

        byteArray.add(version);
        byteArray.add(length);
        byteArray.add(command);
        byteArray.add(vendor_id);
        byteArray.add(market_id);
        byteArray.add(ischecksum);
        byteArray.add(checksum);
        byteArray.add(extend);

        byte[] data = ByteArrayUtils.streamCopy(byteArray);
        return  data;

    }

    public byte[] getHeadByte() throws Exception{
        this.headpackage = bulidPackage();
        return this.headpackage;
    }

    public int getCommandCode(){return this.commandCode;}

    public int getVersion(){return this.version;}

    public int getVendorid(){return  this.vendorid;}

    public int getBodylength(){return this.bodylength;}

    public int getMarketid(){return this.marketid;}

    public int getIs_checksum(){return this.is_checksum;}

    public int getCheck_sum(){return this.check_sum;}
}
