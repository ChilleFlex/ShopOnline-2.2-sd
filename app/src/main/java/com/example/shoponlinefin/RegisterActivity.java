package com.example.shoponlinefin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class RegisterActivity extends AppCompatActivity {

    private Button registerBtn;
    private EditText nameUserReg,phoneUserReg,passUserReg;


    private TextView registered;
    private ProgressDialog loadingBar;
    //String registrationSuccessful = "Registration successful" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.setTitle("Register");



        registerBtn = (Button)findViewById(R.id.registerButton);
        nameUserReg = (EditText)findViewById(R.id.nameRegistration);
        phoneUserReg = (EditText)findViewById(R.id.phoneRegistration);
        passUserReg = (EditText)findViewById(R.id.passRegistration);
        registered = (TextView)findViewById(R.id.alreadyRegisteredtext);
        loadingBar = new ProgressDialog(this);


        //GOTO CHOOSEPRODUCT ACTIVITY WHEN REGISTRATION IS COMPLETE AND CREATE A TOAST
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),registrationSuccessful.toString(),Toast.LENGTH_SHORT).show();
                String nameReg = nameUserReg.getText().toString();
                String phoneReg = phoneUserReg.getText().toString().trim();
                String passReg = passUserReg.getText().toString().trim();

                if (TextUtils.isEmpty(nameReg))
                {
                    Toast.makeText(getApplicationContext(),"Please enter your name...",Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(phoneReg))
                {
                    Toast.makeText(getApplicationContext(),"Please enter your phone number...",Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(passReg))
                {
                    Toast.makeText(getApplicationContext(),"Please enter your password...",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    loadingBar.setTitle("Create Account");
                    loadingBar.setMessage("Please wait, while we are checking the credentials.");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();

                    ValidatePhoneNumber(nameReg,phoneReg,passReg);
            }
            }

            private void ValidatePhoneNumber(final String nameReg, final String phoneReg, final String passReg) {
                final DatabaseReference RootRef;
                RootRef = FirebaseDatabase.getInstance().getReference();

                RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        if (!(dataSnapshot.child("Users").child(phoneReg).exists()))
                        {
                            HashMap<String, Object> userdataMap = new HashMap<>();
                            userdataMap.put("phone", phoneReg);
                            userdataMap.put("password", passReg);
                            userdataMap.put("name", nameReg);

                            ((DatabaseReference) RootRef).child("Users").child(phoneReg).updateChildren(userdataMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task)
                                        {
                                            if (task.isSuccessful())
                                            {
                                                Toast.makeText(RegisterActivity.this, "Congratulations, your account has been created.", Toast.LENGTH_SHORT).show();
                                                loadingBar.dismiss();

                                                Intent intent = new Intent(RegisterActivity.this, ChooseProductActivity.class);
                                                startActivity(intent);
                                            }
                                            else
                                            {
                                                loadingBar.dismiss();
                                                Toast.makeText(RegisterActivity.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                        else
                        {
                            Toast.makeText(RegisterActivity.this, "This " + phoneReg + " already exists.", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                            Toast.makeText(RegisterActivity.this, "Please try again using another phone number.", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        registered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });



    }
}
