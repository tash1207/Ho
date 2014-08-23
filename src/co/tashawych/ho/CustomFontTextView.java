package co.tashawych.ho;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class CustomFontTextView extends TextView {

	public CustomFontTextView(Context context) {
		super(context);
	}

	public CustomFontTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setTypeface(getTypeface(context));
	}

	public CustomFontTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setTypeface(getTypeface(context));
	}
	
	public Typeface getTypeface(Context context) {
		return Typeface.createFromAsset(context.getAssets(), "assets/fonts/GildaDisplay-Regular.ttf");
	}

}
