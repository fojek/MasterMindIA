package com.example.mfortier.test;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

/**
 * Created by mfortier on 2017-10-16.
 */

public class ResultButton extends android.support.v7.widget.AppCompatButton {

    public enum TypeResultat {Noir, Blanc};
    TypeResultat type;
    int compte;

    static ResultButton NoirResultButton(Context c){
        return new ResultButton(c, TypeResultat.Noir);
    }

    static ResultButton BlancResultButton(Context c){
        return new ResultButton(c, TypeResultat.Blanc);
    }

    public ResultButton(Context c, TypeResultat pType) {
        super(c);

        type = pType;
        compte = 0;

        super.setText(String.valueOf(compte));
        if(pType == TypeResultat.Blanc){
            super.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
        }
        else
            super.getBackground().setColorFilter(Color.DKGRAY, PorterDuff.Mode.MULTIPLY);

        super.setWidth(super.getWidth()/2);
    }

    public int getCompte(){
        return compte;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        if (isEnabled()) {
            if (event.getAction() == MotionEvent.ACTION_DOWN)
                super.setText(String.valueOf(++compte));
            if (compte > 4) {
                compte = 0;
                super.setText(String.valueOf(compte));
            }
        }
        return true;
    }
}
