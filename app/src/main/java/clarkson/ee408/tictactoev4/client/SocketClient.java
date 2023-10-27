package clarkson.ee408.tictactoev4.client;

import java.io.*;
import java.net.*;
import com.google.gson.*;
public class SocketClient {

    // TODO change this to match the server ip and port:
    // Should this be moved somewhere else?
    private String server_ip = "1.1.1.1";
    private int server_port = 8080;

    // Static Variable for singleton design
    private static SocketClient instance;

    // Class Attributes
    private Socket socket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private Gson gson;

    // Class Methods
    private SocketClient() {
        this.gson = new Gson();
    }
    public static synchronized SocketClient getInstance() {
        if (instance == null) {
            instance = new SocketClient();
        }
        return instance;
    }
    public void close() throws IOException {
        if (inputStream != null) inputStream.close();
        if (outputStream != null) outputStream.close();
        if (socket != null) socket.close();
    }
    public <T> T sendRequest(Object request, Class<T> responseClass) throws IOException {
        String serializedRequest = gson.toJson(request);

        if (socket == null || socket.isClosed()) {
            // Provide the server IP and Port here
            socket = new Socket(server_ip, server_port);
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());
        }

        outputStream.writeUTF(serializedRequest);

        String responseStr = inputStream.readUTF();
        return gson.fromJson(responseStr, responseClass);
    }
}
