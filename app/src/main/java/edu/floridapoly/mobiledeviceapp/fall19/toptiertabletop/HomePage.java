package edu.floridapoly.mobiledeviceapp.fall19.toptiertabletop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class HomePage extends AppCompatActivity {

    private Button newGameB;
    private Button partyB;
    private Button playerB;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        mAuth = FirebaseAuth.getInstance();

        final Button newGameB = findViewById(R.id.newgameButton);
        final Button newCharB = findViewById(R.id.newCharacterButton);
        final Button partyB = findViewById(R.id.partyButton);
        final Button playerB = findViewById(R.id.playerButton);

        //Handly BButtons

        newGameB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                newGame();
            }

        });
        newCharB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                newChar();
            }

        });

        partyB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openPartyPage();
            }

        });

        playerB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openPlayerPage();
            }

        });



    }

    /*
    This function will display what games the player is in.
     */
    public void gameList()
    {
        //If there is some data
            //Open up the example list of games in the data base
        //Else
            //Empty
    }

    //
    /*
    public void searchGame()
    {
        //When the button is clicked, this moves into the search game
        //
        Intent searchGameIntent = new Intent(this, SearchGame.class);
        startActivity(searchGameIntent);

    }
*/


    private void selectedGame()
    {
        //this function will move the player to the new gamePage
    }

    private void newGame()
    {
        //Moves the user to the game making screen
        Intent myNewGame = new Intent(HomePage.this, NewGame.class);
        startActivity(myNewGame);
    }
    private void newChar()
    {
        //Moves the user to the character making screen
        Intent myNewChar = new Intent(HomePage.this, NewCharacterCreator.class);
        startActivity(myNewChar);
    }

    private void openPlayerPage()
    {
        Intent myPlayerPage = new Intent(HomePage.this,PlayerBookPage.class);
        startActivity(myPlayerPage);
    }

    private void openPartyPage()
    {
        Intent myPartyPage = new Intent(HomePage.this, GamePage.class);
        startActivity(myPartyPage);
    }

    public void logoutUser(View view) {
        mAuth.signOut();
        Intent restart = new Intent(HomePage.this,LoginPage.class);
        startActivity(restart);
        finish();
        return;

    }
}
