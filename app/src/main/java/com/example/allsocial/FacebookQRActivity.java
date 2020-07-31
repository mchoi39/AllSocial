package com.example.allsocial;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class FacebookQRActivity extends AppCompatActivity {
    Button linkToQRbtn;
    ImageView ivFacebookQR;
    EditText etFBLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_q_r);
        etFBLink = (EditText) findViewById(R.id.activity_facebook_et_facebook_link);
        linkToQRbtn = (Button) findViewById(R.id.activity_facebook_btn_facebook_link_to_QR);
        ivFacebookQR = (ImageView) findViewById(R.id.activity_facebook_iv_facebook_QR);
        linkToQRbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String link= etFBLink.getText().toString();
                System.out.println(URLUtil.isValidUrl(link) + "checking if it was valid");
                if (Patterns.WEB_URL.matcher(link.toLowerCase()).matches()) {
                    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                    try {
                        BitMatrix bitMatrix = multiFormatWriter.encode(link, BarcodeFormat.QR_CODE, 250, 250);
                        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                        ivFacebookQR.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        System.out.println("Not working");
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}