package edu.floridapoly.mobiledeviceapp.fall19.toptiertabletop;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;

public class SearchForParty extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private String userType;
    private String notUserType;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;
    private ArrayList<String> al;
    private ArrayAdapter<String> arrayAdapter;
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private int i;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_game);
        ButterKnife.inject(this);
        mAuth = FirebaseAuth.getInstance();
        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            @Override

            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        };

        al = new ArrayList<>();
        getPartyDB();

        arrayAdapter = new ArrayAdapter<>(this, R.layout.item, R.id.helloText, al );

        SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);

        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                al.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
                Toast.makeText(SearchForParty.this, "Left!",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Toast.makeText(SearchForParty.this, "right!",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
            }

            @Override
            public void onScroll(float scrollProgressPercent) {}
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                Toast.makeText(SearchForParty.this, "Click!",Toast.LENGTH_SHORT).show();
            }
        });

    }



    public void getPartyDB(){

        DatabaseReference partyDB = FirebaseDatabase.getInstance().getReference().child("Searching for a Player");
        partyDB.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists() ){
                    String key = dataSnapshot.getKey();
                    if(key != user.getUid())
                    {
                        String adderval = dataSnapshot.child("Party name").getValue().toString();
                        al.add(adderval);
                        arrayAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
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
