package nguyen.son.com.themoviedemo;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.parceler.Parcels;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import nguyen.son.com.themoviedemo.detail.MovieDetailsActivity;
import nguyen.son.com.themoviedemo.di.HomeModule;
import nguyen.son.com.themoviedemo.home.HomeAdapter;
import nguyen.son.com.themoviedemo.home.HomeContract;
import nguyen.son.com.themoviedemo.response.Movie;


public class HomeActivity extends AppCompatActivity implements HomeContract.View, HomeAdapter.Callback,
        DatePickerDialog.OnDateSetListener {
    public static final String TAG = HomeActivity.class.getSimpleName();
    public static final String EXTRA_DATA = "EXTRA_DATA";

    @Inject
    HomeContract.Presenter mPresenter;

    @BindView(R.id.app_rcv) RecyclerView mRecyclerView;

    @BindView(R.id.empty_view) View mEmptyView;
    private TextView filterView;
    private View cleanView;

    private HomeAdapter mAdapter;

    private boolean loading = true;
    private int pastVisibleItems;
    private int visibleItemCount;
    private int totalItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setUpToolbar();


        final GridLayoutManager mLayoutManager = new GridLayoutManager(this, 2);

        mRecyclerView.setDrawingCacheEnabled(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new HomeAdapter(this, this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition();
                    Log.v(TAG, "end list visibleItemCount:" + visibleItemCount + ";totalItemCount:" + totalItemCount
                            + ";pastVisibleItems:" + pastVisibleItems + ";loading:" + loading);

                    if (loading) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            loading = false;
                            Log.v(TAG, "end list totalItemCount:" + totalItemCount);
                            //Do pagination.. i.e. fetch new data
                            mPresenter.loadMore();
                        }
                    }
                }
            }
        });

        setupDependencyInjection();
        mPresenter.loadMovie();
    }

    void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        filterView = (TextView) toolbar.findViewById(R.id.filter);
        cleanView = toolbar.findViewById(R.id.clean);
        filterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        HomeActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });
        cleanView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.cleanFilter();

            }
        });
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayShowHomeEnabled(true);
            ab.setDisplayHomeAsUpEnabled(false);
        }

    }

    private void setupDependencyInjection() {
        DemoApplication.get(this)
                .plus(new HomeModule(this))
                .inject(this);
    }


    @Override
    public void onItemClick(Movie reward, View shareView) {
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra(EXTRA_DATA, Parcels.wrap(reward));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(this, shareView, "details");

            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        final String dateFilter = new StringBuilder().append(year)
                .append("-").append(monthOfYear + 1).append("-").append(dayOfMonth).toString();
        filterView.setText(dateFilter);
        mPresenter.filter(dateFilter);

    }


    @Override
    public void showEmptyView() {
        mEmptyView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);

    }

    @Override
    public void showNowPlayingMovies(List<Movie> results, boolean append) {
        mEmptyView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mAdapter.setData(results, append);
        loading = true;

    }

    @Override
    public void updateFilter() {
        filterView.setText(getString(R.string.date_filter));
    }

    @Override
    public void releaseReferences() {
        mPresenter.release();
    }
}

