package com.integra.esignapplication;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.nsdl.egov.esignaar.NsdlEsignActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.esign);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int REQUEST_CODE=100;
                Intent appStartIntent = new Intent(MainActivity.this, NsdlEsignActivity.class);
                //MainActivity should be the ASP Activity
                appStartIntent.putExtra("msg", "msg"); // msg contains esign request xml from ASP.
                appStartIntent.putExtra("env", "PREPROD"); //Possible values PREPROD or PROD
                appStartIntent.putExtra("returnUrl", ""); // your package name where esign
//                startActivityForResult(appStartIntent, REQUEST_CODE);
                activityResultLauncher.launch(appStartIntent);

            }
        });


    }


    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        assert data != null;
                        Log.d("ESIGN","eSignResponse : " + data.getStringExtra("signedResponse" ));
                        System.out.println("eSignResponse : " + data.getStringExtra("signedResponse"));
                        Toast.makeText(MainActivity.this, "eSignResponse : " + data.getStringExtra("signedResponse" ), Toast.LENGTH_SHORT).show();
                    }
                }
            });
}