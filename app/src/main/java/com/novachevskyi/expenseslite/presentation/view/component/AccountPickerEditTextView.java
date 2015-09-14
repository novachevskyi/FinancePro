package com.novachevskyi.expenseslite.presentation.view.component;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.novachevskyi.expenseslite.R;
import com.novachevskyi.expenseslite.presentation.model.accounts.AccountModel;
import com.novachevskyi.expenseslite.presentation.view.fragment.accounts.AccountsListDialogFragment;

public class AccountPickerEditTextView extends RelativeLayout {

  private static final String ACCOUNTS_DIALOG_LIST_DIALOG = "AccountsListDialogFragment";

  @InjectView(R.id.et_edit) EditText et_edit;

  private FragmentManager supportFragmentManager;

  private AccountModel accountModel = null;
  private AccountsListDialogFragment accountsListDialogFragment;

  public AccountPickerEditTextView(Context context) {
    super(context);
    init();
  }

  public AccountPickerEditTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public AccountPickerEditTextView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    LayoutInflater.from(getContext()).inflate(R.layout.view_account_picker_edit_text, this);
    initSubViews();
  }

  private void initSubViews() {
    ButterKnife.inject(this);

    et_edit.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View v) {
        if (supportFragmentManager != null) {
          accountsListDialogFragment =
              AccountsListDialogFragment.newInstance();
          accountsListDialogFragment.setOnItemClickListener(
              new AccountsListDialogFragment.OnItemClickListener() {
                @Override public void onAccountItemClicked(AccountModel accountModel) {
                  AccountPickerEditTextView.this.accountSelected(accountModel);
                }
              });
          accountsListDialogFragment.show(supportFragmentManager.beginTransaction(),
              ACCOUNTS_DIALOG_LIST_DIALOG);
        } else {
          throw new IllegalArgumentException(
              "Set FragmentManager in AccountPickerEditTextView to show AccountPicker");
        }
      }
    });
  }

  private void accountSelected(AccountModel accountModel) {
    this.accountModel = accountModel;

    et_edit.setText(accountModel.getName());

    accountsListDialogFragment.dismiss();
  }

  public AccountModel getSelectedAccountModel() {
    return this.accountModel;
  }

  public void setSupportFragmentManager(FragmentManager supportFragmentManager) {
    this.supportFragmentManager = supportFragmentManager;
  }

  public void setError(String message) {
    if (et_edit != null) {
      et_edit.setError(message);
    }
  }

  public CharSequence getError() {
    if (et_edit != null) {
      et_edit.getError();
    }

    return null;
  }
}
