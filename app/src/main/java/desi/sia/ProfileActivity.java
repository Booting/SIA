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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import desi.sia.SupportClass.Config;
import desi.sia.SupportClass.FontCache;
import desi.sia.SupportClass.TypeFaceSpan;

public class ProfileActivity extends AppCompatActivity {
    private Typeface fontLatoBold, fontLatoRegular, fontLatoHeavy, fontLatoBlack, fontLatoItalic;
    private Toolbar toolbar;
    private TextView lblWelcome, lblNama, txtNama, lblProdi, txtProdi, lblIP, txtIP, lblIPK, txtIPK,
            lblAngkatan, txtAngkatan, lblDosen, txtDosen, lblStatus, txtStatus, lblHP, txtHP;
    private RequestQueue queue;
    private ProgressDialog pDialog;
    private SharedPreferences appsPref;
    private ImageView imgProfile;
    private Button btnChange, btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        setContentView(R.layout.activity_profile);

        initToolbar();

        appsPref 	    = getSharedPreferences(Config.PREF_NAME, Activity.MODE_PRIVATE);
        queue    	    = Volley.newRequestQueue(this);
        fontLatoBold    = FontCache.get(ProfileActivity.this, "Lato-Bold");
        fontLatoRegular = FontCache.get(ProfileActivity.this, "Lato-Regular");
        fontLatoHeavy   = FontCache.get(ProfileActivity.this, "Lato-Heavy");
        fontLatoBlack   = FontCache.get(ProfileActivity.this, "Lato-Black");
        fontLatoItalic  = FontCache.get(ProfileActivity.this, "Lato-Italic");
        lblWelcome      = (TextView) findViewById(R.id.lblWelcome);
        imgProfile      = (ImageView) findViewById(R.id.imgProfile);
        lblNama         = (TextView) findViewById(R.id.lblNama);
        txtNama         = (TextView) findViewById(R.id.txtNama);
        lblProdi        = (TextView) findViewById(R.id.lblProdi);
        txtProdi        = (TextView) findViewById(R.id.txtProdi);
        lblIP           = (TextView) findViewById(R.id.lblIP);
        txtIP           = (TextView) findViewById(R.id.txtIP);
        lblIPK          = (TextView) findViewById(R.id.lblIPK);
        txtIPK          = (TextView) findViewById(R.id.txtIPK);
        lblAngkatan     = (TextView) findViewById(R.id.lblAngkatan);
        txtAngkatan     = (TextView) findViewById(R.id.txtAngkatan);
        lblDosen        = (TextView) findViewById(R.id.lblDosen);
        txtDosen        = (TextView) findViewById(R.id.txtDosen);
        btnChange       = (Button) findViewById(R.id.btnChange);
        btnUpdate       = (Button) findViewById(R.id.btnUpdate);
        lblStatus       = (TextView) findViewById(R.id.lblStatus);
        txtStatus       = (TextView) findViewById(R.id.txtStatus);
        lblHP           = (TextView) findViewById(R.id.lblHP);
        txtHP           = (TextView) findViewById(R.id.txtHP);

        lblWelcome.setTypeface(fontLatoHeavy);
        lblWelcome.setText("Selamat datang, "+appsPref.getString("Name", ""));
        lblNama.setTypeface(fontLatoBold);
        txtNama.setTypeface(fontLatoRegular);
        lblProdi.setTypeface(fontLatoBold);
        txtProdi.setTypeface(fontLatoRegular);
        lblIP.setTypeface(fontLatoBold);
        txtIP.setTypeface(fontLatoRegular);
        lblIPK.setTypeface(fontLatoBold);
        txtIPK.setTypeface(fontLatoRegular);
        lblAngkatan.setTypeface(fontLatoBold);
        txtAngkatan.setTypeface(fontLatoRegular);
        lblDosen.setTypeface(fontLatoBold);
        txtDosen.setTypeface(fontLatoRegular);
        btnChange.setTypeface(fontLatoRegular);
        btnUpdate.setTypeface(fontLatoRegular);
        lblStatus.setTypeface(fontLatoBold);
        txtStatus.setTypeface(fontLatoRegular);
        lblHP.setTypeface(fontLatoBold);
        txtHP.setTypeface(fontLatoRegular);

        pDialog = new ProgressDialog(ProfileActivity.this);
        pDialog.setMessage("Working...");
        pDialog.setCancelable(false);

        if (getIntent().getStringExtra("Menu")!=null) {
            if (getIntent().getStringExtra("Menu").equalsIgnoreCase("Profile")) {
                lblAngkatan.setVisibility(View.VISIBLE);
                txtAngkatan.setVisibility(View.VISIBLE);
                lblDosen.setVisibility(View.VISIBLE);
                txtDosen.setVisibility(View.VISIBLE);
                lblIP.setVisibility(View.GONE);
                txtIP.setVisibility(View.GONE);
                lblIPK.setVisibility(View.GONE);
                txtIPK.setVisibility(View.GONE);
                lblStatus.setVisibility(View.GONE);
                txtStatus.setVisibility(View.GONE);
                lblHP.setVisibility(View.GONE);
                txtHP.setVisibility(View.GONE);
            } else if (getIntent().getStringExtra("Menu").equalsIgnoreCase("IP & IPK")) {
                lblAngkatan.setVisibility(View.GONE);
                txtAngkatan.setVisibility(View.GONE);
                lblDosen.setVisibility(View.GONE);
                txtDosen.setVisibility(View.GONE);
                lblIP.setVisibility(View.VISIBLE);
                txtIP.setVisibility(View.VISIBLE);
                lblIPK.setVisibility(View.VISIBLE);
                txtIPK.setVisibility(View.VISIBLE);
                lblStatus.setVisibility(View.GONE);
                txtStatus.setVisibility(View.GONE);
                lblHP.setVisibility(View.GONE);
                txtHP.setVisibility(View.GONE);
            } else {
                lblAngkatan.setVisibility(View.GONE);
                txtAngkatan.setVisibility(View.GONE);
                lblDosen.setVisibility(View.GONE);
                txtDosen.setVisibility(View.GONE);
                lblIP.setVisibility(View.GONE);
                txtIP.setVisibility(View.GONE);
                lblIPK.setVisibility(View.GONE);
                txtIPK.setVisibility(View.GONE);
                lblStatus.setVisibility(View.VISIBLE);
                txtStatus.setVisibility(View.VISIBLE);
                lblHP.setVisibility(View.VISIBLE);
                txtHP.setVisibility(View.VISIBLE);
            }
        }

        if (getIntent().getStringExtra("UserId")==null) {
            btnChange.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.GONE);
            getUserDetail(appsPref.getString("CategoryId", ""), appsPref.getString("UserId", ""));
        } else {
            btnChange.setVisibility(View.VISIBLE);
            btnUpdate.setVisibility(View.VISIBLE);
            getUserDetail(getIntent().getStringExtra("CategoryId"), getIntent().getStringExtra("UserId"));
        }
    }

    private void initToolbar() {
        SpannableString spanToolbar;
        if (getIntent().getStringExtra("Menu")!=null) {
            if (getIntent().getStringExtra("CategoryId")!=null) {
                if (getIntent().getStringExtra("CategoryId").equalsIgnoreCase("3")) {
                    spanToolbar = new SpannableString("Manage Mahasiswa");
                } else {
                    spanToolbar = new SpannableString("Manage Dosen");
                }
            } else {
                spanToolbar = new SpannableString(getIntent().getStringExtra("Menu"));
            }
        } else {
            spanToolbar = new SpannableString("Profile");
        }
        spanToolbar.setSpan(new TypeFaceSpan(ProfileActivity.this, "Lato-Bold"), 0, spanToolbar.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

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
        String url;
        if (strCategoriId.equalsIgnoreCase("1")) {
            url = Config.URL+"/getStaff.php?Id="+strUserId;
        } else if (strCategoriId.equalsIgnoreCase("2")) {
            url = Config.URL+"/getDosen.php?Id="+strUserId;
        } else {
            url = Config.URL+"/getMahasiswa.php?Id="+strUserId;
        }

        final JsonArrayRequest jsArrRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jArrResponse) {
                try {
                    JSONObject jObjResponse = jArrResponse.getJSONObject(0);
                    if (strCategoriId.equalsIgnoreCase("2")) {
                        txtNama.setText(jObjResponse.getString("DosenName"));
                        txtProdi.setText(jObjResponse.getString("Prodi"));
                        txtStatus.setText(jObjResponse.getString("Status"));
                        txtHP.setText(jObjResponse.getString("HP"));
                    } else {
                        txtNama.setText(jObjResponse.getString("Name"));
                        txtProdi.setText(jObjResponse.getString("Prodi"));
                        txtAngkatan.setText(jObjResponse.getString("Angkatan"));
                        txtDosen.setText(jObjResponse.getString("Dosen"));
                        txtIP.setText(jObjResponse.getString("IP"));
                        txtIPK.setText(jObjResponse.getString("IPK"));
                    }
                    Glide.with(ProfileActivity.this).load(Config.URL_IMAGES + jObjResponse.getString("Foto")).into(imgProfile);
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

                Intent intent = new Intent(ProfileActivity.this, SignInActivity.class);
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
