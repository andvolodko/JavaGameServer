package objects;

/**
 * Created by IntelliJ IDEA.
 * User: Andrey
 * Date: 11.01.13
 * Time: 22:15
 * To change this template use File | Settings | File Templates.
 */
public class Rock extends BaseObject {
    public Rock() {
        super();
    }

    @Override
    protected void init() {
        super.init();
        //
        name = name +" "+ uid;
    }
}
