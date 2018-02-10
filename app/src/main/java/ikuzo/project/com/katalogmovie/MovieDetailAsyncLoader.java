package ikuzo.project.com.katalogmovie;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Vanillazz on 18/01/2018.
 */

public class MovieDetailAsyncLoader extends AsyncTaskLoader<ArrayList<MovieDetail>> {

    ArrayList<MovieDetail> mResult;
    private boolean mHasResult = false;

    private static final String API_ID = BuildConfig.OPEN_WEATHER_MAP_API_KEY;

    private String movieIds;

    public MovieDetailAsyncLoader(Context context, String movieId) {
        super(context);

        onContentChanged();
        this.movieIds = movieId;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();

        if(takeContentChanged()){
            forceLoad();
        } else if(mHasResult){
            deliverResult(mResult);
        }
    }

    @Override
    public ArrayList<MovieDetail> loadInBackground() {
        final ArrayList<MovieDetail> mData = new ArrayList<>();


        SyncHttpClient syncHttpClient = new SyncHttpClient();

        String movieUrl = "https://api.themoviedb.org/3/movie/"+movieIds+"?api_key="+API_ID+"&language=en-US";

        syncHttpClient.get(movieUrl, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {

                    String result = new String(responseBody);
                    Log.d("TEST", "Result : "+result);
                    JSONObject obj = new JSONObject(result);


                    MovieDetail mMovieDetail = new MovieDetail(obj);
                    mData.add(mMovieDetail);

                } catch (Exception e){

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

        return mData;
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if(mHasResult){
            mResult = null;
            mHasResult = false;
        }
    }

    @Override
    public void deliverResult(ArrayList<MovieDetail> data) {
        mResult = data;
        mHasResult = true;
        super.deliverResult(data);
    }
}
