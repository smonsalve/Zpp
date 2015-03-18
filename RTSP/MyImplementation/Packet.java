public class Packet{ 

    private int headerSize = 12;
    private int payloadSize;

    public byte[] header;
    public byte[] payload;

    public int version;
    public int padding;
    public int extension;
    public int cc;
    public int marker;
    public int payloadType;
    public int sequenceNumber;
    public int timeStamp;
    public int ssrc;

    Packet(int playloadType, int sequenceNumber, int timeStamp, byte[] data, int payloadSize){
    
        int version = 2;
        int padding = 0;
        int extension = 0;
        int cc = 0;
        int marker = 0;
        int ssrc = 0;

        this.sequenceNumber = sequenceNumber; 
        this.timeStamp = timeStamp;
        this.payloadType = payloadType; 
        this.payloadSize = payloadSize;

        header = new byte[headerSize];
        payload = new byte[payloadSize];

        header[0] = (byte) (version << 6 | padding << 5 | extension << 4 | cc);     
        header[1] = (byte) (marker << 7 | payloadType);
        header[2] = (byte) (sequenceNumber >> 8);
        header[3] = (byte) (sequenceNumber);
        header[4] = (byte) (timeStamp >> 24);
        header[5] = (byte) (timeStamp >> 16);
        header[6] = (byte) (timeStamp >> 8);
        header[7] = (byte) (timeStamp);
        //here will be the remainder of the header but there's not ssrc

        payload = data;
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
            int header4 = unsignedInt(header[4]) * 16777216;
            int header5 = unsignedInt(header[5]) * 65536;
            int header6 = unsignedInt(header[6]) * 256;
            int header7 = unsignedInt(header[7]);

            sequenceNumber = header3 + header2;
            timeStamp = header7 + header6 + header5 + header6;
        } 
    }

    public int unsignedInt(byte number){
        if(number >= 0) return number;
        else return number+256;
    }

    public int getpayloadType(){
        return payloadType;
    }
    
    public int getpayloadSize(){
        return payloadSize;
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

    public int getlength(){
        return payloadSize + headerSize; 
    }

    public int getpayload(byte[] data) {
        for (int j=0;j<payloadSize;j++)
            data[j] = payload[j];

        return payloadSize;
    } 

    public int getpacket(byte[] packet){
        for(int j=0;j< headerSize;j++)
            packet[j] = header[j];
        
        for(int j=0;j<payloadSize;j++)
            packet[j+headerSize] = payload[j];

        return payloadSize+headerSize;
    }

    public void printheader(){
    //is headerSize - 4 because of the ssrc variable
        for (int i=0; i < (headerSize-4); i++){
            for (int j = 7; j>=0 ; j--)
                if ((1<<j & header[i]) != 0)
                    System.out.print("1");
                else
                    System.out.print("0");
                System.out.print(" ");
        }
        System.out.println();
    } 

/*
    public void printHeader(){
        String binary;
        for(int j=0;j<8;j++){
            binary = Integer.toBinaryString(header[j] & 0xFF);
            if(binary.length() == 7) binary = "0" + binary;
            System.out.print(binary + " ");
        }
        System.out.println();
    }
*/  
}
