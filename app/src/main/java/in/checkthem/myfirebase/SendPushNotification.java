package in.checkthem.myfirebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SendPushNotification extends AppCompatActivity implements View.OnClickListener{

    private Button buttonSendPush, getNotifButton, getLocation;

    private ProgressDialog progressDialog;

    public EditText editTextTitle, editTextMessage, editTextImage,editTextVehicle;
    private static final int PLACE_PICKER_REQUEST = 1000;
    private GoogleApiClient mClient;
    public TextView textView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_push_notification);

        //radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        //spinner = (Spinner) findViewById(R.id.spinnerDevices);
        textView=(TextView)findViewById(R.id.textView);
        buttonSendPush = (Button) findViewById(R.id.buttonSendPush);
        getNotifButton=(Button)findViewById(R.id.getNotifButton);
        editTextTitle = (EditText) findViewById(R.id.editTextTitle);
        editTextMessage = (EditText) findViewById(R.id.editTextMessage);
        editTextImage = (EditText) findViewById(R.id.editTextImageUrl);
        editTextVehicle=(EditText)findViewById(R.id.editTextvehicle);
        getLocation=(Button)findViewById(R.id.getLocationPickerButton);
        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(SendPushNotification.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });
        buttonSendPush.setOnClickListener(this);
        getNotifButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SendPushNotification.this, ShowNotifFromFirebase.class);
                startActivity(intent);
            }
        });
        mClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();

    }


    @Override
    protected void onStart() {
        super.onStart();
        mClient.connect();
    }

    @Override
    protected void onStop() {
        mClient.disconnect();
        super.onStop();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                StringBuilder stBuilder = new StringBuilder();
                String placename = String.format("%s", place.getName());
                String latitude = String.valueOf(place.getLatLng().latitude);
                String longitude = String.valueOf(place.getLatLng().longitude);
                String address = String.format("%s", place.getAddress());
                stBuilder.append("Name: ");
                stBuilder.append(placename);
                stBuilder.append("\n");
                stBuilder.append("Latitude: ");
                stBuilder.append(latitude);
                stBuilder.append("\n");
                stBuilder.append("Logitude: ");
                stBuilder.append(longitude);
                stBuilder.append("\n");
                stBuilder.append("Address: ");
                stBuilder.append(address);
                textView.setText(stBuilder.toString());
            }
        }
    }

    private void sendSinglePush() {
        final String title = editTextTitle.getText().toString();
        final String message = editTextMessage.getText().toString();
        final String image = editTextImage.getText().toString();
        final String vehicle=editTextVehicle.getText().toString();
        //final String email = spinner.getSelectedItem().toString();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sending Push");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, EndPoints.URL_SEND_SINGLE_PUSH,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        Toast.makeText(SendPushNotification.this, response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("title", title);
                params.put("message", message);
                params.put("vehicle_number",vehicle);
                if (!TextUtils.isEmpty(image))
                    params.put("image", image);
                return params;
            }
        };


        MyVolley.getInstance(this).addToRequestQueue(stringRequest);
        sendToFirebaseDb();
    }
    private void sendToFirebaseDb(){
        //fdb references


        ChildEventListener childEventListener;
        //data
        final String title = editTextTitle.getText().toString();
        final String message = editTextMessage.getText().toString();
        final String vehicle=editTextVehicle.getText().toString();
        FirebaseDatabase firebaseDatabase1;
        DatabaseReference databaseReference1;
        final String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        final String v = SharedPrefManager.getInstance(this).getVehicleNumber();
        final String status="un resolved";
        final String TO="TO";
        final String complaints="complaints";

        final String phone = SharedPrefManager.getInstance(this).getPhoneNumber();

        FirebaseDatabase firebaseDatabase2;
        DatabaseReference databaseReference2;
        //Sending Data to Fdb
        firebaseDatabase2=FirebaseDatabase.getInstance();
        databaseReference2=firebaseDatabase2.getReference().child(""+vehicle).child("complaints").push();
        databaseReference2.child("title").setValue(title);
        databaseReference2.child("message").setValue(message);
        databaseReference2.child("status").setValue(status);
        databaseReference2.child("time").setValue(currentDateTimeString);
        databaseReference2.child("sender").setValue(phone);
        //String key=databaseReference2.getKey();
        //databaseReference2=firebaseDatabase2.getReference().child(""+v);
        //databaseReference2.child(complaints).child("to").setValue(key);



    }
    public void getLocation(){

    }




    @Override
    public void onClick(View view) {
        //calling the method send push on button click
        sendSinglePush();
    }


}
