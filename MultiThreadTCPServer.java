import java.net.*;
import java.util.List;
import java.util.Vector;

public class MultiThreadTCPServer {
    protected static List<Socket> socketList = new Vector<>();

    public static void main(String[] args) throws Exception{

        ServerSocket ss = new ServerSocket(3232);

        while (true) {
            Socket Connection = ss.accept();
            synchronized (socketList){
                socketList.add(Connection);
                System.out.println(socketList);
            }
            new Thread(new HandleThread(Connection)).start();
        }
    }
}
