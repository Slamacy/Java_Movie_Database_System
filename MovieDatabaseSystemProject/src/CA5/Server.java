package CA5;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.sql.*;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import javax.json.JsonObject;

/**
 * @authors 
 * Patricia Bere - D00193593
 * Oisin Murphy - D00191700
 */

public class Server {
    //if the user types "help!" then a box will pop up telling them about everything
    private static final int PORT = 12345;
    private static Connection con = null;
    private static Database database = new Database();
    private static byte[] rd = new byte[1024];
    private static byte[] sd = new byte[2048];
    public static User user;
    public static TreeMap<String, JsonObject> cachedMovies;
    
//    private static DatagramSocket ss = null;
    
    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
        Server sv = new Server();
        
        initializeUser();
        sv.initializeServer(PORT);
    }
    
    public void initializeServer(int i) throws IOException, ClassNotFoundException, SQLException {
        ServerSocket ss = new ServerSocket(i);
        while (true) {
            System.out.println("Server Online!");
            Socket incoming = ss.accept();
            PrintWriter out = new PrintWriter(incoming.getOutputStream());
            cachedMovies = new TreeMap<>();
            Scanner in = new Scanner(incoming.getInputStream());
            ClientHandler ch = new ClientHandler(out, in, this);
            Thread t = new Thread(ch);
            t.start();
        }
    }
    
    public static String search(String movie) throws ClassNotFoundException, SQLException, SocketException, IOException {
        //finds and displays a movie's info based on its name
        String query = "SELECT * FROM `movies` WHERE title = '" + movie + "';"; 
        String message = "";
        if(cachedMovies.containsKey(query)) {
            JsonObject cachedMovieArr = cachedMovies.get(query);
            message += cachedMovieArr.toString(); 
        }
        else {
            JsonObject movies = database.search(movie);
            cachedMovies.put(query, movies);
            message += movies.toString(); 
        } 
        //System.out.println(message);
        System.out.println("Request complete.");
        return(message);
    }
    
    
    public static String searchDirector(String director) throws ClassNotFoundException, SQLException, SocketException, IOException {
        //finds and displays a movie's info based on its name
        String query = "SELECT * FROM `movies` WHERE director = '" + director + "';"; 
        String message = "";
        if(cachedMovies.containsKey(query)) {
            JsonObject cachedMovieArr = cachedMovies.get(query);
            message += cachedMovieArr.toString(); 
        }
        else {
            JsonObject movies = database.searchDirector(director);
            cachedMovies.put(query, movies);
            message += movies.toString(); 
        } 
        //System.out.println(message);
        System.out.println("Request complete.");
        return(message);
    }
    
    public static String add(String movie, String genre, String director, String runtime, String plot, String rating, String format, String year, String starring, String copies, String barcode, String userRating) throws ClassNotFoundException, SQLException {
        //add a movie to the database -> will ask you to input all other details below
        //Example command: Add%%Title%%Genre%%Director%%Runtime%%Plot%%Rating%%Format%%Year%%Starring%%Copies%%Barcode%%User Rating
        boolean added = database.add(movie, genre, director, runtime, plot, rating, format, year, starring, copies, barcode, userRating);
        String message = "";
        if(added == true) {
            message = "Addition successful.";
        }
        else {
            message = "Addition unsuccessful.";
        }
        System.out.println("Request complete.");
        return(message);
    }
    
    public static String remove(String movie) throws IOException, ClassNotFoundException, SQLException {
        //remove a movie from the database by Title
        boolean deleted = database.remove(movie);
        String message = "";
        if(deleted == true) {
            message = "Delete successful.";
        }
        else {
            message = "Delete unsuccessful.";
        }
        System.out.println("Request complete.");
        return(message);
    }
    
    public static String update(String movie, String parameter, String newValue) throws ClassNotFoundException, SQLException, IOException {
        //updates a movie's info (1 at a time)
        boolean updated = database.update(movie, parameter, newValue);
        String message = "";
        if(updated == true) {
            message = "Update successful.";
        }
        else {
            message = "Update unsuccessful.";
        }
        System.out.println("Request complete.");
        return(message);
    }
    
    public static String help() {
        //help commands explained
        String help = "**Command Help**~n"
                + "Search command = To search for a movie type 'search%%(movie title)'~n"
                + "SearchDirector command = To search for movies by a particular director, type 'searchDirector%%(movie title)'~n"
                + "Add command = To add new movies type 'add%%(movie title)&&(movie genre)%%(movie director)&&(movie runtime)%%(movie plot)&&(movie rating)%%(movie format)%%(release year)%%(starring actors)%%(copies)%%(barcode)%%(user rating)'~n"
                + "Remove command = To remove a movie type 'remove%%(movie title)'~n"
                + "Update command = To update a movie field type 'update%%(movie title)%%title(new movie title)' example : Update%%My Left Foot%%Title%%My Right Arm~n"
                + "Recommend command = to get recommended movies type 'recommend'~n"
                + "Watch command = to mark a movie as watched type 'watch%%(movie title)'~n"
                + "Help command = to display all available commands type 'help'~n";
        return help;
    }
    
    public static String getGenre(String movie) throws ClassNotFoundException, SQLException {
        String genre = database.getGenre(movie);
        return genre;
    }
    
    public static String recommend(Set genres) throws ClassNotFoundException, SQLException {
        JsonObject movies = database.recommend(genres);
        String message = "";
        message += movies.toString(); 
        //System.out.println(message);
        System.out.println("Request complete.");
        return(message);
    }
    
    public static String watch(String movie) throws ClassNotFoundException, SQLException {
        //marks a movie as watched for the user
        String message = "";
        boolean added = false;
        try {
            added = user.addMovie(movie);
            user.writeMoviesToFile();
            if(added == true) {
                message = movie + " has been added to your watch list.";
            }
            if(added == false) {
                message = movie + " is already in your watch list.";
            }
        } catch(Exception e) {
            message = movie + " could not be added to your watch list.";
        }
        System.out.println("Request complete.");
        return(message);
    }
    
    public static void initializeUser() {
        user = new User("User");
        user.readMoviesFromFile();
    }
    
    public static void exit() {
        System.out.println("Server closed.");
        System.exit(0);
    }
}
