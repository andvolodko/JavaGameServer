package engine;



import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Andrey
 * Date: 11.01.13
 * Time: 23:48
 * To change this template use File | Settings | File Templates.
 */
public class Signals {

    static private Signals instance;

    private Vector<ISignalListener> listeners = new Vector<ISignalListener>();

    public Signals() {
        instance = this;
    }

    public void add(ISignalListener listener) {
        listeners.add(listener);
    }

    public void remove(ISignalListener listener) {
        listeners.remove(listener);
    }

    public void dispatch(String msg, Object data) {
        for (int i = 0; i < listeners.size(); i++) {
            ISignalListener listener = listeners.get(i);
            listener.signalListener(msg, data);
        }
    }

    static public Signals getInstance() {
        if(instance != null) return instance;
        else {
            new Signals();
            return instance;
        }
    }

}
