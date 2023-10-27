package clarkson.ee408.tictactoev4.socket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import clarkson.ee408.tictactoev4.model.Event;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.logging.Level;

/**
 * Class that handles communication with the SQLite db
 */
public class ServerHandler extends Thread {
    private static Event s_Event = new Event(0, null, null, null, null, -1);
    private Socket m_Socket;
    private String m_Username;
    private DataInputStream m_DataIn;
    private DataOutputStream m_DataOut;
    private Gson m_Gson;

    /**
     * @param socket   socket connection to the client
     * @param username username of connected client
     */
    public ServerHandler(Socket socket, String username) {
        m_Socket = socket;
        m_Username = username;
        m_Gson = new GsonBuilder().serializeNulls().create();

        s_Event = new Event();

        try {
            m_DataIn = new DataInputStream(socket.getInputStream());
            m_DataOut = new DataOutputStream(socket.getOutputStream());
        } catch (java.io.IOException e) {
            SocketServer.s_Logger.log(Level.SEVERE, "Failed to open data stream");
        }
    }

    /**
     * Run creates a handle to the SQLite db
     */
    @Override
    public void run() {
    }

    /**
     * close() terminates handle to the SQLite db
     */
    public void close() {
    }
}