import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
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

    public GameClient(Socket socket, GameServer gameServer) {
        this.socket = socket;
        this.gameServer = gameServer;
        //
        gson = new Gson();
    }

    public void run() {
        try {
        Log.trace("client connection from " + socket.getRemoteSocketAddress());
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
                        clientData.setdata(NetMsgVO.RESPONSE_OK);
                        send(gson.toJson(clientData));
                    }
                }
                clientData = null;
                line = this.socketIn.readLine();
            }

        }
        catch (Exception e) {
            Log.trace(e.getMessage());
        }
    }

    public void send(String data) {
        socketOut.write(data + "\u0000");
        socketOut.flush();
        Log.trace("Send to client: "+data);
    }

    public void remove() {
        socketOut.close();
    }

}
