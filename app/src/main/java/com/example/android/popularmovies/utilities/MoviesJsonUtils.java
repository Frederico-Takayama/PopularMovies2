package com.example.android.popularmovies.utilities;

import android.content.Context;

import android.util.Log;

import com.example.android.popularmovies.data.Movie;
import com.example.android.popularmovies.data.Review;
import com.example.android.popularmovies.data.Trailler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class helps working with Json results
 *
 * Created by lsitec335.takayama on 04/08/17.
 */

public final class MoviesJsonUtils {

    private static final String TAG = MoviesJsonUtils.class.getName();

    /**
     * This method parses JSON from a web response and returns an array of Movie objects
     *
     * @param moviesJsonStr JSON response from server
     * @return Array of Movies describing movies details
     * @throws JSONException If JSON data cannot be properly parsed
     */
    public static Movie[] getMoviesFromJson(Context context, String moviesJsonStr)
            throws JSONException {

        /* example:
        moviesJsonStr:{
            "page":1,
            "total_results":19601,
            "total_pages":981,
            "results":[
            {
                "vote_count":4064,
                "id":211672,
                "video":false,
                "vote_average":6.4,
                "title":"Minions",
                "popularity":197.218355,
                "poster_path":"\/q0R4crx2SehcEEQEkYObktdeFy.jpg",
                "original_language":"en",
                "original_title":"Minions",
                "genre_ids":[
                    10751,
                    16,
                    12,
                    35
                ],
                "backdrop_path":"\/uX7LXnsC7bZJZjn048UCOwkPXWJ.jpg",
                "adult":false,
                "overview":"Minions Stuart, Kevin and Bob are recruited by Scarlet Overkill, a super-villain who, alongside her inventor husband Herb, hatches a plot to take over the world.",
                "release_date":"2015-06-17"
            },
            ...
        */

        JSONObject moviesJson = new JSONObject(moviesJsonStr);//create a JSON Object
        JSONArray resultsArray = moviesJson.getJSONArray("results");
        Movie[] movies = new Movie[resultsArray.length()];
        for (int i = 0; i < resultsArray.length(); i++) {

            JSONObject movieJson = resultsArray.getJSONObject(i);
            movies[i] = new Movie(
                    movieJson.getLong("id"),
                    movieJson.getString("title"),
                    movieJson.getString("poster_path"),
                    movieJson.getString("overview"),
                    movieJson.getDouble("vote_average"),
                    movieJson.getString("release_date"));

            Log.d(TAG, movies[i].toString());
        }

        return movies;
    }

    /**
     * This method parses JSON from a web response and returns an array of Trailler objects
     *
     * @param traillersJsonStr JSON response from server
     * @return Array of Movies describing movies details
     * @throws JSONException If JSON data cannot be properly parsed
     */
    public static Trailler[] getTraillersFromJson(String traillersJsonStr)
            throws JSONException {

        /* example:
        {
            "id": 181808,
            "results": [{
                "id": "597bb8969251414c1d002d92",
                "iso_639_1": "en",
                "iso_3166_1": "US",
                "key": "ye6GCY_vqYk",
                "name": "Behind the Scenes",
                "site": "YouTube",
                "size": 1080,
                "type": "Featurette"
            }, {
                "id": "597bb8b3c3a368182a00000d",
                "iso_639_1": "en",
                "iso_3166_1": "US",
                "key": "zB4I68XVPzQ",
                "name": "Official Teaser Trailer",
                "site": "YouTube",
                "size": 1080,
                "type": "Teaser"
            }]
        }
        */

        JSONObject traillersJson = new JSONObject(traillersJsonStr);//create a JSON Object
        JSONArray resultsArray = traillersJson.getJSONArray("results");
        Trailler[] traillers = new Trailler[resultsArray.length()];
        for (int i = 0; i < resultsArray.length(); i++) {

            JSONObject traillerJson = resultsArray.getJSONObject(i);
            traillers[i] = new Trailler(
                    traillerJson.getString("key"),
                    traillerJson.getString("name"));

            Log.d(TAG, traillers[i].toString());
        }

        return traillers;
    }

    /**
     * This method parses JSON from a web response and returns an array of Review objects
     *
     * @param reviewsJsonStr JSON response from server
     * @return Array of Movies describing movies details
     * @throws JSONException If JSON data cannot be properly parsed
     */
    public static Review[] getReviewsFromJson(String reviewsJsonStr)
            throws JSONException {

        /* example:
        {
            "id": 181808,
            "page": 1,
            "results": [{
                "id": "5a3d22cd0e0a264cbe2375e7",
                "author": "Weedinator",
                "content": "I got so high before going in to see \"The Last Jedi\" it's a wonder I figured out how to get into the theater. We started rocking the shatter bong hardcore, just pump it, pump it, till you can actually feel your brain melting, then hit that nail and do it again and again. At some point somebody asked the question \"where are we?\" and while I was reflecting philosophically on the matter, somebody else pointed out that we were parked in a lot by the cineplex and we slowly realized that we were here to see 'The Last Jedi'! I could barely function at all, so I went to my old go-to routine of donning dark glasses and a white cane to help disguise my complete stonification by pretending I was just some poor blind guy stumbling around and knocking things over. I usually do this to get past security at rock concerts and it never crossed my mind that a blind guy wouldn't be able to see a movie in the first place, but it worked anyway and soon enough we were in our seats. There were these fucking kids sitting right behind us and they kept kicking my seat like little retards, kicking, kicking, kicking.... so I took the lid off my extra-large Coke and just tossed it over my shoulder. Bingo! Direct hit! The little creeps shuffled off all pissed and whining, covered in sticky cola, us laughing at them, calling them losers, it was great! \r\n\r\nThen the movie started. The sound was awesome and everything but the screen was pretty freakin' dark I thought, could hardly make out anything. I started chanting \"Turn up the brightness! Turn up the brightness!\", expecting the rest of the audience to join in to my righteous chant of outrage, but then I realized I still had my Blind Guy Glasses on. Took 'em off and yup, cleared right up. There was some kind of big space battle going on so we took out our vape pens and started hoofing back lungfuls of sweet sweet shatter vapor. My girlfriend started texting me, bitching at me to pick her up some vag pads on the way home. WTF?? Get up off your fat ass and get 'em yourself I texted back. A barrage of bitchtexts followed, I was a jerk, I was an asshole and bla bla bla... I took a picture of my bare ass and sent it to her as a reply, fuckin bitch, anyway, apparently, this was considered 'indecent exposure' according to the usher-dork who wouldn't shut up about it so we had to leave the movie. We saw those stupid kids in the lobby as we were being escorted out, laughing at US, calling US 'losers'... I wanted to get back at them when they came out, even formed this elaborate plan where we would swoop down on them and soak them with freezing water this time, but we ended up just getting high again then went to Burger King.",
                "url": "https://www.themoviedb.org/review/5a3d22cd0e0a264cbe2375e7"
            }, {
            ...
            }]
        }
        */

        JSONObject reviewsJson = new JSONObject(reviewsJsonStr);//create a JSON Object
        JSONArray resultsArray = reviewsJson.getJSONArray("results");
        Review[] reviews = new Review[resultsArray.length()];
        for (int i = 0; i < resultsArray.length(); i++) {

            JSONObject reviewJson = resultsArray.getJSONObject(i);
            reviews[i] = new Review(
                    reviewJson.getString("author"),
                    reviewJson.getString("content"),
                    reviewJson.getString("url"));

            Log.d(TAG, reviews[i].toString());
        }

        return reviews;
    }


}
