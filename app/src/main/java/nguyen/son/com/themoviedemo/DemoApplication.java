package nguyen.son.com.themoviedemo;

import android.app.Activity;
import android.app.Application;

import nguyen.son.com.themoviedemo.api.IMovieApi;
import nguyen.son.com.themoviedemo.di.AppComponent;
import nguyen.son.com.themoviedemo.di.AppModule;
import nguyen.son.com.themoviedemo.di.DaggerAppComponent;
import nguyen.son.com.themoviedemo.di.NetModule;

/**
 * Created by sonnt on 4/2/17.
 */

public class DemoApplication extends Application {
    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(IMovieApi.BASE_URL))
                .build();
    }

    public static AppComponent get(Activity activity) {
        return ((DemoApplication) activity.getApplication()).getAppComponent();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
