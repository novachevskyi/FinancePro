package com.novachevskyi.expenseslite.presentation.view.fragment;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.novachevskyi.expenseslite.presentation.view.helper.SharedPreferencesHelper;

public abstract class BaseAdMobFragment extends BaseFragment {

  private static final String TRANSACTIONS_LIST_UPPER_BANNER_UNIT_ID =
      "ca-app-pub-3193481913527040/7646918414";
  private static final String BUDGETS_LIST_UPPER_BANNER_UNIT_ID =
      "ca-app-pub-3193481913527040/4693452011";
  private static final String ACCOUNTS_LIST_UPPER_BANNER_UNIT_ID =
      "ca-app-pub-3193481913527040/8831103617";
  private static final String CALENDAR_LIST_UPPER_BANNER_UNIT_ID =
      "ca-app-pub-3193481913527040/3216718810";

  private static final String ADD_TRANSACTION_INTERSTITIAL_UNIT_ID =
      "ca-app-pub-3193481913527040/9123651616";
  private static final String ADD_TRANSFER_INTERSTITIAL_UNIT_ID =
      "ca-app-pub-3193481913527040/16003848126";
  private static final String ADD_ACCOUNT_INTERSTITIAL_UNIT_ID =
      "ca-app-pub-3193481913527040/7214769612";
  private static final String ADD_BUDGET_INTERSTITIAL_UNIT_ID =
      "ca-app-pub-3193481913527040/3077118015";

  private static final String INTERSTITIAL_APP_SHOWS_CURRENT_COUNT_KEY =
      "interstitial_app_shows_current_count_key";
  private static final int INTERSTITIAL_APP_SHOW_VALUE = 5;

  private AdView bannerAdView;
  private InterstitialAd interstitialAdView;

  public interface OnAdViewLoadListener {
    void onLoad(AdView adView);
  }

  public interface OnAdInterstitialLoadListener {
    void onLoad(InterstitialAd adView);
  }

  protected void constructAddBudgetInterstitialAd(OnAdInterstitialLoadListener listener) {
    constructInterstitialAdView(ADD_BUDGET_INTERSTITIAL_UNIT_ID, listener);
  }

  protected void constructAddTransactionInterstitialAd(OnAdInterstitialLoadListener listener) {
    constructInterstitialAdView(ADD_TRANSACTION_INTERSTITIAL_UNIT_ID, listener);
  }

  protected void constructAddTransferInterstitialAd(OnAdInterstitialLoadListener listener) {
    constructInterstitialAdView(ADD_TRANSFER_INTERSTITIAL_UNIT_ID, listener);
  }

  protected void constructAddAccountInterstitialAd(OnAdInterstitialLoadListener listener) {
    constructInterstitialAdView(ADD_ACCOUNT_INTERSTITIAL_UNIT_ID, listener);
  }

  private void constructInterstitialAdView(String unitId,
      final OnAdInterstitialLoadListener listener) {
    int showsCount =
        SharedPreferencesHelper.getInt(getContext(), INTERSTITIAL_APP_SHOWS_CURRENT_COUNT_KEY, 0);

    if ((showsCount + 1) % (INTERSTITIAL_APP_SHOW_VALUE + 1) == 0) {
      interstitialAdView = new InterstitialAd(getContext());
      interstitialAdView.setAdUnitId(unitId);
      interstitialAdView.setAdListener(new AdListener() {
        @Override public void onAdLoaded() {
          listener.onLoad(interstitialAdView);
        }
      });
      interstitialAdView.loadAd(new AdRequest.Builder().build());

      SharedPreferencesHelper.setInt(getContext(), INTERSTITIAL_APP_SHOWS_CURRENT_COUNT_KEY, 0);
    } else {
      SharedPreferencesHelper.setInt(getContext(), INTERSTITIAL_APP_SHOWS_CURRENT_COUNT_KEY,
          showsCount + 1);
    }
  }

  protected void constructTransactionsListUpperBanner(OnAdViewLoadListener listener) {
    constructAdView(TRANSACTIONS_LIST_UPPER_BANNER_UNIT_ID, listener);
  }

  protected void constructBudgetsListUpperBanner(OnAdViewLoadListener listener) {
    constructAdView(BUDGETS_LIST_UPPER_BANNER_UNIT_ID, listener);
  }

  protected void constructAccountsListUpperBanner(OnAdViewLoadListener listener) {
    constructAdView(ACCOUNTS_LIST_UPPER_BANNER_UNIT_ID, listener);
  }

  protected void constructCalendarListUpperBanner(OnAdViewLoadListener listener) {
    constructAdView(CALENDAR_LIST_UPPER_BANNER_UNIT_ID, listener);
  }

  private void constructAdView(String unitId, final OnAdViewLoadListener listener) {
    bannerAdView = new AdView(getContext());
    bannerAdView.setAdUnitId(unitId);
    bannerAdView.setAdSize(AdSize.BANNER);
    bannerAdView.setAdListener(new AdListener() {
      @Override
      public void onAdLoaded() {
        listener.onLoad(bannerAdView);
      }
    });

    bannerAdView.loadAd(new AdRequest.Builder().build());
  }
}
