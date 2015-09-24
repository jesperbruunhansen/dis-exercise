import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by jesperbruun on 22/09/15.
 */
public class WebServer {

    public void start() {

        ServerSocket s;
        int portNr = 10010;

        System.out.println("WebServer is running on port " + portNr);
        System.out.println("(press ctrl-c to exit)");
        try {
            s = new ServerSocket(portNr);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return;
        }

        System.out.println("Waiting for incoming request ...");
        while(true) {
            try {

                // hang execution, and wait for incoming request
                Socket remote = s.accept();

                // remote client has now establish connection to the server, yay!
                System.out.println("================================");
                System.out.println("Connection!");
                System.out.println("================================");
                BufferedReader in = new BufferedReader(new InputStreamReader(remote.getInputStream()));
                PrintWriter out = new PrintWriter(remote.getOutputStream());

                /*
                *  Lets read some data from the client header
                */
                String str = ".";
                while (!str.equals("")) {
                    str = in.readLine();
                    System.out.println(str);
                }

                /*
                *  Hmm, maybe this is a good spot for returning some content to
                *  the client now?
                *  Maybe via the "out" PrintWrite object ... but i dont know.
                */
                out.println("HTTP/1.0 200 OK");
                out.println("Content-Type: application/json");
                out.println("Server: java");
                out.println("");
                out.println("{\"success\":\"true\"}");

                /*
                *  Should we do something when the response has been sent?
                *  Like
                */
                out.flush();
                remote.close();




            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
        }
    }


}
