package com.novachevskyi.expenseslite.presentation.view.component;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import java.util.HashMap;
import java.util.Locale;

public class LogoFontManager {
  public static final String SCHEME = "http://schemas.android.com/apk/lib/com.novachevskyi";
  public static final String ATTRIBUTE = "ttf";
  private static final String FONT_FOLDER = "fonts/";
  private static final String FONT_REGULAR = "Dense-Regular.otf";
  private static final HashMap<String, Typeface> sCachedFonts = new HashMap<>();
  private static LogoFontManager instance = null;

  private LogoFontManager() {
  }

  public static LogoFontManager getInstance() {
    if (instance == null) {
      instance = new LogoFontManager();
    }
    return instance;
  }

  public Typeface getFont(final AssetManager assetManager, final String ttf) {
    TypeFonts font = TypeFonts.valueOf(ttf.toUpperCase(Locale.ENGLISH));
    return getFont(assetManager, font);
  }

  public Typeface getFont(final AssetManager assetManager, TypeFonts font) {
    Typeface oReturn = null;
    try {
      switch (font) {
        case REGULAR:
          oReturn = load(assetManager, getPathFont(FONT_REGULAR));
          break;
        default:
          oReturn = load(assetManager, getPathFont(FONT_REGULAR));
          break;
      }
    } catch (Exception ignored) {

    }
    return oReturn;
  }

  private String getPathFont(String name) {
    return FONT_FOLDER + name;
  }

  private Typeface load(final AssetManager assetManager, final String filePath) {
    synchronized (sCachedFonts) {
      try {
        if (!sCachedFonts.containsKey(filePath)) {
          final Typeface typeface = Typeface.createFromAsset(assetManager, filePath);
          sCachedFonts.put(filePath, typeface);
          return typeface;
        }
      } catch (Exception e) {
        sCachedFonts.put(filePath, null);
        return null;
      }
      return sCachedFonts.get(filePath);
    }
  }

  public static enum TypeFonts {
    REGULAR
  }
}
