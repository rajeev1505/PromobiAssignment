package assignment.promobi.rajeev.com.promobiassignment.Rest;



import java.util.Observable;

import assignment.promobi.rajeev.com.promobiassignment.Models.ApiResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;



public interface ApiInterface {

    @GET("articlesearch.json")
    Call<ApiResponse> getArticle(@Query("api_key") String apiKey,
                                 @Query("q") String searchParam,
                                 @Query("sort") String sortOrder,
                                 @Query("begin_date") String beginDate,
                                 @Query("fq") String filterQuery,
                                 @Query("page") int pageNumber
    );


    @GET("articlesearch.json")
    Call<ApiResponse> getAlldata(@Query("api_key") String apiKey,
                                 @Query("q")String searchParam);



    @GET("articlesearch.json")
    rx.Observable<ApiResponse> getObserabledata(@Query("api_key") String apiKey,
                                                @Query("q")String searchParam);





}
