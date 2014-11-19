import java.net.*;
import java.io.*;

public class Servidor 
{
   static DatagramSocket socket;

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
      theLine = userInput.readLine();
	  byte[] dataOut = new byte[theLine.length()];
	  dataOut = theLine.getBytes();
	  DatagramPacket sendPacket = new DatagramPacket(dataOut, dataOut.length, server, 7);
	  socket.send(sendPacket);
	  System.out.println("Sent..");
	  byte[] dataIn = new byte[256];
	  DatagramPacket receivePacket = new DatagramPacket(dataIn, 256);
	  socket.receive (receivePacket);
      String s = new String(receivePacket.getData(), 0, receivePacket.getLength());
      System.out.println("From server: " + s);
     }  // end try
     catch (UnknownHostException e) {System.err.println(e);}  
     catch (SocketException se) {System.err.println(se);}
     catch (IOException e) {System.err.println(e);}

//------------------------------------------------SERVER
	byte[] buffer = new byte[4096];
      try{
           socket = new DatagramSocket(4);
      } catch (Exception e){
	    System.err.println ("Unable to bind port");
	}
	for (;;){
	   try{
		DatagramPacket packet = new DatagramPacket(buffer, 4096);
		socket.receive(packet);
		System.out.println("Me llego..");
		socket.send(packet);
	   }catch (IOException ioe){
		System.err.println ("Error : " + ioe);
	   }
	}	
   }
}