import java.awt.event.*;
import java.net.*;
import java.io.*;

public class Client implements ActionListener{

    private Socket Csocket;

    private static BufferedReader reader;
    private static BufferedWriter writer;

    private static String videoName;

    private int secuence = 0;
    private int id = 0;
    private int MJPEG = 26;
    
    private static int INIT = 0;
    private static int READY = 1;
    private static int PLAYING = 2;
    private static int state; 

    private byte[] buffer;

    Client(){
        buffer = new byte[15000];
    }

    public void actionPerformed(ActionEvent e){
        String val = e.getActionCommand();
        if(val.equals("SETUP")){
            System.out.println("SETUP");                    
        } 
        else if(val.equals("PLAY")){
            System.out.println("PLAY");                    

        }
        else if(val.equals("PAUSE")){
            System.out.println("PAUSE");                    
        
        }
        else if(val.equals("TEAR")){
            System.out.println("TEAR");                    

        }
    }

    public static void main(String[] argv){
        Client client = new Client();
        Clientframe frame = new Clientframe(client);
    }
}
