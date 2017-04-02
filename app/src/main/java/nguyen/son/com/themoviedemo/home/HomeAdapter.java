package nguyen.son.com.themoviedemo.home;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import nguyen.son.com.themoviedemo.R;
import nguyen.son.com.themoviedemo.response.Movie;

/**
 * Created by sonnt on 4/2/17.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolderMovie> {

    private List<Movie> mMovies = new ArrayList<>();
    private Context mContext;

    @NonNull
    private Callback mCallback;

    public interface Callback {
        void onItemClick(Movie reward, View shareView);

    }

    public HomeAdapter(@NonNull Context context, @NonNull Callback callback) {
        this.mContext = context;
        this.mCallback = callback;
    }

    public void setData(List<Movie> data, boolean append) {
        if (!append) {
            mMovies.clear();
        }
        mMovies.addAll(data);
        notifyDataSetChanged();
    }


    static class ViewHolderMovie extends RecyclerView.ViewHolder {

        @BindView(R.id.movie_item_icon)
        ImageView mImage;

        @BindView(R.id.movie_item_name_tv)
        TextView mNameView;

        @BindView(R.id.movie_item_release_date)
        TextView mReleaseDateTv;

        @BindView(R.id.movie_item_rating)
        RatingBar mRatingBar;

        @BindView(R.id.parent_view)
        View parentView;

        public ViewHolderMovie(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    @Override
    public ViewHolderMovie onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        final ViewHolderMovie viewHolder = new ViewHolderMovie(view);
        viewHolder.parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onItemClick(mMovies.get(viewHolder.getAdapterPosition()), viewHolder.mImage);

            }
        });
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolderMovie holder, int position) {
        Movie movie = mMovies.get(position);
        Glide.with(mContext).load(movie.resolvePath(movie.getBackdropPath()))
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.mImage);

        holder.mNameView.setText(movie.getTitle());
        holder.mReleaseDateTv.setText(movie.getReleaseDate());
        holder.mRatingBar.setRating((float) movie.getVoteAverage());
    }


    @Override
    public int getItemCount() {
        return mMovies == null ? 0 : mMovies.size();
    }
}
