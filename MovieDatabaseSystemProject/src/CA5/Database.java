package CA5;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonArrayBuilder;

/**
 * @authors 
 * Patricia Bere - D00193593
 * Oisin Murphy - D00191700
 */

//DAO
public class Database {
    private static final String SERVER = "localhost";
    private static final int PORT = 3306;
    private static final String DATABASE = "movies";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private String connectionString;
    
    public Database() {
        connectionString = "Jdbc:mysql://" + SERVER + ":" + PORT + "/"+ DATABASE;
    }
    
    public JsonObject search(String movie) throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection(connectionString, USERNAME, PASSWORD);
        Statement stmt = con.createStatement();
        ResultSet results = stmt.executeQuery("SELECT * FROM `movies` WHERE title = '" + movie + "';"); 
        //ArrayList<JsonObject> movies = new ArrayList();
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        
        while(results.next()) {
            objectBuilder.add("ID", results.getInt("id"));
            objectBuilder.add("Title", results.getString("title") + "");
            objectBuilder.add("Genre", results.getString("genre") + "");
            objectBuilder.add("Director", results.getString("director") + "");
            objectBuilder.add("Runtime", results.getString("runtime") + "");
            objectBuilder.add("Plot", results.getString("plot") + "");
            objectBuilder.add("Location", results.getString("location") + "");
            objectBuilder.add("Rating", results.getString("rating") + "");
            objectBuilder.add("Format", results.getString("format") + "");
            objectBuilder.add("Year", results.getString("year") + "");
            objectBuilder.add("Starring", results.getString("starring") + "");
            objectBuilder.add("Copies", results.getString("copies") + "");
            objectBuilder.add("Barcode", results.getString("barcode") + "");
            objectBuilder.add("User Rating", results.getString("user_rating") + "");
            JsonObject mov = objectBuilder.build();
            arrayBuilder.add(mov);
        }
        JsonArray movies = arrayBuilder.build();
        objectBuilder.add("Movies", movies);
        JsonObject allMovies = objectBuilder.build();
        con.close();
        return allMovies;
    }
    
    //used to get movies with a specific genre.
    public JsonObject recommend(Set genres) throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection(connectionString, USERNAME, PASSWORD);
        Statement stmt = con.createStatement();
        Object[] genresArr = genres.toArray();
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        int count = 0;
        for (Object genreObj : genresArr) {
            String genre = genreObj + "";
            ResultSet results = stmt.executeQuery("SELECT * FROM `movies` WHERE genre like '" + genre + "%' OR genre like '%" + genre + "%';");
            //ArrayList<JsonObject> movies = new ArrayList();
            while(results.next() && count <= 10) {
                objectBuilder.add("ID", results.getInt("id"));
                objectBuilder.add("Title", results.getString("title") + "");
                objectBuilder.add("Genre", results.getString("genre") + "");
                objectBuilder.add("Director", results.getString("director") + "");
                objectBuilder.add("Runtime", results.getString("runtime") + "");
                objectBuilder.add("Plot", results.getString("plot") + "");
                objectBuilder.add("Location", results.getString("location") + "");
                objectBuilder.add("Rating", results.getString("rating") + "");
                objectBuilder.add("Format", results.getString("format") + "");
                objectBuilder.add("Year", results.getString("year") + "");
                objectBuilder.add("Starring", results.getString("starring") + "");
                objectBuilder.add("Copies", results.getString("copies") + "");
                objectBuilder.add("Barcode", results.getString("barcode") + "");
                objectBuilder.add("User Rating", results.getString("user_rating") + "");
                JsonObject mov = objectBuilder.build();
                arrayBuilder.add(mov);
                count++;
            }
        }
        JsonArray movies = arrayBuilder.build();
        objectBuilder.add("Movies", movies);
        JsonObject allMovies = objectBuilder.build();
        con.close();
        return allMovies;
    }
    
    public JsonObject searchDirector(String director) throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection(connectionString, USERNAME, PASSWORD);
        Statement stmt = con.createStatement();
        ResultSet results = stmt.executeQuery("SELECT * FROM `movies` WHERE director = '" + director + "';"); 
        //ArrayList<JsonObject> movies = new ArrayList();
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        
        while(results.next()) {
            objectBuilder.add("ID", results.getInt("id"));
            objectBuilder.add("Title", results.getString("title") + "");
            objectBuilder.add("Genre", results.getString("genre") + "");
            objectBuilder.add("Director", results.getString("director") + "");
            objectBuilder.add("Runtime", results.getString("runtime") + "");
            objectBuilder.add("Plot", results.getString("plot") + "");
            objectBuilder.add("Location", results.getString("location") + "");
            objectBuilder.add("Rating", results.getString("rating") + "");
            objectBuilder.add("Format", results.getString("format") + "");
            objectBuilder.add("Year", results.getString("year") + "");
            objectBuilder.add("Starring", results.getString("starring") + "");
            objectBuilder.add("Copies", results.getString("copies") + "");
            objectBuilder.add("Barcode", results.getString("barcode") + "");
            objectBuilder.add("User Rating", results.getString("user_rating") + "");
            JsonObject mov = objectBuilder.build();
            arrayBuilder.add(mov);
        }
        JsonArray movies = arrayBuilder.build();
        objectBuilder.add("Movies", movies);
        JsonObject allMovies = objectBuilder.build();
        con.close();
        return allMovies;
    }
    
    public boolean remove(String movie) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection(connectionString, USERNAME, PASSWORD);
        Statement stmt = con.createStatement();
        boolean deleted = false;
        try {
            stmt.execute("DELETE FROM `movies` WHERE title = '" + movie + "';");
            deleted = true;
        } catch(Exception e) {
            deleted = false;
        }
        con.close();
        return deleted;
    }
    
    public boolean update(String movie, String parameter, String newValue) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection(connectionString, USERNAME, PASSWORD);
        Statement stmt = con.createStatement();
        boolean updated = false;
        try {
            stmt.execute("UPDATE `movies` SET " + parameter + " = '" + newValue + "' WHERE title = '" + movie + "';");
            updated = true;
        } catch(Exception e) {
            e.printStackTrace();
            updated = false;
        }
        con.close();
        return updated;
    }
    
    public boolean add(String movie, String genre, String director, String runtime, String plot, String rating, String format, String year, String starring, String copies, String barcode, String userRating) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection(connectionString, USERNAME, PASSWORD);
        Statement stmt = con.createStatement();
        boolean added = false;
        try {
            stmt.execute("INSERT INTO movies (title, genre, director, runtime, plot, rating, format, year, starring, copies, barcode, user_rating) VALUES('" + movie + "', '" + genre + "', '" + director + "', '" + runtime + "',' " + plot + "', '" + rating + "', '" + format + "', '" + year + "', '" + starring + "', " + copies + ", '" + barcode + "', '" + userRating + "');");
            added = true;
        } catch(Exception e) {
            e.printStackTrace();
            added = false;
        }
        con.close();
        return added;
    }
    
    public String getGenre(String movie) throws ClassNotFoundException, ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection(connectionString, USERNAME, PASSWORD);
        Statement stmt = con.createStatement();
        String genres = "";
        try {
            ResultSet results = stmt.executeQuery("SELECT genre FROM movies WHERE title = '" + movie + "';");
            while(results.next()) {
                genres = results.getString(1);
            }
        }
        catch (SQLException e){
            genres = "Fetch failed.";
        }
        return genres;
    }
}