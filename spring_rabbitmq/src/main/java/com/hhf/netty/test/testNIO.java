package com.hhf.netty.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class testNIO {

    static List<SocketChannel> lists=new ArrayList<>();

    public static void main(String[] args) throws IOException {
        //打开一个server端的channel
        ServerSocketChannel serverSocketChannel=ServerSocketChannel.open();
        //设置成非阻塞
        serverSocketChannel.configureBlocking(false);
        //绑定端口
        serverSocketChannel.bind(new InetSocketAddress(9999));
        //循环从ServerSocketChannel中去accept()
        while (true){
            //ServerSocketChannel.accept()到的是SocketChannel
            SocketChannel accept = serverSocketChannel.accept();
            //把SocketChannel添加到list中，保存下来
            if(accept!=null) lists.add(accept);

            Iterator<SocketChannel> iterator = lists.iterator();
            //遍历List<SocketChannel>
            while (iterator.hasNext()){
                //如果List<SocketChannel>中有
                SocketChannel next = iterator.next();
                //把SocketChannel read到一个1024长度的ByteBuffer中。
                ByteBuffer b=ByteBuffer.allocate(1024);
                int read = next.read(b);
                if(read!=-1){
                    byte [] b1 = b.array();
                    System.out.println(new String(b1,"utf-8"));
                }else {
                    //如果read不到，证明读完了，要从lists中移除、避免多次“消费”
                    iterator.remove();
                }
            }
        }
    }

}
