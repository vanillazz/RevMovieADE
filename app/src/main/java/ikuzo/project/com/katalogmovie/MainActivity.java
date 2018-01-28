package ikuzo.project.com.katalogmovie;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<MovieItem>>, AdapterView.OnItemClickListener{

    ListView listMovie;
    MovieListAdapter movieAdapter;
    EditText edtMovieName;
    Button btnCari;

    static final String EXTRAS_MOVIENAME= "EXTRAS_MOVIENAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieAdapter = new MovieListAdapter(this);
        movieAdapter.notifyDataSetChanged();
        listMovie = findViewById(R.id.listviewMovie);
        listMovie.setAdapter(movieAdapter);
        listMovie.setOnItemClickListener(this);

        edtMovieName = findViewById(R.id.edtSearchMovie);
        btnCari = findViewById(R.id.btnSearch);

        btnCari.setOnClickListener(myListener);

        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_MOVIENAME,"COCO");
        getLoaderManager().initLoader(0, bundle, this);

    }


    @Override
    public Loader<ArrayList<MovieItem>> onCreateLoader(int id, Bundle args) {
        String namaFilm = "";
        if(args != null){
            namaFilm = args.getString(EXTRAS_MOVIENAME);

        }

        return new MovieAsyncLoader(this, namaFilm);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<MovieItem>> loader, ArrayList<MovieItem> data) {

        movieAdapter.setMovieData(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<MovieItem>> loader) {
        movieAdapter.setMovieData(null);
    }

    View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String kota = edtMovieName.getText().toString();

            if (TextUtils.isEmpty(kota))return;

            Bundle bundle = new Bundle();
            bundle.putString(EXTRAS_MOVIENAME,kota);
            getLoaderManager().restartLoader(0,bundle,MainActivity.this);
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MovieItem mItem = movieAdapter.getItem(position);

        Toast.makeText(this, "Test "+mItem.getTitle(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRAS_MOVIE_ID, mItem.getId());
        startActivity(intent);
    }
}
