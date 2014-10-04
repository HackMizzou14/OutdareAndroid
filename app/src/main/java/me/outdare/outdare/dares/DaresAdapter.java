package me.outdare.outdare.dares;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import me.outdare.outdare.dare.Dare;

public class DaresAdapter extends ArrayAdapter<Dare> {

    private int resource;

    public DaresAdapter(Context context, int resource) {
        super(context, resource);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder view;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resource, null);

            view = new ViewHolder();
            view.tvDare = (TextView) convertView.findViewById(android.R.id.text1);

            convertView.setTag(view);
        } else {
            view = (ViewHolder) convertView.getTag();
        }

        Dare dare = getItem(position);
        view.tvDare.setText(dare.getDare());

        return convertView;
    }

    static class ViewHolder {
        TextView tvDare;
    }
}
