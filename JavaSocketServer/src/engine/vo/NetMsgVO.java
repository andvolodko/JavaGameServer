package engine.vo;

import org.apache.commons.lang3.StringEscapeUtils;

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
    public static final String ADD_ENTITY = "ae";
    public static final String MOVE_ENTITY = "me";
    public static final String ATTACK_ENTITY = "ate";
    public static final String REMOVE_ENTITY = "re";

    public static final String RESPONSE_OK = "ok";

    //Policy
    public static final String POLICY_REQUEST = "<policy-file-request/>";
    public static final String POLICY_XML = StringEscapeUtils.unescapeJava("<?xml version=\"1.0\"?>"
            + "<cross-domain-policy>"
            + "<allow-access-from domain=\"*\" to-ports=\"*\" />"
            + "</cross-domain-policy>");



}
