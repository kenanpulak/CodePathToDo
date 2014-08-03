package com.kenanpulak.todoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import android.support.v7.app.ActionBarActivity;

public class ToDoActivity extends ActionBarActivity {

    private final int REQUEST_CODE = 20;
    private ArrayList<String> toDoItems;
    private ArrayAdapter<String> toDoAdapter;
    private ListView lvItems;
    private EditText etNewItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        this.getSupportActionBar().setTitle("Simple Todo");
        lvItems = (ListView)findViewById(R.id.lvItems);
        etNewItem = (EditText)findViewById(R.id.etNewItem);
        readItems();
        toDoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, toDoItems);
        lvItems.setAdapter(toDoAdapter);
        setupListViewListener();
    }

    private void setupListViewListener(){

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id){
                Intent editIntent = new Intent(ToDoActivity.this,EditItemActivity.class);
                editIntent.putExtra("pos",pos);
                editIntent.putExtra("itemName",((TextView)view).getText());
                startActivityForResult(editIntent, REQUEST_CODE);
            }
        });

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long id) {
                toDoItems.remove(pos);
                toDoAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
    }

    public void onAddedItem(View v){
        String itemText = etNewItem.getText().toString();
        toDoAdapter.add(itemText);
        etNewItem.setText("");
        writeItems();
    }

    private void readItems() {
        File filesDir = getFilesDir();
        File toDoFile = new File(filesDir, "todo.txt");
        try {
            toDoItems = new ArrayList<String>(FileUtils.readLines(toDoFile));
        } catch (IOException e){
            toDoItems = new ArrayList<String>();
        }

    }

    private void writeItems(){
        File filesDir = getFilesDir();
        File toDoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(toDoFile,toDoItems);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            String updatedItemName = data.getExtras().getString("updatedItemName");
            int itemPos = data.getExtras().getInt("itemPos");
            toDoItems.set(itemPos, updatedItemName);
            toDoAdapter.notifyDataSetChanged();
            writeItems();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.to_do, menu);
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
