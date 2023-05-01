package com.FHStudios.FH;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;



import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.FHStudios.FH.R;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Signin extends AppCompatActivity {
   // DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://fh-demo-87637-default-rtdb.firebaseio.com/");

    private  EditText emailin;
    private  EditText psswin;
    private  Button subtn;
    private  TextView Crac;
    private TextView resetpssw;
    private  GoogleSignInOptions gso;
    private  GoogleSignInClient gsc;
    private  ImageView gglbtn;
    private final static int RC_SIGN_IN = 123;
    private FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListner;



    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);
        FirebaseUser user = mAuth.getCurrentUser();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.signin);
        emailin=findViewById(R.id.emailin);
        psswin=findViewById(R.id.psswin);
        subtn=findViewById(R.id.subtn);
        Crac=findViewById(R.id.Crac);
        resetpssw=findViewById(R.id.forgot_password);
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(Signin.this, MainScreen.class));
            finish();
        }
       Crac.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(Signin.this,CreateAccount.class));
            }



    });
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailAddress = "user@example.com";

        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("TAG", "Email sent.");
                        }
                    }
                });
        resetpssw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View mDialogView = LayoutInflater.from(Signin.this).inflate(R.layout.dialog_signin, null);
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(Signin.this)
                        .setView(mDialogView)
                        .setTitle("Login Form");
                //show dialog
                final AlertDialog mAlertDialog = mBuilder.show();
                //login button click of custom layout
                mDialogView.findViewById(R.id.dialogLoginBtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //dismiss dialog
                        mAlertDialog.dismiss();
                        //get text from EditTexts of custom layout
                        String emailAdress = ((EditText) mDialogView.findViewById(R.id.dialogEmailEt)).getText().toString().trim();

                        System.out.println(emailAdress);
                        mAuth.sendPasswordResetEmail(emailAdress).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(Signin.this, "The link for password recovery was sent", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }


                });
                //cancel button click of custom layout
                mDialogView.findViewById(R.id.dialogCancelBtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //dismiss dialog
                        mAlertDialog.dismiss();
                    }
                });
            }
        });





       gglbtn=findViewById(R.id.gglbtn);
       gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
       gsc= GoogleSignIn.getClient(this, gso);

       gglbtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
            SignIn();

           }
       });


        subtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email=emailin.getText().toString();
                final String password=psswin.getText().toString();
                if (email.isEmpty()||password.isEmpty()){
                    Toast.makeText(Signin.this,"You have to enter password and email",Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(Signin.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.d("","signInWithEmail^success");
                            Intent intent= new Intent(Signin.this,MainScreen.class);
                          intent.putExtra("news","yes");
                            //if есть чтото новое то
                            startActivity(intent);

                            finish();
                        } else{
                            Log.d("","SignInWithEmail Fail");
                            Toast.makeText(Signin.this, "failed to sign in", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });
        mAuthListner=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null){
                    startActivity(new Intent(Signin.this,MainScreen.class));
                }
            }
        };

}




 void SignIn(){
        Intent sgnintent =gsc.getSignInIntent();
        startActivityForResult(sgnintent,RC_SIGN_IN);
}


    //DELETE or complete(google)
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==RC_SIGN_IN){
            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);

            } catch (ApiException e) {

                Log.d("signInResult:failed code=" ,e.toString());
            }

        }

    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount account){
        Log.d("","BBBBBBBBBBBBBBBBBB success");
        AuthCredential credential= GoogleAuthProvider.getCredential(account.getIdToken(),null);
        Log.d("","AAAAAAAAAAAAAAAAAAA success");
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Log.d("","SignInWIthCredential success");
                    FirebaseUser user=mAuth.getCurrentUser();
                    Intent intent= new Intent(Signin.this,MainScreen.class);
                    startActivity(intent);
                    finish();
                }else
                {
                    Log.w("", "signInWithCredential:failure", task.getException());
                    Toast.makeText(Signin.this, " Fail", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void onBackPressed() {
        Toast.makeText(this,"Press Home Button",Toast.LENGTH_SHORT).show();
    }

}


