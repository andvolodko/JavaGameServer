package utils;

/**
 * Created by IntelliJ IDEA.
 * User: Andrey
 * Date: 11.01.13
 * Time: 22:03
 * To change this template use File | Settings | File Templates.
 */
public class Misc {
    
    static private int uid = 0;
    
    static public int getUID() {
        return uid++;
    }
    
}
