package com.hhf.netty.test.exampleNetty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class MyClient {

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup nioEventLoopGroup=new NioEventLoopGroup(10);
        Bootstrap bootstrap=new Bootstrap();
        try{
            bootstrap.group(nioEventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new ClientHandle());
                        }
                    });
            ChannelFuture sync = bootstrap.connect("127.0.0.1", 6666).sync();
            sync.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (channelFuture.isSuccess()) {
                        System.out.println("连接成功...");
                    }
                }
            });
            sync.channel().closeFuture().sync();
        }finally {
            nioEventLoopGroup.shutdownGracefully();
        }


    }

}
