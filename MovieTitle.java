import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;
import java.net.*;
import com.google.gson.*;

class Solution {

    // API url.
    final static String apiUrl = "https://jsonmock.hackerrank.com/api/movies/search/?Title=";

    /*
     * Complete the function below.
     * Base url: https://jsonmock.hackerrank.com/api/movies/search/?Title=
     */

    static String[] getMovieTitles(String substr) {

        // List of movies.
        List<String> movies = new ArrayList<>();

        try {
            String response = getResponse(apiUrl + substr);

            // Parse JSON response.
            JsonParser parser = new JsonParser();
            JsonElement rootNode = parser.parse(response);

            // Get and store the title.
            getTitle(movies, rootNode);

            JsonObject info = rootNode.getAsJsonObject();

            // Get total and page count.
            JsonElement totalMovies = info.get("total");
            JsonElement totalPages = info.get("total_pages");

            int currentPage = 1;

            // Get next pages.
            while (currentPage < Integer.parseInt(totalPages.toString())) {
                getNextPage(movies, ++currentPage, substr);
            }

            // Sort the movies fetched.
            Collections.sort(movies);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return movies.stream().toArray(String[] ::new);
    }

    static void getNextPage(List<String> movies, int currentPage, String substr) throws Exception {

        String response = getResponse(apiUrl + substr + "&page=" + currentPage);

        // Parse.
        JsonParser parser = new JsonParser();
        JsonElement rootNode = parser.parse(response);

        // Get title.
        getTitle(movies, rootNode);
    }

    static void getTitle(List<String> movies, JsonElement rootNode) {

        JsonObject info = rootNode.getAsJsonObject();

        // Get data.
        JsonElement data = info.get("data");
        JsonArray jsonArray = data.getAsJsonArray();

        for (JsonElement element : jsonArray) {
            JsonObject titleObject = element.getAsJsonObject();

            // Get the title.
            String title = titleObject.get("Title").getAsString();
            movies.add(title);
        }
    }

    static String getResponse(String srcUrl) throws Exception {

        StringBuilder result = new StringBuilder();
        URL url = new URL(srcUrl);

        // Url connection.
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        // Read line by line.
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();

        return result.toString();
    }
    public static void main(String[] args) throws IOException{