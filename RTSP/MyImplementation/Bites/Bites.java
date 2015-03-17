public class Bites {

    public byte[] header;

    public Bites(int playloadType, int sequenceNumber, int timeStamp, byte[] data, int payloadSize){
    
        int version = (2 << 6);
        int padding = (0 << 5);
        int extension = (0 << 4);
        int cc = (0);
        int marker = (0 << 7);
        int ssrc = 0;

        header = new byte[4];
        header[0] = (byte) (version | padding | extension | cc);     
        header[1] = (byte) (marker | playloadType);
        header[2] = (byte) (sequenceNumber >> 8);
        header[3] = (byte) (sequenceNumber & 0xFF);
    }

    public static void main(String[] args){
        byte[] bytes = {0};
        Bites bites = new Bites(191,57327,2147483647,bytes,-1);

        int a = (int) bites.header[0] << 24;
        int b = (int) bites.header[1] << 16;
        int c = (int) bites.header[2] << 8;
        int d = (int) bites.header[3]; 

        int ans = a | b & 0xFF0000 | c & 0xFF00 | d & 0xFF;

        System.out.println(ans);
        
    }
}

