/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {
    TextView textView ;
    boolean signup = true;
    EditText password;

    public void showUserList(){
        Intent intent = new Intent(getApplicationContext(),Main2Activity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.textView) {
            Button button = (Button) findViewById(R.id.signUp);
            if (signup) {
                signup = false;
                button.setText("Login");
                textView.setText("Or, SignUp");

            }else{
                signup = true;
                button.setText("SignUp");
                textView.setText("Or, Login");
            }
        }
        else if(view.getId() == R.id.relative || view.getId() == R.id.icon){
            InputMethodManager inputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }
    }

    public void signUp(View view){
        EditText username = (EditText) findViewById(R.id.username);
        ParseUser parseUser = new ParseUser();
        if(signup){

            parseUser.setUsername(username.getText().toString());
            parseUser.setPassword(password.getText().toString());
            if(username.getText().toString().equals("")||password.getText().toString().equals("")){
                Toast.makeText(this,"Username and password are required",Toast.LENGTH_SHORT).show();
            }else {

                parseUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e==null){
                            Log.i("mess","SignUp Successful");

                        }else{
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }else {

            ParseUser.logInInBackground(username.getText().toString(), password.getText().toString(), new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if(user!=null){
                        Log.i("mess","Login Successful");
                        showUserList();
                    }
                    else{
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

      if (ParseUser.getCurrentUser() != null){
          showUserList();
      }

    textView = (TextView) findViewById(R.id.textView);
      textView.setOnClickListener(this);
      password = (EditText) findViewById(R.id.password);
      password.setOnKeyListener(this);
      RelativeLayout relative = (RelativeLayout) findViewById(R.id.relative);
      relative.setOnClickListener(this);
      ImageView imageView = (ImageView) findViewById(R.id.icon) ;
      imageView.setOnClickListener(this);



      ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }


    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {

        if(keyCode == event.KEYCODE_ENTER && event.getAction()==KeyEvent.ACTION_DOWN){
            signUp(v);
        }
        return false;
    }
}