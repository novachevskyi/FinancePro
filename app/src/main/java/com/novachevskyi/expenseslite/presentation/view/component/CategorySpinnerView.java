package com.novachevskyi.expenseslite.presentation.view.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.novachevskyi.expenseslite.R;
import com.novachevskyi.expenseslite.presentation.model.transactions.Category;

public class CategorySpinnerView extends RelativeLayout {

  @InjectView(R.id.sp_value) Spinner sp_value;

  private Category selectedCategory;

  public CategorySpinnerView(Context context) {
    super(context);
    init();
  }

  public CategorySpinnerView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public CategorySpinnerView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    LayoutInflater.from(getContext()).inflate(R.layout.view_spinner, this);
    initSubViews();
  }

  private void initSubViews() {
    ButterKnife.inject(this);

    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
        R.array.view_category_spinner_categories, R.layout.spinner_item);
    adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
    sp_value.setAdapter(adapter);

    if (adapter.getCount() > 0) {
      sp_value.setSelection(0);
    }

    sp_value.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override public void onItemSelected(AdapterView<?> parent, View view, int position,
          long id) {
        selectedCategory = Category.getCategory(position);
      }

      @Override public void onNothingSelected(AdapterView<?> parent) {

      }
    });
  }

  public Category getSelectedCategory() {
    return this.selectedCategory;
  }
}
