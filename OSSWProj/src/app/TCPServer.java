package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import bus.BusInfoClass;
import findPath.RecommendPath;
import findPath.SearchPath;

/**
 * TCPServer
 */
public class TCPServer{

    public static final int ServerPort = 9999;
    public static final String ServerIP = "xxx.xxx.xxx.xxxx";
 
    public void setTCPSocket(){

        try {
            System.out.println("S: Connecting...");
            ServerSocket serverSocket = new ServerSocket(ServerPort);
 
            while (true) {
                Socket client = serverSocket.accept();
                System.out.println("S: Receiving...");
                InetAddress ip=client.getInetAddress();
                System.out.println("test=="+ip+" connected");
                new MultiThread(client).start();
            }
        } catch (Exception e) {
            System.out.println("S: Error");
            e.printStackTrace();
        }
    }



    private class MultiThread extends Thread{
        Socket socket=null;
        String mac=null;
        String msg=null;

        InputStream input;
        OutputStream output;

        public MultiThread(Socket sock) throws IOException {
            this.socket=sock;
            try {
                input=socket.getInputStream();
                output=socket.getOutputStream();
                
                //클라이언트가 보낸 메시지를 받아서 출력
                BufferedReader reader=new BufferedReader(new InputStreamReader(input,"utf-8"));
                String clientMsg = reader.readLine();
                System.out.println("S: Received: '" + clientMsg + "'");

                //서버의 계산
                String sendMsg=returnCalculateResult(clientMsg);

                //클라이언트에게 메시지 전송
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(output));
                writer.println("test==Server: send " + sendMsg);
                writer.flush();
                
                //close()
                writer.close();
                reader.close();
            } catch(IOException e){
                System.out.println("IOException: "+e);
                e.printStackTrace();
            }catch (Exception e) {
                System.out.println("Exception: "+e);
                e.printStackTrace();
            } finally {
                socket.close();
                System.out.println("Finally.");
            }

        }

        // 경로에 쓰인 단어개수 + 경로로로로로로 +   버정에 쓰인 단어개수+ 버스정류장목로고로고록
        private String returnCalculateResult(String clientMsg){
            String result="0";
            String[] values=clientMsg.split("\\s");
            
            //경로
            SearchPath s=new SearchPath(); 
            ArrayList<ArrayList<String>> str= s.getPathsFromStations(Double.parseDouble(values[0]),
                                                                    Double.parseDouble(values[1]),
                                                                    Double.parseDouble(values[2]),
                                                                    Double.parseDouble(values[3]));
            
            if(str.size()==0){
                result="0";
            }else{
                
                result=String.valueOf(str.size())+" ";
    
                for(int i=0;i<str.size();i++){
    
                    String route="";
                    int cnt=0; //단어개수
                    ArrayList<String> substr=str.get(i);
                    for(int j=0;j<substr.size();j++){
                        route=route+substr.get(j)+" ";
                        cnt++;
                    }
                    route=String.valueOf(cnt)+" "+route; //단어개수+경로
    
                    //버스정류장
                    int cnt2=0;
                    String stations="";
                    RecommendPath recommendPath=new RecommendPath();
                    ArrayList<String> substation= recommendPath.getStationNames_on_Path(str.get(i));
                    for(int j=0;j<substation.size();j++){
                        stations=stations+substation.get(j)+" ";
                        cnt2++;
                    }
                    stations=String.valueOf(cnt2)+" "+stations;//단어개수+경로 + 단어개수+정류장
    
                    //client에 보낼 스트링
                    result=str.size()+" "+route+stations+" ";
                }    
            }     
            return result;
        }
    } 
}