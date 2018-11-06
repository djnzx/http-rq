package org.alexr.httprequest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcabi.http.Request;
import com.jcabi.http.Response;
import com.jcabi.http.request.JdkRequest;
import com.jcabi.http.response.JacksonResponse;
import com.jcabi.http.response.JsonResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.json.JsonObject;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

public class ApplicationOne {
    private static String ENDPOINT = "http://localhost:8080/";
    private static String ENDPOINT2 = "http://localhost:8080/json";
    private static String ENDPOINT3 = "http://localhost:8080/post2";
    private static String ENDPOINT5 = "http://localhost:8080/post5";

    public static void main_plain_get_classic(String[] args) throws IOException {
        URL url = new URL(ENDPOINT);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuffer sb = new StringBuffer();
        while (in.ready()) {
            sb.append(in.readLine());
        }
        in.close();

        System.out.println(sb.toString());
    }

    public static void main_plain_get_clean(String[] args) throws IOException {
        // read plain response (String)
        String body = new JdkRequest(new URL(ENDPOINT))
                .fetch()
                .body();
        System.out.println(body);
    }

    public static void main_object_get_clean(String[] args) throws IOException {
        // read complex response
        Response resp = new JdkRequest(new URL(ENDPOINT2))
                .fetch();

        // =============================== #1 ===============================
        // convert to jsonObject
        JsonObject jsonObject = resp
                .as(JsonResponse.class)
                .json()
                .readObject();

        // =============================== #1.1 access by Name ==============
        System.out.println(jsonObject.getInt("id"));
        System.out.println(jsonObject.getString("name"));
        System.out.println(jsonObject.getJsonArray("list"));

        // =============================== #1.1 traverse through the map ====
        jsonObject.forEach((key, value) -> System.out.printf("K:%s, V:%s\n", key, value));

        // =============================== #2 ===============================
        // convert to JsonNode and convert to Entity
        JsonNode node = resp
                .as(JacksonResponse.class)
                .json()
                .read();

        ObjectMapper om = new ObjectMapper();
        Entity entity1 = om.treeToValue(node, Entity.class);

        System.out.println(entity1);
    }

    public static void main1(String[] args) throws IOException {
        JsonObject object = new JdkRequest(new URL(ENDPOINT3))
                .method(Request.POST)
                .body()
                    .formParam("p1", 333)
                    .formParam("p2", "Hello Denis")
                    .back()
                .fetch()
                .as(JsonResponse.class)
                .json()
                .readObject();

        object.forEach((key, value) -> System.out.printf("K:%s, V:%s\n", key, value));

    }

    public static void main(String[] args) throws IOException {

        JsonObject object = new JdkRequest(new URL(ENDPOINT5))
                .method(Request.POST)
                .body()
                    .set("{\"val\" : 671}")
                    .back()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON) // what is sent
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON) // what is expected
                .header(HttpHeaders.SET_COOKIE, "315")
                .fetch()
                .as(JsonResponse.class)
                .json()
                .readObject();

        object.forEach((key, value) -> System.out.printf("K:%s, V:%s\n", key, value));

    }
}
