import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Clientframe extends JFrame implements ActionListener{
        
    private Client client;

    private JButton setUp;
    private JButton play;
    private JButton pause;
    private JButton tear;

    private JPanel main;
    private JPanel button;

    private JLabel label;
    private ImageIcon icon;

    private Timer timer;

    Clientframe(Client client){
        super("Client");

        this.client = client;

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
                    timer.stop();
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
        
        setUp.addActionListener(this);
        play.addActionListener(this);
        pause.addActionListener(this);
        tear.addActionListener(this);

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

        timer = new Timer(20, this);
        timer.setInitialDelay(0);
        timer.setCoalesce(true);
    }

    public void setTimer(boolean state){
        if(state) timer.start();
        else timer.stop();
    }

    public void setImage(byte[] payload){
        try{
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Image image = toolkit.createImage(payload, 0, payload.length);
            icon = new ImageIcon(image);
            label.setIcon(icon);
        }
        catch(Exception a){
            System.out.println(a.getMessage());
        }
    }

    public void actionPerformed(ActionEvent e){
        String val = e.getActionCommand();
        if(val.equals("SETUP")){
            System.out.println("SETUP");                    
            client.setUp();
        } 
        else if(val.equals("PLAY")){
            System.out.println("PLAY");                    
            client.play();
        }
        else if(val.equals("PAUSE")){
            System.out.println("PAUSE");                    
            client.pause();
        }
        else if(val.equals("TEAR")){
            System.out.println("TEAR");                    
            client.tear();
        }
        else{
            client.timer();
        }
    }
}
