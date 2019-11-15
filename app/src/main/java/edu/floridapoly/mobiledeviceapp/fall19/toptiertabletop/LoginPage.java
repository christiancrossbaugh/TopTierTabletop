package edu.floridapoly.mobiledeviceapp.fall19.toptiertabletop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class LoginPage extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    Intent goToHomePage = new Intent(LoginPage.this, HomePage.class);
                    startActivity(goToHomePage);
                    finish();
                    return;
                }
            }
        };


        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        Button loginButton = findViewById(R.id.login);
        Button registerButton = findViewById(R.id.register);
        registerButton.setEnabled(true);
        loginButton.setEnabled(true);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = usernameEditText.getText().toString();
                final String password = passwordEditText.getText().toString();
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginPage.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(LoginPage.this, "sign in error", Toast.LENGTH_SHORT).show();
                        } else {
                        }
                    }
                });
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                openRegistrationPage();
            }
        });
    }

    private void openRegistrationPage(){
        Intent goToRegistration = new Intent(this,NewPlayer.class);
        startActivity(goToRegistration);
        finish();
        return;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthStateListener);
    }
}
