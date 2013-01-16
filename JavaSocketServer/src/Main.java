import engine.GameServer;
import policy.PolicyServer;
import utils.Log;

/**
 * Created with IntelliJ IDEA.
 * User: Andrey
 * Date: 26.12.12
 * Time: 14:45
 * Copyright
 */
public class Main {

    private static GameServer gameServer;
    private static PolicyServer policyServer;

    public static void main(String[] args) {
        try {
            policyServer = new PolicyServer(GameServer.SERVER_PORT + 1);
            policyServer.start();
            gameServer = new GameServer();
            gameServer.start();
        }
        catch (Exception e) {
            Log.trace(e.getMessage());
        }
    }

}

