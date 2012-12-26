import java.io.PrintWriter;
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
public class GameServer extends Thread {

    static private int SERVER_PORT = 9999;
    private ServerSocket socketServer;
    private boolean listening = false;
    private Vector<GameClient> clients = new Vector<GameClient>();

    public GameServer() {

    }

    public void run() {
        try {
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

    protected void finalize() {

    }

}
