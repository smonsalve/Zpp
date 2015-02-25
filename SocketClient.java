import java.net.*;
import java.io.*;

public class SocketClient{
    
    public static String ip = "192.168.1.104";

    public static void main(String[] args){
        Thread t = new Thread(new Runnable(){
            @Override
            public void run(){
                try{
                    Socket s = new Socket(ip,9999);
                    ObjectInputStream reciver = new ObjectInputStream(s.getInputStream());
                    
                    try{
                        byte[] buffer = (byte[]) reciver.readObject();
                        FileOutputStream writer = new FileOutputStream("./image.jpg");
                        writer.write(buffer);
                    }
                    catch(Exception a){    
                        System.out.println("An error ocurred");
                        System.out.println(a.getMessage());
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
