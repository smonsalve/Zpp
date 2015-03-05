import javax.swing.*;
import java.awt.*; 
import java.awt.event.*;

public class Serverframe extends JFrame implements ActionListener{

    private int frame_period = 100;  
    private Timer timer;
    private JLabel label;

    private int imagenb = 0;
    private int RTSPSeqNb = 0;
    private int RTP_dest_port = 0;    

    public static int RTSP_ID = 123456;
    public static int VIDEO_LENGTH = 500;
    public static int MJPEG_TYPE = 26;

    Serverframe(){
        super("Frame");

        timer = new Timer(frame_period,this);        
        timer.setInitialDelay(0);
        timer.setCoalesce(true);

        addWindowListener(
            new WindowAdapter(){
                public void windowClosing(WindowEvent e){
                    timer.stop();
                    System.exit(0);
                }
            }
        ); 

        label = new JLabel("Send frame #      ",JLabel.CENTER);
        getContentPane().add(label, BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent e){
        if(imagenb < VIDEO_LENGTH){
            imagenb++;

            try{
                // int image_length = video.getnextframe(buf); 
            }
            catch(Exception a){
                System.out.println(a.getMessage());
            }
        }
    }
}
