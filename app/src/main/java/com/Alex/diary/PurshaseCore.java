/*
  Copyright 2014 AnjLab
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
      http://www.apache.org/licenses/LICENSE-2.0
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */
package com.Alex.diary;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.PurchaseInfo;

public class PurshaseCore extends Activity {
    // SAMPLE APP CONSTANTS
    private static final String ACTIVITY_NUMBER = "2";
    private static final String LOG_TAG = "Purshase";

    // PRODUCT & SUBSCRIPTION IDS
    private static final String SUBSCRIPTION_LIFE = "premiumlife";
    private static final String SUBSCRIPTION_YEAR = "premiumyear";
    private static final String SUBSCRIPTION_MONTH = "premium";
    private static final String LICENSE_KEY = Constants.base64EncodedPublicKey; // PUT YOUR MERCHANT KEY HERE;
    // put your Google merchant id here (as stated in public profile of your Payments Merchant Center)
    // if filled library will provide protection against Freedom alike Play Market simulators
    private static final String MERCHANT_ID="17018323048163187791";

    private BillingProcessor bp;
    private boolean readyToPurchase = false;
    private String selected = SUBSCRIPTION_YEAR;




    @SuppressLint("StringFormatInvalid")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premium);

        //TextView title = findViewById(R.id.itemTitle);
        //title.setText(String.format(getString(R.string.Subscription), getIntent().getIntExtra(ACTIVITY_NUMBER, 1)));

        if(!BillingProcessor.isIabServiceAvailable(this)) {
            showToast("Сервис недоступен, обновите Android Market/Play до версии >= 3.9.16");
        }

        bp = new BillingProcessor(this, LICENSE_KEY, MERCHANT_ID, new BillingProcessor.IBillingHandler() {
            @Override
            public void onProductPurchased(@NonNull String productId, @Nullable PurchaseInfo purchaseInfo) {
                //showToast("onProductPurchased: " + productId);
                updateTextViews();
            }
            @Override
            public void onBillingError(int errorCode, @Nullable Throwable error) {
                showToast("Ошибка: " + errorCode);
            }
            @Override
            public void onBillingInitialized() {
                //showToast("onBillingInitialized");
                readyToPurchase = true;
                updateTextViews();
            }
            @Override
            public void onPurchaseHistoryRestored() {
                //showToast("onPurchaseHistoryRestored");
                for(String sku : bp.listOwnedProducts())
                    Log.d(LOG_TAG, "Owned Managed Product: " + sku);
                for(String sku : bp.listOwnedSubscriptions())
                    Log.d(LOG_TAG, "Owned Subscription: " + sku);
                updateTextViews();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        bp.loadOwnedPurchasesFromGoogleAsync(new BillingProcessor.IPurchasesResponseListener() {
            @Override
            public void onPurchasesSuccess() {

            }

            @Override
            public void onPurchasesError() {

            }
        });
        updateTextViews();
    }

    @Override
    public void onDestroy() {
        if (bp != null)
            bp.release();
        super.onDestroy();
    }

    private void updateTextViews() {
        TextView text = findViewById(R.id.cancel);
        text.setOnClickListener(this::onClick);
        //text.setText(String.format("%s is%s purchased", PRODUCT_ID, bp.isPurchased(PRODUCT_ID) ? "" : " not"));
        //text = findViewById(R.id.subscriptionIdTextView);
        findViewById(R.id.exit).setOnClickListener(view -> onBackPressed());
        Button b = findViewById(R.id.yearSub);
        b.setOnClickListener(this::onClick);
        b = findViewById(R.id.lifeSub);
        b.setOnClickListener(this::onClick);
        b = findViewById(R.id.monthSub);
        b.setOnClickListener(this::onClick);
        b = findViewById(R.id.Accept);
        b.setOnClickListener(this::onClick);
        //if (bp.isSubscribed(SUBSCRIPTION_ID) || bp.isPurchased(PRODUCT_ID)){ text.setText("Есть подписка");}
        //else {text.setText("Нет подписки");}
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    public void onClick(View v) {
        if (!readyToPurchase) {
            showToast("Сервис не запущен, перезапустите приложение!");
            return;
        }
        if (v.getId() == R.id.yearSub || v.getId() == R.id.monthSub || v.getId() == R.id.lifeSub){
        findViewById(R.id.selectYear).setVisibility(View.INVISIBLE);
        findViewById(R.id.selectlife).setVisibility(View.INVISIBLE);
        findViewById(R.id.selectmonth).setVisibility(View.INVISIBLE);}
        if (v.getId() == R.id.yearSub) findViewById(R.id.selectYear).setVisibility(View.VISIBLE);
        else if (v.getId() == R.id.monthSub) findViewById(R.id.selectmonth).setVisibility(View.VISIBLE);
        else if (v.getId() == R.id.lifeSub) findViewById(R.id.selectlife).setVisibility(View.VISIBLE);
        if (v.getId() == R.id.yearSub) selected = SUBSCRIPTION_YEAR;
        else if (v.getId() == R.id.monthSub) selected = SUBSCRIPTION_MONTH;
        else if (v.getId() == R.id.lifeSub)  selected = SUBSCRIPTION_LIFE;
        if (v.getId() == R.id.cancel) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/account/subscriptions"));
            startActivity(browserIntent);
        }
        else if (v.getId() == R.id.Accept)
            if (selected == SUBSCRIPTION_LIFE) bp.purchase(this, selected);
            else bp.subscribe(this, selected);
        /*
        if (v.getId() == R.id.lifeSub) {
            bp.purchase(this, SUBSCRIPTION_LIFE);
        }

        else if (v.getId() == R.id.productDetailsButton) {
            bp.getPurchaseListingDetailsAsync(PRODUCT_ID, new BillingProcessor.ISkuDetailsResponseListener() {
                @Override
                public void onSkuDetailsResponse(@Nullable List<SkuDetails> products) {
                    if (products != null && !products.isEmpty()) {
                        showToast(products.get(0).toString());
                    } else {
                        showToast("Failed to load SKU details");
                    }
                }

                @Override
                public void onSkuDetailsError(String error) {
                    showToast(error);
                }
            });
        }
        if (v.getId() == R.id.yearSub) {
            bp.subscribe(this,SUBSCRIPTION_YEAR);
        }

        else if (v.getId() == R.id.updateSubscriptionsButton) {
            bp.loadOwnedPurchasesFromGoogleAsync(new BillingProcessor.IPurchasesResponseListener() {
                @Override
                public void onPurchasesSuccess()
                {
                    showToast("Subscriptions updated.");
                    updateTextViews();
                }

                @Override
                public void onPurchasesError()
                {
                    showToast("Subscriptions update eror.");
                    updateTextViews();
                }
            });
        }
        /*
        else if (v.getId() == R.id.subsDetailsButton) {
            bp.getSubscriptionListingDetailsAsync(SUBSCRIPTION_ID, new BillingProcessor.ISkuDetailsResponseListener()
            {
                @Override
                public void onSkuDetailsResponse(@Nullable final List<SkuDetails> products) {
                    showToast(products != null ? products.toString() : "Failed to load subscription details");
                }

                @Override
                public void onSkuDetailsError(String string) {
                    showToast(string);
                }
            });
        }*/

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public boolean Check(Context context){
        BillingProcessor bps = new BillingProcessor(context, LICENSE_KEY, MERCHANT_ID, new BillingProcessor.IBillingHandler() {
            @Override
            public void onProductPurchased(@NonNull String productId, @Nullable PurchaseInfo purchaseInfo) {
                //showToast("onProductPurchased: " + productId);
            }
            @Override
            public void onBillingError(int errorCode, @Nullable Throwable error) {
            }
            @Override
            public void onBillingInitialized() {
            }
            @Override
            public void onPurchaseHistoryRestored() {
                //showToast("onPurchaseHistoryRestored");
                //for(String sku : bps.listOwnedProducts())
                //    Log.d(LOG_TAG, "Owned Managed Product: " + sku);
                //for(String sku : bps.listOwnedSubscriptions())
                //    Log.d(LOG_TAG, "Owned Subscription: " + sku);
            }
        });
        if (bps.isSubscribed(SUBSCRIPTION_YEAR) || bps.isSubscribed(SUBSCRIPTION_MONTH) || bps.isPurchased(SUBSCRIPTION_LIFE)) return true;
        else {
            Toast.makeText(context, "message", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}