package assignment.promobi.rajeev.com.promobiassignment.Activity;

import android.app.Application;



import java.text.SimpleDateFormat;
import java.util.Calendar;

import assignment.promobi.rajeev.com.promobiassignment.Filters.ArticleFilter;

/**
 * Created by rspl-rajeev on 28/4/18.
 */

public class NYTimesApp extends Application {

    public void onCreate(){
        super.onCreate();

        setFilterSettings();


    }

    private void setFilterSettings(){
        ArticleFilter settings = ArticleFilter.getArticleFilterInstance();

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        settings.setBeginDate(dateFormat.format(cal.getTime()));

        settings.setSortOrder("newest");
    }
}
