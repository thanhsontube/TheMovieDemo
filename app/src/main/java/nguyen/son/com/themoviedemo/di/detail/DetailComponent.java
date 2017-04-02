package nguyen.son.com.themoviedemo.di.detail;

import dagger.Subcomponent;
import nguyen.son.com.themoviedemo.detail.MovieDetailsActivity;

/**
 * Created by sonnt on 4/2/17.
 */
@Subcomponent(modules = {DetailModule.class})
public interface DetailComponent {
    void inject(MovieDetailsActivity activity);
}
