package algonquin.cst2335.group_project.nytimes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Represents an item from the NY Times API.
 * Stores information such as publication date, section name, headline, URL, and a brief summary(snippet).
 *
 * @author Su Yeoun Lee
 */
@Entity
public class API_Items {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    protected int id;

    @ColumnInfo(name = "pub_date")
    private String pub_date;
    @ColumnInfo(name = "section_name")
    private String section_name;
    @ColumnInfo(name = "headline")
    private String headline;
    @ColumnInfo(name = "url")
    private String url;
    @ColumnInfo(name = "snippet")
    private String snippet;

    /**
     * Default constructor for the API_Items class.
     */
    public API_Items() {

    }

    /**
     * Constructs an API_Items object with the specified parameters.
     *
     * @param pub_date     The publication date of the API item.
     * @param section_name The name of the section the API item belongs to.
     * @param headline     The headline of the API item.
     * @param url          The URL of the API item.
     * @param snippet      A snippet(brief summary) of the API item.
     */
    public API_Items(String pub_date, String section_name, String headline, String url, String snippet) {
        this.pub_date = pub_date;
        this.section_name = section_name;
        this.headline = headline;
        this.url = url;
        this.snippet = snippet;

    }

    /**
     * Sets the ID of the item.
     *
     * @param id The ID of the item.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the ID of the item.
     *
     * @return The ID of the item.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the publication date of the item.
     *
     * @param pub_date The publication date of the item.
     */
    public void setPub_date(String pub_date) {
        this.pub_date = pub_date;
    }

    /**
     * Sets the name of the section the item belongs to.
     *
     * @param section_name The name of the section the item belongs to.
     */
    public void setSection_name(String section_name) {
        this.section_name = section_name;
    }

    /**
     * Sets the headline of the item.
     *
     * @param headline The headline of the item.
     */
    public void setHeadline(String headline) {
        this.headline = headline;
    }

    /**
     * Sets a snippet of the item.
     *
     * @param snippet A brief summary of the item.
     */
    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    /**
     * Sets the URL of the item.
     *
     * @param url The URL of the item.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Gets the publication date of the item.
     *
     * @return The publication date of the item.
     */
    public String getPub_date() {
        return pub_date;
    }

    /**
     * Gets the name of the section the item belongs to.
     *
     * @return The name of the section the item belongs to.
     */
    public String getSection_name() {
        return section_name;
    }

    /**
     * Gets the headline of the item.
     *
     * @return The headline of the item.
     */
    public String getHeadline() {
        return headline;
    }

    /**
     * Gets the snippet of the item.
     *
     * @return The snippet of the item.
     */
    public String getSnippet() {
        return snippet;
    }

    /**
     * Gets the Url of the item.
     *
     * @return The Url of the item.
     */
    public String getUrl() {
        return url;
    }
}
