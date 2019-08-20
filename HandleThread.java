import java.net.*;
import java.io.*;

public class HandleThread extends MultiThreadTCPServer implements Runnable {
    private Socket Session;

    public HandleThread(Socket ss){
        this.Session = ss;
    }

    public void run() {
        System.out.println(Thread.activeCount());
        while (true) {
            try {
                if (Session.isClosed())break;   //有遇到客户端关闭，但是try的内容没有抛出错误，in.readline()一直输出null
                BufferedReader in = new BufferedReader(new InputStreamReader(Session.getInputStream(), "UTF-8"));
                String msg = in.readLine();
                System.out.println(msg);
                //需要实现一个向ConnectionList发送信息的方法
//                        echoMsg(msg);
                broadcast(msg);
            } catch (Exception e) { e.printStackTrace();break;}
        }
        synchronized (socketList) { socketList.remove(Session);try { Session.close(); } catch (Exception e2) { e2.printStackTrace(); } }
    }
    public void echoMsg(String message){
        //单点返回信息
        try{
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(Session.getOutputStream(),"UTF-8"));
            out.write(message + '\n');
            out.flush();
        }catch (Exception e3){e3.printStackTrace();}
    }

    public void broadcast(String Msg)throws Exception{
        //向所有的会话广播信息
        BufferedWriter out = null;
        synchronized (socketList){
            for(Socket s:socketList){
                try {
                    out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream(),"UTF-8"));
                    out.write(Msg + '\n');
                    out.flush();
                }catch (Exception e2){e2.printStackTrace();socketList.remove(s);s.close();}
            }
        }
    }

}
