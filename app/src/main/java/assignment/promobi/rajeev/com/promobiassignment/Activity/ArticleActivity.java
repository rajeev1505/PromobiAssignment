package assignment.promobi.rajeev.com.promobiassignment.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import assignment.promobi.rajeev.com.promobiassignment.R;

/**
 * Created by rspl-rajeev on 28/4/18.
 */


public class ArticleActivity extends AppCompatActivity {
    private WebView webView;
    private String currentUrl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_article);

        webView=(WebView)findViewById(R.id.wvArticle);

        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        // Configure the client to use when opening URLs
        webView.setWebViewClient(new MyBrowser());

        // Load the initial URL
        Intent i = getIntent();
        currentUrl = i.getStringExtra("webUrl");
        webView.loadUrl(currentUrl);
        Log.e("WEB URL",currentUrl);
    }
    // Manages the behavior when URLs are loaded
    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
