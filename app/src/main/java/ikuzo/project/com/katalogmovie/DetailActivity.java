package ikuzo.project.com.katalogmovie;

import android.annotation.SuppressLint;
import android.app.LoaderManager;
import android.content.Loader;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<MovieDetail>>{

    ImageView imgBackdrop;
    TextView tvTitle;
    TextView tvTagline;
    TextView tvGenres;
    TextView tvRating;
    TextView tvDuration;
    TextView tvOverview;
    TextView tvReleaseDate;
    LinearLayout linearParent;
    RelativeLayout relativeProgress;

    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbar;

    int movieID;

    public static final String EXTRAS_MOVIE_ID= "EXTRAS_MOVIE_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);

        imgBackdrop = findViewById(R.id.detailmovie_backdrop);
        tvTitle = findViewById(R.id.detailmovie_title);
        tvTagline = findViewById(R.id.detailmovie_tagline);
        tvGenres = findViewById(R.id.detailmovie_genres);
        tvRating = findViewById(R.id.detailmovie_rating);
        tvDuration = findViewById(R.id.detailmovie_duration);
        tvOverview = findViewById(R.id.detailmovie_overview);
        tvReleaseDate = findViewById(R.id.detailmovie_releasedate);

        linearParent = findViewById(R.id.linearContent);
        relativeProgress = findViewById(R.id.relativeProgress);

        movieID = getIntent().getIntExtra(EXTRAS_MOVIE_ID, 0);

        if(movieID > 0){

            Bundle bundle = new Bundle();
            bundle.putString(EXTRAS_MOVIE_ID, ""+movieID);
            getLoaderManager().restartLoader(0,bundle,DetailActivity.this);
        }
    }


    @Override
    public Loader<ArrayList<MovieDetail>> onCreateLoader(int id, Bundle args) {
        String idFilm = "";
        if(args != null){
            idFilm = args.getString(EXTRAS_MOVIE_ID);

        }

        return new MovieDetailAsyncLoader(this, idFilm);
    }


    @Override
    public void onLoadFinished(Loader<ArrayList<MovieDetail>> loader, ArrayList<MovieDetail> data) {
        setDetailData(data);
    }

    @SuppressLint("SetTextI18n")
    private void setDetailData(ArrayList<MovieDetail> data) {

        imgBackdrop.setVisibility(View.VISIBLE);
        linearParent.setVisibility(View.VISIBLE);
        relativeProgress.setVisibility(View.GONE);

//        Log.d("TAGS", "SIZEE : "+data.size());
        Log.d("TAGS", "backdrop : "+data.get(0).getBackdrop());
        Log.d("TAGS", "title : "+data.get(0).getTitle());
        Picasso.with(DetailActivity.this)
                .load("http://image.tmdb.org/t/p/w500/"+data.get(0).getBackdrop())
                .fit()
                .centerCrop()
                .into(imgBackdrop);
        tvTitle.setText(data.get(0).getTitle());
        tvTagline.setText(data.get(0).getTagline());
        tvGenres.setText(data.get(0).getGenres());
        tvRating.setText(""+data.get(0).getRating());
        tvDuration.setText(""+data.get(0).getDuration()+" Mins");
        tvOverview.setText(data.get(0).getOverview());
        tvReleaseDate.setText(data.get(0).getRelease_date());


        collapsingToolbar.setTitle(data.get(0).getTitle());

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<MovieDetail>> loader) {

    }
}
