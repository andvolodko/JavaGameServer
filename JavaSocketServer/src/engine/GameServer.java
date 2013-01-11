package engine;

import org.apache.commons.lang3.StringEscapeUtils;
import rooms.BaseRoom;
import rooms.DefenseRoom;
import utils.Log;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: Andrey
 * Date: 26.12.12
 * Time: 16:45
 * Copyright
 */
public class GameServer extends Thread implements ISignalListener {

    static private int SERVER_PORT = 9999;
    private ServerSocket socketServer;
    private boolean listening = false;
    private Vector<GameClient> clients = new Vector<GameClient>();
    private Vector<BaseRoom> rooms = new Vector<BaseRoom>();
    private Signals signals;
    private String networkCmd;

    public GameServer() {
        init();
    }

    private void init() {
        signals = Signals.getInstance();
        signals.add(this);
    }

    public void run() {
        try {
            rooms.add(new DefenseRoom(this));
            socketServer = new ServerSocket(SERVER_PORT);
            listening = true;
            Log.trace("start");
            while(listening) {
                Socket socket = socketServer.accept();
                GameClient client = new GameClient(socket, this);
                client.start();
                clients.add(client);
                //Thread.sleep(1000);
                //socketOut.close();
            }
        }
        catch (Exception e) {
            Log.trace(e.getMessage());
        }
    }

    public void clientRemove(GameClient gameClient) {
        clients.remove(gameClient);
    }

    public void sendMsg(String cmd, String data) {
        String toSend = "{\"cmd\":\""+cmd+"\",\"data\":"+data+"}";
        toSend = StringEscapeUtils.unescapeJava(toSend);
        Log.trace("Send to all: "+toSend);
        for (int i = 0; i < clients.size(); i++) {
            GameClient client = clients.get(i);
            client.send(toSend);
        }
    }

    @Override
    public void signalListener(String msg, Object data) {
        if(msg == SignalsVO.NETWORK_SET_CMD) {
            networkCmd = (String)data;
        } else if(msg == SignalsVO.NETWORK_SEND) {
            sendMsg(networkCmd, (String)data);
        }
    }

    protected void finalize() {

    }

}
