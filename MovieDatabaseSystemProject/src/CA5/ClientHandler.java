package CA5;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

/**
 * @authors 
 * Patricia Bere - D00193593
 * Oisin Murphy - D00191700
 */

public class ClientHandler implements Runnable {

    private PrintWriter out;
    private Scanner in;
    private Server server;

    public ClientHandler(PrintWriter out, Scanner in, Server server) {
        this.out = out;
        this.in = in;
        this.server = server;
    }

    @Override
    public void run() {
        String msg = in.nextLine();
        String message = "";
        //System.out.println(msg);
        StringTokenizer st = new StringTokenizer(msg, "%%");
        //Example command: Search%%My Left Foot
        String function = st.nextToken();
        if (function.equalsIgnoreCase("search")) {
            try {
                String movie = st.nextToken();
                message = server.search(movie);
                if(message.length() < 25) {
                    message = "This movie is currently not in our database.";
                }
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            out.write(message);
            out.flush();
        } 
        else if (function.equalsIgnoreCase("add")) {
            //have every parameter assigned to a string
            //Example command: Add%%Title%%Genre%%Director%%Runtime%%Plot%%Rating%%Format%%Year%%Starring%%Copies%%Barcode%%User Rating
            String movie = st.nextToken();
            String genre = st.nextToken();
            String director = st.nextToken();
            String runtime = st.nextToken();
            String plot = st.nextToken();
            String rating = st.nextToken();
            String format = st.nextToken();
            String year = st.nextToken();
            String starring = st.nextToken();
            String copies = st.nextToken();
            String barcode = st.nextToken();
            String userRating = st.nextToken();
            try {
                message = server.add(movie, genre, director, runtime, plot, rating, format, year, starring, copies, barcode, userRating);
                //message = server.add(movie);
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            out.write(message);
            out.flush();
        } 
        else if (function.equalsIgnoreCase("remove")) {
            String movie = st.nextToken();
            try {
                message = server.remove(movie);
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            out.write(message);
            out.flush();
        } 
        else if (function.equalsIgnoreCase("update")) {
            String movie = st.nextToken();
            String parameter = st.nextToken();
            String newValue = st.nextToken();
            try {
                message = server.update(movie, parameter, newValue);
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            out.write(message);
            out.flush();
        } 
        else if (function.equalsIgnoreCase("recommend")) {
            Scanner file;
            String fullGenres;
            Set<String> genres = new TreeSet<String>();
            String movie = "";
            int count = 0;
            try {
                file = new Scanner(new File("movies.txt"));
                while(file.hasNextLine() && count <= 5) {
                    movie = file.nextLine();
                    fullGenres = server.getGenre(movie);
                    StringTokenizer stb = new StringTokenizer(fullGenres, ",");
                    while(stb.hasMoreTokens()) {
                        genres.add(stb.nextToken().trim());
                    }
                    count++;
                }
                if(count == 0) {
                    message = "You have no movies in your watchlist.";
                }
                else {
                    message = server.recommend(genres);
                }
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
            catch (SQLException ex) {
                ex.printStackTrace();
            }
            out.write(message);
            out.flush();
        }
        else if (function.equalsIgnoreCase("watch")) {
            String movie = st.nextToken();
            try {
                message = server.watch(movie);
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            out.write(message);
            out.flush();
        }
        else if (function.equalsIgnoreCase("searchdirector")) {
            String movie = st.nextToken();
            try {
                message = server.searchDirector(movie);
                if(message.length() < 25) {
                    message = "This movie is currently not in our database.";
                }
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            out.write(message);
            out.flush();
        }
        else if (function.equalsIgnoreCase("help")) {
            
            message = server.help();

            out.write(message);
            out.flush();
        }
        else if (function.equalsIgnoreCase("adminExit")) {
            server.exit();
        }
        else {
            message = "Please enter a valid command.";
            out.write(message);
            out.flush();
        }
        out.close();
    }
}
