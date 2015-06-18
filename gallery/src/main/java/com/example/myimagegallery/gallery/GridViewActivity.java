package com.example.myimagegallery.gallery;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import com.example.adapters.ImageAdapter;

import java.util.ArrayList;
import java.util.List;

public class GridViewActivity extends Activity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);

        Intent intent = getIntent();
        Intent data = intent.getParcelableExtra(MainActivity.GRID_DATA);

        List<String > imageUris = new ArrayList<String>();
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        Cursor cursor = null;

        ClipData clipData = data.getClipData();
        for(int i=0; i <clipData.getItemCount();i++) {
            Uri image = clipData.getItemAt(i).getUri();
            cursor = getContentResolver().query(image, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            imageUris.add(picturePath);
        }
        cursor.close();

        ImageAdapter imageAdapter = new ImageAdapter(this, imageUris);
        GridView gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(imageAdapter);

    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_grid_view, menu);
        return true;
    }

}
