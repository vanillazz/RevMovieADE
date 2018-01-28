package ikuzo.project.com.katalogmovie;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Vanillazz on 16/01/2018.
 */

public class MovieAsyncLoader extends AsyncTaskLoader<ArrayList<MovieItem>> {

    private ArrayList<MovieItem> movieData;
    private boolean mHasResult = false;


    private static final String API_ID = "b31d2fdc75743572bc320421747353e6";

    private String movieName;

    public MovieAsyncLoader(final Context context, String movieName){
        super(context);

        onContentChanged();
        this.movieName = movieName;
    }

    @Override
    protected void onStartLoading() {
        if(takeContentChanged()){
            forceLoad();
        } else if(mHasResult){
            deliverResult(movieData);
        }
    }

    @Override
    public void deliverResult(ArrayList<MovieItem> data) {
        movieData = data;
        mHasResult = true;
        super.deliverResult(data);
    }

    @Override
    public ArrayList<MovieItem> loadInBackground() {
        final ArrayList<MovieItem> movieitems = new ArrayList<>();

        SyncHttpClient client = new SyncHttpClient();

        String url = "https://api.themoviedb.org/3/search/movie?api_key="+API_ID+"&language=en-US&query="+movieName;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    Log.d("TEST", result);
                    JSONObject object = new JSONObject(result);
                    JSONArray list = object.getJSONArray("results");

                    for(int i = 0; i < list.length(); i++){
                        JSONObject obj = list.getJSONObject(i);
                        MovieItem movieItem = new MovieItem(obj);
                        movieitems.add(movieItem);

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
        return movieitems;
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if(mHasResult){
            onReleaseResult(movieData);
            movieData = null;
            mHasResult = false;
        }
    }

    private void onReleaseResult(ArrayList<MovieItem> movieData) {
    }
}
