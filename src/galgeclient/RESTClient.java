/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package galgeclient;

import java.util.ArrayList;
import java.util.Arrays;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class RESTClient {
    
    private static WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/RESTServerGalgeleg/resources";
    
    public RESTClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("galgeleg");
    }
    
    public ArrayList<Object> guess(String ch, String ordet, String synligtOrd, int antalBrugteBogstaver,
            boolean sidsteBogstavVarForkert, int score) {
        WebTarget resource = webTarget;
        resource = resource.path("/guess/" + ordet + "/" + synligtOrd + "/"
                + antalBrugteBogstaver + "/" + sidsteBogstavVarForkert + "/"
                + score + "/" + ch);
        String str = resource.request().get(String.class);
        ArrayList<Object> s0 = new ArrayList<>(Arrays.asList(str.split(";")));
        return s0;
    }
    
    public ArrayList<Object> init() {
        WebTarget resource = webTarget;
        resource = resource.path("/reset");
        String str = resource.request().get(String.class);
        ArrayList<Object> s0 = new ArrayList<>(Arrays.asList(str.split(";")));
        return s0;
    }

    public Boolean login(String usr, String psw) {
        WebTarget resource = webTarget;
        resource = resource.path("/login/" + usr + "/" + psw);
        return resource.request().get(Boolean.class);
    }

    public void setHighscore(String username, int score) {
        WebTarget resource = webTarget;
        resource = resource.path("/setHighscore/" + username + "/" + score);
        resource.request().put(Entity.entity(username, MediaType.TEXT_PLAIN));
    }
    
    public ArrayList<String> getHighscore() {
        WebTarget resource = webTarget;
        resource = resource.path("/getHighscore");
        String str = resource.request().get(String.class);
        ArrayList<String> s0 = new ArrayList<>(Arrays.asList(str.split(";")));
        return s0;
    }
}
