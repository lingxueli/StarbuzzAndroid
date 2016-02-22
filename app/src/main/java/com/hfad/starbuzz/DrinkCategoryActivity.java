package com.hfad.starbuzz;

import android.app.ListActivity;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.*;
import android.view.*;
import android.content.*;

public class DrinkCategoryActivity extends ListActivity {

    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView listDrinks = getListView();

        try{
            SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
            db = starbuzzDatabaseHelper.getReadableDatabase();

            cursor = db.query("DRINK", new String[]{"_id", "NAME"}, null, null, null, null, null);
            CursorAdapter listAdapter = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_1,cursor,new String[]{"NAME"},new int[]{android.R.id.text1},0);
            listDrinks.setAdapter(listAdapter);

        }catch (SQLiteException e){
            Toast toast = Toast.makeText(this,"Database unavialable",Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    protected void onListItemClick(ListView listView, View view, int position, long id){
        Intent intent = new Intent(DrinkCategoryActivity.this,DrinkActivity.class);
        intent.putExtra(DrinkActivity.EXTRA_DRINKNO, (int) id);
        startActivity(intent);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        cursor.close();
        db.close();
    }

}
