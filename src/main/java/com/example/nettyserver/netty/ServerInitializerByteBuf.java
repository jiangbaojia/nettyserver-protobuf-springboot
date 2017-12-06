package com.example.nettyserver.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.timeout.IdleStateHandler;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import java.util.concurrent.TimeUnit;

/**
 * Created by Eric on 2017/11/15.
 */
public class ServerInitializerByteBuf extends ChannelInitializer<SocketChannel> {

    private final SSLContext sslContext;

    public ServerInitializerByteBuf(final  SSLContext sslContext){
        this.sslContext = sslContext;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        final SSLEngine sslEngine = sslContext.createSSLEngine();
        sslEngine.setUseClientMode(false);

        ServerConnectionWatchdog dog = new ServerConnectionWatchdog();

        ChannelPipeline p = socketChannel.pipeline();

        p.addLast("sslHandler", new SslHandler(sslEngine));
        //p.addLast(idleStateTrigger);

        p.addLast("decoder", new HeaderDecoder());
        p.addLast("encoder", new HeaderEncoder());
        p.addLast(new IdleStateHandler(20, 0, 0, TimeUnit.SECONDS));
        p.addLast(new ServerHandler());
        p.addLast(dog);
    }
}
