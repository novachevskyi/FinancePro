package com.novachevskyi.expenseslite.presentation.view.component;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.novachevskyi.datepicker.CalendarDatePickerDialog;
import com.novachevskyi.expenseslite.R;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DatePickerTextViewView extends RelativeLayout {

  private static final String DATE_PICKER_TAG = "DATE_PICKER_TAG";

  @InjectView(R.id.tv_value) TextView tv_value;
  @InjectView(R.id.rl_layout) RelativeLayout rl_layout;

  private CalendarDatePickerDialog dialog;

  private FragmentManager supportFragmentManager;

  private LocalDate localDate;

  public DatePickerTextViewView(Context context) {
    super(context);
    init();
  }

  public DatePickerTextViewView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public DatePickerTextViewView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    LayoutInflater.from(getContext()).inflate(R.layout.view_date_picker_text_view, this);
    initSubViews();
  }

  private void initSubViews() {
    ButterKnife.inject(this);

    dialog = new CalendarDatePickerDialog();
    dialog.setOnDateSetListener(dateSetListener);

    localDate = new LocalDate();
    updateDateTextValue();

    rl_layout.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View v) {
        if (supportFragmentManager != null) {
          dialog.show(supportFragmentManager, DATE_PICKER_TAG);
        } else {
          throw new IllegalArgumentException(
              "Set FragmentManager in DatePickerEditTextView to show DatePicker");
        }
      }
    });
  }

  private void updateDateTextValue() {
    DateTimeFormatter formatter = DateTimeFormat.forPattern(
        getContext().getString(R.string.view_date_picker_text_view_date_format));
    String formattedDate = formatter.print(localDate);
    if (tv_value != null) {
      tv_value.setText(formattedDate);
    }
  }

  CalendarDatePickerDialog.OnDateSetListener dateSetListener =
      new CalendarDatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(CalendarDatePickerDialog dialog, int year, int monthOfYear,
            int dayOfMonth) {
          localDate = new LocalDate(year, monthOfYear + 1, dayOfMonth);
          updateDateTextValue();
        }
      };

  public void setSupportFragmentManager(FragmentManager supportFragmentManager) {
    this.supportFragmentManager = supportFragmentManager;
  }

  public String getSelectedDate() {
    return localDate.toString();
  }
}
