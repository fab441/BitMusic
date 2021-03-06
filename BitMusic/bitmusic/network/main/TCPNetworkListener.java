/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bitmusic.network.main;

import bitmusic.network.exception.NetworkException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author Pak
 */
public final class TCPNetworkListener extends AbstractNetworkListener {
    /**
     * TCP Socket Server.
     */
    private ServerSocket tcpServer;

    /**
    * Singleton thread implementation.
    */
    private static TCPNetworkListener NETLISTENER = null;

    /**
     * Thread in which the listener is running.
     */
    private final Thread thread = new Thread(this);

    /**
     * Default constructor.
     * @param portToListen The port number
     */
    private TCPNetworkListener(final int portToListen) throws NetworkException{
        super(portToListen);
        try {
            tcpServer = new ServerSocket(PORT_LISTENED);
            tcpServer.bind(LOCALPORT);
        } catch (Exception e) {
            throw new NetworkException(
                    "TCP server socket binding with LOCALPORT failed");

        }
        thread.start();
    }

    /**
     * @return unique instance of TCPNetworkListener.
     */
    public static TCPNetworkListener getInstance() {
        NETLISTENER = null;
        try {
            NETLISTENER = new TCPNetworkListener(4444);
        } catch (NetworkException e) {
            e.printStackTrace();
        }
        return NETLISTENER;
    }

    /**
     * TCP/UDP network listening behavior.
     */
    @Override
    public void run() {
        //Loop forever, processing connections
        while(true) {
            try {
                //######################################################
                //TCP CONNECTION ACCEPTED
                //######################################################
                    final Socket connectionSocket = tcpServer.accept();
                    Controller.getInstance().getThreadManager().
                            assignTaskToWorker(connectionSocket);
            } catch (Exception e) {
                //Impossible! it implements run and not run throws ...
                //throw new NetworkException("TCP "
                //        + "or UDP server registration failed");
                e.printStackTrace();
            }
        }
    }
}
