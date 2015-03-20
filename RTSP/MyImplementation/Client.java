import java.net.*;
import java.io.*;
import java.util.StringTokenizer;

public class Client{ 

    private Socket Csocket;
    private DatagramSocket Usocket;
    private DatagramPacket packet;


    public static BufferedReader reader;
    public static BufferedWriter writer;

    public static String videoName;

    private int sequence = 0;
    private int id = 0;
    private int MJPEG = 26;
    private int port;
    
    private static int INIT = 0;
    private static int READY = 1;
    private static int PLAYING = 2;
    private static int state; 

    private byte[] buffer;

    private Clientframe frame;

    Client(){
        buffer = new byte[15000];
    }
    
    public void setFrame(Clientframe frame){
        this.frame = frame;
    }

    public void setUp(){
        if(state == INIT){
            try{
                Usocket = new DatagramSocket(port);  
                Usocket.setSoTimeout(5);
            }
            catch(Exception a){
                System.out.println(a.getMessage());
                System.exit(0);
            }
            sequence = 1;

            sendRtspRequest("SETUP");

            if(parseServerResponse() == 200)
                state = READY;
        }
    }

    public void play(){
        if(state == READY){
            sequence++;
            sendRtspRequest("PLAY"); 
            if(parseServerResponse() == 200){
                state = PLAYING;
                frame.setTimer(true);
            }
        }
    }

    public void pause(){
        if(state == PLAYING){
            sequence++;
            sendRtspRequest("PAUSE");
            if(parseServerResponse() == 200){
                state = READY;
                frame.setTimer(false);
            }
        }      
    }

    public void tear(){
        sequence++;
        sendRtspRequest("TEARDOWN");
        if(parseServerResponse() == 200){
            state = INIT;
            frame.setTimer(false);
            System.exit(0);
        }
    }

    public void timer(){
        packet = new DatagramPacket(buffer, buffer.length); 
        try{
            Usocket.receive(packet);
            Packet packetRtsp = new Packet(packet.getData(), packet.getLength());
            
            System.out.println("Got RTP packet with SeqNum # "+packetRtsp.getSequenceNumber()+" TimeStamp "+packetRtsp.getTimeStamp()+" ms, of type "+packetRtsp.getPayloadType());

            packetRtsp.printHeader();

            int payloadLength = packetRtsp.getPayloadSize();
            byte[] payload = new byte[payloadLength];
            
            packetRtsp.getPayload(payload);
        
            frame.setImage(payload);
        }
        catch(Exception a){
            System.out.println(a.getMessage());
        }
    }

    private int parseServerResponse(){
        int replyCode = 0;
        
        try{
            String line = reader.readLine();
            
            StringTokenizer token = new StringTokenizer(line);
            token.nextToken();
            replyCode = Integer.parseInt(token.nextToken());

            if(replyCode == 200){
                //String number = reader.readLine();
                String session = reader.readLine();
                token = new StringTokenizer(session); 
                token.nextToken();
                id = Integer.parseInt(token.nextToken());
            }
        }
        catch(Exception a){
            System.out.println(a.getMessage());
            System.exit(0);
        }
        return replyCode;
    }
    
    private void sendRtspRequest(String request){
        try{
            writer.write(request+" "+videoName+" "+"RTSP/1.0\n");
            writer.write("CSeq: "+sequence+"\n");
            if(request.equals("SETUP"))
                writer.write("Transport: RTP/UDP; client_port= "+port+"\n");
            else
                writer.write("Session: "+id+"\n");
            writer.flush();
        }
        catch(Exception a){
            System.out.println(a.getMessage());
        }
    }
}
