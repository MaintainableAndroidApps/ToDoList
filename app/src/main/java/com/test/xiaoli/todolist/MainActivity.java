package com.test.xiaoli.todolist;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.support.v7.widget.Toolbar;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    SwipeMenuListView listView;
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;
    String messageText;
    int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.action_bar);
        setSupportActionBar(toolbar);

        listView = (SwipeMenuListView) findViewById(R.id.listView);
        arrayList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, EditMessageClass.class);
                intent.putExtra(Intent_Constants.INTENT_MESSAGE_DATA, arrayList.get(position).toString());
                intent.putExtra(Intent_Constants.INTENT_ITEM_POSITION, position);
                startActivityForResult(intent, Intent_Constants.INTENT_REQUEST_CODE_TWO);
            }
        });

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(170);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_action_remove);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        // set creator
        listView.setMenuCreator(creator);

        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // delete
                        arrayList.remove(position);
                        arrayAdapter.notifyDataSetChanged();
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });


        try {
            Scanner sc = new Scanner(openFileInput("Todo.txt"));
            while(sc.hasNextLine()){
                String data = sc.nextLine();
                arrayAdapter.add(data);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        try {
            PrintWriter pw = new PrintWriter(openFileOutput("Todo.txt", Context.MODE_PRIVATE));
            for (String data: arrayList){
                pw.println(data);
            }
            pw.close(); // don't leak any resources
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        finish(); // quit your app
    }

    public void onClick(View v){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, EditFieldClass.class);
        startActivityForResult(intent, Intent_Constants.INTENT_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==Intent_Constants.INTENT_REQUEST_CODE){ // this activity has responded
            messageText = data.getStringExtra(Intent_Constants.INTENT_MESSAGE_FIELD);
            arrayList.add(messageText);
            arrayAdapter.notifyDataSetChanged();
        }else if(resultCode == Intent_Constants.INTENT_REQUEST_CODE_TWO){
            messageText = data.getStringExtra(Intent_Constants.INTENT_CHANGED_MESSAGE);
            position = data.getIntExtra(Intent_Constants.INTENT_ITEM_POSITION, -1);
            arrayList.remove(position);
            arrayList.add(position, messageText);
            arrayAdapter.notifyDataSetChanged();
        }
    }
}
