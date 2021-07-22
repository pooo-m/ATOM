import Forms.MainForm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
    try{
           /* Socket clientSocket = new Socket("192.168.0.20",50001);
            InputStream is = clientSocket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String receivedData = br.readLine();
            System.out.println("Received Data: "+receivedData);*/

            MainForm atom = new MainForm();
        }catch (Exception e){
            System.out.print(e.getMessage());
        }
    }
}
