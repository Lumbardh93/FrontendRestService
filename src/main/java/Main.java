import com.sun.net.httpserver.HttpServer;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;


public class Main {
    public static void main(String[] args){
        System.out.println("Starting the application ...");

        ResourceConfig rc = new ResourceConfig()
                .packages("at.slm.controllers");

        HttpServer server = JdkHttpServerFactory.createHttpServer(
                URI.create("http://localhost:8080/"), rc);
        System.out.println("Server started");

        // allow access to network to provide server (firewall)

        try {
            System.out.println("Press enter to close the server");
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        server.stop(0);

    }
    public static String GetMessage() {
        return "maintenance monitor";
    }
}
