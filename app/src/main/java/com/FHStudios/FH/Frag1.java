package com.FHStudios.FH;




import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class Frag1 extends Fragment {
    View view;
    AlertDialog.Builder alBu, wtal;
    Button scanBtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_frag1, container, false);
        scanBtn=view.findViewById(R.id.scanbtn);
        alBu=new AlertDialog.Builder(getActivity());
        System.out.println("ronihui");



        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

alBu.setTitle(" error").setMessage("Try one more time or type in manually")
                        .setCancelable(true).setPositiveButton("type in manually", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FragmentManager manager = getFragmentManager();
                                FragmentTransaction transaction = manager.beginTransaction();
                                transaction.replace(R.id.framelayout,new frag_dataenter());
                                transaction.commit();
                            }
                        }).setNegativeButton("one more time", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(getActivity(), Tesstest.class));
                            }
                        }).show();
            }
        });

        return view;
    }

    private void replaceFragment(Fragment fragment) {

            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.enterdates,fragment);
            transaction.commit();

    }
}