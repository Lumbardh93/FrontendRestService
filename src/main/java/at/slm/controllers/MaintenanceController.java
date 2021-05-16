package at.slm.controllers;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Path("/api/maintenanceMode")
public class MaintenanceController {
    public static String message = "-";

    /*
        Frontend-Auslieferung
     */
    @Path("/frontend")
    @GET
    @Produces( MediaType.TEXT_HTML )
    public String getPage()
    {
        try {
            // TODO
            // Pfad anpassen!
            java.nio.file.Path path = Paths.get("file:///C:/Users/eboerse/Documents/FrontendRestService/frontend/index.html");
            return Files.readString(path);
        } catch (IOException e) {
            e.printStackTrace();
            return "Frontend index.html nicht gefunden, Vriable Paths.get() muss angepasst werden!";
        }
    }


    /*
        GET-Funktion
        Über diese Funktion wird der aktuelle Status abgerufen.
        Defaultwert = "-". Wenn das Frontend den Defaultwert erhält, zeigt es "Grün" an.
        Bei allen anderen Werten, außer "-", zeigt das Frontend  "Rot" und die Message an.
        Die Funktion wird über http://localhost:8080/api/maintenanceMode aufgerufen.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getStatus(){
        System.out.println("Status requested. Current message is: "+message);

        Response.ResponseBuilder rb = Response.ok(message);
        return rb.header("Access-Control-Allow-Origin", "*") // Der Header Access-Control-Allow-Origin wird benötigt, damit ein externes Frontend auf diese Ressorce zugreifen darf.
                .build();
    }


    /*
        SET-Funktion
        Diese Funktion kann den String "message" und damit die Anzeige am Frontend abändern.
        Sie wird per POST-Request auf die URL http://localhost:8080/api/maintenanceMode/neueNachricht aufgerufen.
        Ein POST-Request kann mit zusätzlichen Tools wie beispielsweise "Postman" erstellt werden.
     */
    @POST
    @Path("/{message}")
    public String setStatus(@PathParam("message") String newMessage){
        if(newMessage == null || newMessage.trim().equals("")) // Null- und Emptyprüfung für etwaige Unit-Tests die implementiert werden.
            return "Invalid Operation";

        System.out.println("Status change requested. New message is" + newMessage);
        message = newMessage;
        return "New message is: "+newMessage;
    }

    /*
        SET-Funktion "hack" als GET
        Macht das gleiche wie die @POST Set-Funktion, allerdings per "GET"-Request.
        D.h die Message kann ohne zusätzliches Tool direkt im Browser verändert werden, in dem folgender Link aufgerufen wird:
        http://localhost:8080/api/maintenanceMode/neueNachricht
     */
    @GET
    @Path("/{message}")
    public String setStatusGet(@PathParam("message") String newMessage){
        if(newMessage == null || newMessage.trim().equals("")) // Null- und Emptyprüfung für etwaige Unit-Tests die implementiert werden.
            return "Invalid Operation";

        System.out.println("Status change requested. New message is" + newMessage);
        message = newMessage;
        return "New message is: "+newMessage;
    }
}
