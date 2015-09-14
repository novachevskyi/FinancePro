package com.novachevskyi.expenseslite.presentation.view.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.novachevskyi.expenseslite.R;
import com.novachevskyi.expenseslite.presentation.model.transactions.TransactionType;

public class ExpensesSwitchButtonView extends RelativeLayout {

  @InjectView(R.id.rl_switch) RelativeLayout rl_switch;
  @InjectView(R.id.tv_switch_title) TextView tv_switch_title;

  private TransactionType selectedTransactionType;

  public ExpensesSwitchButtonView(Context context) {
    super(context);
    init();
  }

  public ExpensesSwitchButtonView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public ExpensesSwitchButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    LayoutInflater.from(getContext()).inflate(R.layout.view_expenses_switch_button, this);
    initSubViews();
  }

  private void initSubViews() {
    ButterKnife.inject(this);

    setDefault();

    rl_switch.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View v) {
        if (onSwitchClickListener != null) {
          switchTransactionType();

          onSwitchClickListener.onSwitched(selectedTransactionType);
        }
      }
    });
  }

  private void switchTransactionType() {
    if (selectedTransactionType == TransactionType.OUTCOME) {
      tv_switch_title.setText(
          getContext().getString(R.string.view_expenses_switch_button_income_title));
      selectedTransactionType = TransactionType.INCOME;
    } else {
      tv_switch_title.setText(
          getContext().getString(R.string.view_expenses_switch_button_expense_title));
      selectedTransactionType = TransactionType.OUTCOME;
    }
  }

  private void setDefault() {
    tv_switch_title.setText(
        getContext().getString(R.string.view_expenses_switch_button_expense_title));
    selectedTransactionType = TransactionType.OUTCOME;
  }

  private OnSwitchClickListener onSwitchClickListener;

  public void setOnSwitchClickListener(OnSwitchClickListener onSwitchClickListener) {
    this.onSwitchClickListener = onSwitchClickListener;
  }

  public TransactionType getSelectedType() {
    return selectedTransactionType;
  }

  public interface OnSwitchClickListener {
    void onSwitched(TransactionType transactionType);
  }
}
