package com.test.xiaoli.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditMessageClass extends AppCompatActivity {
    String messageText;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.to_do_layout);
        Intent intent = getIntent();
        messageText = intent.getStringExtra(Intent_Constants.INTENT_MESSAGE_DATA);
        position = intent.getIntExtra(Intent_Constants.INTENT_ITEM_POSITION, -1);
        EditText messageData = (EditText) findViewById(R.id.message);
        messageData.setText(messageText);
    }

    public void saveButtonClicked(View v){
        String changedMessageText = ((EditText) findViewById(R.id.message)).getText().toString();
        Intent intent = new Intent();
        intent.putExtra(Intent_Constants.INTENT_CHANGED_MESSAGE, changedMessageText);
        intent.putExtra(Intent_Constants.INTENT_ITEM_POSITION, position);
        setResult(Intent_Constants.INTENT_RESULT_CODE_TWO, intent);
        finish();
    }

}
