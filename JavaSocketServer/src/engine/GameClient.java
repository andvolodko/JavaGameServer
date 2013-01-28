package engine;

import com.google.gson.Gson;
import com.google.gson.internal.StringMap;
import engine.netdata.ClientData;
import engine.netdata.ErrorData;
import engine.netdata.LoginData;
import engine.social.VkontakteAPI;
import engine.vo.ErrorsVO;
import engine.vo.NetMsgVO;
import engine.vo.SignalsVO;
import utils.Log;

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
    private Signals signals;
    public Boolean connected = false;
    public double ID = -1;
    public String TOKEN = "";
    private VkontakteAPI vkapi;

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
                clientData = gson.fromJson(line, ClientData.class);
                //LOGIN
                if (clientData.getcmd().compareToIgnoreCase(NetMsgVO.LOGIN) == 0) {
                    if(clientData.getdata() instanceof StringMap) {
                        StringMap loginData = (StringMap)clientData.getdata();
                        ID = (Double)loginData.get("id");
                        TOKEN = (String)loginData.get("token");
                        vkapi = new VkontakteAPI(ID, TOKEN);
                        vkapi.getUserInfo();
                    }
                    //
                    if(ID != -1 && !gameServer.haveClientWithID(ID)) {
                        connected = true;
                        gameServer.clientAdd(this);
                        clientData.setdata(new LoginData(vkapi.getFirst(), vkapi.getLast()));
                    } else {
                        if(ID == -1) clientData.setdata(new ErrorData(ErrorsVO.NO_USER_ID));
                        else clientData.setdata(new ErrorData(ErrorsVO.HAVE_CLIENT_ID));
                    }
                    send(gson.toJson(clientData));
                    if(!connected) {
                        remove();
                        return;
                    } else {
                        signals.dispatch(SignalsVO.CLIENT_CONNECTED, this);
                    }
                }
                //UPDATE_ENTITIES
                else if(clientData.getcmd().compareToIgnoreCase(NetMsgVO.UPDATE_ENTITIES) == 0) {
                    signals.dispatch(SignalsVO.UPDATE_ENTITIES, null);
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

    public void remove() throws IOException {
        socketOut.close();
        socketIn.close();
    }

}
