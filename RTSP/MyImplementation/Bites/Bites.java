public class Bites {

    public byte[] header;

    public Bites(int playloadType, int sequenceNumber, int timeStamp, byte[] data, int payloadSize){
    
        int version = 2;
        int padding = 0;
        int extension = 0;
        int cc = 0;
        int marker = 0;
        int ssrc = 0;

        header = new byte[8];
        header[0] = (byte) (version << 6 | padding << 5 | extension << 4 | cc);     
        header[1] = (byte) (marker << 7 | playloadType);
        header[2] = (byte) (sequenceNumber >> 8);
        header[3] = (byte) (sequenceNumber);
        header[4] = (byte) (timeStamp >> 24);
        header[5] = (byte) (timeStamp >> 16);
        header[6] = (byte) (timeStamp >> 8);
        header[7] = (byte) (timeStamp);
    }
   
    public void printHeader(){
    //is headerSize - 4 because of the ssrc variable
        for (int i=0; i < (12-4); i++){
            for (int j = 7; j>=0 ; j--)
                if ((1<<j & header[i]) != 0)
                    System.out.print("1");
                else
                    System.out.print("0");
                System.out.print(" ");
        }
        System.out.println();
    } 

    public static void main(String[] args){
        byte[] bytes = {0};
        Bites bites = new Bites(191,57327,2139062143,bytes,-1);
        bites.printHeader();
    }
}

