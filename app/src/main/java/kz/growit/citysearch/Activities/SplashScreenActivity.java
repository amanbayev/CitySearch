package kz.growit.citysearch.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import kz.growit.citysearch.Models.Category;
import kz.growit.citysearch.Models.Subcategory;
import kz.growit.citysearch.R;
import kz.growit.citysearch.Utils.VolleySingleton;

public class SplashScreenActivity extends AppCompatActivity {
    private boolean catsDone = false, subcatsDone = false;
    public static final String PREFS_NAME = "CitySearch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        initDatabase();
    }

    private void initDatabase() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        catsDone = prefs.getBoolean("catsDone", false);
        subcatsDone = prefs.getBoolean("subcatsDone", false);
        RequestQueue queue = VolleySingleton.getInstance(getApplicationContext()).getRequestQueue();
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(getApplicationContext())
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
        if (catsDone && subcatsDone) tryToProceed();
        if (!catsDone) {
            String catsUrl = "http://198.199.82.28/publications/AndroidCategories";
            JsonObjectRequest getCatsReq = new JsonObjectRequest(
                    Request.Method.GET,
                    catsUrl,
                    new JSONObject(),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            JSONArray catsArray;
                            Realm realm = Realm.getDefaultInstance();
                            try {
                                catsArray = response.getJSONArray("AndroidCategories");
                                for (int i = 0; i < catsArray.length(); i++) {
                                    JSONObject temp = catsArray.getJSONObject(i);
                                    realm.beginTransaction();
                                    Category tempCat = realm.createObject(Category.class);
                                    tempCat.setId(temp.getString("_id"));
                                    tempCat.setName(temp.getString("name"));
                                    tempCat.setImageUrl(temp.getString("imageUrl"));
                                    realm.commitTransaction();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
                            editor.putBoolean("catsDone", true);
                            editor.apply();
                            catsDone = true;
                            tryToProceed();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    }
            );
            queue.add(getCatsReq);
        }
        if (!subcatsDone) {
            String subcatsUrl = "http://198.199.82.28/publications/AndroidSubcategories";
            JsonObjectRequest getSubcats = new JsonObjectRequest(
                    Request.Method.GET,
                    subcatsUrl,
                    new JSONObject(),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray subcatsArray = response.getJSONArray("AndroidSubcategories");
                                Realm realm = Realm.getDefaultInstance();
                                for (int i = 0; i < subcatsArray.length(); i++) {
                                    JSONObject temp = subcatsArray.getJSONObject(i);
                                    realm.beginTransaction();
                                    Subcategory tempSub = realm.createObject(Subcategory.class);
                                    tempSub.setId(temp.getString("_id"));
                                    tempSub.setName(temp.getString("name"));
                                    tempSub.setParentId(temp.getString("parentId"));
                                    tempSub.setImageUrl(temp.getString("imageUrl"));
                                    realm.commitTransaction();
                                }
                                subcatsDone = true;
                                SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
                                editor.putBoolean("subcatsDone", true);
                                editor.apply();
                                tryToProceed();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    }
            );
            queue.add(getSubcats);
        }


    }

    private void tryToProceed() {
        if (catsDone && subcatsDone) {
            Intent goToMain = new Intent(SplashScreenActivity.this, MainActivity.class);
            startActivity(goToMain);
            finish();
        }
    }
}
