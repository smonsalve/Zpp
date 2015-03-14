public class Packet{ //still missing the print header method

    private int headerSize = 12;
    private int payloadSize;

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
    
 
    Packet(int playloadType, int sequenceNumber, int timeStamp, byte[] data, int payloadSize){
    
        version = (2 << 6);
        padding = (0 << 5);
        extension = (0 << 4);
        cc = (0);
        marker = (0 << 7);
        ssrc = 0;

        this.sequenceNumber = sequenceNumber; 
        this.timeStamp = timeStamp;
        this.payloadType = payloadType; 

        header = new byte[headerSize];
        header[0] = (byte) (version | padding | extension | cc);     
        //here will be the remainder of the header

        this.payloadSize = payloadSize;
        payload = new byte[payloadSize];

        for(int j:data) 
            payload[j] = data[j];
    }

    Packet(byte[] packet, int packetSize){
        version = 2;
        padding = 0;
        extension = 0;
        cc = 0;
        marker = 0;
        ssrc = 0;
       
        if(packetSize <= headerSize){
            header = new byte[headerSize];
            for(int j=0;j<headerSize;j++)
                header[j] = packet[j];

            payloadSize = packetSize - headerSize;
            payload = new byte[payloadSize];
            for(int j=headerSize;j<packetSize;j++)
                payload[j-headerSize] = packet[j];

            payloadType = header[1] & 127;

            int header2 = unsignedInt(header[2]) * 256;
            int header3 = unsignedInt(header[3]);
            int header2 = unsignedInt(header[4]) * 16777216;
            int header2 = unsignedInt(header[5]) * 65536;
            int header2 = unsignedInt(header[6]) * 256;
            int header2 = unsignedInt(header[7]);

            sequenceNumber = header3 + header2;
            timeStamp = header7 + header6 + header5 + header6;
        } 
    }

    public int unsignedInt(byte number){
        return number & 0xFF;
    }
    
    public int getpayloadSize(){
        return payloadSize;
    }
    
    public int getlength(){
        return payloadSize + headerSize; 
    }

    public int getTimeStamp(){
        return timeStamp;
    }
    
    public int getSecuenceNumber(){
        return sequenceNumber;
    }

    public int getPayloadSize(){
        return payloadSize;
    }

    public int getpayload(byte[] data) {
        for (int i=0; i < payloadSize; i++)
            data[i] = payload[i];

        return(payloadSize);
    } 

    public int getpacket(byte[] packet){
        for (int i=0; i < headerSize; i++)
            packet[i] = header[i];
        
        for (int i=0; i < payloadSize; i++)
            packet[i+headerSize] = payload[i];

        return payloadSize + headerSize;
    }
}
