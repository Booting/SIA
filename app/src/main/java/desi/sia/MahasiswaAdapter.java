package desi.sia;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.json.JSONArray;
import java.util.ArrayList;
import desi.sia.SupportClass.FontCache;

public class MahasiswaAdapter extends BaseAdapter {

    private final LayoutInflater mLayoutInflater;
    private Context context;
    private Typeface fontLatoBold, fontLatoRegular, fontLatoHeavy, fontLatoBlack, fontLatoItalic;
    private JSONArray jArrMahasiswa;
    private String strCategoriId;

    private final String ID    = "Id";
    private final String NAME  = "Name";
    private final String PRODI = "Prodi";

    private ArrayList<String> arrayId = new ArrayList<String>(),
            arrayName  = new ArrayList<String>(),
            arrayProdi = new ArrayList<String>();

    public MahasiswaAdapter(Context context, JSONArray jArrMahasiswa, String strCategoriId) {
        this.mLayoutInflater = LayoutInflater.from(context);
        this.context         = context;
        fontLatoBold         = FontCache.get(context, "Lato-Bold");
        fontLatoRegular      = FontCache.get(context, "Lato-Regular");
        fontLatoHeavy        = FontCache.get(context, "Lato-Heavy");
        fontLatoBlack        = FontCache.get(context, "Lato-Black");
        fontLatoItalic       = FontCache.get(context, "Lato-Italic");
        this.jArrMahasiswa   = jArrMahasiswa;
        this.strCategoriId   = strCategoriId;

        for (int i = 0; i < this.jArrMahasiswa.length(); i++) {
            arrayId.add(this.jArrMahasiswa.optJSONObject(i).optString(ID));
            arrayName.add(this.jArrMahasiswa.optJSONObject(i).optString(NAME));
            arrayProdi.add(this.jArrMahasiswa.optJSONObject(i).optString(PRODI));
        }
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return jArrMahasiswa.length();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.dosen_cell, parent, false);
            vh = new ViewHolder();

            vh.linParent   = (LinearLayout) convertView.findViewById(R.id.linParent);
            vh.txtPosition = (TextView) convertView.findViewById(R.id.txtPosition);
            vh.txtDosen    = (TextView) convertView.findViewById(R.id.txtDosen);
            vh.txtProdi    = (TextView) convertView.findViewById(R.id.txtProdi);
            vh.btnEdit     = (Button) convertView.findViewById(R.id.btnEdit);

            vh.txtPosition.setTypeface(fontLatoHeavy);
            vh.txtDosen.setTypeface(fontLatoBold);
            vh.txtProdi.setTypeface(fontLatoRegular);
            vh.btnEdit.setTypeface(fontLatoRegular);

            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        vh.txtPosition.setText(""+(position+1));
        vh.txtDosen.setText(arrayName.get(position));
        vh.txtProdi.setText(arrayProdi.get(position));

        if (strCategoriId.equalsIgnoreCase("1")) {
            vh.btnEdit.setVisibility(View.VISIBLE);
        } else {
            vh.btnEdit.setVisibility(View.GONE);
        }

        vh.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProfileActivity.class);
                intent.putExtra("Menu", "IP & IPK");
                intent.putExtra("UserId", arrayId.get(position));
                intent.putExtra("CategoryId", "3");
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    static class ViewHolder {
    	LinearLayout linParent;
        TextView txtPosition, txtDosen, txtProdi;
        Button btnEdit;
    }

}