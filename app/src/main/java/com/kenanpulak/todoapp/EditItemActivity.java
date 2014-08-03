package com.kenanpulak.todoapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.kenanpulak.todoapp.R;

public class EditItemActivity extends ActionBarActivity {

    int itemPos;
    private EditText etEditItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        this.getSupportActionBar().setTitle("Edit Item");
        etEditItem = (EditText)findViewById(R.id.etEditItem);
        String itemName = getIntent().getStringExtra("itemName");
        itemPos = getIntent().getIntExtra("pos",-1);
        etEditItem.setText(itemName);
        etEditItem.setSelection(itemName.length());
    }

    public void saveItem(View v){
        String updatedItemName = etEditItem.getText().toString();
        Intent data = new Intent();
        data.putExtra("updatedItemName", updatedItemName);
        data.putExtra("itemPos",itemPos);
        setResult(RESULT_OK,data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
