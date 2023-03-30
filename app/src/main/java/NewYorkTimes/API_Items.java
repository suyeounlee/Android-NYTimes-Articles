package NewYorkTimes;

public class API_Items {

    private String pub_date;
    private String section_name;
    private String main;
    private String snippet;
    private String image;

    public API_Items() {

    }

    public API_Items(String pub_date, String section_name, String main, String snippet, String image) {
        this.pub_date = pub_date;
        this.section_name = section_name;
        this.main = main;
        this.snippet = snippet;
        this.image = image;
    }

    public void setPub_date(String pub_date) {
        this.pub_date = pub_date;
    }

    public void setSection_name(String section_name) {
        this.section_name = section_name;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPub_date() {
        return pub_date;
    }

    public String getSection_name() {
        return section_name;
    }

    public String getMain() {
        return main;
    }

    public String getSnippet() {
        return snippet;
    }

    public String getImage() {
        return image;
    }
}
