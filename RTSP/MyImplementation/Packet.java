public class Packet{

    private int headerSize = 12;
    private int playloadSize;

    public int version;
    public int padding;
    public int extension;
    public int cc;
    public int marker;
    public int payloadType;
    public int sequenceNumber;
    public int timeStamp;
    public int ssrc;

    public byte[] header;
    public byte[] payload;
    
 
    Packet(int playloadType, int secuenceNumber, int timeStamp, byte[] data, int payloadSize){
    
        version = 2;
        padding = 0;
        extension = 0;
        cc = 0;
        marker = 0;
        ssrc = 0;

        this.secuenceNumber = secuenceNumber; 
        this.timeStamp = timeStamp;
        this.payloadType = payloadType; 

        header = new byte[headerSize];
        //fill the header with the data

        this.payloadSize = payloadSize;
        payload = new byte[payloadSize];
    }
}
