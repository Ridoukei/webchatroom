import java.io.*;
import java.net.*;
import java.time.LocalTime;

public class TCPClient {
    public static void main(String[] args) throws Exception{
        String IP = "202.5.18.232";
        int port = 3232;
        Socket client = new Socket(IP, port);

        System.out.print("your ID is ? :");
        BufferedReader IDstream = new BufferedReader(new InputStreamReader(System.in,"UTF-8")) ;
        String ID = IDstream.readLine();
        System.out.println();

        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream(),"UTF-8"));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream(),"UTF-8"));

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
                BufferedReader input = new BufferedReader(new InputStreamReader(System.in,"UTF-8"));
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
