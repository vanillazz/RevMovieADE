package ikuzo.project.com.katalogmovie;

import android.content.Intent;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

//    RecyclerView listMovie;
//    EditText edtMovieName;
//    Button btnCari;
//    //    MovieListAdapterOld movieAdapter;
//
//    private ArrayList<MovieItem> movieData;
//    MovieListAdapter movieAdapter;
//
//

    private ApplicationPreferences mMoviePreferences;

    DrawerLayout drawer;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        profileCircleImageView = (CircleImageView) navigationView.getHeaderView(0).findViewById(R.id.imageView);
//        Glide.with(MainActivity.this)
//                .load(profileImageUrl)
//                .into(profileCircleImageView);
        navigationView.setNavigationItemSelectedListener(this);




        mMoviePreferences = new ApplicationPreferences(this);

        String lastMovie = mMoviePreferences.getLastMode();

        Bundle bundle = new Bundle();
        if(lastMovie != null){

            getSupportActionBar().setTitle(lastMovie);
            if (savedInstanceState == null) {
                Fragment currentFragment = new FragmentListMovie();
                bundle.putString(FragmentListMovie.EXTRAS_NAME, lastMovie);
                currentFragment.setArguments(bundle);
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.content_main, currentFragment)
                        .commit();
            }

        } else {
            getSupportActionBar().setTitle(R.string.now_playing);

            if (savedInstanceState == null) {
                bundle.putString(FragmentListMovie.EXTRAS_NAME, getString(R.string.now_playing));
                Fragment currentFragment = new FragmentListMovie();
                currentFragment.setArguments(bundle);
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.content_main, currentFragment)
                        .commit();
            }
        }

//        listMovie = findViewById(R.id.listviewMovie);
//        listMovie.setHasFixedSize(true);
//
//        edtMovieName = findViewById(R.id.edtSearchMovie);
//        btnCari = findViewById(R.id.btnSearch);
//        btnCari.setOnClickListener(myListener);

//        movieData = new ArrayList<>();
//        listMovie.setLayoutManager(new LinearLayoutManager(this));
//        movieAdapter = new MovieListAdapter(this);
//        movieAdapter.setDataList(movieData);
//        listMovie.setAdapter(movieAdapter);
//
//        Bundle bundle = new Bundle();
//        bundle.putString(EXTRAS_MOVIENAME,"COCO");
//        getLoaderManager().initLoader(0, bundle, this);
//
//
//        RecyclerClickSupport.addTo(listMovie).setOnItemClickListener(new RecyclerClickSupport.OnItemClickListener() {
//            @Override
//            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
//                moveToDetailPage(movieData.get(position));
//            }
//        });
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Bundle bundle = new Bundle();
        Fragment fragment = null;

        String title = "";

        fragment = new FragmentListMovie();
        if (id == R.id.nav_now_playing) {
            title = getString(R.string.now_playing);
//            fragment = new FragmentListMovie();
            bundle.putString(FragmentListMovie.EXTRAS_NAME, title);
            fragment.setArguments(bundle);
        } else if (id == R.id.nav_upcoming) {
            title = getString(R.string.upcoming);
//            fragment = new FragmentUpcoming();
            bundle.putString(FragmentListMovie.EXTRAS_NAME, title);
            fragment.setArguments(bundle);

        } else if (id == R.id.nav_popular) {
            title = getString(R.string.popular);
//            fragment = new FragmentUpcoming();
            bundle.putString(FragmentListMovie.EXTRAS_NAME, title);
            fragment.setArguments(bundle);

        } else if (id == R.id.nav_top_rated) {
            title = getString(R.string.top_rated);
//            fragment = new FragmentUpcoming();
            bundle.putString(FragmentListMovie.EXTRAS_NAME, title);
            fragment.setArguments(bundle);

        } else if (id == R.id.nav_custom) {
            title = getString(R.string.custom_search);
//            fragment = new FragmentCustomSearch();
            bundle.putString(FragmentListMovie.EXTRAS_NAME, title);
            fragment.setArguments(bundle);

        }

                /*
        Ganti halaman dengan memanggil fragment replace
         */
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_main, fragment)
                    .commit();
        }
        getSupportActionBar().setTitle(title);

        mMoviePreferences.setLastMode(title);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings){
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }

//    private void moveToDetailPage(MovieItem movieItem) {
//        Toast.makeText(this, "Test "+movieItem.getId(), Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(this, DetailActivity.class);
//        intent.putExtra(DetailActivity.EXTRAS_MOVIE_ID, movieItem.getId());
//        startActivity(intent);
//    }


//    @Override
//    public Loader<ArrayList<MovieItem>> onCreateLoader(int id, Bundle args) {
//        String namaFilm = "";
//        if(args != null){
//            namaFilm = args.getString(EXTRAS_MOVIENAME);
//
//        }
//
//        return new MovieAsyncLoader(this, namaFilm);
//    }
//
//    @Override
//    public void onLoadFinished(Loader<ArrayList<MovieItem>> loader, ArrayList<MovieItem> data) {
//        movieData = data;
//        movieAdapter.setDataList(data);
//        movieAdapter.notifyDataSetChanged();
////        movieAdapter.setMovieData(data);
//    }
//
//    @Override
//    public void onLoaderReset(Loader<ArrayList<MovieItem>> loader) {
//        movieData.clear();
//        movieAdapter.getDataList().clear();
////        movieAdapter.notifyDataSetChanged();
//    }
//
//    View.OnClickListener myListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            String kota = edtMovieName.getText().toString();
//
//            if (TextUtils.isEmpty(kota))return;
//
//            Bundle bundle = new Bundle();
//            bundle.putString(EXTRAS_MOVIENAME,kota);
//            getLoaderManager().restartLoader(0,bundle,MainActivity.this);
//        }
//    };

//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        MovieItem mItem = movieAdapter.getItem(position);
//
//        Toast.makeText(this, "Test "+mItem.getTitle(), Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(this, DetailActivity.class);
//        intent.putExtra(DetailActivity.EXTRAS_MOVIE_ID, mItem.getId());
//        startActivity(intent);
//    }
}
