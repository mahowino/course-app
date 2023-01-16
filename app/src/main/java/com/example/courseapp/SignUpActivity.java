package com.example.courseapp;

import static com.example.courseapp.Helpers.UserHelper.logInUser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.courseapp.Helpers.UserHelper;
import com.example.courseapp.Interfaces.onResult;
import com.example.courseapp.Model.User;
import com.example.courseapp.Utils.LoadingDialog;

public class SignUpActivity extends AppCompatActivity {
    EditText memail,mpassword;
    Button signup_btn;
    TextView loginRedirect;
    //FirebaseAuth fAuth;
    LoadingDialog loadingDialog;
    ImageView passwordVisibility;

    private static boolean isPasswordVisible=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initializeVariables();
        setListeners();

    }
    private void initializeVariables() {
        loadingDialog=new LoadingDialog(this);

        memail = findViewById(R.id.email_login_edittextSignUp);
        mpassword = findViewById(R.id.password_edittext_SignUp);
        passwordVisibility=findViewById(R.id.imageView_password_SignUp);
        signup_btn=findViewById(R.id.signup_button);
        loginRedirect=findViewById(R.id.txtAlreadyAccountLogIn);

    }



    private void setListeners() {


        signup_btn.setOnClickListener(v -> logIn());

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

        loginRedirect.setOnClickListener(view -> {
            Intent intent=new Intent(SignUpActivity.this, LoginScreen.class);
            startActivity(intent);
        });
    }

    private void logIn() {
        loadingDialog.startLoadingAlertDialog();
        User user=getDetails();
        UserHelper.createNewUser(user, new onResult() {
            @Override
            public void onSuccess() {

                loadingDialog.dismissDialog();
                Toast.makeText(SignUpActivity.this, "User created succesfully", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(SignUpActivity.this, HomePage.class);
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