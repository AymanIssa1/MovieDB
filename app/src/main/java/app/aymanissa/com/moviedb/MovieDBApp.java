package app.aymanissa.com.moviedb;

import android.app.Application;

import com.zplesac.connectionbuddy.ConnectionBuddy;
import com.zplesac.connectionbuddy.ConnectionBuddyConfiguration;

/**
 * Created by Ayman on 3/2/2018.
 */

public class MovieDBApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ConnectionBuddyConfiguration networkInspectorConfiguration = new ConnectionBuddyConfiguration.Builder(this).build();
        ConnectionBuddy.getInstance().init(networkInspectorConfiguration);
    }

}
