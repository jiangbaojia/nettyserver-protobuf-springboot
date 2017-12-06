package com.example.nettyserver.netty;

import io.netty.channel.Channel;
import io.netty.channel.socket.SocketChannel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Eric on 2017/11/21.
 */
public class NettyChannelMap {
/*    private static Map<String,SocketChannel> map=new ConcurrentHashMap<String, SocketChannel>();
    public static void add(String clientId,SocketChannel socketChannel){
        map.put(clientId,socketChannel);
    }
    public static Channel get(String clientId){
        return map.get(clientId);
    }
    public static void remove(SocketChannel socketChannel){
        for (Map.Entry entry:map.entrySet()){
            if (entry.getValue()==socketChannel){
                map.remove(entry.getKey());
            }
        }
    }*/

    private static Map<String, ChannelMapObject> map = new ConcurrentHashMap<>();
    public static void add(String clientId, ChannelMapObject obj){
        map.put(clientId, obj);
    }

    public static Channel getChannel(String clientId) {
        ChannelMapObject channelMapObject = map.get(clientId);
        return channelMapObject.getSocketChannel();

    }

    public static Channel getChannel(SocketChannel socketChannel){
        for (Map.Entry entry:map.entrySet()){
            SocketChannel channel = ((ChannelMapObject) entry.getValue()).getSocketChannel();
            return channel;
        }
        return null;
    }

    public static void remove(SocketChannel socketChannel){
        for(Map.Entry entry:map.entrySet()) {
            SocketChannel channel = ((ChannelMapObject) entry.getValue()).getSocketChannel();
            if (channel == socketChannel) {
                channel.close();
                map.remove(entry.getKey());
            }
        }
    }

    public static ChannelMapObject getMapObject(SocketChannel socketChannel){
        for (Map.Entry entry:map.entrySet()){
            ChannelMapObject object = (ChannelMapObject)entry.getValue();
            SocketChannel channel = object.getSocketChannel();
            if (channel == socketChannel)
                return object;
        }
        return null;
    }

}
