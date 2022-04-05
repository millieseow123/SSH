package vttp.edu.SSH.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.apache.catalina.connector.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class SearchService {

    // set env variable: GIPHY_API_KEY
    @Value("${giphy.api.key}")
    private String giphyKey;

    private static final String URL = "https://api.giphy.com/v1/gifs/search";

    public List<String> getGiphs(String inputPhrase) throws IOException {
        return getGiphs(inputPhrase, 10, "pg");
    }

    public List<String> getGiphs(String inputPhrase, Integer limit) {
        return getGiphs(inputPhrase, 12);
    }

    public List<String> getGiphs(String inputPhrase, String rating){
        return getGiphs(inputPhrase, "pg");
    }

    public List<String> getGiphs(String inputPhrase, Integer limit, String rating) throws IOException {

        List<String> giphy = new LinkedList<>(); //create a list to add list of URLs and return this list

        String url = UriComponentsBuilder  //build URL as in request URL on webpage
                .fromUriString(URL)
                .queryParam("api_key", giphyKey)
                .queryParam("q", inputPhrase)
                .queryParam("limit", limit)
                .queryParam("offset", 0)
                .queryParam("rating", rating)
                .queryParam("lang", "en")
                .toUriString();

                System.out.println(">>>url: " +url );
        RequestEntity<Void> req = RequestEntity.get(url).build(); //build URL

        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = null;

        try {
            resp = template.exchange(req, String.class);
        } catch (Exception e) {
            System.err.printf("Failed");
            return null;
            // TODO: handle exception
        }
//creating list of string of URLs in json
        JsonObject data = null; //data contins json object
        try (InputStream is = new ByteArrayInputStream(resp.getBody().getBytes())) {
            JsonReader reader = Json.createReader(is); 
            data = reader.readObject();
        }
            JsonArray dataArray = data.getJsonArray("data");
            System.out.println(">>>dataArray: " +dataArray );
        for (int i =0; i<dataArray.size(); i++){
            JsonObject objInJsonArray = dataArray.getJsonObject(i);
            JsonObject  getJsonImage = objInJsonArray.getJsonObject("images");
            JsonObject getFixedWidth = getJsonImage.getJsonObject(("fixed_width"));
            String getURL = getFixedWidth.getString("url");
            giphy.add(getURL);
        }


        System.out.println(giphy.size());
        return giphy;
    }

}
