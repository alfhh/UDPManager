import java.net.*;
import java.io.*;

public class Servidor 
{
   static DatagramSocket socket;

   public static void main(String args[]){

	Telefono phone=new Telefono();
	byte[] buffer = new byte[4096];
      try{
           socket = new DatagramSocket(7);
      } catch (Exception e){
	    System.err.println ("Unable to bind port");
	}
	
	System.out.println("Server iniciado");
	for (;;){
	   try{
		DatagramPacket packet = new DatagramPacket(buffer, 4096);
		socket.receive(packet);
		String s = new String(packet.getData(), 0, packet.getLength());
		System.out.println(s);
		phone.marcar(s);
		socket.send(packet);
	   }catch (IOException ioe){
		System.err.println ("Error : " + ioe);
	   }
	}	
   }
}