package com.example.touchdraw;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;

public class ColorPickerFragment extends DialogFragment
                         implements ColorPickerDialog.OnColorChangedListener
{
    private DrawView drawView;

    public ColorPickerFragment(DrawView view)
    {
        drawView = view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        int color = drawView.getColor();

        // Create a new instance of ColorPickerDialog and return it
        return new ColorPickerDialog(getActivity(), this, "", color, color);
    }

    public void colorChanged(String key, int color) {
        // Do something with the color chosen by the user
        drawView.setColor(color);
    }
}
