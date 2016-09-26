package desi.sia;

import android.content.Context;
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

public class UserAdapter extends BaseAdapter {

    private final LayoutInflater mLayoutInflater;
    private Context context;
    private Typeface fontLatoBold, fontLatoRegular, fontLatoHeavy, fontLatoBlack, fontLatoItalic;
    private JSONArray jArrUser;

    private final String ID       = "Id";
    private final String CATEGORY = "CategoryName";
    private final String USERNAME = "Username";

    private ArrayList<String> arrayId = new ArrayList<String>(),
            arrayCategory = new ArrayList<String>(),
            arrayUsername = new ArrayList<String>();

    public UserAdapter(Context context, JSONArray jArrUser) {
        this.mLayoutInflater = LayoutInflater.from(context);
        this.context         = context;
        fontLatoBold         = FontCache.get(context, "Lato-Bold");
        fontLatoRegular      = FontCache.get(context, "Lato-Regular");
        fontLatoHeavy        = FontCache.get(context, "Lato-Heavy");
        fontLatoBlack        = FontCache.get(context, "Lato-Black");
        fontLatoItalic       = FontCache.get(context, "Lato-Italic");
        this.jArrUser       = jArrUser;

        for (int i = 0; i < this.jArrUser.length(); i++) {
            arrayId.add(this.jArrUser.optJSONObject(i).optString(ID));
            arrayCategory.add(this.jArrUser.optJSONObject(i).optString(CATEGORY));
            arrayUsername.add(this.jArrUser.optJSONObject(i).optString(USERNAME));
        }
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return jArrUser.length();
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
        vh.txtDosen.setText(arrayUsername.get(position));
        vh.txtProdi.setText(arrayCategory.get(position));

        vh.btnEdit.setVisibility(View.VISIBLE);
        vh.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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