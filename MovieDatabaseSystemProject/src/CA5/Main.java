package CA5;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.Socket;
import java.util.Scanner;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;

/**
 * @authors 
 * Patricia Bere - D00193593
 * Oisin Murphy - D00191700
 */

//client side -> user will be able to enter text here and it will be sent to the server
//the server will send back the information from the request

public class Main {
    private final static int PORT = 12345;
    private final static String HOSTNAME = "localhost";
    private static Scanner in = new Scanner(System.in);
    
    public static void main(String[] args) {
        try {
            System.out.println("Please input 'help' if you need help issuing a command");
            System.out.println("To exit the program, please input 'exit'.");
            String command = "";
            while(!command.equalsIgnoreCase("exit")) {
                Socket socket = new Socket("localhost", PORT);
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                Scanner fromServer = new Scanner(socket.getInputStream());
                System.out.print("Command: ");
                command = in.nextLine();
                command += "\n";
                out.write(command);
                out.flush();
                if(command.equalsIgnoreCase("exit\n") || command.equalsIgnoreCase("adminExit\n")) {
                    System.out.println("Thank you for using our application. :D");
                    System.exit(0);
                }
                
                String data = fromServer.nextLine();
                
                //System.out.println(data);
                if(data.startsWith("{") || data.startsWith("[")) {
                    JsonObject received = Json.createReader(new StringReader(data)).readObject();
                    JsonArray movies = received.getJsonArray("Movies");
                    for(int i = 0; i < movies.size(); i++) {
                        JsonObject movie = movies.getJsonObject(i);
                        //System.out.println(movie);
                        Movie m = new Movie();
                        m.setId(movie.getInt("ID"));
                        m.setTitle(movie.getJsonString("Title").getString());
                        m.setGenre(movie.getJsonString("Genre").getString());
                        m.setDirector(movie.getJsonString("Director").getString());
                        m.setRuntime(movie.getJsonString("Runtime").getString());
                        m.setPlot(movie.getJsonString("Plot").getString());
                        m.setLocation(movie.getJsonString("Location").getString());
                        m.setRating(movie.getJsonString("Rating").getString());
                        m.setFormat(movie.getJsonString("Format").getString());
                        m.setYear(movie.getJsonString("Year").getString());
                        m.setStarring(movie.getJsonString("Starring").getString());
                        m.setCopies(movie.getJsonString("Copies").getString());
                        m.setBarcode(movie.getJsonString("Barcode").getString());
                        m.setUser_rating(movie.getJsonString("User Rating").getString());
                        System.out.println(m.displayMovie());
                    }
                }
                else {
                    if(data.contains("~n"))
                    {
                        data = data.replace("~n", "\n");
                    }
                    System.out.println(data);
                    command = "";
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
