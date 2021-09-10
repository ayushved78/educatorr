package com.example.education_app;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.InputStream;

public class relog extends AppCompatActivity {
    EditText u_name, u_email, u_pass, u_contact;
    Button signup,upload;
    ImageView img;
    Uri filepath;
    Bitmap bitmap;
    DBHelper db;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        u_name = (EditText) findViewById(R.id.fn);
        u_email = (EditText) findViewById(R.id.email);
        u_pass = (EditText) findViewById(R.id.pass);
        u_contact = (EditText) findViewById(R.id.contact);
        img = (ImageView) findViewById(R.id.img);
        upload = (Button) findViewById(R.id.browse);
        signup = (Button) findViewById(R.id.signup);
        db = new DBHelper(this);
                //uploads of img
                upload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Dexter.withActivity(relog.this)
                                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                                .withListener(new PermissionListener() {
                                    @Override
                                    public void onPermissionGranted(PermissionGrantedResponse response) {
                                        Intent intent=new Intent(Intent.ACTION_PICK);
                                        intent.setType("image/*");
                                        startActivityForResult(Intent.createChooser(intent,"Select Image File"),1);

                                    }

                                    @Override
                                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                                    }

                                    @Override
                                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken token) {
                                        token.continuePermissionRequest();

                                    }
                                }).check();
                    } //onClick ends here
                });

                //signup DB
                signup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String user = u_name.getText().toString();
                        String email = u_email.getText().toString();
                        String pass = u_pass.getText().toString();
                        String phn = u_contact.getText().toString();

                        if(user.equals("") || pass.equals("") || email.equals("") || phn.equals(""))
                            Toast.makeText(relog.this,"All Fields Required *",Toast.LENGTH_SHORT);
                        else{
                            if (pass.equals(pass)){
                                Boolean checkuser = db.checkUsername(user);
                                if (checkuser==false){
                                    Boolean insert = db.insertData(user,email,pass,phn);
                                    if(insert==true){
                                        Toast.makeText(relog.this,"Successfully Registered!!",Toast.LENGTH_SHORT);
                                        Intent intent = new Intent(getApplicationContext(),HomeClass.class);
                                        startActivity(intent);
                                    }else{
                                        Toast.makeText(relog.this,"FAILED!",Toast.LENGTH_SHORT);
                                    }
                                }else{
                                    Toast.makeText(relog.this,"User Already Exist!",Toast.LENGTH_SHORT);
                                }
                            }else{
                                Toast.makeText(relog.this,"Username and Password Cannot be same!",Toast.LENGTH_SHORT);
                            }
                        }
                    }
                });

                //firebase here
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode==1 && resultCode==RESULT_OK) {
            filepath=data.getData();
            try {
                InputStream inputstream = getContentResolver().openInputStream(filepath);
                bitmap = BitmapFactory.decodeStream(inputstream);
                img.setImageBitmap(bitmap);
            }catch (Exception ex) {
                System.out.println(ex);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
