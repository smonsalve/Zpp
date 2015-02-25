import java.net.*;
import java.io.*;

public class SocketServer{
    
    public static String path = "/home/alejandro/Pictures/Webcam/image.jpg";

    public static void main(String[] args){
        Thread t = new Thread(new Runnable(){
            @Override
            public void run(){  
                try{
                    ServerSocket server = new ServerSocket(9999);
                    System.out.println("Running...........");
                    while(true){
                        Socket s = server.accept();
                        System.out.println("conection acepted");
            
                        FileInputStream file = new FileInputStream(path);
                        byte[] buffer = new byte[file.available()];
                        file.read(buffer);
                        
                        ObjectOutputStream sender = new ObjectOutputStream(s.getOutputStream());
                        sender.writeObject(buffer);
                        sender.close();

                        s.close();
                    }
                }
                catch(Exception a){
                    System.out.println("An error ocurred");
                    System.out.println(a.getMessage());
                } 
            }
        });   
        t.start();
    }
}

