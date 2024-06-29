package com.example.media;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {

    EditText enternumber;
    Button getotp;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        enternumber = findViewById(R.id.input_mobile_number);
        getotp = findViewById(R.id.buttongetotp);

        final ProgressBar progressBar = findViewById(R.id.progressbarcircle);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!getotp.getText().toString().trim().isEmpty()) {
                    if ((enternumber.getText().toString().trim()).length() == 10) {

                        progressBar.setVisibility(view.VISIBLE);
                        getotp.setVisibility(view.INVISIBLE);

                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                "+91" + enternumber.getText().toString(), 60,
                                TimeUnit.SECONDS, LoginActivity.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                        progressBar.setVisibility(view.VISIBLE);
                                        getotp.setVisibility(view.INVISIBLE);
                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                        progressBar.setVisibility(view.VISIBLE);
                                        getotp.setVisibility(view.INVISIBLE);
                                        Toast.makeText(LoginActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();

                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String backendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        super.onCodeSent(backendotp, forceResendingToken);
                                        progressBar.setVisibility(view.VISIBLE);
                                        getotp.setVisibility(view.INVISIBLE);
                                        Intent intent = new Intent(getApplicationContext(), OtpVerification.class);
                                        intent.putExtra("mobile", enternumber.getText().toString());
                                        intent.putExtra("backendotp", backendotp);
                                        startActivity(intent);
                                    }
                                });

                    } else {
                        Toast.makeText(LoginActivity.this, "Please enter Correct number", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(LoginActivity.this, "Enter Mobile Number", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}