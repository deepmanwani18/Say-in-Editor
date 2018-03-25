package com.perfection.utkarsh.sayineditor;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SnippetActivity extends AppCompatActivity {

    private final String TEMP_CODE_SNIPPET = "code_snippets_101.txt";
    private static final int PERMISSION_REQUEST_CODE = 1;
    EditText code_custom;
    EditText voice_custom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snippet);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab_snippet = (FloatingActionButton) findViewById(R.id.fab_snippet);
        fab_snippet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.enter_snippet);
                enterSnippet();
            }
        });
    }
    public void enterSnippet(){
        Button btn = (Button)findViewById(R.id.submit_snippet);
        code_custom = (EditText) findViewById(R.id.code_custom);
        voice_custom = (EditText) findViewById(R.id.voice_custom);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= 23)
                {
                    if (checkPermission())
                    {
                        // Code for above or equal 23 API Oriented Device
                        // Your Permission granted already .Do next code

                        File path = new File(Environment.getExternalStorageDirectory().toString() + File.separator + "SayItEditor");
                        boolean success = true;
                        if (!path.exists()) {
                            success = path.mkdir();
                        }
                        if (success) {
                            Log.e("App", "success to create directory");
                        } else {
                            Log.e("App", "failed to create directory");
                        }

                        File file = new File(path,TEMP_CODE_SNIPPET);
                        try {
                            FileOutputStream fileOutputStream = new FileOutputStream(file, true);  //(new File(file.getAbsolutePath().toString()),true);
                            fileOutputStream.write(("\nVoice:" + voice_custom.getText().toString()).getBytes());
                            fileOutputStream.write(("\nCode:" + code_custom.getText().toString()).getBytes());
                            fileOutputStream.close();
                            Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                        } catch (FileNotFoundException e) {
                            Toast.makeText(getApplicationContext(),"Error" + "1",Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            Toast.makeText(getApplicationContext(),"Error" + "2",Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        requestPermission(); // Code for permission
                    }
                }
            }
        });
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(SnippetActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(SnippetActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(SnippetActivity.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(SnippetActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Permission Granted, Now you can use local drive .");
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
    }
}

