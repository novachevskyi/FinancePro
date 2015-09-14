package com.novachevskyi.expenseslite.presentation.view.adapter.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.novachevskyi.expenseslite.R;
import com.novachevskyi.expenseslite.presentation.model.main.DrawerMenuType;
import java.util.ArrayList;
import java.util.List;

public class DrawerMenuAdapter
    extends RecyclerView.Adapter<DrawerMenuAdapter.DrawerMenuViewHolder> {

  public interface OnItemClickListener {
    void onDrawerMenuItemClicked(DrawerMenuType drawerMenuType);
  }

  private OnItemClickListener onItemClickListener;

  private List<DrawerMenuType> accountsCollection;
  private LayoutInflater layoutInflater;
  private final Context context;

  public DrawerMenuAdapter(Context context) {
    this.context = context;
    if (context != null) {
      this.layoutInflater =
          (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    initItems();
  }

  private void initItems() {
    this.accountsCollection = new ArrayList<>();
    this.accountsCollection.add(DrawerMenuType.GENERAL_REPORT);
    this.accountsCollection.add(DrawerMenuType.CATEGORIES_REPORT);
    this.accountsCollection.add(DrawerMenuType.YEAR_REPORT);
    this.accountsCollection.add(DrawerMenuType.ACCOUNTS);
    this.accountsCollection.add(DrawerMenuType.TRANSACTIONS);
    this.accountsCollection.add(DrawerMenuType.BUDGETS);
  }

  @Override public int getItemCount() {
    return (this.accountsCollection != null) ? this.accountsCollection.size() : 0;
  }

  @Override public DrawerMenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if (this.layoutInflater != null) {
      View view =
          this.layoutInflater.inflate(R.layout.recycler_view_item_drawer_menu, parent, false);

      return new DrawerMenuViewHolder(view);
    }

    return null;
  }

  @Override public void onBindViewHolder(DrawerMenuViewHolder holder, final int position) {
    final DrawerMenuType drawerMenuType = this.accountsCollection.get(position);

    switch (drawerMenuType) {
      case ACCOUNTS:
        holder.tv_name.setText(context.getString(R.string.adapter_drawer_menu_account_header));
        holder.iv_icon.setImageResource(R.drawable.ic_header_account);
        break;
      case TRANSACTIONS:
        holder.tv_name.setText(context.getString(R.string.adapter_drawer_menu_transaction_header));
        holder.iv_icon.setImageResource(R.drawable.ic_header_transaction);
        break;
      case BUDGETS:
        holder.tv_name.setText(context.getString(R.string.adapter_drawer_menu_budget_header));
        holder.iv_icon.setImageResource(R.drawable.ic_header_category);
        break;
      case GENERAL_REPORT:
        holder.tv_name.setText(context.getString(R.string.adapter_drawer_menu_general_header));
        holder.iv_icon.setImageResource(R.drawable.ic_header_general);
        break;
      case YEAR_REPORT:
        holder.tv_name.setText(context.getString(R.string.adapter_drawer_menu_calendar_header));
        holder.iv_icon.setImageResource(R.drawable.ic_header_calendar);
        break;
      case CATEGORIES_REPORT:
        holder.tv_name.setText(context.getString(R.string.adapter_drawer_menu_categories_header));
        holder.iv_icon.setImageResource(R.drawable.ic_header_budget);
        break;
    }

    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (DrawerMenuAdapter.this.onItemClickListener != null) {
          DrawerMenuAdapter.this.onItemClickListener.onDrawerMenuItemClicked(drawerMenuType);
        }
      }
    });
  }

  public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }

  @Override public long getItemId(int position) {
    return position;
  }

  static class DrawerMenuViewHolder extends RecyclerView.ViewHolder {
    @InjectView(R.id.tv_name) TextView tv_name;
    @InjectView(R.id.iv_icon) ImageView iv_icon;

    public DrawerMenuViewHolder(View itemView) {
      super(itemView);
      ButterKnife.inject(this, itemView);
    }
  }
}
