import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: Andrey
 * Date: 26.12.12
 * Time: 14:45
 * Copyright
 */
public class Main {

    private static GameServer gameServer;

    public static void main(String[] args) {
        try {
            gameServer = new GameServer();
            gameServer.start();
        }
        catch (Exception e) {
            Log.trace(e.getMessage());
        }
    }

}

