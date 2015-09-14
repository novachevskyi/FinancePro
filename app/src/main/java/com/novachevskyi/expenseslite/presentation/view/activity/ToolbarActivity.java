package com.novachevskyi.expenseslite.presentation.view.activity;

import android.support.v7.widget.Toolbar;
import com.novachevskyi.expenseslite.R;

public abstract class ToolbarActivity extends BaseActivity {
  private Toolbar toolbar;

  protected void initToolbar(String title) {
    toolbar = (Toolbar) findViewById(R.id.toolbar);
    toolbar.setTitle(title);
    configureToolbar();
  }

  private void configureToolbar() {
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  }

  protected void setToolbarTitle(String title) {
    if (toolbar != null) {
      toolbar.setTitle(title);
    }
  }

  protected Toolbar getToolbar() {
    return toolbar;
  }
}
