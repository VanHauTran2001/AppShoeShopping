package data;

import android.content.Context;
import android.content.SharedPreferences;

public class MyShareFerences {
    private static final String MY_SHARED_FREFENRENCES = "MY_SHARED_FREFENRENCES";
    private Context context;

    public MyShareFerences(Context context) {
        this.context = context;
    }

    public void putStringValue(String key , String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED_FREFENRENCES,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,value);
        editor.apply();
    }
    public String getStringValue(String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED_FREFENRENCES,Context.MODE_PRIVATE);
        return sharedPreferences.getString(key,"");
    }
}
