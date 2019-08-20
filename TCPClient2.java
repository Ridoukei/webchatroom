package webprogramming;

import java.io.*;
import java.net.Socket;
import java.time.LocalTime;

public class TCPClient2 {
    public static void main(String[] args) throws Exception{
        String IP = "127.0.0.1";
        int port = 3232;
        Socket client = new Socket(IP, port);

        System.out.print("你的昵称是？： ");
        BufferedReader IDstream = new BufferedReader(new InputStreamReader(System.in)) ;
        String ID = IDstream.readLine();

        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

        new Thread(new Runnable() {
            @Override
            public void run() {
                char[] buffer = new char[64];
                while(true) {
                    try {
                        int i = in.read(buffer);
                        //if (i == -1) {System.out.println("end");}
                        System.out.print(new String(buffer,0,i));
                    }catch (Exception e){e.printStackTrace();break;}
                }
                try{client.close();}catch (Exception e1){e1.printStackTrace();}
            }
        }).start();

        while (true) {
            try {
                BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
                String msg = input.readLine() + '\r';
                LocalTime timeNow = LocalTime.now();
                String info = ID+" "+timeNow.getHour()+":"+timeNow.getMinute()+" ";
                out.write(info + msg);
                out.flush();
            }catch (Exception e){
                e.printStackTrace();
                break;
            }
        }
        client.close();
    }
}
