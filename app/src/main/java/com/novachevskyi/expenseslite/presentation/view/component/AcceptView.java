package com.novachevskyi.expenseslite.presentation.view.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.novachevskyi.expenseslite.R;

public class AcceptView extends RelativeLayout {

  @InjectView(R.id.rl_main) RelativeLayout rl_main;

  public AcceptView(Context context) {
    super(context);
    init();
  }

  public AcceptView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public AcceptView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    LayoutInflater.from(getContext()).inflate(R.layout.view_accept, this);
    initSubViews();
  }

  private void initSubViews() {
    ButterKnife.inject(this);

    rl_main.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View v) {
        if (onAcceptClickListener != null) {
          onAcceptClickListener.onAccept();
        }
      }
    });
  }

  private OnAcceptClickListener onAcceptClickListener;

  public void setOnAcceptClickListener(OnAcceptClickListener onAcceptClickListener) {
    this.onAcceptClickListener = onAcceptClickListener;
  }

  public interface OnAcceptClickListener {
    void onAccept();
  }
}
