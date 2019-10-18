package com.example.dolang.MenuFragment;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dolang.Activity.DetailActivity;
import com.example.dolang.Adapter.NearmeAdapter;
import com.example.dolang.Converter.BaseListResponse;
import com.example.dolang.Model.Tour;
import com.example.dolang.Network.ApiClient;
import com.example.dolang.Network.TourInterface;
import com.example.dolang.R;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment implements OnMapReadyCallback {

    GoogleMap nMap;
    SupportMapFragment mapFragment;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private PlacesClient placesClient;
    private Location mLastKnownLocation;
    private LocationCallback locationCallback;
    private SearchView searcview;
    private MarkerOptions markerOptions = new MarkerOptions();
    private LatLng center, latLng;
    private String tittle;
    private List<Tour> TourList;
    private TourInterface tourInterface;
    private Call<BaseListResponse<Tour>> call;
    private View mapView;
    private Button btnFilter;
    public static final String ID = "id";
    public static String TITLE = "nama";
    public static final String LAT = "lat";
    public static final String LNG = "lng";
    private RecyclerView rv_nearme;
    private final float DEFAULT_ZOOM = 18;


    //e
    private ArrayList<Tour> tours = new ArrayList<>();
    private TourInterface api = ApiClient.getApiInterfaceService();
    private Call<BaseListResponse<Tour>> request;
    HashMap<Marker, Tour> hashMap = new HashMap<>();
    private BottomSheetBehavior bs = new BottomSheetBehavior();
    private Location myLocation;

    public Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        rv_nearme = v.findViewById(R.id.rv_near_me);
        rv_nearme.setLayoutManager(new LinearLayoutManager(getActivity()));
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        Places.initialize(getActivity(), getString(R.string.google_maps_key));
        placesClient = Places.createClient(getActivity());
        mapView = mapFragment.getView();
        View bottomSheet = v.findViewById(R.id.bottomsheet);
        bs = BottomSheetBehavior.from(bottomSheet);
        bs.setHideable(true);
        bs.setPeekHeight(0);
        btnFilter = v.findViewById(R.id.btnFilter);
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bs.getState() == BottomSheetBehavior.STATE_HIDDEN || bs.getState() == BottomSheetBehavior.STATE_COLLAPSED){
                    bs.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
                }else if(bs.getState() == BottomSheetBehavior.STATE_HALF_EXPANDED){
                    bs.setState(BottomSheetBehavior.STATE_EXPANDED);
                }else{

                }
            }
        });


//        searcview = v.findViewById(R.id.searcview);
//        searcview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                String location = searcview.getQuery().toString();
//                List<Address> addressList = null;
//
//                if (location != null || !location.equals("")) {
//                    Geocoder geocoder = new Geocoder(getActivity());
//                    try {
//                        addressList = geocoder.getFromLocationName(location, 1);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    Address address = addressList.get(0);
//                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
//                    nMap.addMarker((new MarkerOptions().position(latLng).title(location)));
//                    nMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
//                }
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });

        mapFragment.getMapAsync(this);


        return v;
    }

//    private void setMapStyle() {
//
//        boolean result =nMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getActivity(), R.raw.maps_style_light));
//        if (result){
//            Log.e("MAP", "error set Map Style");
//        }
//    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        nMap = googleMap;

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        nMap.setMyLocationEnabled(true);
        nMap.getUiSettings().setAllGesturesEnabled(true);
        nMap.getUiSettings().setMyLocationButtonEnabled(true);

//        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.location);


        if (mapView != null && mapView.findViewById(Integer.parseInt("1")) != null) {
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 40, 180);
        }

        //check if gps is enabled or not and then request user to enable it
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

        SettingsClient settingsClient = LocationServices.getSettingsClient(getActivity());
        Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(builder.build());

        task.addOnSuccessListener(getActivity(), new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                getDeviceLocation();
            }
        });

        task.addOnFailureListener(getActivity(), new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    ResolvableApiException resolvable = (ResolvableApiException) e;
                    try {
                        resolvable.startResolutionForResult(getActivity(), 51);
                    } catch (IntentSender.SendIntentException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        nMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
//                    if (materialSearchBar.isSuggestionsVisible())
//                        materialSearchBar.clearSuggestions();
//                    if (materialSearchBar.isSearchEnabled())
//                        materialSearchBar.disableSearch();
                return false;
            }
        });

//        float zoomLevel = 20.0f;
        getAllTours();
        // Latitude Longtitude
//        LatLng pcc = new LatLng(-6.929153, 109.366736);
//        nMap.addMarker(new MarkerOptions().position(pcc).title("").draggable(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));
        // fromResource(R.drawable.rsmap)));
//        nMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pcc, zoomLevel));
//        setMapStyle();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 51) {
            if (resultCode == RESULT_OK) {
                getDeviceLocation();
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void getDeviceLocation() {
        mFusedLocationProviderClient.getLastLocation()
                .addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            mLastKnownLocation = task.getResult();
                            if (mLastKnownLocation != null) {
                                Location l = new Location("MyLocation");
                                l.setLatitude(mLastKnownLocation.getLatitude());
                                l.setLongitude(mLastKnownLocation.getLongitude());
                                myLocation = l;
                                nMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                                System.err.println("Location" + l);
                                populateTour();
                            } else {
                                final LocationRequest locationRequest = LocationRequest.create();
                                locationRequest.setInterval(10000);
                                locationRequest.setFastestInterval(5000);
                                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                                locationCallback = new LocationCallback() {
                                    @Override
                                    public void onLocationResult(LocationResult locationResult) {
                                        super.onLocationResult(locationResult);
                                        if (locationResult == null) {
                                            return;
                                        }
                                        mLastKnownLocation = locationResult.getLastLocation();
                                        nMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                                        mFusedLocationProviderClient.removeLocationUpdates(locationCallback);
                                    }
                                };
                                mFusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);

                            }
                        } else {
                            Toast.makeText(getActivity(), "unable to get last location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void getAllTours(){
        tours.clear();
        request = api.getTour();
        request.enqueue(new Callback<BaseListResponse<Tour>>() {
            @Override
            public void onResponse(Call<BaseListResponse<Tour>> call, Response<BaseListResponse<Tour>> response) {
                if(response.isSuccessful()){
                    BaseListResponse<Tour> body = response.body();
                    if(body != null && body.getStatus()){
                        tours = (ArrayList<Tour>) body.getData();
                        for(final Tour m : tours){
                            LatLng pcc = new LatLng(Double.parseDouble(m.getLatitude()), Double.parseDouble(m.getLongitude()));
                            Marker mr = nMap.addMarker(new MarkerOptions().position(pcc)
                                    .title(m.getName()).snippet(m.getCategory()).draggable(true).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));
                            hashMap.put(mr, m);
                            nMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                                @Override
                                public View getInfoWindow(Marker marker) {
                                    return null;
                                }

                                @Override
                                public View getInfoContents(Marker marker) {
                                    View v = getLayoutInflater().inflate(R.layout.etc_popup_windows_marker, null);
                                    LatLng latLng = marker.getPosition();
                                    TextView tv1 = v.findViewById(R.id.example_1);
                                    TextView tv2 = v.findViewById(R.id.example_2);
                                    LinearLayout item = v.findViewById(R.id.example_root);
                                    String title = marker.getTitle();
                                    String category = marker.getSnippet();
                                    tv1.setText(title);
                                    tv2.setText(category);
                                    return v;
                                }
                            });
                        }

                        LatLng pcc = new LatLng(Double.parseDouble(tours.get(tours.size() - 1).getLatitude()), Double.parseDouble(tours.get(tours.size() - 1).getLongitude()));
//                        nMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pcc, 16.0f));
                        nMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                            @Override
                            public void onInfoWindowClick(Marker marker) {
                                System.err.println("mbuh" + marker);
                                Intent intent = new Intent(getActivity(), DetailActivity.class);
                                intent.putExtra("ID", hashMap.get(marker).getId());
                                startActivity(intent);
                            }
                        });



                    }
                }else{
                    Toast.makeText(getActivity(), "Tidak dapat mengambil response dari serve1", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseListResponse<Tour>> call, Throwable t) {
//                Toast.makeText(getActivity(), "Checking : "+ t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static <K, V> K getKey(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }



    private void populateTour(){
        Call<BaseListResponse<Tour>> t = api.getTour();
        t.enqueue(new Callback<BaseListResponse<Tour>>() {
            @Override
            public void onResponse(Call<BaseListResponse<Tour>> call, Response<BaseListResponse<Tour>> response) {
                if(response.isSuccessful()){
                    BaseListResponse<Tour> body = response.body();
                    if(body != null && body.getStatus()){
                        BaseListResponse<Tour> b = response.body();
                        if(b.getData() != null){
                            List<Tour> ts = b.getData();
                            System.err.println("Before sorting ids");
                            for(Tour xz : ts){
                                System.err.println(xz.getId());
                            }
                            Collections.sort(ts, new Comparator<Tour>() {
                                @Override
                                public int compare(Tour o1, Tour o2) {
                                    DecimalFormat df2 = new DecimalFormat("#.#");
                                    df2.setRoundingMode(RoundingMode.UP);
                                    Location tourLocation = new Location(o1.getName());
                                    tourLocation.setLatitude(Double.parseDouble(o1.getLatitude()));
                                    tourLocation.setLongitude(Double.parseDouble(o1.getLongitude()));
                                    String a1 = df2.format((myLocation.distanceTo(tourLocation) / 1000));
                                    Location tourLocation2 = new Location(o2.getName());
                                    tourLocation2.setLatitude(Double.parseDouble(o2.getLatitude()));
                                    tourLocation2.setLongitude(Double.parseDouble(o2.getLongitude()));
                                    String a2 = df2.format((myLocation.distanceTo(tourLocation2) / 1000));
                                    Double a1d = 0.0;
                                    Double a2d = 0.0;
                                    if(a1.contains(",")){
                                        a1 = a1.replace(",", ".");
                                        a1d = Double.parseDouble(a1);
                                    }else{
                                        a1d = Double.parseDouble(a1);
                                    }
                                    if(a2.contains(",")){
                                        a2 = a2.replace(",", ".");
                                        a2d = Double.parseDouble(a2);

                                    }else{
                                        a2d = Double.parseDouble(a2);
                                    }
                                    return a1d.compareTo(a2d);
                                }
                            });

                            System.err.println("After sorting ids");
                            for(Tour xz : ts){
                                System.err.println(xz.getId());
                            }

                            rv_nearme.setLayoutManager(new LinearLayoutManager(getActivity()));
                            rv_nearme.setAdapter(new NearmeAdapter(ts, getActivity(), myLocation));
                        }
                    }
                    }else{
                    Toast.makeText(getActivity(), "Kesalahan terjadi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseListResponse<Tour>> call, Throwable t) {
                System.err.println("asu"+t.getMessage());
                Toast.makeText(getActivity(), "Tidak dapat mengambil data wisata", Toast.LENGTH_SHORT).show();
            }
        });
    }

}