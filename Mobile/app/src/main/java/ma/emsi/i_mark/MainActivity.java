package ma.emsi.i_mark;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MainActivity extends AppCompatActivity {

    String insertUrl = "https://i-mark.herokuapp.com/images";

    private Button confirm, open;
    private SimpleDrawingView img;
    Drawable d;

    private double latitude, longitude;

    String height, width, tag;

    String encodeImageString;

    private String[] PERMISSIONS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PERMISSIONS = new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.CAMERA
        };

        if (!hasPermissions(MainActivity.this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS, 1);
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            startActivityForResult(intent, 100);
        } else {
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            startActivityForResult(intent, 100);
        }

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 150, new
                LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                    }
                });

        confirm = findViewById(R.id.confirm);
        img = findViewById(R.id.image);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder annotator = new AlertDialog.Builder(MainActivity.this);
                annotator.setTitle("Saisir l'annotation !");

                final EditText annotation = new EditText(MainActivity.this);
                annotator.setView(annotation);

                annotator.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        tag = annotation.getText().toString();
                        Date currentTime = Calendar.getInstance().getTime();
                        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());
                        Log.d("width", width);
                        Log.d("height", height);
                        Log.d("date_captured", currentTime.toString());
                        Log.d("latitude", latitude + "");
                        Log.d("longitude", longitude + "");
                        Log.d("annotation", tag);
                        Log.d("bbox", img.getBbox().toString());
                        Log.d("image", encodeImageString);
                        Toast.makeText(MainActivity.this, "width : " + width + "\nheight : " + height + "\ndate_captured : " + dateFormat.format(currentTime) + "\nlatitude : " + latitude + "\nlongitude : " + longitude + "\nannotation : " + tag + "\nbbox : \n" + img.getBbox(), Toast.LENGTH_LONG).show();

                        JSONObject image = new JSONObject();
                        JSONObject gps_location = new JSONObject();
                        JSONObject object = new JSONObject();
                        JSONArray objects = new JSONArray();
                        ArrayList<JSONObject> objectsArray = new ArrayList();

                        try {
                            image.put("file_url", tag);
                            image.put("width", width);
                            image.put("height", height);

                            gps_location.put("latitude", latitude);
                            gps_location.put("longitude", longitude);

                            image.put("gps_location", gps_location);

                            object.put("annotation",tag);
                            object.put("bbox",img.getBbox().toString());

                            objectsArray.add(object);

                            image.put("objects", objectsArray);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.d("JSON OBJECT",image.toString());

                    }
                });
                annotator.show();
            }
        });
    }

    void addAnnotation(JSONObject img) {
         // Make request for JSONObject

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, insertUrl, img,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("MainActivity", response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("MainActivity", error.toString());
            }
        }) {
            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        // Adding request to request queue
        Volley.newRequestQueue(this).add(jsonObjReq);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap photo = (Bitmap) data.getExtras().get("data");
        d = new BitmapDrawable(getResources(), photo);
        img.setBackground(d);
        height = photo.getHeight() + "";
        width = photo.getWidth() + "";
        encodeBitmapImage(photo);
    }

    private boolean hasPermissions(Context context, String... PERMISSIONS) {
        if (context != null && PERMISSIONS != null) {
            for (String permission : PERMISSIONS) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "GPS Permission is granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "GPS Permission is denied", Toast.LENGTH_SHORT).show();
            }

            if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "GPS Permission is granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "GPS Permission is denied", Toast.LENGTH_SHORT).show();
            }

            if (grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Camera Permission is granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Camera Permission is denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void encodeBitmapImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] bytesofimage = byteArrayOutputStream.toByteArray();
        encodeImageString = android.util.Base64.encodeToString(bytesofimage, Base64.DEFAULT);
    }
}