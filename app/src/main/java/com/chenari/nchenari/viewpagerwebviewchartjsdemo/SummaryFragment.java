package com.chenari.nchenari.viewpagerwebviewchartjsdemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by nimachenari on 11/9/17
 *
 */
public class SummaryFragment extends Fragment {

    private ArrayList<IndvSessWebViewFragment> fragmentArrayList;

    // TAG constant
    private static final String TAG = "SummaryFragment";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_summary, container, false);

        // Populate fragmentArrayList
        fragmentArrayList = new ArrayList<>();
        fragmentArrayList.add(new IndvSessWebViewFragment());
        fragmentArrayList.add(new IndvSessWebViewFragment());
        fragmentArrayList.add(new IndvSessWebViewFragment());
        fragmentArrayList.add(new IndvSessWebViewFragment());

        // Create the adapter that will return a fragment for each of the primary sections of the activity
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());

        // Set up the ViewPager with the SectionsPagerAdapter
        ViewPager mViewPager = v.findViewById(R.id.pager);
        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(mSectionsPagerAdapter.getCount() - 1);

       return v;
    } // end method onCreate


    /**
     * A {@link FragmentStatePagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            return fragmentArrayList.get(position);
        }

        @Override
        public int getCount() {
            // Show as many pages as sessions available
            return fragmentArrayList.size();
        }

        /**
         * @Override
         * public CharSequence getPageTitle(int position) {
         *  return sessionsArrayList.get(position).getLongDateString();
        } */
    }

    public static class IndvSessWebViewFragment extends Fragment {

        // TAG constant
        private static final String TAG = "IndvSessWebViewFragment";

        @SuppressLint("SetJavaScriptEnabled")
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View v = inflater.inflate(R.layout.fragment_indv_page_webview, container, false);

            // initialize webView from layout
            WebView chartWebView = v.findViewById(R.id.chartWebView);
            // enable javaScript for webView
            WebSettings webSettings = chartWebView.getSettings();
            webSettings.setJavaScriptEnabled(true);

            chartWebView.setWebViewClient(new WebViewClient());

            // bind class to JavaScript running in WebView
            chartWebView.addJavascriptInterface(new WebAppInterface(v.getContext()), "Android");
            // webview should avoid using hardware acceleration.
            // chartWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            // Load Chart.js html5 file
            chartWebView.loadUrl("file:///android_asset/www/index.html");

            return v;
        }
    }

    /**
     * WebAppInterface for binding JavaScript code to Android code
     * Creates an interface called Android for JavaScript running in the WebView
     * note: no need to initialize the Android interface from JavaScript.
     * The WebView automatically makes it available to your web page.
     */
    public static class WebAppInterface {
        Context mContext;

        /** Instantiate the interface and set the context */
        WebAppInterface(Context c) {
            mContext = c;
        }

        /** show a toast from the web page */
        @JavascriptInterface
        public void showToast(String toast) {
            Toast.makeText(mContext, toast, Toast.LENGTH_LONG).show();
        }

        @JavascriptInterface
        public void showAlternativeToast(String toast) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        }

        /** send data from java code to Chart.js graphs in WebView as JSON array */

        @JavascriptInterface
        public JSONArray getChartData() {
            String texto = " [375,433,591,644] ";

            JSONArray jsonArray = null;

            try {
                jsonArray = new JSONArray(texto);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return jsonArray;
        }


    }

    /**
     * Custom Animation for viewPager with PageTransformer (Zoom-out page transformer)
     */
    public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 1) { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA +
                        (scaleFactor - MIN_SCALE) /
                                (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }
}
