package com.example.allsocial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.util.HashMap;
import java.util.Map;

public class SignUpSocialMedia extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText class1, class2, class3, class4, class5, class6;
    Button finishBtn;
    ProgressBar progressBar;
    FirebaseAuth rAuth;
    FirebaseFirestore rStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_social_media);

        finishBtn = (Button) findViewById(R.id.FinishBtn);

        class1 = findViewById(R.id.editTextClass1);
        class2 = findViewById(R.id.editTextClass2);
        class3 = findViewById(R.id.editTextClass3);
        class4 = findViewById(R.id.editTextClass4);
        class5 = findViewById(R.id.editTextClass5);
        class6 = findViewById(R.id.editTextClass6);


        rAuth = FirebaseAuth.getInstance();
        rStore = FirebaseFirestore.getInstance(); //instantiate

        progressBar = findViewById(R.id.progressBar);

        if(rAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        //Code to register user into the firebase

        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                final String c1 = class1.getText().toString();
                final String c2 = class2.getText().toString();
                final String c3 = class3.getText().toString();
                final String c4 = class4.getText().toString();
                final String c5 = class5.getText().toString();
                final String c6 = class6.getText().toString();

                Intent in = getIntent();
                final String name = in.getStringExtra("name");
                final String email = in.getStringExtra("email");
                String password = in.getStringExtra("password");

                progressBar.setVisibility(View.VISIBLE);

                //register user in firebase

                rAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SignUpSocialMedia.this, "User Created.", Toast.LENGTH_SHORT).show();
                            //store data in FireStore before going to main activity
                            userID = rAuth.getCurrentUser().getUid(); //used to get the ID of the current user

                            Map<String, Object> userInfo = new HashMap<>();

                            userInfo.put("rName", name);
                            userInfo.put("email", email);
                            DocumentReference userDocRef = rStore.collection("Users").document(userID);
                            userDocRef.set(userInfo);

                            DocumentReference documentReference = rStore.collection("Users").document(userID).collection("MyQRCodes").document("MyQRCodesDoc");

                            Map<String, Object> user = new HashMap<>();



                            if ((c1.length() > 0)) {
                                user.put("facebook", c1);
                            } else {
                                user.put("facebook", "");
                            }
                            if (c2.length() > 0) {
                                user.put("instagram", c2);
                            } else {
                                user.put("instagram", "");
                            }
                            if (c3.length() > 0){
                                user.put("linkedin", c3);
                            } else {
                                user.put("linkedin", "");
                            }
                            if (c4.length() > 0){
                                user.put("twitter", c4);
                            }else {
                                user.put("twitter", "");
                            }
                            if (c5.length() > 0) {
                                user.put("snapchat", c5);
                            } else {
                                user.put("snapchat", "");

                            }
                            if (c6.length() > 0) {
                                user.put("youtube", c6);
                            } else {
                                user.put("youtube", "");
                            }

                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "User profile created for " + userID);
                                }
                            });


//                            WriteBatch batch = rStore.batch();
//                            DocumentReference class1 = rStore.collection("Users").document(userID).collection("MyClassesForFragment").document("class1");
//                            Map<String, Object> map = new HashMap<>();
//                            map.put("className", c1);
//                            batch.set(class1, map);
//                            DocumentReference class2 = rStore.collection("Users").document(userID).collection("MyClassesForFragment").document("class2");
//                            map = new HashMap<>();
//                            map.put("className", c2);
//                            batch.set(class2, map);
//                            DocumentReference class3 = rStore.collection("Users").document(userID).collection("MyClassesForFragment").document("class3");
//                            map = new HashMap<>();
//                            map.put("className", c3);
//                            batch.set(class3, map);
//                            DocumentReference class4 = rStore.collection("Users").document(userID).collection("MyClassesForFragment").document("class4");
//                            map = new HashMap<>();
//                            map.put("className", c4);
//                            batch.set(class4, map);
//                            DocumentReference class5 = rStore.collection("Users").document(userID).collection("MyClassesForFragment").document("class5");
//                            map = new HashMap<>();
//                            map.put("className", c5);
//                            batch.set(class5, map);
//                            DocumentReference class6 = rStore.collection("Users").document(userID).collection("MyClassesForFragment").document("class6");
//                            map = new HashMap<>();
//                            map.put("className", c6);
//                            batch.set(class6, map);
//                            batch.commit();



                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        }else {
                            Toast.makeText(SignUpSocialMedia.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

    }
}