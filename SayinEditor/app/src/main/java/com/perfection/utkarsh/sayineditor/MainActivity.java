package com.perfection.utkarsh.sayineditor;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final int REQ_CODE_SPEECH_INPUT = 100;
    EditText mainEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

         mainEditText = (EditText) findViewById(R.id.mainEditText);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.micfab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promptSpeechInput();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        keyboard();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.curly_brackets) {
            // Handle the camera action
        } else if (id == R.id.square_brackets) {

        } else if (id == R.id.semi_colon) {

        } else if (id == R.id.angular_brackets) {

        } else if (id == R.id.addition) {

        } else if (id == R.id.subtraction) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.speech_prompt));

        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if(resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    insertCode(result.get(0));
                }
                break;
            }
        }
    }

    private void insertCode(String input) {
        mainEditText = (EditText) findViewById(R.id.mainEditText);
        int cursorPosition = mainEditText.getSelectionStart();
        String codeBeforeCursor = mainEditText.getText().toString().substring(0,cursorPosition);
        String codeAfterCursor = mainEditText.getText().toString().substring(cursorPosition,mainEditText.getText().toString().length());

        String codeToBeInserted = "";

        Toast.makeText(getApplicationContext(),"" + input,Toast.LENGTH_SHORT).show();

        if(input.startsWith("declare integer")) {
            codeToBeInserted = "int" + input.substring(15) + ";";//int,float,char, double, long int , long long int
            mainEditText.setText(codeBeforeCursor + codeToBeInserted + codeAfterCursor);
            mainEditText.setSelection(codeBeforeCursor.length() + codeToBeInserted.length() - 1);
        } else if(input.startsWith("declare long integer")) {
            codeToBeInserted = "long int" + input.substring(20) + ";";
            mainEditText.setText(codeBeforeCursor + codeToBeInserted + codeAfterCursor);
            mainEditText.setSelection(codeBeforeCursor.length() + codeToBeInserted.length() - 1);
        } else if(input.startsWith("declare long long integer")) {
            codeToBeInserted = "long long int" + input.substring(25) + ";";
            mainEditText.setText(codeBeforeCursor + codeToBeInserted + codeAfterCursor);
            mainEditText.setSelection(codeBeforeCursor.length() + codeToBeInserted.length() - 1);
        } else if(input.startsWith("declare float")) {
            codeToBeInserted = "float" + input.substring(13) + ";";
            mainEditText.setText(codeBeforeCursor + codeToBeInserted + codeAfterCursor);
            mainEditText.setSelection(codeBeforeCursor.length() + codeToBeInserted.length() - 1);
        } else if(input.startsWith("declare character")) {
            codeToBeInserted = "char" + input.substring(17) + ";";
            mainEditText.setText(codeBeforeCursor + codeToBeInserted + codeAfterCursor);
            mainEditText.setSelection(codeBeforeCursor.length() + codeToBeInserted.length() - 1);
        } else if(input.startsWith("declare double")) {
            codeToBeInserted = "double" + input.substring(14) + ";";
            mainEditText.setText(codeBeforeCursor + codeToBeInserted + codeAfterCursor);
            mainEditText.setSelection(codeBeforeCursor.length() + codeToBeInserted.length() - 1);
        } else if(input.equals("for loop")) {
            codeToBeInserted = "for(  ;  ;  ) {\n\n}";
            mainEditText.setText(codeBeforeCursor + codeToBeInserted + codeAfterCursor);
        }
    }

    public void keyboard(){
        EditText editText=(EditText)findViewById(R.id.mainEditText);
        FloatingActionButton key =(FloatingActionButton)findViewById(R.id.keyfab);
        key.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }
}
