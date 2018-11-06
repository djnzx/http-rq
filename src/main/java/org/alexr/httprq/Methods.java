package org.alexr.httprq;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.jcabi.http.Request;
import com.jcabi.http.Response;
import com.jcabi.http.request.JdkRequest;
import com.jcabi.http.response.JacksonResponse;
import com.jcabi.http.response.JsonResponse;
import org.alexr.httprq.entity.E1;
import org.alexr.httprq.entity.Entity;

import javax.json.JsonObject;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Methods implements Endpoints {

  public void method1() throws IOException {
    // classic way
    URL url = new URL(ENDPOINT1);
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

  public void method2() throws IOException {
    // read plain response (String)
    String body = new JdkRequest(new URL(ENDPOINT1))
        .fetch()
        .body();
    System.out.println(body);
  }

  public void method3() throws IOException {
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

  public void method4() throws IOException {
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

  public void method5() throws IOException {
    Gson g = new Gson();
    String REQ = g.toJson(new E1(928));
    JsonObject object = new JdkRequest(new URL(POST5))
        .method(Request.POST)
        .body()
        .set(REQ)
        .back()
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON) // what is sent
        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON) // what is expected
        .header(HttpHeaders.SET_COOKIE, "MyCookie=315")
        .fetch()
        .as(JsonResponse.class)
        .json()
        .readObject();

    object.forEach((key, value) -> System.out.printf("K:%s, V:%s\n", key, value));
  }

  // https://www.baeldung.com/jackson-vs-gson
  public void method6_post_w_gson_from_plain() throws IOException {
    // engine
    Gson g = new Gson();
    // object to send
    E1 objectToSend = new E1(928);
    // object to send as JSON
    String contentToSend = g.toJson(objectToSend);
    // request
    String body = new JdkRequest(new URL(POST5))
        .method(Request.POST)
        .body()
        .set(contentToSend)
        .back()
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON) // what is sent
        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON) // what is expected
        .header(HttpHeaders.SET_COOKIE, "MyCookie=315")
        .fetch()
        .as(JsonResponse.class)
        .body();
    // toPojo entity
    Entity parsed = g.fromJson(body, Entity.class);
    // print it
    System.out.println(parsed);
  }

  public void method7_post_w_jackson_from_plain() throws IOException {
    // engine
    ObjectMapper m = new ObjectMapper();
    // object to send
    E1 objectToSend = new E1(928);
    // object to send as JSON
    String REQ = m.writeValueAsString(objectToSend);
    // request
    String body = new JdkRequest(new URL(POST5))
        .method(Request.POST)
        .body()
        .set(REQ)
        .back()
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON) // what is sent
        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON) // what is expected
        .header(HttpHeaders.SET_COOKIE, "MyCookie=315")
        .fetch()
        .as(JsonResponse.class)
        .body();
    // toPojo entity
    Entity parsed = m.readValue(body, Entity.class);
    // JsonParser, File, URL, String
    // print it
    System.out.println(parsed);
  }

  public void method8_post_w_jackson_smart() throws IOException {
    // engine
    ObjectMapper m = new ObjectMapper();
    // object to send
    E1 objectToSend = new E1(928);
    // object to send as JSON
    String REQ = m.writeValueAsString(objectToSend);
    // request
    JsonNode node =
    new JdkRequest(new URL(POST5))
        .method(Request.POST)
        .body()
        .set(REQ)
        .back()
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON) // what is sent
        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON) // what is expected
        .header(HttpHeaders.SET_COOKIE, "MyCookie=315")
        .fetch()
        .as(JacksonResponse.class)
        .json()
        .read();

    // toPojo node entity
    Entity parsed = m.treeToValue(node, Entity.class);
    // print it
    System.out.println(parsed);
  }

  public void method9_post_w_jackson_super_smart() throws IOException {
    // engine
    ObjectMapper m = new ObjectMapper();
    // object to send
    E1 objectToSend = new E1(928);
    // object to send as JSON
    String REQ = m.writeValueAsString(objectToSend);
    // request
    Entity parsed = new JdkRequest(new URL(POST5))
        .method(Request.POST)
        .body()
        .set(REQ)
        .back()
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON) // what is sent
        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON) // what is expected
        .header(HttpHeaders.SET_COOKIE, "MyCookie=315")
        .fetch()
        .as(JacksonWithPojoResponse.class)
        .toPojo(Entity.class);
    // print it
    System.out.println(parsed);
  }
}
