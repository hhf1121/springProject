package com.hhf.netty.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class testNIO_Selector {


    public static void main(String[] args) throws IOException {
        //打开一个ServerSocketChannel
        ServerSocketChannel open = ServerSocketChannel.open();
        //设置为非阻塞
        open.configureBlocking(false);
        //绑定端口
        open.bind(new InetSocketAddress(9999));
        //获取一个Selector多路复用器
        Selector selector = Selector.open();
        //把这个ServerSocketChannel注册到Selector上，并关注它的OP_ACCEPT事件。
        open.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务启动成功");
        //死循环去Selector上获取
        while (true){
            selector.select();
            //Selector上一旦有事件变化，就会触发
            System.out.println("触发事件...");
            //拿到这个ServerSocketChannel在Selector上对应的key。
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();
            while (iterator.hasNext()) {
                //如果这个key上有变化。
                SelectionKey next = iterator.next();
                //判断这个key的类型。
                if (next.isAcceptable()) {
                    //如果是OP_ACCEPT（连接事件），在channel中获取到对应的ServerSocketChannel。
                    ServerSocketChannel channel = (ServerSocketChannel)next.channel();
                    //ServerSocketChannel会accept到某个SocketChannel
                    SocketChannel accept = channel.accept();
                    //把这个SocketChannel也设置为非阻塞，也注册到Selector上，并关注它的OP_READ事件。
                    accept.configureBlocking(false);
                    accept.register(selector,SelectionKey.OP_READ);
                    System.out.println("客户端连接成功");
                }else if(next.isReadable()){
                    //如果是OP_READ事件，这个事件肯定是上一步中注册的SocketChannel，所以在channel中获取到对应的ocketChannel。
                    SocketChannel sc=(SocketChannel)next.channel();
                    //初始化一个ByteBuffer，把SocketChannel中的流读进去。
                    ByteBuffer b=ByteBuffer.allocate(1024);
                    int read = sc.read(b);
                    if(read!=-1){
                        byte[] array = b.array();
                        System.out.println(new String(array,"UTF-8"));
                    }else {
                        //如果无可读数据，关闭
                        sc.close();
                    }
                }
                //每一次循环完毕之后，移除对应的channel
                iterator.remove();
            }
        }


    }

}
