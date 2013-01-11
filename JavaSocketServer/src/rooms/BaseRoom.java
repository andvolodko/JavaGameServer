package rooms;

import engine.ISignalListener;
import engine.Signals;
import objects.BaseObject;

import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Andrey
 * Date: 11.01.13
 * Time: 22:06
 * To change this template use File | Settings | File Templates.
 */
public class BaseRoom implements ISignalListener {

    protected Vector<BaseObject> objects = new Vector<BaseObject>();
    protected Signals signals;

    public BaseRoom() {
        super();
        init();
    }

    protected void init() {
        signals = Signals.getInstance();
        signals.add(this);
    }

    public void addObject(BaseObject obj) {
        objects.add(obj);
    }

    public void removeObject(BaseObject obj) {
        objects.remove(obj);
    }

    @Override
    public void signalListener(String msg, Object data) {

    }
}
