public class Main {
    public static void main(String[] args){
        Server server = new Server();
        Serverframe serverFrame = new Serverframe(Server);
        serverFrame.setSize(500,500);
        serverFrame.setVisible(true);
    
        int port = Integer.parseInt(argv[0]);

        try{
            ServerSocket Ssocket = new ServerSocket(port);
            server.Ssocket = Ssocket.accept();
            server.Ssocket.accept();

            server.clientIp = server.Ssocket.getInetAddress();

            server.state = INIT;

            server.reader = new BufferedReader(
                                new InputStreamReader(
                                    server.Ssocket.getInputStream()));
            server.writer = new BufferedWriter(
                                new OutputStreamWriter(
                                    server.Ssocket.getOutputStream()));
        }
        catch(Exception a){
            System.out.println(a.getMessage());
        }

        int request; 
        boolean done = false;
        while(!done){
            request = server.parse_RTSP_request(); 
            if(request == server.SETUP){
                done = true;
                
                server.state = READY;
                server.send_RTSP_response();
    
                try{
                    server.video = new Video(server.videoName);
                    server.Usocket = new DatagramSocket();
                }
                catch(Exception a){
                    System.out.println(a.getMessage());
                }   
            }
        }

        while(true){
            request = server.parse_RTSP_request(); 
            
            if(request == server.PLAY && server.state == READY){
                server.send_RTSP_response();
                serverFrame.setTimer(true);
                server.state = PLAYING; 
            }
            else if((server.request == server.PAUSE) && (server.state == server.PLAYING)){
                server.send_RTSP_response();
                serverFrame.setTimer(false);
                server.state = READY;
            }
            else if(server.request == server.TEARDOWN){
                server.send_RTSP_response();
                timer.stop();
                server.Ssocket.close();
                server.Usocket.close();
                System.exit(0);
            }
        }

    }
}

