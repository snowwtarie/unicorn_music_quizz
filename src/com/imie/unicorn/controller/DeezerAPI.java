package com.imie.unicorn.controller;

import com.google.gson.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

class PlaylistDeserializer implements JsonDeserializer<ArrayList<Track>>
{
    @Override
    public ArrayList<Track> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        ArrayList<Track> tracks = new ArrayList<Track>();

        JsonObject jObjectPlaylist = json.getAsJsonObject();
        JsonArray jArrayData = jObjectPlaylist.get("data").getAsJsonArray();

        for (JsonElement jsonElementTrack : jArrayData) {
            JsonObject jObjectTrack = jsonElementTrack.getAsJsonObject();
            JsonObject jArrayArtist = jObjectTrack.get("artist").getAsJsonObject();
            tracks.add(new Track(jObjectTrack.get("id").getAsInt(), jObjectTrack.get("preview").getAsString(), jObjectTrack.get("title").getAsString(), jArrayArtist.get("name").getAsString()));
        }

        return tracks;
    }
}

public class DeezerAPI {
    private long idPlaylist;

    public DeezerAPI(int p_idPlaylist){
        //récupération de l'id de la playlist
        this.idPlaylist = p_idPlaylist;
    }

    public void getListTrack() throws IOException {
        String jsonInString = callApi("playlist/"+this.idPlaylist+"/tracks");

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        gsonBuilder.registerTypeAdapter(ArrayList.class, new PlaylistDeserializer());
        Gson gson = gsonBuilder.create();
        ArrayList<Track> tracks = gson.fromJson(jsonInString, ArrayList.class);

        for (Track track : tracks)
            System.out.println(track);

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
