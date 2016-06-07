package jason.xie.columns.model;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Jason Xie on 2016/6/6.
 */

public class SharedPreferencesHelper {

    private SharedPreferences preferences;

    public SharedPreferencesHelper(Context context) {
        preferences = context.getSharedPreferences("shared_preference",
                Context.MODE_PRIVATE);
    }

    public void saveDataString(String key, String value) {
        preferences.edit().putString(key, value).apply();
    }

    public String getDataString(String key) {
        return preferences.getString(key, "");
    }

    public void saveCustomIds(List<String> ids){
        if(ids == null || ids.size() == 0){
            saveDataString("custom_ids", "");
            return;
        }
        StringBuilder builder = new StringBuilder();
        for(String id : ids){
            builder.append(id + "_");
        }
        saveDataString("custom_ids", builder.toString());
    }

    public List<String> getCustomIds(){
        List<String> ids = new ArrayList<>();
        String idString = getDataString("custom_ids");
        if(idString != null && !idString.isEmpty()){
            ids = Arrays.asList(idString.split("_"));
        }
        return ids;
    }
}
