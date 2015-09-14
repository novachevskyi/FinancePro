package com.novachevskyi.expenseslite.presentation.view.component;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.RelativeLayout;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.novachevskyi.expenseslite.R;

public class MoneyEditTextView extends RelativeLayout {

  @InjectView(R.id.tv_value) EditText et_value;

  public MoneyEditTextView(Context context) {
    super(context);
    init();
  }

  public MoneyEditTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public MoneyEditTextView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    LayoutInflater.from(getContext()).inflate(R.layout.view_money_edit_text, this);
    initSubViews();
  }

  private void initSubViews() {
    ButterKnife.inject(this);

    final String regex = "^\\-?(\\d{0,12}|\\d{0,12}\\.\\d{0,2})$";
    et_value.setFilters(new InputFilter[] {
        new InputFilter() {
          @Override
          public CharSequence filter(CharSequence source, int start, int end, Spanned destination,
              int destinationStart, int destinationEnd) {
            if (end > start) {
              String destinationString = destination.toString();
              String resultingTxt = destinationString.substring(0, destinationStart)
                  + source.subSequence(start, end)
                  + destinationString.substring(destinationEnd);

              return resultingTxt.matches(regex) ? null : "";
            }

            return null;
          }
        }
    });
  }

  public Editable getText() {
    if (et_value != null) {
      return et_value.getText();
    }

    return null;
  }

  public double getValue() {
    if (et_value != null) {
      String text = et_value.getText().toString();

      try {
        return Double.parseDouble(text);
      } catch (Exception ignored) {

      }
    }

    return 0l;
  }

  public void setError(String message) {
    if (et_value != null) {
      et_value.setError(message);
    }
  }

  public CharSequence getError() {
    if (et_value != null) {
      et_value.getError();
    }

    return null;
  }
}
