package com.hhf.netty.test.exampleNetty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class ServerHandler extends ChannelInboundHandlerAdapter {


    /**
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        Channel read = channel.read();

    }

    /**
     * 读取客户端发送的数据
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("客户端:"+buf.toString(CharsetUtil.UTF_8));
    }

    /**
     * 读取完成之后，触发。
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //给客户端写一个消息。ByteBuf类型
        ByteBuf buf = Unpooled.copiedBuffer("我收到了".getBytes(CharsetUtil.UTF_8));
        ctx.writeAndFlush(buf);
    }


    /**
     * 处理异常, 一般是需要关闭通道
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        ctx.close();
    }
}
