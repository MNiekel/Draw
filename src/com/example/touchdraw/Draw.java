package com.example.touchdraw;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.graphics.Color;

public class Draw extends Activity
{
    DrawView drawView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        drawView = new DrawView(this);
        setContentView(drawView);
        drawView.requestFocus();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.reset:
                drawView.clear();
                return true;
            case R.id.last:
                drawView.undo();
                return true;
            case R.id.color:
                pickColor();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void pickColor()
    {
        DialogFragment f = new ColorPickerFragment(drawView);
        f.show(getFragmentManager(), "colorPicker");
    }
}
