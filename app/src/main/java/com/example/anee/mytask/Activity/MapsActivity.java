package com.example.anee.mytask.Activity;

import android.Manifest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anee.mytask.R;
import com.example.anee.mytask.models.PlaceInfo;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.example.anee.mytask.models.PlaceAutocompleteAdapter;

import static com.example.anee.mytask.Activity.AddTaskActivity.tasklongitude;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String TAG = "MapsActivity";
    private static final float DEFAULT_ZOOM = 15f;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int PLACE_PICKER_REQUEST = 1;
    SharedPreferences sharedPreferences;


    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(new LatLng(-40, -168), new LatLng(71, 136));

    private AutoCompleteTextView mSearchText;
    private ImageView mGps, mPlcaePicker;
    private Button ok;
    private PlaceInfo mPlaceInfo;

    public AddTaskActivity addTaskActivity = new AddTaskActivity();

    private String tasklocation, taskname;

    int location = -1;
    private Marker mMarker;
    private Boolean mLocationPermissionsGranted = false;
    private static final int LOCATION_PERMISSION_CODE = 1234;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private PlaceAutocompleteAdapter mplaceAutocompleteAdapter;
    private GoogleApiClient mGoogleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        Intent intent = getIntent();
        Log.i("Location Info", Integer.toString(intent.getIntExtra("LocationInfo", -1)));


        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_maps);
        mPlcaePicker = findViewById(R.id.place_picker);
        mSearchText = findViewById(R.id.Edit_text_input_search);
        mGps = findViewById(R.id.gps);
        ok = findViewById(R.id.ok);





//        sharedPreferences = getSharedPreferences("task",0);
//
//        String  taskname = sharedPreferences.getString("taskname","");
//        Log.i("taskname",taskname);
        getLocationPermission();


    }


    private void init() {

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();
        mSearchText.setOnItemClickListener(mAutocompleteClickListener);
        mplaceAutocompleteAdapter = new PlaceAutocompleteAdapter(this,
                mGoogleApiClient, LAT_LNG_BOUNDS, null);
        mSearchText.setAdapter(mplaceAutocompleteAdapter);

        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    geoLocation();
                    closeKeyboard();

                }

                return false;
            }
        });

        mGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Onclick :Clicked gps icon");
                getDeviceLocation();

            }

        });

        mPlcaePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    startActivityForResult(builder.build(MapsActivity.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });
        hideSoftKeyboard();


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this, AddTaskActivity.class);
                // Log.i("location",tasklocation);
                intent.putExtra("location", tasklocation);
                startActivity(intent);
                finish();
            }
        });

    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);

                PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                        .getPlaceById(mGoogleApiClient, place.getId());
                placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void geoLocation() {

        String searchString = mSearchText.getText().toString();
        Geocoder geocoder = new Geocoder(MapsActivity.this);
        List<Address> list = new ArrayList<>();
        try {

            list = geocoder.getFromLocationName(searchString, 1);


        } catch (IOException e) {

        }

        if (list.size() > 0) {
            Address address = list.get(0);
            Log.d(TAG, "geoLocation: found a location:" + address.toString());
            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()),
                    DEFAULT_ZOOM, address.getAddressLine(0));
            closeKeyboard();


        }

    }


    private void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation:getting the  devices current location");
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            if (mLocationPermissionsGranted) {

                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful() && task.getResult() != null) {


                            Log.d(TAG, "onComplete: found location");

                            Location currentLocation = (Location) task.getResult();

                            moveCamera(new LatLng(currentLocation.getLatitude(),
                                            currentLocation.getLongitude()),
                                    DEFAULT_ZOOM, "My Location");
                            hideSoftKeyboard();


                        } else {
                            Log.d(TAG, "onComplete:current location is null");
                            Toast.makeText(MapsActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();


                        }
                    }
                });

            }

        } catch (SecurityException e) {

            Log.e(TAG, "getDeviceLocation:SecurityException:" + e.getMessage());
        }


    }

    private void moveCamera(LatLng latLng, float zoom, PlaceInfo placeInfo) {

        Log.d(TAG, "moveCamera:moving the camera to : lst" + latLng.latitude + ",lng: " + latLng.longitude);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        mMap.clear();


        if (placeInfo != null) {
            try {

                String snippet = "Address" + placeInfo.getAddress() + "\n" +
                        "Latlng" + placeInfo.getLatitude() + placeInfo.getLongitude() + "\n";

                MarkerOptions options = new MarkerOptions()

                        .position(latLng)
                        .title(placeInfo.getAddress())
                        .snippet(snippet);

                tasklocation = placeInfo.getAddress();
                addTaskActivity.tasklatitude = placeInfo.getLatitude();
                addTaskActivity.tasklongitude = placeInfo.getLongitude();


                mMap.addMarker(options);
            } catch (NullPointerException e) {
                Log.e(TAG, "moveCamera:NullPointerException" + e.getMessage());

            }


        } else {
            mMap.addMarker(new MarkerOptions().position(latLng));
        }
    }


    private void moveCamera(LatLng latLng, float zoom, String title) {

        Log.d(TAG, "moveCamera:moving the camera to : lst" + latLng.latitude + ",lng: " + latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        if (!title.equals("My Location")) {
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title(title);

            mMap.addMarker(options);

        }
        closeKeyboard();
    }


    private void initMap() {

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(MapsActivity.this);
    }

    private void getLocationPermission() {

        String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true;
                initMap();


            } else {
                ActivityCompat.requestPermissions(this, permission, LOCATION_PERMISSION_CODE);
            }

        } else {
            ActivityCompat.requestPermissions(this, permission, LOCATION_PERMISSION_CODE);
        }
        hideSoftKeyboard();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionsGranted = false;
        switch (requestCode) {

            case LOCATION_PERMISSION_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {

                        if (grantResults[1] != PackageManager.PERMISSION_GRANTED) {

                            mLocationPermissionsGranted = false;
                            return;

                        }
                    }

                    mLocationPermissionsGranted = true;
                    init();

                }
            }
        }

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
        mMap.setOnMapLongClickListener(this);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (mLocationPermissionsGranted) {

            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            init();


        }


    }

    private void hideSoftKeyboard() {

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    // Google places API autocomplete suggestions......

    private AdapterView.OnItemClickListener mAutocompleteClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            final AutocompletePrediction item = mplaceAutocompleteAdapter.getItem(i);
            final String placeId = item.getPlaceId();
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);

        }
    };

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(@NonNull PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                Log.d(TAG, "Place query did not complete successfully:" + places.getStatus().toString());
                places.release();
                return;
            }
            final Place place = places.get(0);

            try {

                mPlaceInfo = new PlaceInfo();
                mPlaceInfo.setName(place.getName().toString());
                mPlaceInfo.setAddress(place.getAddress().toString());
                mPlaceInfo.setLatitude(place.getLatLng().latitude);
                mPlaceInfo.setId(place.getId());

                Log.d(TAG, "Result:place:" + mPlaceInfo.toString());
            } catch (NullPointerException e) {
                Log.e(TAG, "Result:NullPointerException:" + e.getMessage());

            }
            moveCamera(new LatLng(place.getViewport().getCenter()
                    .latitude, place.getViewport()
                    .getCenter().latitude), DEFAULT_ZOOM, mPlaceInfo);

            places.release();


        }
    };

    @Override
    public void onMapClick(LatLng point) {
        if (mMarker.equals(mMarker)) {


        }
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:


//                String  taskname = sharedPreferences.getString("taskname","");
//                Log.i("taskname",taskname);
                Intent intent = new Intent(MapsActivity.this, AddTaskActivity.class);
                // Log.i("location",tasklocation);
                intent.putExtra("location", tasklocation);
                startActivity(intent);
                finish();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onMapLongClick(LatLng latLng) {
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        try {
            List<Address> addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addressList != null && addressList.size() > 0) {

                tasklocation = addressList.get(0).getAddressLine(0).toString();


                addTaskActivity.tasklatitude = latLng.latitude;
                tasklongitude = latLng.longitude;


            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (mMap != null) {
            mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        }

    }


}