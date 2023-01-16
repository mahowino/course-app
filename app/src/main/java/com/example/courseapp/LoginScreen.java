package com.example.courseapp;

import static com.example.courseapp.Helpers.UserHelper.logInUser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.courseapp.Interfaces.onResult;
import com.example.courseapp.Model.User;
import com.example.courseapp.Utils.LoadingDialog;

public class LoginScreen extends AppCompatActivity {
    EditText memail,mpassword;
    Button  login_button;
    TextView signUpRedirect;
    LoadingDialog loadingDialog;
    ImageView passwordVisibility;

    private static boolean isPasswordVisible=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        initializeVariables();
        setListeners();


    }
    private void initializeVariables() {
        loadingDialog=new LoadingDialog(this);

        memail = findViewById(R.id.email_login_edittext);
        mpassword = findViewById(R.id.password_edittext_login);

        passwordVisibility=findViewById(R.id.imageView_password_login);
        login_button=findViewById(R.id.login_button);
        signUpRedirect=findViewById(R.id.txtRedirectSignUp);


    }



    private void setListeners() {
        signUpRedirect.setOnClickListener(view -> {
            Intent intent=new Intent(LoginScreen.this, SignUpActivity.class);
            startActivity(intent);
        });

        login_button.setOnClickListener(v -> logIn());

        //onclick listeners
        passwordVisibility.setOnClickListener(v -> {
            if (!isPasswordVisible)
                setPasswordTransformation(
                        null,
                        getResources().getDrawable(R.drawable.password_visible),
                        true);
            else
                setPasswordTransformation(
                        PasswordTransformationMethod.getInstance(),
                        getResources().getDrawable(R.drawable.password_invisible),
                        false);
        });
    }

    private void logIn() {
        loadingDialog.startLoadingAlertDialog();
        User user=getDetails();
        logInUser(user, new onResult() {
            @Override
            public void onSuccess() {
                loadingDialog.dismissDialog();
                Intent intent=new Intent(LoginScreen.this, HomePage.class);
                Toast.makeText(LoginScreen.this, "User logged in succesfully", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }

            @Override
            public void onError(String e) {
                Toast.makeText(getApplicationContext(), e, Toast.LENGTH_SHORT).show();
            }
        });

    }


    private User getDetails() {
        User guard=new User();
        guard.setEmail(memail.getText().toString());
        guard.setPassword(mpassword.getText().toString());

        return guard;
    }

    private void setPasswordTransformation(PasswordTransformationMethod method, Drawable drawable, boolean isPasswordVisible){
        mpassword.setTransformationMethod(method);
        passwordVisibility.setImageDrawable(drawable);
        this.isPasswordVisible=isPasswordVisible;
    }


}