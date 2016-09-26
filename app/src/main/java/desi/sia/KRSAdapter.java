package desi.sia;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import desi.sia.SupportClass.FontCache;

public class KRSAdapter extends BaseAdapter {

    private final LayoutInflater mLayoutInflater;
    private Context context;
    private Typeface fontLatoBold, fontLatoRegular, fontLatoHeavy, fontLatoBlack, fontLatoItalic;

    public KRSAdapter(Context context) {
        this.mLayoutInflater = LayoutInflater.from(context);
        this.context         = context;
        fontLatoBold         = FontCache.get(context, "Lato-Bold");
        fontLatoRegular      = FontCache.get(context, "Lato-Regular");
        fontLatoHeavy        = FontCache.get(context, "Lato-Heavy");
        fontLatoBlack        = FontCache.get(context, "Lato-Black");
        fontLatoItalic       = FontCache.get(context, "Lato-Italic");
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return 6;
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
            convertView = mLayoutInflater.inflate(R.layout.krs_cell, parent, false);
            vh = new ViewHolder();

            vh.linParent     = (LinearLayout) convertView.findViewById(R.id.linParent);
            vh.txtPosition   = (TextView) convertView.findViewById(R.id.txtPosition);
            vh.txtMataKuliah = (TextView) convertView.findViewById(R.id.txtMataKuliah);
            vh.txtSKS        = (TextView) convertView.findViewById(R.id.txtSKS);
            vh.txtKeterangan = (TextView) convertView.findViewById(R.id.txtKeterangan);

            vh.txtPosition.setTypeface(fontLatoRegular);
            vh.txtMataKuliah.setTypeface(fontLatoRegular);
            vh.txtSKS.setTypeface(fontLatoRegular);
            vh.txtKeterangan.setTypeface(fontLatoRegular);

            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        vh.txtPosition.setText(""+(position+1));
        vh.txtMataKuliah.setText("Mata Kuliah "+position);
        vh.txtSKS.setText("SKS "+position);
        vh.txtKeterangan.setText("Ket "+position);

        return convertView;
    }

    static class ViewHolder {
    	LinearLayout linParent;
        TextView txtPosition, txtMataKuliah, txtSKS, txtKeterangan;
    }

}