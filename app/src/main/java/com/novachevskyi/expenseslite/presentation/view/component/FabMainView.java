package com.novachevskyi.expenseslite.presentation.view.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.novachevskyi.expenseslite.R;

public class FabMainView extends RelativeLayout {

  @InjectView(R.id.rl_fab) RelativeLayout rl_fab;

  @InjectView(R.id.fab_actions) FloatingActionsMenu fab_actions;
  @InjectView(R.id.fab_action_transaction) FloatingActionButton fab_action_transaction;
  @InjectView(R.id.fab_action_account) FloatingActionButton fab_action_account;
  @InjectView(R.id.fab_action_budget) FloatingActionButton fab_action_budget;

  @InjectView(R.id.fab_budget_actions) FloatingActionsMenu fab_budget_actions;
  @InjectView(R.id.fab_budget_add) FloatingActionButton fab_budget_add;

  @InjectView(R.id.fab_account_actions) FloatingActionsMenu fab_account_actions;
  @InjectView(R.id.fab_account_add) FloatingActionButton fab_account_add;

  @InjectView(R.id.fab_transaction_actions) FloatingActionsMenu fab_transaction_actions;
  @InjectView(R.id.fab_transaction_pay) FloatingActionButton fab_transaction_pay;
  @InjectView(R.id.fab_transaction_income) FloatingActionButton fab_transaction_income;
  @InjectView(R.id.fab_transaction_transfer) FloatingActionButton fab_transaction_transfer;

  private FeedbackController feedbackController;

  public FabMainView(Context context) {
    super(context);
    init();
  }

  public FabMainView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public FabMainView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    feedbackController = new FeedbackController(getContext());
    LayoutInflater.from(getContext()).inflate(R.layout.view_fab_main, this);
    initSubViews();
  }

  @Override
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    feedbackController.start();
  }

  @Override
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    feedbackController.stop();
  }

  private void initSubViews() {
    ButterKnife.inject(this);

    hideSubFab();

    rl_fab.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View v) {
        fab_actions.collapse();
      }
    });

    fab_actions.setOnFloatingActionsMenuUpdateListener(
        new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
          @Override public void onMenuExpanded() {
            rl_fab.setBackgroundResource(R.color.white_semi_transparent);
            rl_fab.setClickable(true);
            feedbackController.tryVibrate();
          }

          @Override public void onMenuCollapsed() {
            rl_fab.setBackgroundResource(android.R.color.transparent);
            rl_fab.setClickable(false);
            hideSubFab();
            feedbackController.tryVibrate();
          }
        });

    fab_account_actions.setOnFloatingActionsMenuUpdateListener(
        new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
          @Override public void onMenuExpanded() {
            hideAccountMainButton();
            feedbackController.tryVibrate();
          }

          @Override public void onMenuCollapsed() {
            showAccountMainButton();
            hideAccountSubFab();
            feedbackController.tryVibrate();
          }
        });
    fab_transaction_actions.setOnFloatingActionsMenuUpdateListener(
        new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
          @Override public void onMenuExpanded() {
            hideTransactionMainButton();
            feedbackController.tryVibrate();
          }

          @Override public void onMenuCollapsed() {
            showTransactionMainButton();
            hideTransactionSubFab();
            feedbackController.tryVibrate();
          }
        });
    fab_budget_actions.setOnFloatingActionsMenuUpdateListener(
        new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
          @Override public void onMenuExpanded() {
            hideBudgetMainButton();
            feedbackController.tryVibrate();
          }

          @Override public void onMenuCollapsed() {
            showBudgetMainButton();
            hideBudgetSubFab();
            feedbackController.tryVibrate();
          }
        });

    fab_action_account.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View v) {
        fab_account_actions.setVisibility(VISIBLE);
        fab_account_actions.expand();
      }
    });

    fab_action_transaction.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View v) {
        fab_transaction_actions.setVisibility(VISIBLE);
        fab_transaction_actions.expand();
      }
    });

    fab_action_budget.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View v) {
        fab_budget_actions.setVisibility(VISIBLE);
        fab_budget_actions.expand();
      }
    });

    fab_actions.expand();
    fab_actions.collapse();

    setAccountButtonsClickListeners();
    setTransactionButtonsClickListeners();
    setBudgetButtonsClickListeners();
  }

  private void setTransactionButtonsClickListeners() {
    fab_transaction_pay.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View v) {
        if (onFabMainViewButtonClickListener != null) {
          onFabMainViewButtonClickListener.onTransactionPay();
          fab_actions.collapse();
          feedbackController.tryVibrate();
        }
      }
    });

    fab_transaction_income.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View v) {
        if (onFabMainViewButtonClickListener != null) {
          onFabMainViewButtonClickListener.onTransactionIncome();
          fab_actions.collapse();
          feedbackController.tryVibrate();
        }
      }
    });

    fab_transaction_transfer.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View v) {
        if (onFabMainViewButtonClickListener != null) {
          onFabMainViewButtonClickListener.onTransactionTransfer();
          fab_actions.collapse();
          feedbackController.tryVibrate();
        }
      }
    });
  }

  private void setAccountButtonsClickListeners() {
    fab_account_add.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View v) {
        if (onFabMainViewButtonClickListener != null) {
          onFabMainViewButtonClickListener.onAccountAdd();
          fab_actions.collapse();
          feedbackController.tryVibrate();
        }
      }
    });
  }

  private void setBudgetButtonsClickListeners() {
    fab_budget_add.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View v) {
        if (onFabMainViewButtonClickListener != null) {
          onFabMainViewButtonClickListener.onBudgetAdd();
          fab_actions.collapse();
          feedbackController.tryVibrate();
        }
      }
    });
  }

  private OnFabMainViewButtonClickListener onFabMainViewButtonClickListener;

  public void setOnFabMainViewButtonClickListener(
      OnFabMainViewButtonClickListener onFabMainViewButtonClickListener) {
    this.onFabMainViewButtonClickListener = onFabMainViewButtonClickListener;
  }

  public interface OnFabMainViewButtonClickListener {
    void onAccountAdd();

    void onTransactionIncome();

    void onTransactionPay();

    void onTransactionTransfer();

    void onBudgetAdd();
  }

  private void hideBudgetMainButton() {
    fab_action_budget.setVisibility(INVISIBLE);
  }

  private void hideAccountMainButton() {
    fab_action_account.setVisibility(INVISIBLE);
  }

  private void hideTransactionMainButton() {
    fab_action_transaction.setVisibility(INVISIBLE);
  }

  private void showBudgetMainButton() {
    fab_action_budget.setVisibility(VISIBLE);
  }

  private void showAccountMainButton() {
    fab_action_account.setVisibility(VISIBLE);
  }

  private void showTransactionMainButton() {
    fab_action_transaction.setVisibility(VISIBLE);
  }

  private void hideAccountSubFab() {
    fab_account_actions.collapse();
    fab_account_actions.setVisibility(GONE);
  }

  private void hideTransactionSubFab() {
    fab_transaction_actions.collapse();
    fab_transaction_actions.setVisibility(GONE);
  }

  private void hideBudgetSubFab() {
    fab_budget_actions.collapse();
    fab_budget_actions.setVisibility(GONE);
  }

  private void hideSubFab() {
    hideAccountSubFab();
    hideTransactionSubFab();
    hideBudgetSubFab();
  }
}
