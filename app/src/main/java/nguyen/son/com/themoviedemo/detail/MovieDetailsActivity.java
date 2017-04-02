package nguyen.son.com.themoviedemo.detail;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.parceler.Parcels;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import nguyen.son.com.themoviedemo.DemoApplication;
import nguyen.son.com.themoviedemo.HomeActivity;
import nguyen.son.com.themoviedemo.R;
import nguyen.son.com.themoviedemo.di.detail.DetailModule;
import nguyen.son.com.themoviedemo.response.Movie;

/**
 * Created by sonnt on 4/2/17.
 */

public class MovieDetailsActivity extends AppCompatActivity implements MovieDetailsContract.View {

    @BindView(R.id.movie_detail_background)
    ImageView mImageViewBg;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.details_original_overview)
    TextView mOverview;

    @BindView(R.id.details_title)
    TextView mOriginalTitle;

    @Inject MovieDetailsContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);
        setupDependencyInjection();
        setUpToolbar();
        mPresenter.setMovieData((Movie) Parcels.unwrap(getIntent().getParcelableExtra(HomeActivity.EXTRA_DATA)));
    }

    private void setupDependencyInjection() {
        DemoApplication.get(this)
                .plus(new DetailModule(this))
                .inject(this);
    }

    void setUpToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayShowHomeEnabled(true);
            ab.setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.updateLayout();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showMovieBackground(String path) {
        Glide.with(this).load(path)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mImageViewBg);

    }

    @Override
    public void showTitle(String title) {
        mToolbar.setTitle(title);

    }

    @Override
    public void showOverview(String overview) {
        mOverview.setText(overview);
    }

    @Override
    public void showOriginalTitle(String originalTitle) {
        mOriginalTitle.setText(originalTitle);
    }
}
