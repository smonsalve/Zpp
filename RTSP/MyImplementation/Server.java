import javax.swing.Timer;

public class Server{
   
    private byte[] buffer;
 
    Server(){
        buffer = new byte[15000];
    }

    public static void main(String[] args){
        Server server = new Server();
        Frame frame = new Frame();
        frame.setSize(500,500);
        frame.setVisible(true);
    }
}
