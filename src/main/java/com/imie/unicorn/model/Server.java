package com.imie.unicorn.model;

import com.imie.unicorn.controller.Player;
import com.imie.unicorn.controller.UnicornCore;
import com.imie.unicorn.view.Message;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;


public class Server {

    private Selector selector;
    private Charset charset =  Charset.forName("UTF-8");
    private UnicornCore core;
    private ServerSocketChannel server;

    public void init() throws Exception {
        this.core = UnicornCore.getUnicornCore();

        selector = Selector.open();
        server = ServerSocketChannel.open();
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

    private void handleReceivedMessage(Message message){

        //Message received :
        if(message.getKey().equals("connection")) {
            //1 : initPlayer --> player connected, has to be added to the game. Player object expected
            core.addPlayer((Player) message.getValue());
        }
         if(message.getKey().equals("playerReady")){
             //2 : ready --> player ready to play. Player object expected
             core.getPlayerList().get(((Player) message.getValue())).setIsReady(true);
         }
         if(message.getKey().equals("")){

         }
    }

    private Message messageAllareReady(){
        if(core.checkIfAllReady())
            return new Message("ready", core.getPlayerList());
        return null;
    }



    public static void main(String[] args) throws Exception {
        Server server = new Server();
        server.init();

    }
}
