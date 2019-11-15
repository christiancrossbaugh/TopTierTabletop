package edu.floridapoly.mobiledeviceapp.fall19.toptiertabletop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewGame extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_player);
        mAuth = FirebaseAuth.getInstance();




        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user !=null){
                    Intent goToHomePage = new Intent(NewGame.this, HomePage.class);
                    startActivity(goToHomePage);
                    finish();
                    return;
                }
            }
        };



        final EditText charNameEditText = findViewById(R.id.username);
        final EditText charStoryEditText = findViewById(R.id.password);
        final EditText charPictureText = findViewById(R.id.name);

        final Button finishButton = findViewById(R.id.register);
        finishButton.setEnabled(true);

        finishButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){


                final String charName = charNameEditText.getText().toString();
                final String charStory = charStoryEditText.getText().toString();
                final String charPicture = charPictureText.getText().toString();


            }
        });
    }

    public void checkUserMatch(){
        //DatabaseReference dmDB = FirebaseDatabase
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
