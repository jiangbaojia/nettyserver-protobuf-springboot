package com.example.nettyserver.netty;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by Eric on 2017/11/20.
 */
@ChannelHandler.Sharable
public  class ServerConnectionWatchdog extends ChannelInboundHandlerAdapter{
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //channel失效处理,客户端下线或者强制退出等任何情况都触发这个方法
        System.out.println("捕获异常");
        super.channelInactive(ctx);
    }

}
