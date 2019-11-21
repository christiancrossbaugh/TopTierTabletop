package edu.floridapoly.mobiledeviceapp.fall19.toptiertabletop;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;

public class SearchForParty extends AppCompatActivity {
    private cards cards_data[];
    private arrayAdapter arrayAdapter;
    private FirebaseAuth mAuth;
    private String userType;
    private String notUserType;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private int i;
    private String currentUId;

    private DatabaseReference usersDb;

    ListView listView;
    List<cards> rowItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_game);
        ButterKnife.inject(this);

        usersDb = FirebaseDatabase.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();
        currentUId = mAuth.getCurrentUser().getUid();

        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            @Override

            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        };

        rowItems = new ArrayList<cards>();
        getPartyDB();

        arrayAdapter = new arrayAdapter(this,R.layout.item,rowItems);

        SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);

        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                rowItems.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {

                cards obj = (cards) dataObject;
                String userId = obj.getUserId();
                String characterName = obj.getName();

                usersDb.child("Searching for a Player").child(userId).child("connections").child("no").child(currentUId).setValue(true);
                Toast.makeText(SearchForParty.this, "Left!",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                cards obj = (cards) dataObject;
                String userId = obj.getUserId();
                String characterName = obj.getName();
                usersDb.child("Searching for a Player").child(userId).child("connections").child("yes").child(currentUId).setValue(true);
                
                isConnectionMatch(userId);
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

    private void isConnectionMatch(String userId) {
        DatabaseReference currentUserConnectionsDb = usersDb.child("Searching for a Player").child(currentUId).child("connections").child("yes").child(userId);
        currentUserConnectionsDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Toast.makeText(SearchForParty.this,"New connection",Toast.LENGTH_LONG).show();
                    usersDb.child("Searching for a Party").child(dataSnapshot.getKey()).child("connections").child("matches").child(currentUId).setValue(true);
                    usersDb.child("Searching for a Player").child(currentUId).child("connections").child("matches").child(dataSnapshot.getKey()).setValue(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


    public void getPartyDB(){

        DatabaseReference partyDB = FirebaseDatabase.getInstance().getReference().child("Searching for a Player");
        partyDB.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists() && !dataSnapshot.child("connections").child("no").hasChild(currentUId)&& !dataSnapshot.child("connections").child("yes").hasChild(currentUId) ){
                    String key = dataSnapshot.getKey();
                    if(key != user.getUid())
                    {
                        cards item = new cards(dataSnapshot.getKey(),dataSnapshot.child("Party name").getValue().toString());

                        rowItems.add(item);
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
