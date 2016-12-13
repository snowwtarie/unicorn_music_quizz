package com.imie.unicorn.model;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;


public class Server {

    private Selector selector;
    private Charset charset =  Charset.forName("UTF-8");

    public void init() throws Exception {
        selector = Selector.open();
        ServerSocketChannel server = ServerSocketChannel.open();
        InetSocketAddress isa = new InetSocketAddress("127.0.0.1", 3000);

        server.socket().bind(isa);
        server.configureBlocking(false);
        server.register(selector, SelectionKey.OP_ACCEPT);

        while (selector.select() > 0) {
            for (SelectionKey key : selector.selectedKeys()) {
                selector.selectedKeys().remove(key);

                if (key.isAcceptable()) {
                    SocketChannel socketChannel = server.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    key.interestOps(SelectionKey.OP_ACCEPT);
                }

                if (key.isReadable()) {
                    SocketChannel sc = (SocketChannel) key.channel();
                    ByteBuffer buff = ByteBuffer.allocate(1024);
                    String content = "";

                    try {
                        while (sc.read(buff) > 0) {
                            buff.flip();
                            content += charset.decode(buff);
                            buff.clear();
                        }

                        System.out.println("Client << " + content);
                        key.interestOps(SelectionKey.OP_READ);
                    } catch (IOException e) {
                        key.cancel();

                        if (key.channel() == null) {
                            key.channel().close();
                        }
                    }

                    if (content.length() > 0) {
                        for (SelectionKey sk : selector.keys()) {
                            Channel channel = sk.channel();

                            if (channel instanceof SocketChannel) {
                                SocketChannel to = (SocketChannel) channel;
                                to.write(charset.encode(content));
                            }
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        new Server().init();
    }
}
