package com.imie.unicorn.controller;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import java.io.IOException;

public class DeezerAPI {
    private long idPlaylist;

    public DeezerAPI(int p_idPlaylist){
        //récupération de l'id de la playlist
        this.idPlaylist = p_idPlaylist;
    }

    public void getListTrack() throws IOException {
        System.out.println(callApi("playlist/908622995/tracks"));

    }

    private String callApi(String URI) throws IOException {
        // Ouverture connexion
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet getRequest = new HttpGet("http://api.deezer.com/" + URI);
        getRequest.addHeader("accept", "application/json");
        HttpResponse httpResponse = httpClient.execute(getRequest);

        // Vérification du status de la réponse
        if (httpResponse.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + httpResponse.getStatusLine().getStatusCode());
        }

        // Récupération du JSON
        HttpEntity entity = httpResponse.getEntity();
        String json = EntityUtils.toString(entity, "UTF-8");


        // Fermeture connexion
        httpClient.getConnectionManager().shutdown();

        return json;
    }
}
