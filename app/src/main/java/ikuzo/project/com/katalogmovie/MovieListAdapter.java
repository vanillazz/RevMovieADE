package ikuzo.project.com.katalogmovie;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Movie;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Vanillazz on 16/01/2018.
 */

public class MovieListAdapter extends BaseAdapter {

    private ArrayList<MovieItem> movieData = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;

    public MovieListAdapter(Context context){
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setMovieData(ArrayList<MovieItem> items){
        movieData = items;
        notifyDataSetChanged();
    }

    public void addMovieData(final MovieItem item){
        movieData.add(item);
        notifyDataSetChanged();
    }

    public void clearData(){
        movieData.clear();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getCount() {
        if(movieData == null){
            return 0;
        }
        return movieData.size();
    }

    @Override
    public MovieItem getItem(int position) {
        return movieData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(holder == null){

            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.movie_item, null);
            holder.tvTitle = convertView.findViewById(R.id.tv_title);
            holder.tvOverview = convertView.findViewById(R.id.tv_overview);
            holder.tvReleaseDate = convertView.findViewById(R.id.tv_releasedate);
            holder.tvRatings = convertView.findViewById(R.id.tv_ratings);
            holder.imgPoster = convertView.findViewById(R.id.imgPoster);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvTitle.setText(movieData.get(position).getTitle());
        holder.tvOverview.setText(movieData.get(position).getOverview());
        holder.tvReleaseDate.setText("Release date : "+movieData.get(position).getRelease_date());
        holder.tvRatings.setText(String.valueOf(movieData.get(position).getRatings()));

        Picasso.with(context)
                .load("http://image.tmdb.org/t/p/w154/"+movieData.get(position).getPoster())
                .resize(100, 130)
                .centerCrop()
                .into(holder.imgPoster);

        return convertView;
    }

    private class ViewHolder {
        TextView tvTitle;
        TextView tvOverview;
        TextView tvReleaseDate;
        TextView tvRatings;
        ImageView imgPoster;
    }
}
