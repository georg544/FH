package com.FHStudios.FH;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

public class LVadapterAdd extends BaseAdapter {
    Context context;
    ArrayList<Data> aD;
    LayoutInflater inflater;
    public LVadapterAdd(Context context, ArrayList<Data> aD){
        this.aD=aD;
        this.context=context;

        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getCount() {
        return aD.size();
    }

    @Override
    public Object getItem(int position) {
        return aD.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder,holder1;

        if (convertView == null) {
            holder = new ViewHolder();
            holder1 = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.frag_cust_listview,null);

            holder.editText = (EditText) convertView.findViewById(R.id.ED_frag_custom);
            holder1.editText=(EditText) convertView.findViewById(R.id.ED_frag_custom2);

            convertView.setTag(holder);
            convertView.setTag(holder1);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder) convertView.getTag();
            holder1 = (ViewHolder) convertView.getTag();
        }
            holder.editText.setText(aD.get(position).getComponent());
            holder1.editText.setText(aD.get(position).getQuantity());

            holder.editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }
@Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    aD.get(position).setComponent(holder.editText.getText().toString());
                }
@Override
                public void afterTextChanged(Editable editable) {

                }
            });
            holder1.editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    aD.get(position).setComponent(holder1.editText.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            return convertView;
        }



    private class ViewHolder {

        protected EditText editText;

    }
}
