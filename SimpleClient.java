package webprogramming;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class SimpleClient {
    public static void main(String[] args) throws Exception{
        String IP = "127.0.0.1";
        int port = 3232;
        Socket client = new Socket(IP, port);
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream(), "UTF-8"));
        DataOutputStream out = new DataOutputStream(client.getOutputStream());

        while(true){
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
            String msg = input.readLine() + '\r';
            System.out.println(client.isConnected());
            out.writeBytes(msg);
            out.flush();
            while(true) {
                int i = in.read();
                if (i == -1) break;
                System.out.print((char) i);
            }
            System.out.println();
        }
    }
}
