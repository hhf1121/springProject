package com.hhf.netty.test.exampleNetty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class ClientHandle extends ChannelInboundHandlerAdapter {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf b= Unpooled.copiedBuffer("你好，我是客户端。".getBytes(CharsetUtil.UTF_8));
        ctx.writeAndFlush(b);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf b=(ByteBuf)msg;
//        ctx.writeAndFlush(b);
        System.out.println("服务端："+b.toString(CharsetUtil.UTF_8));
        Thread.sleep(3000);
        ByteBuf msgInfo=Unpooled.copiedBuffer("666".getBytes(CharsetUtil.UTF_8));
        ctx.writeAndFlush(msgInfo);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        ctx.close();
    }
}
