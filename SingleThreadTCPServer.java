package webprogramming;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SingleThreadTCPServer {

    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(3232);
        System.out.println("waiting for connection......");
        Socket Connection = ss.accept();
        System.out.println("connected.");
        BufferedReader in = new BufferedReader(new InputStreamReader(Connection.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(Connection.getOutputStream()));

        while(true){
            try {
                String msg = in.readLine();
                System.out.println(msg);
                out.write(msg + '\n');
                out.flush();
            }catch (Exception e) {e.printStackTrace();break;}
        }
    }
}
