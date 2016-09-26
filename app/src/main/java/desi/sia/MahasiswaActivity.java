package desi.sia;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import desi.sia.SupportClass.Config;
import desi.sia.SupportClass.FontCache;
import desi.sia.SupportClass.TypeFaceSpan;

public class MahasiswaActivity extends AppCompatActivity {
    private Typeface fontLatoBold, fontLatoRegular, fontLatoHeavy, fontLatoBlack, fontLatoItalic;
    private Toolbar toolbar;
    private TextView lblWelcome;
    private RequestQueue queue;
    private ProgressDialog pDialog;
    private SharedPreferences appsPref;
    private ListView listMahasiswa;
    private MahasiswaAdapter mahasiswaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        setContentView(R.layout.activity_mahasiswa);

        initToolbar();

        appsPref 	    = getSharedPreferences(Config.PREF_NAME, Activity.MODE_PRIVATE);
        queue    	    = Volley.newRequestQueue(this);
        fontLatoBold    = FontCache.get(MahasiswaActivity.this, "Lato-Bold");
        fontLatoRegular = FontCache.get(MahasiswaActivity.this, "Lato-Regular");
        fontLatoHeavy   = FontCache.get(MahasiswaActivity.this, "Lato-Heavy");
        fontLatoBlack   = FontCache.get(MahasiswaActivity.this, "Lato-Black");
        fontLatoItalic  = FontCache.get(MahasiswaActivity.this, "Lato-Italic");
        lblWelcome      = (TextView) findViewById(R.id.lblWelcome);
        listMahasiswa   = (ListView) findViewById(R.id.listMahasiswa);

        lblWelcome.setTypeface(fontLatoHeavy);
        lblWelcome.setText("Selamat datang, "+appsPref.getString("Name", ""));

        pDialog = new ProgressDialog(MahasiswaActivity.this);
        pDialog.setMessage("Working...");
        pDialog.setCancelable(false);

        if (getIntent().getStringExtra("Menu")!=null) {

        }

        getListMahasiswa(appsPref.getString("CategoryId", ""), appsPref.getString("UserId", ""));
    }

    private void initToolbar() {
        SpannableString spanToolbar;
        if (getIntent().getStringExtra("Menu")!=null) {
            spanToolbar = new SpannableString(getIntent().getStringExtra("Menu"));
        } else {
            spanToolbar = new SpannableString("Manage Mahasiswa");
        }
        spanToolbar.setSpan(new TypeFaceSpan(MahasiswaActivity.this, "Lato-Bold"), 0, spanToolbar.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

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

    public void getListMahasiswa(final String strCategoriId, String strUserId) {
        pDialog.show();
        String url = Config.URL+"/getManageMahasiswa.php";

        JsonArrayRequest jsArrRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jArrResponse) {
                mahasiswaAdapter = new MahasiswaAdapter(MahasiswaActivity.this, jArrResponse, strCategoriId);
                listMahasiswa.setAdapter(mahasiswaAdapter);
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

                Intent intent = new Intent(MahasiswaActivity.this, SignInActivity.class);
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
