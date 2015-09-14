package com.novachevskyi.expenseslite.presentation.view.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.novachevskyi.expenseslite.R;
import com.novachevskyi.expenseslite.presentation.presenter.accounts.AccountsTotalAmountPresenter;
import com.novachevskyi.expenseslite.presentation.view.base.accounts.AccountsTotalAmountLoadView;

public class AccountsTotalAmountView extends RelativeLayout implements AccountsTotalAmountLoadView {

  private AccountsTotalAmountPresenter accountsTotalAmountPresenter;

  @InjectView(R.id.rl_total) RelativeLayout rl_total;
  @InjectView(R.id.tv_total) TextView tv_total;

  public AccountsTotalAmountView(Context context) {
    super(context);
    init();
  }

  public AccountsTotalAmountView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public AccountsTotalAmountView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    LayoutInflater.from(getContext()).inflate(R.layout.view_accounts_total_amount, this);

    accountsTotalAmountPresenter = new AccountsTotalAmountPresenter(this);

    initSubViews();
  }

  private void initSubViews() {
    ButterKnife.inject(this);

    tv_total.setText(getContext().getString(R.string.view_accounts_total_amount_empty));

    accountsTotalAmountPresenter.initialize();
  }

  @Override public void totalAmountLoaded(double totalAmount) {
    tv_total.setText(String.format(
        getContext().getString(R.string.view_accounts_total_amount_template),
        totalAmount));
  }

  @Override public void showLoading() {

  }

  @Override public void hideLoading() {

  }

  @Override public void showRetry() {

  }

  @Override public void hideRetry() {

  }

  @Override public void showError(String message) {

  }

  @Override public void dataValidated() {

  }

  public void reloadData() {
    accountsTotalAmountPresenter.initialize();
  }
}
