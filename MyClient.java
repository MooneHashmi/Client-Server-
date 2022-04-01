import java.io.*;  
import java.net.*;  
public class MyClient {  
    public static void main(String[] args) {  
        try{      
            Socket s=new Socket("localhost",50000);  
            BufferedReader dis = new BufferedReader(new InputStreamReader(s.getInputStream()));
            //DataInputStream dis=new DataInputStream(s.getInputStream());
            DataOutputStream dout=new DataOutputStream(s.getOutputStream());  


            // Sending HELO
            dout.write(("HELO\n").getBytes());  
            dout.flush(); 

            // Receiving OK
            String str=(String)dis.readLine();  
            System.out.println("Server message = '" + str + "'");
            dout.flush();
            
            // Sending AUTH
            dout.write(("AUTH Muneeb\n").getBytes());  
            dout.flush(); 
            
            // Receiving OK
            str=(String)dis.readLine();  
            System.out.println("Server message = '" + str + "'");
            dout.flush();
            
            // Sending REDY
            dout.write(("REDY\n").getBytes());  
            dout.flush(); 

            
            // JOBN 37 0 1135 5 1200 6400
            // GETS Capable 5 1200 6400


            str=(String)dis.readLine(); 
            String[] noneDetector = str.split(" ");
            if (noneDetector [0] != "NONE" ){
                String core = noneDetector[-3];
                String ram = noneDetector[-2];
                String hardDisk = noneDetector[-1];

                dout.write(("GETS Capable " + core + " " + ram + " " + hardDisk + "\n").getBytes());
                dout.flush();

            str=(String)dis.readLine(); 
            String[] data = str.split(" "); 

            dout.write(("OK\n").getBytes());
            dout.flush();

            str=(String)dis.readLine(); 
            String[] servers = str.split("\n");

            
            int maxCore = 0;
            int serverIDcount = 0;
            for (int i = 1; i < Integer.parseInt(data[1]); i++){

            String [] maxJob = servers[maxCore].split(" ");
            String [] currentJob = servers[i].split(" ");
            
            if (Integer.parseInt(maxJob[4]) < Integer.parseInt(currentJob[4])){
                maxCore = i;
            }
            if(Integer.parseInt(maxJob[4]) == Integer.parseInt(currentJob[4])){
                
            }
            }
            dout.write(("OK\n").getBytes());
            dout.flush();
            }
            


            dout.write(("SCHD 0 super-silk 0\n").getBytes());  
            dout.flush();
            
            str=(String)dis.readLine();  
            System.out.println("Server message = '" + str + "'");
            dout.flush();
            
            

            dout.write(("QUIT\n").getBytes());
            dout.flush();

            str = dis.readLine();  
            System.out.println("Server message = '" + str + "'");
            dout.flush();

            dout.close();  
            s.close();  
        }catch(Exception e){System.out.println(e);}  
    
    }  
}  


