import java.net.*;
import java.io.*;

public class Server{
   
 //   private Video video;

    private byte[] buffer;

    public Socket Ssocket;
    public DatagramSocket Usocket;
    
    public InetAddress ClientIp;
    
    public static BufferedReader reader;
    public static BufferedWriter writer;    

    public static String videoName;

    public static int state;

    public static final int INIT = 0;
    public static final int READY = 1;
    public static final int PLAYING = 2;
 
    public static final int SETUP = 3;
    public static final int PLAY = 4;
    public static final int PAUSE = 5;
    public static final int TEARDOWN = 6;

    Server(){
        buffer = new byte[15000];
    }

    public static void main(String[] argv){
        Server server = new Server();
        Serverframe Serverframe = new Serverframe();
        Serverframe.setSize(500,500);
        Serverframe.setVisible(true);
    
        int port = Integer.parseInt(argv[0]);

        try{
            ServerSocket Ssocket = new ServerSocket(port);
            server.Ssocket = Ssocket.accept();
            Ssocket.accept();

            server.ClientIp = server.Ssocket.getInetAddress();

            state = INIT;

            reader = new BufferedReader(
                            new InputStreamReader(
                                server.Ssocket.getInputStream()));
            writer = new BufferedWriter(
                            new OutputStreamWriter(
                                server.Ssocket.getOutputStream()));
        }
        catch(Exception a){
            System.out.println(a.getMessage());
        }

        int request = 0; //dont forget erase the  = 0
        boolean setup = false;
        while(!setup){
            //call to server.parse_RTSP_request(); and save it on request
            if(request == SETUP){
                setup = true;
                
                state = READY;
                //Call send_RTSP_response()
                //Creates a new Video stream with video variable
                
                try{
                    server.Usocket = new DatagramSocket();
                }
                catch(Exception a){
                    System.out.println(a.getMessage());
                }
            }
        }

        while(true){
            //call to server.parse_RTSP_request(); and save it on request
            
            if(request == PLAY){
                //Call server.send_RTSP_response();
                //Call server.timer.start();
                state = PLAYING; 
            }
            else if((request == PAUSE) && (state == PLAYING)){
                //Call server.send_RTSP_response();
                //Call server.timer.stop();
                state = READY;
            }
            else if(request == TEARDOWN){
                //Call server.send_RTSP_response();
                //Call server.timer.stop();
                //Call server.RTSPsocket.close();
                //Call server.RTPsocket.close();
            
                System.exit(0);
            }
        }
    }
}
