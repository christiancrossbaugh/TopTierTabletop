package edu.floridapoly.mobiledeviceapp.fall19.toptiertabletop;

import  edu.floridapoly.mobiledeviceapp.fall19.toptiertabletop.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.nsd.NsdManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewPlayer extends AppCompatActivity{

private FirebaseAuth mAuth;
private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mAuth = FirebaseAuth.getInstance();
        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user !=null){
                    Intent goToHomePage = new Intent(NewPlayer.this, HomePage.class);
                    startActivity(goToHomePage);
                    finish();
                    return;
                }
            }
        };



        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final EditText nameEditText = findViewById(R.id.name);
        final Button registerButton = findViewById(R.id.register);
        registerButton.setEnabled(true);

        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){


                final String username = usernameEditText.getText().toString();
                final String password = passwordEditText.getText().toString();
                final String name = nameEditText.getText().toString();

                mAuth.createUserWithEmailAndPassword(username,password).addOnCompleteListener(NewPlayer.this,new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task){
                        if(!task.isSuccessful()){
                            Toast.makeText(NewPlayer.this,"sign up error", Toast.LENGTH_SHORT).show();
                        } else {
                            String userId = mAuth.getCurrentUser().getUid();
                            DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("player name");
                            currentUserDb.setValue(name);
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthStateListener);
    }

    @Override
    protected void onStop(){
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthStateListener);
    }

}
