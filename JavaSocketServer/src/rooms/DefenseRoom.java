package rooms;

import com.google.gson.Gson;
import engine.GameClient;
import engine.GameServer;
import engine.vo.NetMsgVO;
import engine.vo.SignalsVO;
import objects.BaseObject;
import objects.Hero;
import objects.Rock;
import utils.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by IntelliJ IDEA.
 * User: Andrey
 * Date: 11.01.13
 * Time: 22:09
 * To change this template use File | Settings | File Templates.
 */
public class DefenseRoom extends BaseRoom {

    private GameServer gameServer;
    private Gson gson;

    public DefenseRoom(GameServer gameServer) {
        super();
        this.gameServer = gameServer;
    }

    @Override
    protected void init() {
        super.init();
        //
        gson = new Gson();
        //Add units
        for (int i = 0; i < 10; i++) {
            Rock rock = new Rock();
            rock.setX((int)(500 * Math.random()));
            rock.setY((int)(300 * Math.random()));
            addObject(rock);
        }
        //Create timer
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                moveUnits();
            }
        }, 3000, 3000);
    }
    
    private void moveUnits() {
        for (int i = 0; i < objects.size(); i++) {
            BaseObject rock = (BaseObject) objects.get(i);
            rock.setX((int)(500 * Math.random()));
            rock.setY((int)(300 * Math.random()));
        }
        Log.trace("moveUnits");
        //
        updateObject();
    }

    public void updateObject() {
        //gameServer.sendMsg(NetMsgVO.UPDATE_ENTITIES, gson.toJson(objects));
        //
        signals.dispatch(SignalsVO.NETWORK_SET_CMD, NetMsgVO.UPDATE_ENTITIES);
        signals.dispatch(SignalsVO.NETWORK_SEND, gson.toJson(objects));
    }

    @Override
    public void signalListener(String msg, Object data) {
        if(msg == SignalsVO.CLIENT_CONNECTED) {
            GameClient gameClient = (GameClient)data;
            Hero hero = new Hero();
            objects.add(hero);
        } else if(msg == SignalsVO.UPDATE_ENTITIES) {
            updateObject();
        }

    }
}
