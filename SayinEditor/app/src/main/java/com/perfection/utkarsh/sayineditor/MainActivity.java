package com.perfection.utkarsh.sayineditor;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Environment;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SaveFileDialog.SaveFileDialogListener {

    public String mainPath;
    private DialogFragment dialog;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView mLeftDrawerView;
    private NavigationView mRightDrawerView;
    private Toolbar toolbar;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    EditText mainEditText;

    private final String TEMP_FILE_NAME = "hackonhills_tempfile_04e09f1999.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainEditText = (EditText) findViewById(R.id.mainEditText);
        mainEditText.setText("#include<bits/stdc++.h>" +
                "\nusing namespace std;" +
                "\nint main()\n{\n\n}");
        mainEditText.setSelection(58);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.micfab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promptSpeechInput();
            }
        });

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();

        NavigationView navigationViewLeft = (NavigationView) findViewById(R.id.nav_view_left);
        navigationViewLeft.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        String ret = "";
        try {
            InputStream inputStream = getApplicationContext().openFileInput(TEMP_FILE_NAME);
            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString).append("\n");
                }

                inputStream.close();
                ret = stringBuilder.toString();
                mainEditText.setText(ret);
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        if (mDrawerLayout == null || mLeftDrawerView == null || mRightDrawerView == null || mDrawerToggle == null) {
            // Configure navigation drawer
            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            mLeftDrawerView = findViewById(R.id.nav_view_left);
            mRightDrawerView = findViewById(R.id.nav_view_right);
            mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
                public void onDrawerClosed(View drawerView) {
                    if (drawerView.equals(mLeftDrawerView)) {
                        getSupportActionBar().setTitle(getTitle());
                        supportInvalidateOptionsMenu();
                        mDrawerToggle.syncState();
                    }
                }

                public void onDrawerOpened(View drawerView) {
                    if (drawerView.equals(mLeftDrawerView)) {
                        getSupportActionBar().setTitle(getString(R.string.app_name));
                        supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                        mDrawerToggle.syncState();
                    }
                }

                @Override
                public void onDrawerSlide(View drawerView, float slideOffset) {
//                    super.onDrawerSlide(drawerView, slideOffset);
                }
            };

            mDrawerLayout.setDrawerListener(mDrawerToggle);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        FileOutputStream outputStream;
        try {
            outputStream = openFileOutput(TEMP_FILE_NAME, Context.MODE_PRIVATE);
            outputStream.write(mainEditText.getText().toString().getBytes());
            outputStream.close();
        } catch (FileNotFoundException e) {
                Toast.makeText(getApplicationContext(),"Error 1",Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
                Toast.makeText(getApplicationContext(),"Error 2",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("DHSL","HS");
//        String ret = "";
//        try {
//            InputStream inputStream = getApplicationContext().openFileInput(TEMP_FILE_NAME);
//            if ( inputStream != null ) {
//                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//                String receiveString = "";
//                StringBuilder stringBuilder = new StringBuilder();
//
//                while ( (receiveString = bufferedReader.readLine()) != null ) {
//                    stringBuilder.append(receiveString).append("\n");
//                }
//
//                inputStream.close();
//                ret = stringBuilder.toString();
//                mainEditText.setText(ret);
//            }
//        } catch (FileNotFoundException e) {
//            Log.e("login activity", "File not found: " + e.toString());
//        } catch (IOException e) {
//            Log.e("login activity", "Can not read file: " + e.toString());
//        }


    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        for (int i = 0; i < menu.size(); i++) {
            menu.getItem(i).setVisible(!mDrawerLayout.isDrawerOpen(mLeftDrawerView));
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
            if(id == R.id.action_insert_code) {
                mDrawerToggle.onOptionsItemSelected(item);
                if (mDrawerLayout.isDrawerOpen(mRightDrawerView))
                    mDrawerLayout.closeDrawer(mRightDrawerView);
                else {
                    mDrawerLayout.openDrawer(mRightDrawerView);
                }

                return true;
            }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
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


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.action_share) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, mainEditText.getText().toString() + "");
            intent.setType("text/plain");
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        } else if(id == R.id.action_save) {
            showSaveFileDialog();
        }
        else if(id == R.id.snippets) {
            Intent intent = new Intent(MainActivity.this,SnippetActivity.class);
            startActivity(intent);
        }




        else if (id == R.id.curly_brackets) {

        } else if (id == R.id.semi_colon) {

        } else if (id == R.id.addition) {

        } else if (id == R.id.subtraction) {

        } else if (id == R.id.division) {

        } else if (id == R.id.modulus) {

        } else if (id == R.id.angular_bracket_left) {

        } else if (id == R.id.curved_brackets) {

        } else if (id == R.id.angular_bracket_gequal) {

        } else if (id == R.id.angular_bracket_lequal) {

        } else if (id == R.id.angular_bracket_right) {

        } else if (id == R.id.assignment) {

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
                if (resultCode == RESULT_OK && data != null) {
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
        String codeBeforeCursor = mainEditText.getText().toString().substring(0, cursorPosition);
        String codeAfterCursor = mainEditText.getText().toString().substring(cursorPosition, mainEditText.getText().toString().length());

        String codeToBeInserted = "";

        Toast.makeText(getApplicationContext(), "" + input, Toast.LENGTH_SHORT).show();

        if (input.startsWith("declare integer")) {
            codeToBeInserted = "int" + input.substring(15) + ";\n";//int,float,char, double, long int , long long int
            mainEditText.setText(codeBeforeCursor + codeToBeInserted + codeAfterCursor);
            mainEditText.setSelection(codeBeforeCursor.length() + codeToBeInserted.length() - 1);
            Toast.makeText(getApplicationContext(), "" + codeToBeInserted, Toast.LENGTH_SHORT).show();
        } else if (input.startsWith("declare long integer")) {
            codeToBeInserted = "long int" + input.substring(20) + ";\n";
            mainEditText.setText(codeBeforeCursor + codeToBeInserted + codeAfterCursor);
            mainEditText.setSelection(codeBeforeCursor.length() + codeToBeInserted.length() - 1);
        } else if (input.startsWith("declare long long integer")) {
            codeToBeInserted = "long long int" + input.substring(25) + ";\n";
            mainEditText.setText(codeBeforeCursor + codeToBeInserted + codeAfterCursor);
            mainEditText.setSelection(codeBeforeCursor.length() + codeToBeInserted.length() - 1);
        } else if (input.startsWith("declare float")) {
            codeToBeInserted = "float" + input.substring(13) + ";\n";
            mainEditText.setText(codeBeforeCursor + codeToBeInserted + codeAfterCursor);
            mainEditText.setSelection(codeBeforeCursor.length() + codeToBeInserted.length() - 1);
        } else if (input.startsWith("declare character")) {
            codeToBeInserted = "char" + input.substring(17) + ";\n";
            mainEditText.setText(codeBeforeCursor + codeToBeInserted + codeAfterCursor);
            mainEditText.setSelection(codeBeforeCursor.length() + codeToBeInserted.length() - 1);
        } else if (input.startsWith("declare double")) {
            codeToBeInserted = "double" + input.substring(14) + ";\n";
            mainEditText.setText(codeBeforeCursor + codeToBeInserted + codeAfterCursor);
            mainEditText.setSelection(codeBeforeCursor.length() + codeToBeInserted.length() - 1);
        } else if (input.equals("for loop")) {
            codeToBeInserted = "for(  ;  ;  ) {\n\n}";
            mainEditText.setText(codeBeforeCursor + codeToBeInserted + codeAfterCursor);
            mainEditText.setSelection(codeBeforeCursor.length() + 4);
        } else if (input.startsWith("print out") || input.startsWith("printout")) {
            codeToBeInserted = "cout<<" + input.substring(9) + ";\n";
            mainEditText.setText(codeBeforeCursor + codeToBeInserted + codeAfterCursor);
            mainEditText.setSelection(codeBeforeCursor.length() + codeToBeInserted.length());
        } else if (input.startsWith("print")) {
            codeToBeInserted = "cout<<\"" + input.substring(6) + "\";\n";
            mainEditText.setText(codeBeforeCursor + codeToBeInserted + codeAfterCursor);
            mainEditText.setSelection(codeBeforeCursor.length() + codeToBeInserted.length());
        } else if (input.startsWith("input")) {
            codeToBeInserted = "cin>>" + input.substring(6) + ";\n";
            mainEditText.setText(codeBeforeCursor + codeToBeInserted + codeAfterCursor);
            mainEditText.setSelection(codeBeforeCursor.length() + codeToBeInserted.length());
        } else if (input.startsWith("power")) {
            codeToBeInserted = "pow(" + input.substring(5) + "," + ");\n";
            mainEditText.setText(codeBeforeCursor + codeToBeInserted + codeAfterCursor);
        } else if (input.startsWith("declare array integer")) {              //declare an array integer a of size 1000
            codeToBeInserted = "int" + input.substring(21, input.indexOf("of size")) + "[" + input.substring(input.indexOf("of size")) + "];\n";//int,float,char, double, long int , long long int
            mainEditText.setText(codeBeforeCursor + codeToBeInserted + codeAfterCursor);
            mainEditText.setSelection(codeBeforeCursor.length() + codeToBeInserted.length() - 1);
            //TODO: non-singular names of arrays cannot be declared. Solve this bug.
        } else if (input.startsWith("add test case loop")) {              //add testcase loop of int a
            codeToBeInserted = "int " + input.substring(19) + ";\ncin>>" + input.substring(19) + "\nwhile(" + input.substring(19) + "--)\n{\n}";//int,float,char, double, long int , long long int
            mainEditText.setText(codeBeforeCursor + codeToBeInserted + codeAfterCursor);
            mainEditText.setSelection(codeBeforeCursor.length() + codeToBeInserted.length() - 1);
        } else if (input.startsWith("create function")) {
            codeToBeInserted = "";
        }
    }


    public void showSaveFileDialog() {
        // Create an instance of the dialog fragment and show it
        dialog = new SaveFileDialog();
        dialog.show(getSupportFragmentManager(), "SaveFileDialogFragment");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    @Override
    public void result(String inputString) {
        File path = new File("/storage/emulated/0/","Hack");
        path.mkdirs();

        File file = new File(path,inputString);
        file.mkdirs();

        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(new File(file.getAbsolutePath().toString()),true);
            fileOutputStream.write(mainEditText.getText().toString().getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            Toast.makeText(getApplicationContext(),inputString + "1",Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(),inputString + "2",Toast.LENGTH_SHORT).show();
        }

//        Toast.makeText(getApplicationContext(),inputString + "",Toast.LENGTH_SHORT).show();

    }


}
