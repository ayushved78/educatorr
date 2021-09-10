package com.example.education_app;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
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

public class reg extends AppCompatActivity {
    EditText u_name, u_pass, u_repass, u_contact;
    Button signup,upload;
    ImageView img;
    Uri filepath;
    Bitmap bitmap;
    dbhelp DB;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        u_name = (EditText) findViewById(R.id.fn);
        u_pass = (EditText) findViewById(R.id.email);
        u_repass = (EditText) findViewById(R.id.pass);
        u_contact = (EditText) findViewById(R.id.contact);
        img = (ImageView) findViewById(R.id.img);
        upload = (Button) findViewById(R.id.browse);
        signup = (Button) findViewById(R.id.signup);
        DB = new dbhelp(this);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(reg.this)
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
                String pass = u_pass.getText().toString();
                String repass = u_repass.getText().toString();
                String phn = u_contact.getText().toString();

                if(user.equals("") || pass.equals("") || repass.equals("") || phn.equals(""))
                    Toast.makeText(reg.this,"All Fields Required *",Toast.LENGTH_SHORT);
                else{
                    if (pass.equals(repass)){
                        Boolean checkuser = DB.checkUsername(user);
                        if (checkuser==false){
                            Boolean insert = DB.insertData(user,pass);
                            if(insert==true){
                                Toast.makeText(reg.this,"Successfully Registered!!",Toast.LENGTH_SHORT);
                                Intent intent = new Intent(getApplicationContext(),HomeClass.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(reg.this,"FAILED!",Toast.LENGTH_SHORT);
                            }
                        }else{
                            Toast.makeText(reg.this,"User Already Exist!",Toast.LENGTH_SHORT);
                        }
                    }else{
                        Toast.makeText(reg.this,"Username and Password Cannot be same!",Toast.LENGTH_SHORT);
                    }
                }
            }
        });
    }
}
