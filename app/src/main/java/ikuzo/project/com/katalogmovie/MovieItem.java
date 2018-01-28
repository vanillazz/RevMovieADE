package ikuzo.project.com.katalogmovie;

import android.util.Log;

import org.json.JSONObject;

/**
 * Created by Vanillazz on 16/01/2018.
 */

public class MovieItem {

    int id;
    String title;
    String overview;
    String poster;
    String release_date;
    double ratings;


    public MovieItem(JSONObject jsonObject){
        try {

            int id = jsonObject.getInt("id");
            String title = jsonObject.getString("title");
            String overview = jsonObject.getString("overview");
            String poster = jsonObject.getString("poster_path");
            String release_date = jsonObject.getString("release_date");
            double ratings = jsonObject.getDouble("vote_average");

//            Log.d("TAGS", "id : "+id);
//            Log.d("TAGS", "title : "+title);
//            Log.d("TAGS", "overview : "+overview);
//            Log.d("TAGS", "poster : "+poster);
//            Log.d("TAGS", "release_date : "+release_date);
//            Log.d("TAGS", "ratings : "+ratings);

            this.id = id;
            this.title = title;
            this.overview = overview;
            this.poster = poster;
            this.release_date = release_date;
            this.ratings = ratings;

        } catch (Exception e){
            e.printStackTrace();
        }
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public double getRatings() {
        return ratings;
    }

    public void setRatings(double ratings) {
        this.ratings = ratings;
    }
}
