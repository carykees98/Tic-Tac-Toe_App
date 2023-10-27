package clarkson.ee408.tictactoev4.socket;

import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Wrapper Class for a socket
 */
public class SocketServer {
    public static final Logger s_Logger = Logger.getLogger(SocketServer.class.getName());
    private final int m_Port;
    private ServerSocket m_Socket;

    /**
     * Default constructor for the SocketServer class
     */
    public SocketServer() {
        m_Port = 5000;
    }

    /**
     * @param port Port for the socket to bind to (between 0 and 65535)
     */
    public SocketServer(int port) {
        m_Port = port;
    }

    /**
     * Entry point for the server
     *
     * @param args Command Line Arguments
     */
    public static void main(String[] args) {
        SocketServer server = new SocketServer();
        server.setup();
        server.startAcceptingRequest();
    }

    /**
     * Configures the SocketServer
     */
    public void setup() {
        try {
            m_Socket = new ServerSocket(m_Port);
        } catch (java.io.IOException e) {
            s_Logger.log(Level.SEVERE, "ServerSocket IO exception: " + e.getMessage() + "\nShutting Down");
            System.exit(-1);
        } catch (IllegalArgumentException e) {
            s_Logger.log(Level.SEVERE, "Invalid port: " + m_Port + ". Shutting down");
        } catch (Exception e) {
            s_Logger.log(Level.SEVERE, "ServerSocket threw exception" + e.getMessage());
        }

        s_Logger.log(Level.INFO, "Server Address:" + m_Socket.getInetAddress());
        s_Logger.log(Level.INFO, "Server Port:" + m_Socket.getLocalPort());
    }

    /**
     * Tells the socket to start accepting requests from clients
     */
    public void startAcceptingRequest() {
        try {
            ServerHandler connection1 = new ServerHandler(m_Socket.accept(), "Connection 1");
            ServerHandler connection2 = new ServerHandler(m_Socket.accept(), "Connection 2");
        } catch (java.io.IOException e) {
            s_Logger.log(Level.SEVERE, "Failed to accept connection");
        } catch (Exception e) {
            s_Logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * Returns the port that the socket is connected to
     *
     * @return Returns the value of m_Port
     */
    public int getPort() {
        return m_Port;
    }
}