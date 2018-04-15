package com.parse.starter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        final ListView listView = (ListView) findViewById(R.id.listView);
        final ArrayList<String> userList =  new ArrayList<>();

        final ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,userList);

        ParseQuery<ParseUser>  userParseQuery = ParseUser.getQuery();

        userParseQuery.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
        userParseQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if(e==null){
                    if(objects.size()>0){
                        for (ParseUser parseUser : objects){
                            userList.add(parseUser.getUsername());
                        }
                        listView.setAdapter(arrayAdapter);
                    }
                }
            }
        });
    }
}
