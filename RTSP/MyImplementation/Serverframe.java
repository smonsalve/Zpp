import javax.swing.*;
import java.awt.*; 
import java.awt.event.*;

public class Serverframe extends JFrame implements ActionListener{

    private Timer timer;
    private JLabel label;
    private Server server;

    private int imageNumber;
    private int videoLength;

    Serverframe(Server server){
        super("Frame");

        this.server = server;
        imageNumber = 0;
        videoLength = 500;

        timer = new Timer(server.framePeriod,this);        
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

    public void setTimer(boolean state){
        if(state) timer.start();
        else timer.stop();
    }

    public void actionPerformed(ActionEvent e){
        if(imageNumber < videoLength){
            imageNumber++;
            server.buttonPressed(imageNumber);
            label.setText("Send frame #"+imageNumber);
        }
        else{
            timer.stop();
        }
    }
}
