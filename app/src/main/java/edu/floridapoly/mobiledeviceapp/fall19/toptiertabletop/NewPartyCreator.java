package edu.floridapoly.mobiledeviceapp.fall19.toptiertabletop;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class NewPartyCreator extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private double lat;
    private double longi;

    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;
    private static final String TAG = "LocationMapSimple";
    private static final int REQUEST_ERROR = 0;

    private static final String[] LOCATION_PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
    };
    private static final int REQUEST_LOCATION_PERMISSIONS = 0;

    private GoogleApiClient mClient;

    private FusedLocationProviderClient fusedLocationClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        mAuth = FirebaseAuth.getInstance();

        mClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(@Nullable Bundle bundle) {
                    }

                    @Override
                    public void onConnectionSuspended(int i) {

                    }
                })
                .build();

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
                DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference().child("Searching for a Player").child(userId).child("Party name");
                currentUserDb.setValue(charName);
                currentUserDb = FirebaseDatabase.getInstance().getReference().child("Searching for a Player").child(userId).child("Party story");
                currentUserDb.setValue(charStory);
                currentUserDb = FirebaseDatabase.getInstance().getReference().child("Searching for a Player").child(userId).child("Party lat");
                currentUserDb.setValue(lat);
                currentUserDb = FirebaseDatabase.getInstance().getReference().child("Searching for a Player").child(userId).child("Party long");
                currentUserDb.setValue(longi);
                //Profile pic here


                Intent goToHomePage = new Intent(NewPartyCreator.this, HomePage.class);
                startActivity(goToHomePage);
            }
        });

        GetLocationSetup();
    }


    @Override
    protected void onResume() {
        super.onResume();

        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int errorCode = apiAvailability.isGooglePlayServicesAvailable(this);

        if (errorCode != ConnectionResult.SUCCESS) {
            Dialog errorDialog = apiAvailability.getErrorDialog(this,
                    errorCode,
                    REQUEST_ERROR,
                    new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialogInterface) {
                            // Leave if services are unavailable.
                            finish();
                        }
                    });

            errorDialog.show();
        }
    }

    public void GetLocationSetup() {
        if (hasLocationPermission()) {
            getLocation();
        } else {
            requestPermissions(LOCATION_PERMISSIONS,
                    REQUEST_LOCATION_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSIONS:
                if (hasLocationPermission()) {
                    getLocation();
                }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void getLocation() {
        LocationRequest request = LocationRequest.create();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setNumUpdates(1);
        request.setInterval(0);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getBaseContext());
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // GPS location can be null if GPS is switched off
                        if (location != null) {
                            Log.i(TAG, "Got a fix: " + location);
                            Toast toast = Toast.makeText(getBaseContext(),"Got a fix: " + location,Toast.LENGTH_SHORT);
                            toast.show();

                            lat =location.getLatitude();
                            longi = location.getLongitude();

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("MapDemoActivity", "Error trying to get last GPS location");
                        e.printStackTrace();
                    }
                });

    }

    private boolean hasLocationPermission() {
        int result = ContextCompat
                .checkSelfPermission(this, LOCATION_PERMISSIONS[0]);
        return result == PackageManager.PERMISSION_GRANTED;
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
        mClient.disconnect();
    }



}
