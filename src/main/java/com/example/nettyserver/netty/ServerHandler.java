package com.example.nettyserver.netty;

import com.example.nettyserver.NettyserverApplication;
import com.example.nettyserver.controller.ActionMapUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;

/**
 * Created by Raymond on 2/9/2016.
 */
public class ServerHandler extends SimpleChannelInboundHandler<BaseMsg> {
    private static final int MAX_UN_REC_HEARTBEAT_TIME = 3;

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //channel失效，从Map中移除
        NettyChannelMap.remove((SocketChannel)ctx.channel());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, BaseMsg baseMsg) throws Exception {
         /* 请求分发*/
        ActionMapUtil.invote(baseMsg.getCommand(),channelHandlerContext, baseMsg);

        ReferenceCountUtil.release(baseMsg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx){
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        //cause.printStackTrace();//这行会打印客户端断开连接时的异常
        SocketChannel sc = (SocketChannel)ctx.channel();
        NettyserverApplication.logger.error("Client socket cut down,IP:" + sc.remoteAddress());
        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // IdleStateHandler 所产生的 IdleStateEvent 的处理逻辑.
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            switch (e.state()) {
                case READER_IDLE:
                    handleReaderIdle(ctx);
                    break;
                case WRITER_IDLE:
                    handleWriterIdle(ctx);
                    break;
                case ALL_IDLE:
                    handleAllIdle(ctx);
                    break;
                default:
                    break;
            }
        }
    }


    protected void handleReaderIdle(ChannelHandlerContext ctx) {
        ChannelMapObject object = NettyChannelMap.getMapObject((SocketChannel)ctx.channel());
        if (null != object){
            if (object.getLostHeartBeatCount() >= MAX_UN_REC_HEARTBEAT_TIME){
                NettyChannelMap.remove((SocketChannel)ctx.channel());
                System.out.println("Close channel couse by heart beat, IP:" + ctx.channel().remoteAddress());
            }
            else {
                //NettyChannelMap.remove((SocketChannel)ctx.channel());
                object.plusLostCount();
                System.out.println("Un receiv heartbeat request, count:" + object.getLostHeartBeatCount());
            }
        }

    }

    protected void handleWriterIdle(ChannelHandlerContext ctx) {
        System.err.println("---WRITER_IDLE---");
    }

    protected void handleAllIdle(ChannelHandlerContext ctx) {
        System.err.println("---ALL_IDLE---");
    }
}
