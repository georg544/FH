package com.FHStudios.FH;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class frag_dataenter extends Fragment implements View.OnClickListener {
View v;
private Button admorebtn,sendbtn,backbtn;
LinearLayout layout;
int GRAMM=1, MGRAMM=0;
EditText qq,ww;
private static final int DIALOG_CALL=11;
ArrayList<Data> aL=new ArrayList<>();
    private int lines=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_dataenter, container, false);
        admorebtn=v.findViewById(R.id.addmoreLVbtn);
        sendbtn=v.findViewById(R.id.senddatabtn);
        backbtn=v.findViewById(R.id.backdatabtn);
        layout=v.findViewById(R.id.laylist);
        View eddtext=getLayoutInflater().inflate(R.layout.frag_cust_listview,null,false);
        TextView grgm=eddtext.findViewById(R.id.textview_frag_cust_listview);
        TextView ed1=eddtext.findViewById(R.id.ED_frag_custom);
        ed1.setText("Calories");
        grgm.setVisibility(View.INVISIBLE);
        TextView ed2=eddtext.findViewById(R.id.ED_frag_custom2);
        Button cross=(Button) eddtext.findViewById(R.id.cross);
        cross.setVisibility(View.INVISIBLE);
        lines=1;
        ed1.setTag(lines);
        ed2.setTag(lines);
        ed2.setOnClickListener(this);
        ed1.setOnClickListener(this);
        layout.addView(eddtext);
        aLadding(lines,ed1,ed2,GRAMM);




        admorebtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                lines++;
                if(lines>=10) admorebtn.setVisibility(View.INVISIBLE);
                View eddtext=getLayoutInflater().inflate(R.layout.frag_cust_listview,null,false);
                TextView ed1=(TextView)eddtext.findViewById(R.id.ED_frag_custom);
                TextView ed2=(TextView)eddtext.findViewById(R.id.ED_frag_custom2);
                Button cross=(Button) eddtext.findViewById(R.id.cross);

                // Set onClickListener for the new TextViews
                ed1.setOnClickListener(frag_dataenter.this);
                ed2.setOnClickListener(frag_dataenter.this);

                // Set the tag for the new TextViews
                ed1.setTag(lines);
                ed2.setTag(lines);

                cross.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        removeView(eddtext);
                    }
                });
                layout.addView(eddtext);
                aLadding(lines,ed1,ed2,1);
            }
        });
        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < aL.size() ; i++) {
                   DBhelper mDB= new DBhelper(getActivity());
                   mDB.addData(aL.get(i).getComponent().toString().trim(),aL.get(i).getQuantity().toString().trim(),Integer.valueOf(aL.get(i).getGrmg().toString().trim()));
                    //System.out.println(aL.get(i));
                }

                System.out.println(aL);
            }
        });

        return v;
    }

    private void removeView(View v) {
        lines--;
        if(lines<10){admorebtn.setVisibility(View.VISIBLE);}
        layout.removeView(v);
    }
    private void aLadding(int ind,TextView component, TextView quant, int grmg){
        aL.add(ind-1, new Data(component.getText().toString(), quant.getText().toString(),GRAMM ));
    }
    private void alremoving(int ind){

    }

    @Override
    public void onClick(View v) {
        // Find the index of the Data object associated with the clicked view
        int index = layout.indexOfChild((View) v.getParent()) - (v.getId() == R.id.ED_frag_custom ? 0 : 1);

        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_input_first, null);

        final EditText inQuantity = view.findViewById(R.id.in_quantity);

        if ((v.getId() == R.id.ED_frag_custom && (int) v.getTag() == 1)||(v.getId() == R.id.ED_frag_custom2 && (int) v.getTag() == 1)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setView(view)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            String quantity = inQuantity.getText().toString();
                            String name="Calories";

                            View parentLayout = (View) v.getParent();

                            TextView quantityTextView = parentLayout.findViewById(R.id.ED_frag_custom2);

                            quantityTextView.setText(quantity);

                            // Update the Data object in the aL ArrayList
                            aL.set(index, new Data(name, String.valueOf(quantity), GRAMM));
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .create()
                    .show();
            return;
        }
        switch (v.getId()) {
            case R.id.ED_frag_custom:
            case R.id.ED_frag_custom2:
                // Inflate the custom dialog view
                View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_input, null);
                final EditText inputName = dialogView.findViewById(R.id.input_name);
                final EditText inputQuantity = dialogView.findViewById(R.id.input_quantity);

                // Create and show the AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setView(dialogView)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Get the entered data
                                String name = inputName.getText().toString();
                                String quantity = inputQuantity.getText().toString();

                                // Find the corresponding TextViews in the parent layout
                                View parentLayout = (View) v.getParent();
                                TextView nameTextView = parentLayout.findViewById(R.id.ED_frag_custom);
                                TextView quantityTextView = parentLayout.findViewById(R.id.ED_frag_custom2);

                                // Update the TextViews
                                nameTextView.setText(name);
                                quantityTextView.setText(quantity);

                                // Update the Data object in the aL ArrayList
                                aL.set(index, new Data(name, String.valueOf(quantity), GRAMM));
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .create()
                        .show();
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == DIALOG_CALL) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    String name = data.getStringExtra("name");
                    double quantity = data.getDoubleExtra("quantity", 0.0);
                    // Update the TextViews accordingly
                }
            }
        }
    }
}