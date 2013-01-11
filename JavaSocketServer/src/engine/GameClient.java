package engine;

import com.google.gson.Gson;
import utils.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: Andrey
 * Date: 26.12.12
 * Time: 16:50
 * Copyright
 */
public class GameClient extends Thread{
    private Socket socket;
    private GameServer gameServer;
    private BufferedReader socketIn;
    private PrintWriter socketOut;
    private Gson gson;
    private ClientData clientData;
    private Signals signals;

    public GameClient(Socket socket, GameServer gameServer) {
        this.socket = socket;
        this.gameServer = gameServer;
        //
        gson = new Gson();
        signals = Signals.getInstance();
    }

    public void run() {
        try {
        Log.trace("Client connection from " + socket.getRemoteSocketAddress());
            socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            socketOut = new PrintWriter(socket.getOutputStream(), true);
            socketOut.flush();
            String line = socketIn.readLine();

            while (line != null) {
                Log.trace("client says '" + line + "'");
                //
                if (line.indexOf(NetMsgVO.POLICY_REQUEST) >= 0) {
                    send(NetMsgVO.POLICY_XML);
                } else {
                    clientData = gson.fromJson(line, ClientData.class);
                    //
                    if (clientData.getcmd().compareToIgnoreCase(NetMsgVO.LOGIN) == 0) {
                        //TODO check login data
                        clientData.setdata(NetMsgVO.RESPONSE_OK);
                        send(gson.toJson(clientData));
                    }
                    //
                    else if(clientData.getcmd().compareToIgnoreCase(NetMsgVO.UPDATE_ENTITIES) == 0) {
                        signals.dispatch(SignalsVO.UPDATE_ENTITIES, null);
                    }
                }
                clientData = null;
                line = this.socketIn.readLine();
            }
            Log.trace("Client disconnected from " + socket.getRemoteSocketAddress());
            gameServer.clientRemove(this);
        }
        catch (Exception e) {
            Log.trace("Game client error: " + e.getMessage());
        }
    }

    public void send(String data) {
        socketOut.write(data + "\u0000");
        socketOut.flush();
        Log.trace("Send to client: " + data);
    }

    public void remove() {
        socketOut.close();
    }

}
