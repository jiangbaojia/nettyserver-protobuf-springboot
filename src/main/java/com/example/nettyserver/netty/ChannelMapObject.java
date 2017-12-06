package com.example.nettyserver.netty;

import io.netty.channel.socket.SocketChannel;

/**
 * Created by Eric on 2017/11/22.
 */
public class ChannelMapObject {
    private int lostHeartBeatCount = 0;
    private SocketChannel SC;

    public ChannelMapObject(SocketChannel socketChannel){
        this.SC = socketChannel;
    }

    public int getLostHeartBeatCount(){return this.lostHeartBeatCount;}
    public void plusLostCount(){this.lostHeartBeatCount ++;}

    public SocketChannel getSocketChannel(){return this.SC; }
}
