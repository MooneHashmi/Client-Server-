import java.io.*;  
import java.net.*;  
public class MyServer {  
    public static void main(String[] args){  
        try{  
            ServerSocket ss=new ServerSocket(6666);  
            Socket s=ss.accept();//establishes connection   
            
            BufferedReader dis = new BufferedReader(new InputStreamReader(s.getInputStream()));
            //DataInputStream dis=new DataInputStream(s.getInputStream());
            DataOutputStream dout=new DataOutputStream(s.getOutputStream());   
            
            String  str=(String)dis.readLine();  
            System.out.println("Client message= "+str);
            
            dout.write(("GDAY\n").getBytes());
            dout.flush();

            str = dis.readLine();  
            System.out.println("Client message= "+str);
            dout.flush();

            dout.write(("BYE\n").getBytes());
            dout.flush();
            
            ss.close();  
        }catch(Exception e){System.out.println(e);}  
    }  
}  