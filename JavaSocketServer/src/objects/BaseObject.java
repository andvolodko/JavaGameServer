package objects;

import utils.Misc;

/**
 * Created by IntelliJ IDEA.
 * User: Andrey
 * Date: 11.01.13
 * Time: 22:12
 * To change this template use File | Settings | File Templates.
 */
public class BaseObject {
    
    protected int uid = Misc.getUID();
    protected String name = "noname";
    protected double x = 0;
    protected double y = 0;

    public BaseObject() {
        super();
        init();
    }

    protected void init() {
        
    }
    
    public int getUID() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setY(double yLoc) {
        y = yLoc;
    }

    public void setX(double xLoc) {
        x = xLoc;
    }

    public class BaseObjectShort {

        protected double x;
        protected double y;
        protected String name;

        public BaseObjectShort() {

        }
    }

}

