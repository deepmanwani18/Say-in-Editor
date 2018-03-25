package com.perfection.utkarsh.sayineditor;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SnippetActivity extends AppCompatActivity {

    private final String TEMP_CODE_SNIPPET = "code_snippets_101.txt";


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
        final EditText code_custom =(EditText)findViewById(R.id.code_custom);
        EditText voice_custom = (EditText)findViewById(R.id.voice_custom);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileOutputStream outputStream;
                try {
                    outputStream = openFileOutput(TEMP_CODE_SNIPPET, Context.MODE_PRIVATE);
                    outputStream.write(code_custom.getText().toString().getBytes());
                    outputStream.close();
                } catch (FileNotFoundException e) {
                    Toast.makeText(getApplicationContext(),"Error 1",Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(),"Error 2",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
