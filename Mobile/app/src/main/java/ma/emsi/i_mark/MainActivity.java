package ma.emsi.i_mark;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
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
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MainActivity extends AppCompatActivity {

    String insertUrl = "http://i-mark.herokuapp.com/images";

    private Button confirm, open,previous,camera,clear,done;
    private polygoneView polygoneView;
    Drawable d;

    private double latitude, longitude;

    String height, width, tag;

    String encodeImageString;

    private String[] PERMISSIONS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        polygoneView = findViewById(R.id.image);

        previous = findViewById(R.id.previous);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                polygoneView.previousAnnotation();
                polygoneView.callOnClick();
            }
        });

        clear = findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                polygoneView.clearAnnotation();
                polygoneView.callOnClick();
            }
        });

        camera = findViewById(R.id.camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!hasPermissions(MainActivity.this, PERMISSIONS)) {
                    ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS, 1);
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    startActivityForResult(intent, 100);
                } else {
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    startActivityForResult(intent, 100);
                }
            }
        });

        done = findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                polygoneView.finishAnnotation();
                polygoneView.callOnClick();
            }
        });

        PERMISSIONS = new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.CAMERA
        };

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
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Log.d("width", width);
                        Log.d("height", height);
                        Log.d("date_captured", currentTime.toString());
                        Log.d("latitude", latitude + "");
                        Log.d("longitude", longitude + "");
                        Log.d("annotation", tag);
                        Log.d("polygone", polygoneView.getPolygone().toString());
                        Log.d("image", encodeImageString);
                        Toast.makeText(MainActivity.this, "width : " + width + "\nheight : " + height + "\ndate_captured : " + sdf.format(currentTime) + "\nlatitude : " + latitude + "\nlongitude : " + longitude + "\nannotation : " + tag + "\npolygone : \n" + polygoneView.getPolygone(), Toast.LENGTH_LONG).show();

                        JSONObject image = new JSONObject();

                        JSONObject gps_location = new JSONObject();

                        JSONObject objects = new JSONObject();
                        JSONArray objectsArray = new JSONArray();

                        JSONObject polygons = new JSONObject();
                        JSONArray polygonsArray = new JSONArray();

                        JSONObject coordonnees = new JSONObject();
                        JSONArray coordonneesArray = new JSONArray();

                        JSONObject pointObject = new JSONObject();

                        try {
                            image.put("file_url", encodeImageString);
                            image.put("width", width);
                            image.put("height", height);
                            image.put("date_captured", sdf.format(currentTime));

                            image.put("gps_location", gps_location);
                            gps_location.put("latitude", latitude);
                            gps_location.put("longitude", longitude);

                            image.put("objects", objectsArray);
                            objectsArray.put(objects);
                            objects.put("annotation", tag);
                            objects.put("polygons", polygonsArray);

                            coordonnees.put("coordonnees", coordonneesArray);
                            polygonsArray.put(coordonnees);

                            for (Point p : polygoneView.getPolygone()) {
                                pointObject.put("x", p.getX());
                                pointObject.put("y", p.getY());
                                coordonneesArray.put(pointObject);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.d("JSON OBJECT", image.toString());
                        pushToDB(image);

                    }
                });
                annotator.show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap photo = (Bitmap) data.getExtras().get("data");
        d = new BitmapDrawable(getResources(), photo);
        polygoneView.setBackground(d);
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

    void pushToDB(JSONObject js){
        // Make request for JSONObject
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, insertUrl, js,
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
}