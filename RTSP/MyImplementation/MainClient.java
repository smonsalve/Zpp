import java.net.*;
import java.io.*;

public class MainClient{
    public static void main(String[] argv){
        Client client = new Client();
        Clientframe frame = new Clientframe(client);
        client.setFrame(frame);
       
        try{ 
            int port = Integer.parseInt(argv[1]);
            String serverHost = argv[0];
            InetAddress serverIp = InetAddress.getByName(serverHost);
            client.videoName = argv[2];
                
            client.port = Integer.parseInt(argv[3]);
            client.Csocket = new Socket(serverIp, port);
            client.reader = new BufferedReader(
                                new InputStreamReader(
                                    client.Csocket.getInputStream()));
            client.writer = new BufferedWriter(
                                new OutputStreamWriter(
                                    client.Csocket.getOutputStream()));
            state = INIT;
        }
        catch(Exception a){
            System.out.println(a.getMessage());
        }
    }
}
