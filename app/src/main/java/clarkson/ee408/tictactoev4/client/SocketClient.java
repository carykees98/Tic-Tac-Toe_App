package clarkson.ee408.tictactoev4.client;

import android.util.Log;

import java.io.*;
import java.net.*;

import com.google.gson.*;

public class SocketClient {

    // TODO change this to match the server ip and port:
    // Should this be moved somewhere else?
    private String server_ip = "0.0.0.0";
    private int server_port = 5000;

    // Static Variable for singleton design
    private static SocketClient s_Instance;

    // Class Attributes
    private Socket socket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private final Gson gson = new Gson();

    // Class Methods
    private SocketClient() {
        try {
            socket = new Socket(server_ip, server_port);

            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            Log.e("SocketClient", e.getMessage());
        }
    }

    public static synchronized SocketClient getInstance() {
        if (s_Instance == null) {
            s_Instance = new SocketClient();
        }

        return s_Instance;
    }

    public void close() throws IOException {
        if (inputStream != null) inputStream.close();
        if (outputStream != null) outputStream.close();
        if (socket != null) socket.close();
    }

    public synchronized <T> T sendRequest(Object request, Class<T> responseClass) throws IOException {
        // Send Request
        String serializedRequest = gson.toJson(request);
        outputStream.writeUTF(serializedRequest);

        // Receive response
        String responseStr = inputStream.readUTF();
        return gson.fromJson(responseStr, responseClass);
    }
}
