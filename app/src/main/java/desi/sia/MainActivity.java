package desi.sia;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import desi.sia.SupportClass.Config;
import desi.sia.SupportClass.FontCache;
import desi.sia.SupportClass.TypeFaceSpan;

public class MainActivity extends AppCompatActivity {
    private Typeface fontLatoBold, fontLatoRegular, fontLatoHeavy, fontLatoBlack, fontLatoItalic;
    private Toolbar toolbar;
    private TextView lblWelcome;
    private Button btnO1, btnO2, btnO3, btnO4;
    private RequestQueue queue;
    private ProgressDialog pDialog;
    private SharedPreferences appsPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        setContentView(R.layout.activity_main);

        initToolbar();

        appsPref 	    = getSharedPreferences(Config.PREF_NAME, Activity.MODE_PRIVATE);
        queue    	    = Volley.newRequestQueue(this);
        fontLatoBold    = FontCache.get(MainActivity.this, "Lato-Bold");
        fontLatoRegular = FontCache.get(MainActivity.this, "Lato-Regular");
        fontLatoHeavy   = FontCache.get(MainActivity.this, "Lato-Heavy");
        fontLatoBlack   = FontCache.get(MainActivity.this, "Lato-Black");
        fontLatoItalic  = FontCache.get(MainActivity.this, "Lato-Italic");
        lblWelcome      = (TextView) findViewById(R.id.lblWelcome);
        btnO1           = (Button) findViewById(R.id.btnO1);
        btnO2           = (Button) findViewById(R.id.btnO2);
        btnO3           = (Button) findViewById(R.id.btnO3);
        btnO4           = (Button) findViewById(R.id.btnO4);

        lblWelcome.setTypeface(fontLatoHeavy);
        btnO1.setTypeface(fontLatoRegular);
        btnO2.setTypeface(fontLatoRegular);
        btnO3.setTypeface(fontLatoRegular);
        btnO4.setTypeface(fontLatoRegular);

        if (appsPref.getString("CategoryId", "").equalsIgnoreCase("1")) {
            btnO1.setText("Manage\nUser");
            btnO2.setText("Manage\nMahasiswa");
            btnO3.setText("Manage\nDosen");
            btnO4.setText("Manage\nKRS");
        } else {
            btnO1.setText("Profil");
            btnO2.setText("IP & IPK");
            btnO3.setText("Daftar\nDosen");
            btnO4.setText("Form\nKRS");
        }

        btnO1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (appsPref.getString("CategoryId", "").equalsIgnoreCase("1")) {
                    Intent intent = new Intent(MainActivity.this, UserActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                    intent.putExtra("Menu", "Profile");
                    startActivity(intent);
                }
            }
        });

        btnO2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (appsPref.getString("CategoryId", "").equalsIgnoreCase("1")) {
                    Intent intent = new Intent(MainActivity.this, MahasiswaActivity.class);
                    intent.putExtra("Menu", "Manage Mahasiswa");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                    intent.putExtra("Menu", "IP & IPK");
                    startActivity(intent);
                }
            }
        });

        btnO3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (appsPref.getString("CategoryId", "").equalsIgnoreCase("1")) {
                    Intent intent = new Intent(MainActivity.this, DosenActivity.class);
                    intent.putExtra("Menu", "Daftar DOsen");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, DosenActivity.class);
                    intent.putExtra("Menu", "Daftar DOsen");
                    startActivity(intent);
                }
            }
        });

        btnO4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (appsPref.getString("CategoryId", "").equalsIgnoreCase("1")) {

                } else {
                    Intent intent = new Intent(MainActivity.this, KRSActivity.class);
                    startActivity(intent);
                }
            }
        });

        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setMessage("Working...");
        pDialog.setCancelable(false);

        getUserDetail(appsPref.getString("CategoryId", ""), appsPref.getString("UserId", ""));
    }

    private void initToolbar() {
        SpannableString spanToolbar = new SpannableString("HOME");
        spanToolbar.setSpan(new TypeFaceSpan(MainActivity.this, "Lato-Bold"), 0, spanToolbar.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        //Initiate Toolbar/ActionBar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(spanToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void getUserDetail(String strCategoriId, String strUserId) {
        pDialog.show();
        String url;
        if (strCategoriId.equalsIgnoreCase("1")) {
            url = Config.URL+"/getStaff.php?Id="+strUserId;
        } else if (strCategoriId.equalsIgnoreCase("2")) {
            url = Config.URL+"/getDosen.php?Id="+strUserId;
        } else {
            url = Config.URL+"/getMahasiswa.php?Id="+strUserId;
        }

        JsonArrayRequest jsArrRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jArrResponse) {
                try {
                    JSONObject jObjResponse = jArrResponse.getJSONObject(0);
                    lblWelcome.setText("Selamat datang, "+jObjResponse.getString("Name"));

                    SharedPreferences.Editor editor = appsPref.edit();
                    editor.putString("Name", jObjResponse.getString("Name"));
                    editor.commit();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                pDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                Toast.makeText(getBaseContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsArrRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_menu:
                SharedPreferences.Editor editor = appsPref.edit();
                editor.putString("UserId", "");
                editor.putString("CategoryId", "");
                editor.putString("Name", "");
                editor.commit();

                Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }
}
