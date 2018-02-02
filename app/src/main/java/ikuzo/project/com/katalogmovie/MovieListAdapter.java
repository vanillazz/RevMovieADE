package ikuzo.project.com.katalogmovie;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Vanillazz on 28/01/2018.
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder> {
    Context context;

    ArrayList<MovieItem> dataList;

    public ArrayList<MovieItem> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<MovieItem> dataList) {
        this.dataList = dataList;
    }

    public MovieListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MovieListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new MovieListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieListViewHolder holder, int position) {
        Picasso.with(context)
                .load("http://image.tmdb.org/t/p/w154/"+dataList.get(position).getPoster())
                .resize(100, 130)
                .centerCrop()
                .into(holder.imgPoster);
        holder.tvRating.setText(String.valueOf(dataList.get(position).getRatings()));
        holder.tvTitle.setText(dataList.get(position).getTitle());
        holder.tvReleaseDate.setText(dataList.get(position).getRelease_date());
        holder.tvOverview.setText(dataList.get(position).getOverview());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MovieListViewHolder extends RecyclerView.ViewHolder{
        ImageView imgPoster;
        TextView tvRating, tvTitle, tvReleaseDate, tvOverview;

        public MovieListViewHolder(View view) {
            super(view);

            imgPoster = (ImageView) view.findViewById(R.id.img_poster);
            tvRating = (TextView) view.findViewById(R.id.tv_ratings);
            tvTitle = (TextView) view.findViewById(R.id.tv_title);
            tvReleaseDate = (TextView) view.findViewById(R.id.tv_releasedate);
            tvOverview = (TextView) view.findViewById(R.id.tv_overview);


        }
    }
}
