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
import android.widget.Button;
import android.widget.ListView;
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

public class KRSActivity extends AppCompatActivity {
    private Typeface fontLatoBold, fontLatoRegular, fontLatoHeavy, fontLatoBlack, fontLatoItalic;
    private Toolbar toolbar;
    private TextView lblWelcome, lblSemester, txtSemester, lblTahunAjaran, txtTahunAjaran, lblNIM, txtNIM,
            lblNama, txtNama, txtPosition, txtMataKuliah, txtSKS, txtKeterangan;
    private RequestQueue queue;
    private ProgressDialog pDialog;
    private SharedPreferences appsPref;
    private ListView listKRS;
    private KRSAdapter krsAdapter;
    private Button btnKonfirmasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        setContentView(R.layout.activity_krs);

        initToolbar();

        appsPref 	    = getSharedPreferences(Config.PREF_NAME, Activity.MODE_PRIVATE);
        queue    	    = Volley.newRequestQueue(this);
        fontLatoBold    = FontCache.get(KRSActivity.this, "Lato-Bold");
        fontLatoRegular = FontCache.get(KRSActivity.this, "Lato-Regular");
        fontLatoHeavy   = FontCache.get(KRSActivity.this, "Lato-Heavy");
        fontLatoBlack   = FontCache.get(KRSActivity.this, "Lato-Black");
        fontLatoItalic  = FontCache.get(KRSActivity.this, "Lato-Italic");
        lblWelcome      = (TextView) findViewById(R.id.lblWelcome);
        listKRS         = (ListView) findViewById(R.id.listKRS);
        lblSemester     = (TextView) findViewById(R.id.lblSemester);
        txtSemester     = (TextView) findViewById(R.id.txtSemester);
        lblTahunAjaran  = (TextView) findViewById(R.id.lblTahunAjaran);
        txtTahunAjaran  = (TextView) findViewById(R.id.txtTahunAjaran);
        lblNIM          = (TextView) findViewById(R.id.lblNIM);
        txtNIM          = (TextView) findViewById(R.id.txtNIM);
        lblNama         = (TextView) findViewById(R.id.lblNama);
        txtNama         = (TextView) findViewById(R.id.txtNama);
        txtPosition     = (TextView) findViewById(R.id.txtPosition);
        txtMataKuliah   = (TextView) findViewById(R.id.txtMataKuliah);
        txtSKS          = (TextView) findViewById(R.id.txtSKS);
        txtKeterangan   = (TextView) findViewById(R.id.txtKeterangan);
        btnKonfirmasi   = (Button) findViewById(R.id.btnKonfirmasi);

        lblWelcome.setTypeface(fontLatoHeavy);
        lblWelcome.setText("Selamat datang, "+appsPref.getString("Name", ""));
        lblSemester.setTypeface(fontLatoBold);
        txtSemester.setTypeface(fontLatoRegular);
        lblTahunAjaran.setTypeface(fontLatoBold);
        txtTahunAjaran.setTypeface(fontLatoRegular);
        lblNIM.setTypeface(fontLatoBold);
        txtNIM.setTypeface(fontLatoRegular);
        lblNama.setTypeface(fontLatoBold);
        txtNama.setTypeface(fontLatoRegular);
        txtPosition.setTypeface(fontLatoHeavy);
        txtMataKuliah.setTypeface(fontLatoHeavy);
        txtSKS.setTypeface(fontLatoHeavy);
        txtKeterangan.setTypeface(fontLatoHeavy);
        btnKonfirmasi.setTypeface(fontLatoBold);

        btnKonfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Terimakasih sudah mengisi form KRS", Toast.LENGTH_LONG).show();

            }
        });

        pDialog = new ProgressDialog(KRSActivity.this);
        pDialog.setMessage("Working...");
        pDialog.setCancelable(false);

        getUserDetail(appsPref.getString("CategoryId", ""), appsPref.getString("UserId", ""));
    }

    private void initToolbar() {
        SpannableString spanToolbar = new SpannableString("Form KRS");
        spanToolbar.setSpan(new TypeFaceSpan(KRSActivity.this, "Lato-Bold"), 0, spanToolbar.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

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

    public void getUserDetail(final String strCategoriId, String strUserId) {
        pDialog.show();
        String url = Config.URL+"/getMahasiswa.php?Id="+strUserId;

        final JsonArrayRequest jsArrRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jArrResponse) {
                try {
                    JSONObject jObjResponse = jArrResponse.getJSONObject(0);
                    txtNama.setText(jObjResponse.getString("Name"));
                    txtNIM.setText(jObjResponse.getString("Id"));
                    txtSemester.setText("V");
                    txtTahunAjaran.setText("2016");

                    krsAdapter = new KRSAdapter(KRSActivity.this);
                    listKRS.setAdapter(krsAdapter);
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

                Intent intent = new Intent(KRSActivity.this, SignInActivity.class);
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
