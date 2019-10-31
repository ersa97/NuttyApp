package com.example.nuttyapp.ui.setting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nuttyapp.R;
import com.example.nuttyapp.ui.login.LoginActivity;
import com.example.nuttyapp.ui.main.activity.MainActivity;
import com.example.nuttyapp.ui.welcome.WelcomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AccountSetting extends AppCompatActivity {

    TextView textViewEmail;
    Button buttonLogOut;
    TextView TextReset;
    FirebaseUser user;
    FirebaseAuth firebaseAuth;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);

        user = FirebaseAuth.getInstance().getCurrentUser();

        textViewEmail = findViewById(R.id.emailSekarang);
        buttonLogOut = findViewById(R.id.Button_Logout);
        TextReset = findViewById(R.id.Button_reset_password);

        email = user.getEmail();
        textViewEmail.setText(email);

        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.getInstance().signOut();
                Intent intent = new Intent(AccountSetting.this, LoginActivity.class);
                finish();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        TextReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.sendPasswordResetEmail(String.valueOf(textViewEmail))
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(AccountSetting.this,
                                            "arahan untuk reset password telah dikirim ke email anda",
                                            Toast.LENGTH_SHORT).show();
                                    firebaseAuth.signOut();
                                    Intent intent = new Intent(AccountSetting.this, LoginActivity.class);
                                    finish();
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(AccountSetting.this, "gagal untuk menreset password", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
