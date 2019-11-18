package edu.floridapoly.mobiledeviceapp.fall19.toptiertabletop;

import android.content.Intent;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class NewCharacterCreator extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_player);
        mAuth = FirebaseAuth.getInstance();
        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            @Override

            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        };



        final EditText charNameEditText = findViewById(R.id.charNameText);
        final EditText charStoryEditText = findViewById(R.id.storyText);
        //final EditText charPictureText = findViewById(R.id.name);

        final Button finishButton = findViewById(R.id.immortalize);
        finishButton.setEnabled(true);

        finishButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){


                final String charName = charNameEditText.getText().toString();
                final String charStory = charStoryEditText.getText().toString();
                //final String charPicture = charPictureText.getText().toString();


                String userId = mAuth.getCurrentUser().getUid();
                DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference().child("Searching for a Party").child(userId).child("character name");
                currentUserDb.setValue(charName);
                currentUserDb = FirebaseDatabase.getInstance().getReference().child("Searching for a Party").child(userId).child("character story");
                currentUserDb.setValue(charStory);
                //Profile pic here


                Intent goToHomePage = new Intent(NewCharacterCreator.this, HomePage.class);
                startActivity(goToHomePage);
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
