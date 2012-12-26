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

    static private int SERVER_PORT = 9999;
    static private ServerSocket socketServer;
    static private boolean listening = false;

    public static void main(String[] args) {
        try {
            socketServer = new ServerSocket(SERVER_PORT);
            listening = true;
            trace("start");
            while(listening) {
                Socket socket = socketServer.accept();
                trace("client connection from " + socket.getRemoteSocketAddress());
                PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true);
                socketOut.write("Hi and bye ))" + "u0000");
                socketOut.flush();
                //Thread.sleep(1000);
                //socketOut.close();
            }
        }
        catch (Exception e) {
            trace(e.getMessage());
        }
    }

    public static void trace(String msg) {
        System.out.print("Trace: " + msg +"\n");
    }

}

