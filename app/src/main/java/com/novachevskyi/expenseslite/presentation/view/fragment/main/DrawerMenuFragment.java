package com.novachevskyi.expenseslite.presentation.view.fragment.main;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.novachevskyi.expenseslite.R;
import com.novachevskyi.expenseslite.presentation.model.main.DrawerMenuType;
import com.novachevskyi.expenseslite.presentation.utils.purchases.PremiumStatus;
import com.novachevskyi.expenseslite.presentation.view.adapter.ListLayoutManager;
import com.novachevskyi.expenseslite.presentation.view.adapter.main.DrawerMenuAdapter;
import com.novachevskyi.expenseslite.presentation.view.fragment.BaseFragment;

public class DrawerMenuFragment extends BaseFragment {

  @InjectView(R.id.rv_list) RecyclerView rv_list;

  @InjectView(R.id.iv_icon) ImageView iv_icon;
  @InjectView(R.id.tv_name) TextView tv_name;
  @InjectView(R.id.rl_bottom) RelativeLayout rl_bottom;
  @InjectView(R.id.rl_premium) RelativeLayout rl_premium;

  public static DrawerMenuFragment newInstance() {
    DrawerMenuFragment drawerMenuFragment = new DrawerMenuFragment();

    Bundle argumentsBundle = new Bundle();
    drawerMenuFragment.setArguments(argumentsBundle);

    return drawerMenuFragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override protected void initializePresenter() {

  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View fragmentView = inflater.inflate(R.layout.fragment_drawer_menu_list, container, false);
    ButterKnife.inject(this, fragmentView);

    initializeList();
    initializeBottomButton();
    initializePremiumButton();

    return fragmentView;
  }

  private void initializePremiumButton() {
    if (PremiumStatus.getInstance().getIsPremium(getContext())) {
      rl_premium.setVisibility(View.GONE);
    } else {
      rl_premium.setVisibility(View.VISIBLE);
      rl_premium.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          becomePremium();
        }
      });
    }
  }

  private void becomePremium() {
    if (onItemClickListener != null) {
      onItemClickListener.onPremium();
    }
  }

  private void initializeBottomButton() {
    iv_icon.setImageResource(R.drawable.ic_header_logout);
    tv_name.setText(getContext().getString(R.string.fragment_drawer_menu_logout_title));

    rl_bottom.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (onItemClickListener != null) {
          onItemClickListener.onLogout();
        }
      }
    });
  }

  private void initializeList() {
    ListLayoutManager listLayoutManager = new ListLayoutManager(getActivity());
    this.rv_list.setLayoutManager(listLayoutManager);

    DrawerMenuAdapter drawerMenuAdapter = new DrawerMenuAdapter(getActivity());
    this.rv_list.setAdapter(drawerMenuAdapter);

    drawerMenuAdapter.setOnItemClickListener(new DrawerMenuAdapter.OnItemClickListener() {
      @Override public void onDrawerMenuItemClicked(DrawerMenuType drawerMenuType) {
        if (onItemClickListener != null) {
          onItemClickListener.onDrawerMenuItemClicked(drawerMenuType);
        }
      }
    });
  }

  public interface OnItemClickListener {
    void onDrawerMenuItemClicked(DrawerMenuType drawerMenuType);

    void onLogout();

    void onPremium();
  }

  private OnItemClickListener onItemClickListener;

  public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }
}
