package engine;

/**
 * Created by IntelliJ IDEA.
 * User: Andrey
 * Date: 07.01.13
 * Time: 2:00
 * To change this template use File | Settings | File Templates.
 */
public class NetMsgVO {
    public static final String LOGIN = "login";
    public static final String UPDATE_ENTITIES = "ue";

    public static final String RESPONSE_OK = "ok";

    //Policy
    public static final String POLICY_REQUEST = "<policy-file-request/>";
    public static final String POLICY_XML = "<?xml version=\"1.0\"?>"
            + "<cross-domain-policy>"
            + "<allow-access-from domain=\"*\" to-ports=\"*\" />"
            + "</cross-domain-policy>";

}
