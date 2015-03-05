import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Clientframe extends JFrame{
        
    //private Timer timer;

    private JButton setUp;
    private JButton play;
    private JButton pause;
    private JButton tear;

    private JPanel main;
    private JPanel button;

    private JLabel label;
    private ImageIcon icon;

    Clientframe(Client client){
        super("Client");

        //timer = new Timer(20, new TimerListener());
        //timer.setInicialDelay(0);
        //timer.setCoalesce(true);

        setUp = new JButton("Setup");
        play = new JButton("Play");
        pause = new JButton("pause");  
        tear = new JButton("tear");

        main = new JPanel();
        button = new JPanel();
        label = new JLabel();

        addWindowListener(                                            
            new WindowAdapter(){
                public void windowClosing(WindowEvent e){
           //         timer.stop();
                    System.exit(0);
                }
            }
        );     

        label.setIcon(null);
        
        button.setLayout(new GridLayout(1,0));
        button.add(setUp);
        button.add(play);
        button.add(pause);
        button.add(tear);
        
        setUp.addActionListener(client);
        play.addActionListener(client);
        pause.addActionListener(client);
        tear.addActionListener(client);

        setUp.setActionCommand("SETUP");
        play.setActionCommand("PLAY");
        pause.setActionCommand("PAUSE");
        tear.setActionCommand("TEAR");
        
        main.setLayout(null);
        main.add(label);
        main.add(button);
        
        label.setBounds(0,0,380,280);
        button.setBounds(0,280,390,50);

        getContentPane().add(main, BorderLayout.CENTER);
        setSize(new Dimension(390, 370));
        setVisible(true);
    }
}
