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
    private PrintWriter socketOut;

    public GameClient(Socket socket, GameServer gameServer) {
        this.socket = socket;
        this.gameServer = gameServer;
    }

    public void run() {
        try {
        Log.trace("client connection from " + socket.getRemoteSocketAddress());
        socketOut = new PrintWriter(socket.getOutputStream(), true);
        socketOut.write("Hi and bye ))" + "u0000");
        socketOut.flush();
        }
        catch (Exception e) {
            Log.trace(e.getMessage());
        }
    }

    public void send(String data) {
        socketOut.write(data);
        socketOut.flush();
    }

    public void remove() {
        socketOut.close();
    }

}
