package assignment.promobi.rajeev.com.promobiassignment.Filters;

import java.text.SimpleDateFormat;
import java.util.Date;



public class ArticleFilter {

    private ArticleFilter(){};

    private static ArticleFilter articleFilterInstance = null;

    public static synchronized ArticleFilter getArticleFilterInstance(){
        if(articleFilterInstance == null)
            synchronized (ArticleFilter.class){
                if(articleFilterInstance == null){
                    articleFilterInstance = new ArticleFilter();
                }
            }

        return articleFilterInstance;
    }

    private String beginDate;


    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }


    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    private String sortOrder;

    public String getBeginDate() {
        return this.beginDate;
    }

    public String getSortOrder() {
        return this.sortOrder;
    }

    public String getFilterBeginDate(){
        Date date = new Date(this.beginDate);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        return dateFormat.format(date);
    }

    public boolean isArts() {
        return isArts;
    }

    public void setArts(boolean arts) {
        isArts = arts;
    }

    private boolean isArts;

    private boolean isFashion;

    public boolean isFashion() {
        return isFashion;
    }

    public void setFashion(boolean fashion) {
        isFashion = fashion;
    }

    public boolean isSports() {
        return isSports;
    }

    public void setSports(boolean sports) {
        isSports = sports;
    }

    private boolean isSports;

    public String getFilterQuery(){
        String filterQuery = null;
        if(isArts || isFashion || isSports){
            filterQuery = "news_desk:(";
            if(isArts){
                filterQuery += "\"Arts\"";
            }
            if(isSports){
                filterQuery +=" \"Sports\"";
            }
            if(isFashion){
                filterQuery += "\"Fashion & Style\"";
            }

            filterQuery += ")";
        }
        return  filterQuery;
    }
}
