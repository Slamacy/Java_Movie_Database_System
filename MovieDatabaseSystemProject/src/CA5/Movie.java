package CA5;

import java.util.StringTokenizer;

/**
 * @authors 
 * Patricia Bere - D00193593
 * Oisin Murphy - D00191700
 */

public class Movie {
    private int id; 
    private String title;
    private String genre;
    private String director;
    private String runtime;
    private String plot;
    private String location;
    private String rating;
    private String format;
    private String year;
    private String starring;
    private String copies;
    private String barcode;
    private String user_rating;
    
    public Movie() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getStarring() {
        return starring;
    }

    public void setStarring(String starring) {
        this.starring = starring;
    }

    public String getCopies() {
        return copies;
    }

    public void setCopies(String copies) {
        this.copies = copies;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getUser_rating() {
        return user_rating;
    }

    public void setUser_rating(String user_rating) {
        this.user_rating = user_rating;
    }
    
    public String displayMovie() {
        String display = "**************************************************************************************"
                         + "\nMovie ID: " + this.getId()
                         + "\nTitle: " + this.getTitle()
                         + "\nGenre: " + this.getGenre()
                         + "\nDirector: " + this.getDirector()
                         + "\nRuntime: " + this.getRuntime()
                         + "\nPlot: " + this.formatPlot()
                         + "\nLocation: " + this.getLocation()
                         + "\nRating: " + this.getRating()
                         + "\nFormat: " + this.getFormat()
                         + "\nYear: " + this.getYear()
                         + "\nStarring: " + this.getStarring()
                         + "\nCopies: " + this.getCopies()
                         + "\nBarcode: " + this.getBarcode()
                         + "\nUser rating: " + this.getUser_rating()
                         + "\n**************************************************************************************\n";
        return display;
    }
    //Prints out the movie's details in a nice format
    
    public String formatPlot() {
        String fDesc = plot;
        StringTokenizer st = new StringTokenizer(fDesc, " ");
        String word, formatLine = "";
        int count = 0;
        while (st.hasMoreTokens())
        {
            word = st.nextToken();
            count++;
            formatLine += word + " ";
            if (count % 10 == 0)
            {
                formatLine += "\n      ";
            }
        }
        return formatLine;
    }
}
