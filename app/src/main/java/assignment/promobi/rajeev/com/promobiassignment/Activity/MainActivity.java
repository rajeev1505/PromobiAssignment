package assignment.promobi.rajeev.com.promobiassignment.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Observable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import assignment.promobi.rajeev.com.promobiassignment.Adapter.CompleteAdapter;
import assignment.promobi.rajeev.com.promobiassignment.Filters.ArticleFilter;
import assignment.promobi.rajeev.com.promobiassignment.Fragment.FragmentComplete;
import assignment.promobi.rajeev.com.promobiassignment.Fragment.SettingsFragment;
import assignment.promobi.rajeev.com.promobiassignment.Helper.EndlessRecyclerViewScrollListener;
import assignment.promobi.rajeev.com.promobiassignment.Models.ApiResponse;
import assignment.promobi.rajeev.com.promobiassignment.Models.Article;
import assignment.promobi.rajeev.com.promobiassignment.R;
import assignment.promobi.rajeev.com.promobiassignment.Rest.ApiClient;
import assignment.promobi.rajeev.com.promobiassignment.Rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DrawerLayout drawer;
    private TextView tvEmpName;
    private TextView tvEmpEmail;
    String EmpNAME,EmpEMAIL;
    WebView webView;
    private String currentUrl ="";
    ProgressBar loadProgress;

    private RecyclerView mRecyclerView;
    private CompleteAdapter mAdapter;
    Handler mHandler;
    String mQueryString;
    private EndlessRecyclerViewScrollListener scrollListener;



    List<Article> articles;

    private final static String API_KEY = "beb70f49a21a4ccb92b7ec62a03f054e";

    private static final String TAG = FragmentComplete.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView)findViewById(R.id.recycleviewcomplete);
        mRecyclerView.isVerticalScrollBarEnabled();
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mHandler = new Handler(Looper.getMainLooper());
        articles=new ArrayList<>();
        scrollListener = new EndlessRecyclerViewScrollListener((LinearLayoutManager) layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                final int pageNo = page;
                mHandler.removeCallbacksAndMessages(null);

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(mQueryString!=null){
                            if(mQueryString.length() > 1) {
                                //GetArticles
                                getArticles(mQueryString,pageNo);
                            }
                        }
                    }}, 300);

            }
        };
        mRecyclerView.addOnScrollListener(scrollListener);

        getArticles("today",20);


        /*viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);*/
//


    }



    public  void getArticles(String searchText, @Nullable final Integer offset ){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<ApiResponse> call = apiService.getAlldata(API_KEY,searchText);

        loadProgress = (ProgressBar) findViewById(R.id.progressBar);
        loadProgress.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                try {
                    if(response!= null && response.body()!= null) {
                        articles = response.body().getResponse().getArticles();
                        mAdapter = new CompleteAdapter(getApplicationContext(),articles);

                        mRecyclerView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                        loadProgress.setVisibility(View.GONE);
                        for (Article article:articles) {

                            Log.e("#####",article.getHeadline().getMain());

                        }


                    }else {
                        Toast.makeText(getApplicationContext(),"There are no articles with the criteria specified",Toast.LENGTH_LONG).show();
                    }
                }catch (Exception ex){
                    Log.e(TAG,ex.getStackTrace().toString());
                    Toast.makeText(getApplicationContext(),ex.toString(),Toast.LENGTH_LONG).show();
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Issues connecting to API",Toast.LENGTH_SHORT).show();
                Log.e(TAG,t.toString());
            }
        });
    }




    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentComplete(),"Completed");


        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter
    {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        private final List<Integer> mFragmentIcon = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
        public void addFragment(android.support.v4.app.Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);



        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }


    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }else if (id == R.id.action_filter){

            FragmentManager fragMan = getSupportFragmentManager();

            SettingsFragment fragment = new SettingsFragment();

            fragment.show(fragMan,"fragment_settings");

            return  true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.main, menu);

        //Get searchmenu item
        MenuItem searchItem = menu.findItem(R.id.action_search);

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        //set listener for SearchView
        setListeners(searchView);

        return super.onCreateOptionsMenu(menu);
    }

    public void setListeners(SearchView searchView){

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String newText) {
                mQueryString = newText;
                if(mQueryString.length() == 0 ){
                    mAdapter.clearArticles();
                    mAdapter.notifyDataSetChanged();
                    // mAdapter.resetState();
                    return true;
                }
                mHandler.removeCallbacksAndMessages(null);

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(mQueryString.length() > 1) {
                            //GetArticles
                            getArticles(mQueryString,0);
                        }
                    }
                }, 300);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {



                return true;
            }

        });
    }





}
