package assignment.promobi.rajeev.com.promobiassignment.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


import assignment.promobi.rajeev.com.promobiassignment.Adapter.CompleteAdapter;
import assignment.promobi.rajeev.com.promobiassignment.Models.ApiResponse;
import assignment.promobi.rajeev.com.promobiassignment.Models.Article;
import assignment.promobi.rajeev.com.promobiassignment.R;
import assignment.promobi.rajeev.com.promobiassignment.Rest.ApiClient;
import assignment.promobi.rajeev.com.promobiassignment.Rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rspl-rajeev on 1/2/18.
 */

public class FragmentComplete extends Fragment{

    private RecyclerView mRecyclerView;
    private CompleteAdapter mAdapter;



    List<Article> articles;

    private final static String API_KEY = "beb70f49a21a4ccb92b7ec62a03f054e";

    private static final String TAG = FragmentComplete.class.getSimpleName();



    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_complete, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycleviewcomplete);
        mRecyclerView.isVerticalScrollBarEnabled();
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        articles=new ArrayList<>();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

      getArticles("Our",0);






    }


    public  void getArticles(String searchText, @Nullable final Integer offset ){



        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<ApiResponse> call = apiService.getArticle(API_KEY,"Our",
                "newest",
                "20160111",
                "r",
                offset);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                try {
                    if(response!= null && response.body()!= null) {
                        articles = response.body().getResponse().getArticles();
                        mAdapter = new CompleteAdapter(getContext(),articles);
                        mRecyclerView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                       }else {
                        Toast.makeText(getContext(),"There are no articles with the criteria specified",Toast.LENGTH_LONG).show();
                    }
                }catch (Exception ex){
                    Log.e(TAG,ex.getStackTrace().toString());
                    Toast.makeText(getContext(),ex.toString(),Toast.LENGTH_LONG).show();
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(getContext(),"Issues connecting to API",Toast.LENGTH_SHORT).show();
                Log.e(TAG,t.toString());
            }
        });
    }



}





