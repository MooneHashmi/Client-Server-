import java.io.*;  
import java.net.*;  
public class MyClient {  
    public static void main(String[] args) {  
        try{      
            Socket s=new Socket("localhost",50000);  
            BufferedReader dis = new BufferedReader(new InputStreamReader(s.getInputStream()));
            //DataInputStream dis=new DataInputStream(s.getInputStream());
            DataOutputStream dout=new DataOutputStream(s.getOutputStream());  
            
            dout.write(("HELO\n").getBytes());  
            dout.flush(); 
            
            String str=(String)dis.readLine();  
            System.out.println("Server message= "+str);
            dout.flush();
            
            dout.write(("AUTH user\n").getBytes());  
            dout.flush(); 
            
            str=(String)dis.readLine();  
            System.out.println("Server message= "+str);
            dout.flush();
            
            dout.write(("REDY\n").getBytes());  
            dout.flush(); 
            
            dout.write(("SCHD 0 super-silk 0\n").getBytes());  
            dout.flush();
            
            str=(String)dis.readLine();  
            System.out.println("Server message= "+str);
            dout.flush();
            
            

            dout.write(("QUIT\n").getBytes());
            dout.flush();

            str = dis.readLine();  
            System.out.println("Server message= "+str);
            dout.flush();

            dout.close();  
            s.close();  
        }catch(Exception e){System.out.println(e);}  
    
    }  
}  
