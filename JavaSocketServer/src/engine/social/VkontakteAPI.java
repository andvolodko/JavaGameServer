package engine.social;

import com.google.gson.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * User: Andrey
 * Date: 28.01.13
 * Time: 23:49
 * To change this template use File | Settings | File Templates.
 */
public class VkontakteAPI {

    private String token;
    private double uid;
    private Gson gson;
    private String firstName = "";
    private String lastName = "";

    public VkontakteAPI(double uid, String token) {
        this.token = token;
        this.uid = uid;
        gson = new Gson();
    }

    public void getUserInfo() throws IOException {
        JsonObject data = query("users.get", "uids="+uid);
        JsonElement elem = data.get("response");
        JsonArray arr = elem.getAsJsonArray();
        JsonElement elem2 = arr.get(0);
        JsonObject obj = elem2.getAsJsonObject();
        firstName = obj.get("first_name").getAsString();
        lastName = obj.get("last_name").getAsString();
    }

    private JsonObject query(String method, String params) throws IOException {
        URL url = new URL("https://api.vk.com/method/"+method+"?"+params+"&access_token=" + token);
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
        String data = "";

        try {
            /* Now read the retrieved document from the stream. */
            String line;

            while( (line=in.readLine())!= null) {
                data += line;
            }

        } finally {
            in.close();
        }

        JsonParser parser = new JsonParser();
        JsonObject array = parser.parse(data).getAsJsonObject();
        //String message = gson.fromJson(array.get(0), String.class);
        //int number = gson.fromJson(array.get(1), int.class);

        return array;
    }

    public String getFirst() {
        return firstName;
    }

    public String getLast() {
        return lastName;
    }
}
