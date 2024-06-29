package com.example.media;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OtpVerification extends AppCompatActivity {

    EditText inputnumber1,inputnumber2,inputnumber3,inputnumber4,inputnumber5,inputnumber6;
    Button verifybutton;
    String getotpbackend;
    String entercodeotp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_otp_verification);

        verifybutton = findViewById(R.id.buttonverifyotp);

         inputnumber1 =findViewById(R.id.inputotp1);
         inputnumber2 =findViewById(R.id.inputotp2);
         inputnumber3 =findViewById(R.id.inputotp3);
         inputnumber4 =findViewById(R.id.inputotp4);
         inputnumber5 =findViewById(R.id.inputotp5);
         inputnumber6 =findViewById(R.id.inputotp6);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView textView = findViewById(R.id.textshowmobilenumber);
        textView.setText(String.format("+91-%s",getIntent().getStringExtra("mobile")));

        getotpbackend = getIntent().getStringExtra("backendotp");
        final ProgressBar progressBarverifyOtp = findViewById(R.id.progressbarcircleotp);
        final Button getverifyotp =findViewById(R.id.buttonverifyotp);


        verifybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!inputnumber1.getText().toString().trim().isEmpty() &&
                        !inputnumber2.getText().toString().trim().isEmpty() &&
                        !inputnumber3.getText().toString().trim().isEmpty() &&
                        !inputnumber4.getText().toString().trim().isEmpty() &&
                        !inputnumber5.getText().toString().trim().isEmpty() &&
                        !inputnumber6.getText().toString().trim().isEmpty()) {
                    entercodeotp = inputnumber1.getText().toString() + inputnumber2.getText().toString() + inputnumber3.getText().toString() + inputnumber4.getText().toString() + inputnumber5.getText().toString() + inputnumber6.getText().toString();
                    if (getotpbackend != null)
                    {
                        progressBarverifyOtp.setVisibility(view.VISIBLE);
                        getverifyotp.setVisibility(view.INVISIBLE);
                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(getotpbackend, entercodeotp);
                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBarverifyOtp.setVisibility(view.GONE);
                                getverifyotp.setVisibility(view.VISIBLE);

                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(OtpVerification.this, "Enter Correct OTP", Toast.LENGTH_LONG).show();
                                }

                            }
                        });
                    }
                    else{
                        Toast.makeText(OtpVerification.this, "Please Check internet Connection", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(OtpVerification.this, "Please Enter all Numbers", Toast.LENGTH_SHORT).show();
                }
            }
        });
        numberotpmove();
        
        TextView resendlabel = findViewById(R.id.textresendotp);
        resendlabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + getIntent().getStringExtra("mobile"), 60,
                        TimeUnit.SECONDS, OtpVerification.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                                Toast.makeText(OtpVerification.this,e.getMessage(),Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCodeSent(@NonNull String newbackendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(newbackendotp, forceResendingToken);
                                getotpbackend = newbackendotp;
                                Toast.makeText(OtpVerification.this,"OTP send Successfully",Toast.LENGTH_LONG).show();


                            }
                        });

            }
        });
    }

    private void numberotpmove() {
        inputnumber1.addTextChangedListener((new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputnumber2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        }));
        inputnumber2.addTextChangedListener((new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputnumber3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        }));
        inputnumber3.addTextChangedListener((new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputnumber4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        }));
        inputnumber4.addTextChangedListener((new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputnumber5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        }));
        inputnumber5.addTextChangedListener((new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputnumber6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        }));
    }

}