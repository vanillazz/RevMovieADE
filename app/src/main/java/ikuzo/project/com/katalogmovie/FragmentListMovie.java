package ikuzo.project.com.katalogmovie;


import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentListMovie extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<MovieItem>>{

    RecyclerView listMovie;

//    EditText edtMovieName;
//    Button btnCari;
    //    MovieListAdapterOld movieAdapter;

    private ArrayList<MovieItem> movieData;
    MovieListAdapter movieAdapter;


    static final String EXTRAS_NAME = "EXTRAS_NAME";
    static final String EXTRAS_MOVIENAME= "EXTRAS_MOVIENAME";


    public FragmentListMovie() {
        // Required empty public constructor
    }

    String kiriman;
    int modeAwal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_list_movie, container, false);

        listMovie = (RecyclerView) view.findViewById(R.id.recyclerMovie);
        listMovie.setHasFixedSize(true);

//        edtMovieName = (EditText) view.findViewById(R.id.edtSearchMovie);
//        btnCari = (Button) view.findViewById(R.id.btnSearch);
//        btnCari.setOnClickListener(myListener);


        movieData = new ArrayList<>();
        listMovie.setLayoutManager(new LinearLayoutManager(getActivity()));
        movieAdapter = new MovieListAdapter(getActivity());
        movieAdapter.setDataList(movieData);
        listMovie.setAdapter(movieAdapter);


        kiriman = getArguments().getString(EXTRAS_NAME, "");
        if(kiriman.equals(getString(R.string.now_playing))){
            modeAwal = MovieAsyncLoader.REQUEST_TYPE_NOW;
        }
        else if(kiriman.equals(getString(R.string.upcoming))){
            modeAwal = MovieAsyncLoader.REQUEST_TYPE_UPC0MING;
        }
        else if(kiriman.equals(getString(R.string.popular))){
            modeAwal = MovieAsyncLoader.REQUEST_TYPE_POPULAR;
        }
        else if(kiriman.equals(getString(R.string.top_rated))){
            modeAwal = MovieAsyncLoader.REQUEST_TYPE_TOP_RATED;
        }
        else if(kiriman.equals(getString(R.string.custom_search))){
            modeAwal = MovieAsyncLoader.REQUEST_TYPE_CUSTOM;
            setHasOptionsMenu(true);
        } else {
            modeAwal = MovieAsyncLoader.REQUEST_TYPE_NOW;
        }

        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_MOVIENAME,"COCO");
        getLoaderManager().initLoader(0, bundle, this);



        RecyclerClickSupport.addTo(listMovie).setOnItemClickListener(new RecyclerClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                moveToDetailPage(movieData.get(position));
            }
        });
        return view;
    }

    private void moveToDetailPage(MovieItem movieItem) {
        Toast.makeText(getActivity(), "Test "+movieItem.getId(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRAS_MOVIE_ID, movieItem.getId());
        startActivity(intent);
    }

    @Override
    public Loader<ArrayList<MovieItem>> onCreateLoader(int id, Bundle args) {
        String namaFilm = "";
        if(args != null){
            namaFilm = args.getString(EXTRAS_MOVIENAME);

        }

        if(kiriman.equalsIgnoreCase(getString(R.string.custom_search))){
            return new MovieAsyncLoader(getActivity(), modeAwal, namaFilm);
        } else {
            return new MovieAsyncLoader(getActivity(), modeAwal, "");
        }
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<MovieItem>> loader, ArrayList<MovieItem> data) {
        movieData = data;
        movieAdapter.setDataList(data);
        movieAdapter.notifyDataSetChanged();
//        movieAdapter.setMovieData(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<MovieItem>> loader) {
        movieData.clear();
        movieAdapter.getDataList().clear();
//        movieAdapter.notifyDataSetChanged();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if(kiriman.equals(getString(R.string.custom_search))){
            inflater.inflate(R.menu.custom_search_menu, menu);

            SearchView searchView = (SearchView) MenuItemCompat.getActionView((menu.findItem(R.id.search)));
            searchView.setQueryHint(getResources().getString(R.string.search_hint));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Toast.makeText(getActivity(), query, Toast.LENGTH_SHORT).show();

                    Bundle bundle = new Bundle();
                    bundle.putString(EXTRAS_MOVIENAME, query);
                    getLoaderManager().restartLoader(0, bundle, FragmentListMovie.this);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }

    }


//    View.OnClickListener myListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            String kota = edtMovieName.getText().toString();
//
//            if (TextUtils.isEmpty(kota))return;
//
//            Bundle bundle = new Bundle();
//            bundle.putString(EXTRAS_MOVIENAME,kota);
//            getLoaderManager().restartLoader(0, bundle, FragmentListMovie.this);
//        }
//    };
}
