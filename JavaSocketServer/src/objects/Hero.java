package objects;

import engine.vo.TypesVO;

/**
 * Created by IntelliJ IDEA.
 * User: Andrey
 * Date: 21.01.13
 * Time: 2:25
 * To change this template use File | Settings | File Templates.
 */
public class Hero extends BaseObject {

    public Hero(double ID, String nameLoc) {
        super();
        type = TypesVO.HERO;
        uid = (int)ID;
        name = nameLoc;
    }

    @Override
    protected void init() {
        super.init();
        //
        //name = name +" "+ uid;
    }


}
