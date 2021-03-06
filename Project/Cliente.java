import java.net.*;
import java.io.*;

public class Cliente{
   public static void main(String args[]){
	if (args.length != 1){
		System.err.println ("Syntax - java Cliente hostname");
		return;
	}
	String hostname = args[0];
	InetAddress addr = null;
	try{
	   addr = InetAddress.getByName(hostname);
	}catch (UnknownHostException uhe){
	   System.err.println ("Unable to resolve host");
	   return;
	}
      try {
      String theLine;
      InetAddress server = InetAddress.getByName(hostname);
      BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
      DatagramSocket socket = new DatagramSocket();
      while (true) {
        theLine = userInput.readLine();
        if (theLine.equals(".")) break;
        byte[] dataOut = new byte[theLine.length()];
        dataOut = theLine.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(dataOut, dataOut.length, server, 7);
        socket.send(sendPacket);
        System.out.println("Sent..");
      }  // end while
     }  // end try
     catch (UnknownHostException e) {System.err.println(e);}  
     catch (SocketException se) {System.err.println(se);}
     catch (IOException e) {System.err.println(e);}
  }  // end main
}
