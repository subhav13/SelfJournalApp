package com.example.self;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import util.JournalApi;

public class Create_Acc_ extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;
    private Button createAccBtn;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("users");

    private EditText emailEditTxt,passwordEditTxt,usernameEditTxt;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__acc_);
        firebaseAuth = FirebaseAuth.getInstance();
        emailEditTxt = findViewById(R.id.emailAddressTxt_ACC);
        passwordEditTxt = findViewById(R.id.password_txt_ACC);
        usernameEditTxt = findViewById(R.id.username_ACC);
        createAccBtn = findViewById(R.id.create_btn_ACC);
        progressBar = findViewById(R.id.progressBar_ACC);

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                currentUser = firebaseAuth.getCurrentUser();
                if(currentUser!=null){

                }else {

                }
            }
        };
        createAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(emailEditTxt.getText().toString())
                        && !TextUtils.isEmpty(passwordEditTxt.getText().toString())&&
                        !TextUtils.isEmpty(usernameEditTxt.getText().toString())){
                    String email = emailEditTxt.getText().toString();
                    String password = passwordEditTxt.getText().toString();
                    String username = usernameEditTxt.getText().toString();

                            createUserEmailAcc(email,password,username);
                }else{
                    Toast.makeText(Create_Acc_.this,"Empty Fields not allowed",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    private void createUserEmailAcc(String email, String password, final String username){
        if(!TextUtils.isEmpty(email)&&
        !TextUtils.isEmpty(password)&&
        !TextUtils.isEmpty(username)){
            progressBar.setVisibility(View.VISIBLE);
            firebaseAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                currentUser = firebaseAuth.getCurrentUser();
                                assert currentUser != null;
                                final String currentUserID = currentUser.getUid();
                                Map<String,String> userOBJ = new HashMap<>();
                                userOBJ.put("userID",currentUserID);
                                userOBJ.put("username",username);
                                collectionReference.add(userOBJ)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                documentReference
                                                        .get()
                                                        .addOnCompleteListener
                                                                (new OnCompleteListener<DocumentSnapshot>(){
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if(Objects.requireNonNull(task.getResult()).exists()){
                                                            progressBar.setVisibility(View.INVISIBLE);
                                                            String name = task.getResult().getString("username");
                                                            JournalApi journalApi = JournalApi.getInstance();
                                                            journalApi.setUsername(name);
                                                            journalApi.setUserID(currentUserID);
                                                            Intent intent = new Intent(Create_Acc_.this
                                                                    ,PostJournalAcivity.class);
                                                            intent.putExtra("username",name);
                                                            intent.putExtra("userID",currentUserID);
                                                            startActivity(intent);

                                                        }else{
                                                            progressBar.setVisibility(View.INVISIBLE);

                                                        }

                                                    }
                                                });
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });
                            }else{
                                //something went wrong

                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }else {
            //

        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = firebaseAuth.getCurrentUser();
        firebaseAuth.addAuthStateListener(authStateListener);
    }
}
