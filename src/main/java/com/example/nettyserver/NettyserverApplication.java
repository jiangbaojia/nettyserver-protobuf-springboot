package com.example.nettyserver;

import com.example.nettyserver.Auth.ServerAuth;
import com.example.nettyserver.constant.Constant;
import com.example.nettyserver.netty.ServerInitializerByteBuf;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.apache.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.net.ssl.SSLContext;

@SpringBootApplication
public class NettyserverApplication implements CommandLineRunner {

	final static int PORT = 8888;
	public static Logger logger = Logger.getLogger(NettyserverApplication.class);

	public static void main(String[] args) {
		//全局spring上下文，需要用到时，需要在类的构造函数调用
		ApplicationContext cx = new ClassPathXmlApplicationContext("spring-server.xml");
		Constant.ctx = cx;

		SpringApplication.run(NettyserverApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception{
		System.out.println("fuck spring boot");
		SSLContext sslContext = ServerAuth.getSSLContext();

		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try{

			ServerBootstrap b = new ServerBootstrap();
			b.option(ChannelOption.TCP_NODELAY, true);
			b.option(ChannelOption.SO_KEEPALIVE,true);

			b.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.handler(new LoggingHandler(LogLevel.INFO))
					.childHandler(new ServerInitializerByteBuf(sslContext));

			b.bind(PORT).sync().channel().closeFuture().sync();
			System.out.println("Server start sucess");

		}finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}
