package com.example.shoponlinefin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shoponlinefin.Model.Users;
import com.example.shoponlinefin.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {

    private EditText phoneLoginUser,passLoginUser;
    private Button loginBtn;
    private TextView iAmAdmin,iAmNotAdmin,registerNow;
    private CheckBox rememberMeChkbox;
    private ProgressDialog loadingBar;
    private String parentDbName = "Users";
    String toast = "Invalid user input";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.setTitle("Login");

        phoneLoginUser= (EditText)findViewById(R.id.phoneLogin);
        passLoginUser = (EditText) findViewById(R.id.passLogin);
        loginBtn = (Button)findViewById(R.id.loginButton);
        iAmAdmin = (TextView)findViewById(R.id.iAmAdmin);
        iAmNotAdmin = (TextView)findViewById(R.id.iAmNotAdmin);
        rememberMeChkbox = (CheckBox)findViewById(R.id.rememberMeLogin);
        loadingBar = new ProgressDialog(this);
        registerNow = (TextView)findViewById(R.id.registerNow);

        registerNow.setPaintFlags(registerNow.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);  //UNDERLINING THE REGISTERNOW TEXTVIEW

        Paper.init(this);

        //inputUserPhone = enterPhoneUser.getText().toString();
        //inputUserPass = enterPassUser.getText().toString();

        //GO FROM LOGIN TO CHOOSEPRODUCT ACTIVITY
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    loginUser();

            }
        });

        //GO FROM LOGIN TO REGISTER ACTIVITY IF NOT REGISTERED
        registerNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });

        //GO TO ADMIN LOGIN
        iAmAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginBtn.setText("Login Admin");
                iAmAdmin.setVisibility(View.INVISIBLE);
                iAmNotAdmin.setVisibility(View.VISIBLE);
                registerNow.setVisibility(View.INVISIBLE);
                parentDbName = "Admins";
            }
        });
        iAmNotAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginBtn.setText("Login");
                iAmAdmin.setVisibility(View.VISIBLE);
                iAmNotAdmin.setVisibility(View.INVISIBLE);
                registerNow.setVisibility(View.VISIBLE);
                parentDbName = "Users";
            }
        });

    }

    private void loginUser() {
        String phone = phoneLoginUser.getText().toString();
        String pass = passLoginUser.getText().toString();

        if (TextUtils.isEmpty(phone))
        {
            Toast.makeText(getApplicationContext(),"Please enter your phone number...",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(pass))
        {
            Toast.makeText(getApplicationContext(),"Please enter your password...",Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            
            allowAccessToAccount(phone,pass);
        }
    }

    private void allowAccessToAccount(final String phone, final String pass) {

        if(rememberMeChkbox.isChecked())
        {
            Paper.book().write(Prevalent.UserPhoneKey, phone);
            Paper.book().write(Prevalent.UserPasswordKey, pass);
        }

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(parentDbName).child(phone).exists())
                {
                    Users usersData = dataSnapshot.child(parentDbName).child(phone).getValue(Users.class);

                    if (usersData.getPhone().equals(phone))
                    {
                        if (usersData.getPassword().equals(pass))
                        {
                            if (parentDbName.equals("Admins"))
                            {
                                Toast.makeText(LoginActivity.this, "Welcome Admin, you are logged in Successfully...", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                                startActivity(intent);
                            }
                            else if (parentDbName.equals("Users"))
                            {
                                Toast.makeText(LoginActivity.this, "logged in Successfully...", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                Prevalent.currentOnlineUser = usersData;
                                startActivity(intent);
                            }
                        }
                        else
                        {
                            loadingBar.dismiss();
                            Toast.makeText(LoginActivity.this, "Password is incorrect.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Account with this " + phone + " number do not exists.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
