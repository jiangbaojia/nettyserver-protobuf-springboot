package com.example.nettyserver.netty;

import java.io.Serializable;

/**
 * Created by Eric on 2017/11/21.
 */
public class BaseMsg implements Serializable {
    private int command;
    private String Loginid;
    private byte[] bodyData;

    public BaseMsg(int command, byte[] bodyData){
        this.command = command;
        this.bodyData = bodyData;
    }

    public int getCommand(){return this.command;}
    public void setCommand(int command){
        this.command = command;
    }

    public byte[] getBodyData(){return this.bodyData;}
    public void setBodyData(byte[] bodyData){
        this.bodyData = bodyData;
    }
}
