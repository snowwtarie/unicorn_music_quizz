package com.imie.unicorn.model;

import java.io.IOException;
import java.net.*;

/**
 * Created by Yornletard on 13/12/2016.
 */
public class Discovery implements Runnable {

    DatagramSocket socket;


    @Override
    public void run() {
        try{
            socket = new DatagramSocket(8888, InetAddress.getByName("0.0.0.0"));
            socket.setBroadcast(true);

            while(true){
                System.out.println("Unicorn Discovery started...Server Waiting for client...");
                byte[] recvbuf = new byte[15000];
                DatagramPacket packetClient = new DatagramPacket(recvbuf, recvbuf.length);
                socket.receive(packetClient);
                System.out.println("Client request :" + packetClient.getData() + "");
                System.out.println("Client : " + packetClient.getAddress() + " a contacté le  connection au serveur.");
                if(packetClient.getData().equals("connectionRequest")){
                    //sending server ip to client
                    byte[] response = socket.getInetAddress().toString().getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(response, response.length, packetClient.getAddress(), packetClient.getPort());
                    socket.send(sendPacket);
                    System.out.println("Unicorn Discovery sended information connection to client : " + packetClient.getAddress());
                }
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
