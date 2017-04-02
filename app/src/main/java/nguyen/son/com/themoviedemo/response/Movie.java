package nguyen.son.com.themoviedemo.response;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import nguyen.son.com.themoviedemo.api.IMovieApi;

/**
 * Created by sonnt on 4/2/17.
 */

@Parcel
public class Movie {

    private static final String TAG = "Movie";
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("adult")
    private boolean adult;
    @SerializedName("overview")
    private String overview;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("id")
    private long id;
    @SerializedName("original_title")
    private String originalTitle;
    @SerializedName("original_language")
    private String originalLanguage;
    @SerializedName("title")
    private String title;
    @SerializedName("backdrop_path")
    private String backdropPath;
    @SerializedName("popularity")
    private double popularity;
    @SerializedName("vote_count")
    private long voteCount;
    @SerializedName("video")
    private boolean video;
    @SerializedName("vote_average")
    private double voteAverage;

    private boolean favorite;
    private long favoriteTime;

    /**
     * @return The posterPath
     */
    public String getPosterPath() {
        return posterPath;
    }

    /**
     * @param posterPath The poster_path
     */
    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    /**
     * @return The adult
     */
    public boolean isAdult() {
        return adult;
    }

    /**
     * @param adult The adult
     */
    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    /**
     * @return The overview
     */
    public String getOverview() {
        return overview;
    }

    /**
     * @param overview The overview
     */
    public void setOverview(String overview) {
        this.overview = overview;
    }

    /**
     * @return The releaseDate
     */
    public String getReleaseDate() {
        return releaseDate;
    }

    /**
     * @param releaseDate The release_date
     */
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * @return The id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return The originalTitle
     */
    public String getOriginalTitle() {
        return originalTitle;
    }

    /**
     * @param originalTitle The original_title
     */
    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    /**
     * @return The originalLanguage
     */
    public String getOriginalLanguage() {
        return originalLanguage;
    }

    /**
     * @param originalLanguage The original_language
     */
    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    /**
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return The backdropPath
     */
    public String getBackdropPath() {
        return backdropPath;
    }

    /**
     * @param backdropPath The backdrop_path
     */
    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    /**
     * @return The popularity
     */
    public double getPopularity() {
        return popularity;
    }

    /**
     * @param popularity The popularity
     */
    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    /**
     * @return The voteCount
     */
    public long getVoteCount() {
        return voteCount;
    }

    /**
     * @param voteCount The vote_count
     */
    public void setVoteCount(long voteCount) {
        this.voteCount = voteCount;
    }

    /**
     * @return The video
     */
    public boolean isVideo() {
        return video;
    }

    /**
     * @param video The video
     */
    public void setVideo(boolean video) {
        this.video = video;
    }

    /**
     * @return The voteAverage
     */
    public double getVoteAverage() {
        return voteAverage;
    }

    /**
     * @param voteAverage The vote_average
     */
    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public long getFavoriteTime() {
        return favoriteTime;
    }

    public void setFavoriteTime(long favoriteTime) {
        this.favoriteTime = favoriteTime;
    }


    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String resolvePath(String path) {
        return String.format("%s%s?%s=%s", IMovieApi.BASE_IMAGE_URL, path, IMovieApi.API_PARAM, IMovieApi.API_KEY);
    }

    public String getReleaseYear() {
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String year = getReleaseDate();
        try {
            Date date = parser.parse(year);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            year = String.valueOf(calendar.get(Calendar.YEAR));
        } catch (ParseException e) {
            Log.d(TAG, "failed to get year", e);
        }
        return year;
    }

    public String getVoteAverageFormat() {
        return String.format(Locale.US, "%.2f", voteAverage);
    }

    public String getVoteCountFormat() {
        return String.valueOf(voteCount);
    }

}
