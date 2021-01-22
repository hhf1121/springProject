package com.hhf.netty.test.exampleNetty;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class MySever {

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup boss=new NioEventLoopGroup(1);
        NioEventLoopGroup worker=new NioEventLoopGroup(10);
        try {
            ServerBootstrap serverBootstrap=new ServerBootstrap();
            serverBootstrap.group(boss,worker)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,1024)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new ServerHandler());
                        }
                    });
            //同步sync
            ChannelFuture future = serverBootstrap.bind(6666).sync();
//            future.addListener(new ChannelFutureListener() {
//                @Override
//                public void operationComplete(ChannelFuture channelFuture) throws Exception {
//                    //连接成功的回调
//                    if (channelFuture.isSuccess()) {
//                        System.out.println("连接成功...");
//                    }
//                }
//            });
            //同步sync
            future.channel().closeFuture().sync();
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

}
