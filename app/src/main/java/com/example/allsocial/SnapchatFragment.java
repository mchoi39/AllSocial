package com.example.allsocial;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.HashMap;

public class SnapchatFragment extends Fragment {
    private static final String TAG = FacebookFragment.class.getName();
    Button linkToQRbtn;
    ImageView ivSnapchatQR;
    EditText etSnapchatLink;
    FirebaseAuth mAuth;
    FirebaseFirestore store;
    String userID;
    FirebaseUser mFirebaseUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_snapchat_fragment, container, false);
        mAuth = FirebaseAuth.getInstance();
        store = FirebaseFirestore.getInstance();
        etSnapchatLink = (EditText) view.findViewById(R.id.fragment_snapchat_et_snapchat_link);
        linkToQRbtn = (Button) view.findViewById(R.id.fragment_snapchat_btn_snapchat_link_to_QR);
        ivSnapchatQR = (ImageView) view.findViewById(R.id.fragment_snapchat_iv_snapchat_QR);
        mFirebaseUser = mAuth.getCurrentUser();
        if (mFirebaseUser != null) {
            System.out.println(mFirebaseUser.getUid());
            DocumentReference docRef = store.collection("Users").document(mFirebaseUser.getUid()).collection("MyQRCodes").document("MyQRCodesDoc");
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        assert document != null;
                        if (document.exists()) {
                            String val = document.getString("snapchat");
                            if (val != null && val.length() > 0){
                                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                                try {
                                    BitMatrix bitMatrix = multiFormatWriter.encode(val, BarcodeFormat.QR_CODE, 250, 250);
                                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                                    ivSnapchatQR.setImageBitmap(bitmap);
                                } catch (WriterException e) {
                                    System.out.println("Not working");
                                    e.printStackTrace();
                                }
                            } else {
                                ivSnapchatQR.setImageResource(R.drawable.snapchat_512px);
                            }

                        } else {
                            Log.d(TAG, "No such document");
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });
        }

        linkToQRbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String link= etSnapchatLink.getText().toString();
                System.out.println(URLUtil.isValidUrl(link) + "checking if it was valid");
                if (Patterns.WEB_URL.matcher(link.toLowerCase()).matches()) {
                    DocumentReference ref = store.collection("Users").document(mFirebaseUser.getUid()).collection("MyQRCodes").document("MyQRCodesDoc");
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("snapchat", link.toLowerCase());
                    ref.set(map, SetOptions.merge());
                    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                    try {
                        BitMatrix bitMatrix = multiFormatWriter.encode(link, BarcodeFormat.QR_CODE, 250, 250);
                        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                        ivSnapchatQR.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        System.out.println("Not working");
                        e.printStackTrace();
                    }
                }
            }
        });
        return view;
    }
}