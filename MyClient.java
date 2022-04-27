import java.io.*;
import java.net.*;

public class MyClient {

  public static void main(String[] args) {
    try {
      Socket s = new Socket("localhost", 50000);
      BufferedReader dis = new BufferedReader(new InputStreamReader(s.getInputStream()));
      DataOutputStream dout = new DataOutputStream(s.getOutputStream());

      // Sending HELO
      dout.write(("HELO\n").getBytes());
      dout.flush();

      // Receiving OK
      String str = (String) dis.readLine();
      System.out.println("Server message = '" + str + "'");
      dout.flush();

      // Sending AUTH

      dout.write(("AUTH " + System.getProperty("user.name") + "\n").getBytes());
      dout.flush();

      // Receiving OK
      str = (String) dis.readLine();
      System.out.println("Server message = '" + str + "'");
      dout.flush();

      // Sending REDY
      dout.write(("REDY\n").getBytes());
      dout.flush();

      str = (String) dis.readLine();
      String[] noneDetector = str.split(" ");
      if (noneDetector[0] != "NONE") {
        String core = noneDetector[4];
        String ram = noneDetector[5];
        String hardDisk = noneDetector[6];

        dout.write(("GETS Capable " + core + " " + ram + " " + hardDisk + "\n").getBytes());
        dout.flush();
      }

      // Receiving Data in a single line that has String "DATA", no of servers and the
      // length of each server, seperated by spaces.
      // We use split() method to separate each of the three elements in a list called
      // data.
      str = (String) dis.readLine();
      String[] data = str.split(" ");

      // Sending OK
      dout.write(("OK\n").getBytes());
      dout.flush();

      // Recieving each server and storing it in a list called servers
      String[] servers = new String[Integer.parseInt(data[1])];
      for (int i = 0; i < Integer.parseInt(data[1]); i++) {
        str = (String) dis.readLine();
        servers[i] = str;
      }
      // Sending OK
      dout.write(("OK\n").getBytes());
      dout.flush();

      // inilialised a variable called maxServerIndex that would store index of the
      // first server that has the highest core value.
      int maxServerIndex = 0;
      // used a for loop that would iterate over each of the server in the servers
      // list and update value of maxServerIndex.
      for (int i = 1; i < servers.length; i++) {
        String[] maxServer = servers[maxServerIndex].split(" ");
        String[] currentServer = servers[i].split(" ");
        if (Integer.parseInt(maxServer[4]) < Integer.parseInt(currentServer[4])) {
          maxServerIndex = i;
        }
      }
      // inilialised a variable called maxServerIDcount that would store the total
      // number of servers in the list of the max core value
      int maxServerIDcount = 0;
      // used a for loop that would increment maxServerIDcount whenever a server is
      // found that has a max core
      for (int i = 0; i < servers.length; i++) {
        if (servers[maxServerIndex].split(" ")[0].equals(servers[i].split(" ")[0])) {
          maxServerIDcount++;
        }
      }
      // printing details of the max server and its count
      System.out.println("Max Server: '" + servers[maxServerIndex] + "Count: " + maxServerIDcount + "'");

      // jobID is a variable that stored the counter for each job found
      int jobID = 0;
      // serverID ranges within 0 and maxServerIDcount(exclusive). This variable is
      // used to schedule jobs to this server
      int serverID = 0;
      // the loop will stop iterating if jobtype is None
      while (!(noneDetector[0].equals("NONE"))) {
        // if jobtype is JOBN it will schedule this job
        if (noneDetector[0].equals("JOBN")) {
          dout.write(
              ("SCHD " + jobID + " " + servers[maxServerIndex].split(" ")[0] + " " + serverID + "\n").getBytes());
          dout.flush();
          // this if-else cond makes sure serverID is in the range(0,maxServerIDcount)
          if ((serverID + 1) == maxServerIDcount) {
            serverID = 0;
          } else {
            serverID++;
          }
          jobID++;
          dout.flush();
          // takes the next job and stores it for the next iteration of the while loop
          str = (String) dis.readLine();
          System.out.println(str);
        }
        str = (String) dis.readLine();
        noneDetector = str.split(" ");
        System.out.println(Integer.toString(jobID) + " " + str);
        
        if(!(noneDetector[0].equals("NONE"))) {
          dout.write(("REDY\n").getBytes());
          dout.flush();
          break;
        }

      }

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