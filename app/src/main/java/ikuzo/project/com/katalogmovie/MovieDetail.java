package ikuzo.project.com.katalogmovie;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Vanillazz on 18/01/2018.
 */

public class MovieDetail {

    String backdrop;
    String title;
    String tagline;
    String genres;
    Double rating;
    int duration;
    String overview;
    String release_date;

    public MovieDetail(JSONObject object){
        try {

            String backdrop = object.getString("backdrop_path");
            String title = object.getString("title");
            String tagline = object.getString("tagline");
            String genres = "";
            JSONArray arrGenre = object.getJSONArray("genres");
            for (int i = 0; i < arrGenre.length(); i++){
                String temp = arrGenre.getJSONObject(i).getString("name");

                if(i == 0){
                    genres = genres + temp;
                } else {
                    genres = genres + ", "+temp;
                }
            }
            Double rating = object.getDouble("vote_average");
            int duration = object.getInt("runtime");
            String overview = object.getString("overview");
            String release_date = object.getString("release_date");


            Log.d("TAGS", "backdrop : "+backdrop);
            Log.d("TAGS", "title : "+title);
            Log.d("TAGS", "tagline : "+tagline);
            Log.d("TAGS", "genres : "+genres);
            Log.d("TAGS", "rating : "+rating);
            Log.d("TAGS", "duration : "+duration);
            Log.d("TAGS", "overview : "+overview);
            Log.d("TAGS", "release_date : "+release_date);

            this.backdrop = backdrop;
            this.title = title;
            this.tagline = tagline;
            this.genres = genres;
            this.rating = rating;
            this.duration = duration;
            this.overview = overview;
            this.release_date = release_date;


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }
}
