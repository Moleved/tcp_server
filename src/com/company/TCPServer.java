package com.company;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;


public class TCPServer
{
    public final static int COMM_PORT = 5050;

    private ServerSocket serverSocket;
    private InetSocketAddress inboundAddr;
    private ArrayList<Schedule> payload;

    public TCPServer(ArrayList<Schedule> list) {
        this.payload = list;
    }

    public void startServer() {
        initServerSocket();
        try
        {
            while (true)
            {
                Socket sock = this.serverSocket.accept();

                System.out.println("Connected!");

                OutputStream oStream = sock.getOutputStream();
                ObjectOutputStream ooStream = new ObjectOutputStream(oStream);


                InputStream iSteam = sock.getInputStream();
                while(true) {

                    for (Schedule elem : payload) {
                        if (!elem.isDeleted()) ooStream.writeObject(elem);
                        Thread.sleep(100);
                    }
                    System.out.println("Sent");

                    ObjectInputStream oiStream = new ObjectInputStream(iSteam);
                    Schedule schedule = (Schedule) oiStream.readObject();

                    new MessageHandler(payload, schedule).handle();

                    System.out.println(schedule);
                    Thread.sleep(1000);
                }
            }
        }
        catch (SecurityException se)
        {
            System.err.println("Unable to get host address due to security.");
            System.err.println(se.toString());
            System.exit(1);
        }
        catch (IOException ioe)
        {
            System.err.println("Unable to read data from an open socket.");
            System.err.println(ioe.toString());
            System.exit(1);
        }
        catch (InterruptedException ie) { }
        catch (ClassNotFoundException ex) { }
        finally
        {
            try
            {
                this.serverSocket.close();
            }
            catch (IOException ioe)
            {
                System.err.println("Unable to close an open socket.");
                System.err.println(ioe.toString());
                System.exit(1);
            }
        }
    }

    private void initServerSocket()
    {
        this.inboundAddr = new InetSocketAddress(COMM_PORT);
        try
        {
            this.serverSocket = new java.net.ServerSocket(COMM_PORT);
            assert this.serverSocket.isBound();
            if (this.serverSocket.isBound())
            {
                System.out.println("SERVER inbound data port " +
                        this.serverSocket.getLocalPort() +
                        " is ready and waiting for client to connect...");
            }
        }
        catch (SocketException se)
        {
            System.err.println("Unable to create socket.");
            System.err.println(se.toString());
            System.exit(1);
        }
        catch (IOException ioe)
        {
            System.err.println("Unable to read data from an open socket.");
            System.err.println(ioe.toString());
            System.exit(1);
        }
    }
}
