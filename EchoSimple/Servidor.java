import java.net.*;
import java.io.*;

public class Servidor 
{
   static DatagramSocket socket;

   public static void main(String args[]){

	byte[] buffer = new byte[4096];
      try{
           socket = new DatagramSocket(7);
      } catch (Exception e){
	    System.err.println ("Unable to bind port");
	}
	for (;;){
	   try{
		DatagramPacket packet = new DatagramPacket(buffer, 4096);
		socket.receive(packet);
		String id = new String(packet.getData(), 8, packet.getLength());
		System.out.println(id);
		socket.send(packet);
	   }catch (IOException ioe){
		System.err.println ("Error : " + ioe);
	   }
	}	
   }
}