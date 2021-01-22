package com.hhf.netty.test.exampleNetty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class ClientHandle extends ChannelInboundHandlerAdapter {


    /**
     * 当客户端连接服务器完成就会触发该方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf b= Unpooled.copiedBuffer("你好，我是客户端。".getBytes(CharsetUtil.UTF_8));
        ctx.writeAndFlush(b);
    }

    /**
     * 当通道有读取事件时会触发，即服务端发送数据给客户端
     * @param ctx
     * @param msg
     * @throws Exception
     */
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
