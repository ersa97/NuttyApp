package com.example.nuttyapp.ui.signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nuttyapp.R;
import com.example.nuttyapp.ui.login.LoginActivity;
import com.example.nuttyapp.ui.main.activity.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {

    TextInputEditText editTextEmail, editTextPassword;
    TextView Login_goto;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    Button sign_up;
    private ConstraintLayout constraintloading;
    FirebaseUser user;

    String email;
    String password;

    private boolean doubleBackPressed;

    private Handler handler = new Handler();

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            doubleBackPressed = false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.SignUp_email);
        editTextPassword = findViewById(R.id.SignUp_password);


        sign_up = findViewById(R.id.SignUp_button);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authSignup();
            }
        });

        Login_goto = findViewById(R.id.Login_goto);
        Login_goto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }


    public static Intent newIntent(Context context){
        return new Intent(context, SignUpActivity.class);
    }


    private void authSignup() {
        email = editTextEmail.getText().toString();
        password = editTextPassword.getText().toString();
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d("success", "createUserWithEmail:success");
                    user = mAuth.getCurrentUser();
                    movetoLogin();
                } else {
                    Log.w("Failed", "signUpWithEmail:failure", task.getException());
                    Toast.makeText(SignUpActivity.this, "Authentication failed. " +
                                    "password must be at least 6 character long",
                            Toast.LENGTH_LONG).show();

                }
            }
        });
    }


    private void movetoLogin() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
}

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()!=null){
            moveToMain();
        }

    }

    private void moveToMain(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (handler != null){
            handler.removeCallbacks(runnable);
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackPressed) {
            super.onBackPressed();
            return;
        }

        this.doubleBackPressed = true;
        Toast.makeText(this, "please click again to exit app", Toast.LENGTH_SHORT).show();

        handler.postDelayed(runnable, 2000);
    }


}
