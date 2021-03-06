package com.routinetracker.utils.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.routinetracker.R;

public class AppTextView extends AppCompatTextView {
    public AppTextView(Context context) {
        super(context);
    }

    public AppTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AppTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attributeSet) {
        String fontPath;
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.AppTextView);
        int font_val = typedArray.getInteger(R.styleable.AppTextView_txt_font_type, 1);
//        boolean setInputType = typedArray.getBoolean(R.styleable.AppEditText_set_input_type, true);
        switch (font_val) {
            case 0:
                fontPath = "fonts/light.otf";
                break;
            case 1:
                fontPath = "fonts/regular.otf";
                break;
            case 2:
                fontPath = "fonts/bold.otf";
                break;
            case 3:
                fontPath = "fonts/italic.ttf";
                break;
            default:
                fontPath = "fonts/regular.otf";
                break;
        }
//        if (setInputType)
//            setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), fontPath);
        setTypeface(tf);
        typedArray.recycle();
    }

}