import java.net.*;
import java.io.*;
import java.util.StringTokenizer;

public class Server{
   
    public Video video;

    private byte[] buffer;

    public Socket Ssocket;
    public DatagramSocket Usocket;
    public DatagramPacket dataPacket;
    
    public InetAddress clientIp;
    
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

    public static int MJPEG = 26;
    public int RTPport = 0;    
    public int RTSPid = 123456;
    public int RTSPsequence = 0;
    public int framePeriod = 100;

    Server(){
        buffer = new byte[15000];
    }

    public void buttonPressed(int imageNumber){
        try{
            int imageLength = video.getNextFrame(buffer); 
            Packet packet = new Packet(MJPEG, imageNumber, 
                            imageNumber*framePeriod, buffer, imageLength);

            int packetLength = packet.getLength();
            byte[] packetBits = new byte[packetLength];
            packet.getPacket(packetBits);

            dataPacket = new DatagramPacket(packetBits, packetLength, 
                         clientIp, RTPport);

            Usocket.send(packetBits);
            packet.printHeader();
        }
        catch(Exception a){
            System.out.println(a.getMessage());
            System.exit(0);
        }
    }

    public int parseRtspRequest(){
        try{
            int requestType = -1;
            String RequestLine = reader.readLine();
            System.out.println(RequestLine);
            StringTokenizer tokens = new StringTokenizer(RequestLine);
            String requestTypeString = tokens.nextToken();

            if(requestTypeString.equals("SETUP")) requestType = SETUP;
            else if(requestTypeString.equals("PLAY")) requestType = PLAY;
            else if(requestTypeString.equals("PAUSE")) requestType = PAUSE;
            else if(requestTypeString.equals("TEARDOWN")) requestType = TEARDOWN;

            if (requestType == SETUP)
                videoName = tokens.nextToken();
            

            String SeqNumLine = reader.readLine();
            System.out.println(SeqNumLine);
            tokens = new StringTokenizer(SeqNumLine);
            tokens.nextToken();
            RTSPsequence = Integer.parseInt(tokens.nextToken());
            String LastLine = reader.readLine();
            System.out.println(LastLine);

            if(requestType == SETUP){
                tokens = new StringTokenizer(LastLine);
                for(int j=0;j<3;j++)
                    tokens.nextToken();
                RTPport = Integer.parseInt(tokens.nextToken());     
            } 
            return requestType;
        }
        catch(Exception a){
            System.out.println(a.getMessage());
            System.exit(0);
        }
        return -1;
    }

    public void sendRtspResponse(){
        try{
            writer.write("RTSP/1.0 200 OK\n");
            writer.write("CSeq: "+RTSPsequence+"\n");
            writer.write("Session: "+RTSPid+"\n");
            writer.flush();
            System.out.println("RTSP Server - Sent response to Client.");
        }
        catch(Exception a){
            System.out.println(a.getMessage());
            System.exit(0);
      }
    }
}
