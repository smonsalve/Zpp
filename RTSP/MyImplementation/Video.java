import java.io.*;

public class Video{
    
    public FileInputStream file;
    public int frame;    

    Video(String fileName){
        try{
            file = new FileInputStream(fileName);
            frame = 0;
        }
        catch(Exception a){
            System.out.println(a.getMessage());
        }
    }

    public int getNextFrame(byte[] frame) throws Exception{
        int length;
        String stringLength;

        byte[] frameLength = new byte[5];

        file.read(frameLength,0,5);

        stringLength = new String(frameLength);
        length = Integer.parseInt(stringLength);

        return file.read(frame,0,length);
    }
}
