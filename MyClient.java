import java.io.*;
import java.net.*;

public class MyClient {

  public static void main(String[] args) {
    try {
      Socket s = new Socket("localhost", 50000);
      BufferedReader dis = new BufferedReader(
          new InputStreamReader(s.getInputStream()));
      // DataInputStream dis=new DataInputStream(s.getInputStream());
      DataOutputStream dout = new DataOutputStream(s.getOutputStream());

      // Sending HELO
      dout.write(("HELO\n").getBytes());
      dout.flush();

      // Receiving OK
      String str = (String) dis.readLine();
      System.out.println("Server message = '" + str + "'");
      dout.flush();

      // Sending AUTH
      dout.write(("AUTH Muneeb\n").getBytes());
      dout.flush();

      // Receiving OK
      str = (String) dis.readLine();
      System.out.println("Server message = '" + str + "'");
      dout.flush();

      // Sending REDY
      dout.write(("REDY\n").getBytes());
      dout.flush();

      // GETS Capable All

      str = (String) dis.readLine();
      String[] noneDetector = str.split(" ");
      System.out.println(noneDetector[0]);
      System.out.println(str);
      if (noneDetector[0] != "NONE")
        dout.write(("GETS All\n").getBytes());
      dout.flush();


      // Receiving Data x x
      str = (String) dis.readLine();
      String[] data = str.split(" ");

      // Sending OK
      dout.write(("OK\n").getBytes());
      dout.flush();

      String[] servers = new String[Integer.parseInt(data[1])];
      for (int i = 0; i < Integer.parseInt(data[1]); i++) {
        str = (String) dis.readLine();
        servers[i] = str;
      }
      // Sending OK
      dout.write(("OK\n").getBytes());
      dout.flush();

      int maxServerIndex = 0;
      for (int i = 1; i < servers.length; i++) {
        String[] maxServer = servers[maxServerIndex].split(" ");
        String[] currentServer = servers[i].split(" ");
        if (Integer.parseInt(maxServer[4]) < Integer.parseInt(currentServer[4])) {
          maxServerIndex = i;
        }
      }
      int maxServerIDcount = 0;
      for (int i = 0; i < servers.length; i++) {
        if (servers[maxServerIndex].split(" ")[0].equals(servers[i].split(" ")[0])) {
          maxServerIDcount = maxServerIDcount + 1;
        }
      }
      System.out.println(
          "Max Server: '" +
              servers[maxServerIndex] +
              "Count: " +
              maxServerIDcount +
              "'");

      int jobID = 0;
      int serverID = 0;
      int count = 0;
    
      while (!(noneDetector[0].equals("NONE"))) {
        //if (jobID != (maxServerIDcount - 1)) {
          //for (int i = 0; i < maxServerIDcount; i++) {
            if (noneDetector[0] .equals("JOBN")){
            //serverID = (serverID % maxServerIDcount);
            dout.write(
                ("SCHD " + jobID +" " + servers[maxServerIndex].split(" ")[0] +" " + serverID +"\n").getBytes());
            dout.flush();
            if ((serverID +1) == maxServerIDcount){
              serverID = 0;
            }
            else{
              serverID++;
            }
            jobID++;
            //dout.write(("REDY\n").getBytes());
            dout.flush();
            str = (String) dis.readLine();
            noneDetector = str.split(" ");
          }
          dout.write(("REDY\n").getBytes());
          dout.flush();
          str = (String) dis.readLine();
          noneDetector = str.split(" ");
          count++;
          System.out.println(Integer.toString(count)+" "+ noneDetector[0]);


        } //else {
          //jobID = 0;
       // }
     // }

      dout.write(("QUIT\n").getBytes());
      dout.flush();
      System.out.println("SENT: 'QUIT'");

      str = (String) dis.readLine();

      dout.close();
      s.close();

      System.exit(0);
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}