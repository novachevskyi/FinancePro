package com.novachevskyi.expenseslite.presentation.view.component;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class LogoTextView extends TextView {

  public LogoTextView(Context context) {
    super(context);
    setup(context, null);
  }

  public LogoTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
    setup(context, attrs);
  }

  public LogoTextView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    setup(context, attrs);
  }

  private void setup(Context context, AttributeSet attrs) {
    setPaintFlags(getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);

    if (attrs != null && !this.isInEditMode()) {
      final String sTTFName =
          attrs.getAttributeValue(LogoFontManager.SCHEME, LogoFontManager.ATTRIBUTE);
      if (sTTFName != null) {
        final Typeface mTypeFace =
            LogoFontManager.getInstance().getFont(context.getAssets(), sTTFName);
        if (mTypeFace != null) {
          setTypeface(mTypeFace);
        }
      }
    }
  }
}
