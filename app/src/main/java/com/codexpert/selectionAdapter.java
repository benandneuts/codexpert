package com.codexpert;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.core.content.ContextCompat;

public class selectionAdapter extends BaseAdapter {
    private ListTheme listTheme;
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<ThemeListener> listThemeListener = new ArrayList<ThemeListener>();

    public selectionAdapter(Context context, ListTheme aListP) {
        mContext = context;
        listTheme = aListP;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() { return listTheme.size(); }

    @Override
    public Object getItem(int i) { return listTheme.get(i);}

    @Override
    public long getItemId(int i) { return i;}

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        LinearLayout layoutItem;

        //(1) : Réutilisation des layouts
        if (view == null) {
            //Initialisation de notre item à partir du  layout XML "personne_layout.xml"
            layoutItem = (LinearLayout) mInflater.inflate(R.layout.layout_theme, parent, false);
        } else {
            layoutItem = (LinearLayout) view;
        }

        ImageView tv_image = (ImageView)layoutItem.findViewById(R.id.imageTheme);
        TextView tv_name = (TextView)layoutItem.findViewById(R.id.description);

        tv_name.setText(listTheme.get(i).getNom());
        tv_image.setImageResource(listTheme.get(i).getImage());

        if(listTheme.get(i).getIsNotAvaible()){
            tv_name.setTextColor(ContextCompat.getColor(mContext, R.color.LightCoral));
        }

        tv_name.setTag(i);
        tv_name.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Integer position = (Integer)v.getTag();
                sendListener(listTheme.get(position), position);
            }
        });

        //On retourne l'item créé.
        return layoutItem;
    }

    public void addListener(ThemeListener aListener) {
        listThemeListener.add(aListener);
    }

    private void sendListener(Theme item, int position) {
        for(int i = listThemeListener.size()-1; i >= 0; i--) {
            if(item.getIsNotAvaible()) {
                listThemeListener.get(i).notAvaibleClick(item, position);
            }
            else {
                listThemeListener.get(i).onClick(item, position);
            }
        }
    }
}
