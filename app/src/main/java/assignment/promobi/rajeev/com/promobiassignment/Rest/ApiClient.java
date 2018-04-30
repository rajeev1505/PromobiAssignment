package assignment.promobi.rajeev.com.promobiassignment.Rest;



import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {

    public static final String BASE_URL = "https://api.nytimes.com/svc/search/v2/";
    //https://api.nytimes.com/svc/archive/v1
    private static Retrofit retrofit = null;

    public static  Retrofit getClient(){
        if(retrofit == null){

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
