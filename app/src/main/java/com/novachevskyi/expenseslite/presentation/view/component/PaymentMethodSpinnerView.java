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
import com.novachevskyi.expenseslite.presentation.model.accounts.AccountType;

public class PaymentMethodSpinnerView extends RelativeLayout {

  @InjectView(R.id.sp_value) Spinner sp_value;

  private AccountType selectedAccountType;

  public PaymentMethodSpinnerView(Context context) {
    super(context);
    init();
  }

  public PaymentMethodSpinnerView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public PaymentMethodSpinnerView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        R.array.view_payment_method_spinner_methods, R.layout.spinner_item);
    adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
    sp_value.setAdapter(adapter);

    if (adapter.getCount() > 0) {
      sp_value.setSelection(0);
    }

    sp_value.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override public void onItemSelected(AdapterView<?> parent, View view, int position,
          long id) {
        selectedAccountType = AccountType.getAccountType(position);
      }

      @Override public void onNothingSelected(AdapterView<?> parent) {

      }
    });
  }

  public AccountType getSelectedAccountType() {
    return this.selectedAccountType;
  }
}
