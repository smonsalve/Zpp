import java.net.*;
import java.io.*;

public class MainServer {
    public static void main(String[] argv){
        Server server = new Server();
        Serverframe serverFrame = new Serverframe(server);
        serverFrame.setSize(500,500);
        serverFrame.setVisible(true);
    
        int port = Integer.parseInt(argv[0]);

        try{
            ServerSocket Ssocket = new ServerSocket(port);
            server.Ssocket = Ssocket.accept();
            Ssocket.close();

            server.clientIp = server.Ssocket.getInetAddress();

            server.state = server.INIT;

            server.reader = new BufferedReader(
                                new InputStreamReader(
                                    server.Ssocket.getInputStream()));
            server.writer = new BufferedWriter(
                                new OutputStreamWriter(
                                    server.Ssocket.getOutputStream()));
        }
        catch(Exception a){
            System.out.println(a.getMessage());
        }

        int request; 
        boolean done = false;
        while(!done){
            request = server.parseRtspRequest(); 
            if(request == server.SETUP){
                done = true;
                
                server.state = server.READY;
                server.sendRtspResponse();
    
                try{
                    server.video = new Video(server.videoName);
                    server.Usocket = new DatagramSocket();
                }
                catch(Exception a){
                    System.out.println(a.getMessage());
                }   
            }
        }

        while(true){
            request = server.parseRtspRequest(); 
            
            if(request == server.PLAY && server.state == server.READY){
                server.sendRtspResponse();
                serverFrame.setTimer(true);
                server.state = server.PLAYING; 
            }
            else if((request == server.PAUSE) && (server.state == server.PLAYING)){
                server.sendRtspResponse();
                serverFrame.setTimer(false);
                server.state = server.READY;
            }
            else if(request == server.TEARDOWN){
                server.sendRtspResponse();
                serverFrame.setTimer(false);
                try{
                    server.Ssocket.close();
                    server.Usocket.close();
                }
                catch(Exception a){
                    System.out.println(a.getMessage());
                }           
                System.exit(0);
            }
        }

    }
}

