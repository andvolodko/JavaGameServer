package engine.netdata;

/**
 * Created by IntelliJ IDEA.
 * User: Andrey
 * Date: 07.01.13
 * Time: 1:54
 * To change this template use File | Settings | File Templates.
 */
public class ClientData {
    private String cmd;
    private Object data;

    public String getcmd() { return cmd; }
    public Object getdata() { return data; }

    public void setcmd(String cmd) { this.cmd = cmd; }
    public void setdata(Object data) { this.data = data; }
}
