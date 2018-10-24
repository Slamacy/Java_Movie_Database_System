package CA5;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @authors 
 * Patricia Bere - D00193593
 * Oisin Murphy - D00191700
 */

public class User {
    
    private String username;
    private ArrayList<String> watchedMovies;
    
    public User(String username)
    {
        this.username = username;
        this.watchedMovies = new ArrayList();
//        for(String i : movies)
//        {
//            movies.add(i);
//        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<String> getWatchedMovies() {
        return watchedMovies;
    }

    public void setWatchedMovies(ArrayList<String> WatchedMovies) {
        this.watchedMovies = WatchedMovies;
    }
    
    public void writeMoviesToFile() {
        try(FileWriter fw = new FileWriter("movies.txt")) {
            PrintWriter print = new PrintWriter(new BufferedWriter(fw));
            for(String m: this.watchedMovies) {
                print.println(m);
            }
            print.flush();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public boolean addMovie(String movie) {
        boolean added = false;
        if(!this.watchedMovies.contains(movie)) {
            this.watchedMovies.add(movie);
            added = true;
        }
        else {
            added = false;
        }
        return added;
    }
    
    public void removeMovie(String movie) {
        for(String m: this.getWatchedMovies()) {
            if(m.equalsIgnoreCase(movie)) {
                this.watchedMovies.remove(m);
            }
        }
    }
    
    public void readMoviesFromFile() {
        try {
            Scanner read = new Scanner(new File("movies.txt"));
            while (read.hasNextLine())
            {
                String movie = read.nextLine();
                this.watchedMovies.add(movie); 
            }
        } catch(FileNotFoundException e) {
             System.out.println("User file could not be found.");
        }
    }
}
