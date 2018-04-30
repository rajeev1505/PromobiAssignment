package assignment.promobi.rajeev.com.promobiassignment.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by rspl-rajeev on 28/4/18.
 */

public class Byline {
    @SerializedName("person")
    @Expose
    private List<Person> person = null;
    @SerializedName("original")
    @Expose
    private String original;
    @SerializedName("organization")
    @Expose
    private String organization;

    public List<Person> getPerson() {
        return person;
    }

    public void setPerson(List<Person> person) {
        this.person = person;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

}
