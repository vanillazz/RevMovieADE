package ikuzo.project.com.katalogmovie;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Vanillazz on 03/02/2018.
 */

public class ApplicationPreferences {

    private String KEY_LAST_MODE = "last_mode_used";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public ApplicationPreferences(Context context) {
        String PREFS_NAME = "MOVIE_PREF";
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public String getLastMode() {
        return preferences.getString(KEY_LAST_MODE, null);
    }

    public void setLastMode(String name) {
        editor = preferences.edit();
        editor.putString(KEY_LAST_MODE, name);
        editor.apply();

    }
}
