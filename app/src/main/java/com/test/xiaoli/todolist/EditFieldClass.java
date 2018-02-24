package com.test.xiaoli.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditFieldClass extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.to_do_layout);
    }

    public void saveButtonClicked(View v){
        String messageText = ((EditText) findViewById(R.id.message)).getText().toString();
        if(messageText.equals("")){
        }else{
            Intent intent = new Intent();
            // works like a hashmap
            intent.putExtra(Intent_Constants.INTENT_MESSAGE_FIELD, messageText);
            setResult(Intent_Constants.INTENT_RESULT_CODE, intent);
            finish();
        }
    }
}
