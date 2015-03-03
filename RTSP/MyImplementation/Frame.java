import javax.swing.*;
import java.awt.*; 
import java.awt.event.*;

public class Frame extends JFrame implements ActionListener{

    private int frame_period = 100;  
    private Timer timer;
    private JLabel label;

    Frame(){
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

    

  public void actionPerformed(ActionEvent e) {}
}
