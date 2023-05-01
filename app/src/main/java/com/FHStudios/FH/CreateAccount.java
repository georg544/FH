package com.FHStudios.FH;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.FHStudios.FH.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreateAccount extends AppCompatActivity {

    //DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://fh-demo-87637-default-rtdb.firebaseio.com/");
    private Button signUpBtn;
    private TextView alrAcc;
    private EditText nameEV,lastNameEV,emailin,psswin,pasConEV,phoneEV;
    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createacc);

        signUpBtn=findViewById(R.id.SignUpBtn);
        nameEV=findViewById(R.id.nameEV);
        lastNameEV=findViewById(R.id.LastNameEV);
        emailin=findViewById(R.id.emailin);
        phoneEV=findViewById(R.id.phoneEV);
        psswin=findViewById(R.id.psswin);
        pasConEV=findViewById(R.id.pasConEV);
        alrAcc=findViewById(R.id.AlAc);
        mAuth = FirebaseAuth.getInstance();


        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String FNameStr=nameEV.getText().toString();
                String LNameStr=lastNameEV.getText().toString();
                String emailStr=emailin.getText().toString();
                String passwStr=psswin.getText().toString();
                String pasConStr=pasConEV.getText().toString();
                String phoneStr=phoneEV.getText().toString();

                if(FNameStr.isEmpty()||LNameStr.isEmpty()||emailStr.isEmpty()||passwStr.isEmpty()||pasConStr.isEmpty()){
                    Toast.makeText(CreateAccount.this,"make sure you fill all the fields above",Toast.LENGTH_LONG).show();
                return;}
                mAuth.createUserWithEmailAndPassword(emailStr,passwStr).addOnCompleteListener(CreateAccount.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        Log.d("", "createUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        Intent intent = new Intent(CreateAccount.this,MainScreen.class);
                        startActivity(intent);
                        finish();
                    }
                    else {

                        Log.w("", "createUserWithEmail:failure", task.getException());
                        Toast.makeText(CreateAccount.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();

                    }
                    }
                });
               }
        });
        alrAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




    }








}