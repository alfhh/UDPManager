import java.net.*;
import java.io.*;
import java.util.LinkedList;

public class Servidor 
{
   static LinkedList link = new LinkedList();
   static DatagramSocket socket;

   public static void main(String args[]){

	byte[] buffer = new byte[4096];
      try{
           socket = new DatagramSocket(7); //Port number to be connected
      } catch (Exception e){
	    System.err.println ("Unable to bind port");
	}
	for (;;){
	   try{

			DatagramPacket packet = new DatagramPacket(buffer, 4096);
			socket.receive(packet);

			String temp = new String(packet.getData(), 0, packet.getLength()-4);

			if (temp.equals("REGISTRO")) {
				String newID = new String(packet.getData(), 8, packet.getLength()-8);
				link.add(newID);
				System.out.println("ID " + newID + " added..");
			}

			else if (temp.equals("SHOW")) {
				System.out.println("Recorded ID's: ");
				for (int i = 0; i < link.size(); i++) {
					System.out.println(link.get(i));
				}
			}

			else{

				System.out.println("Other operation..");
			}

			socket.send(packet); // Packet resent to the source

	   }catch (IOException ioe){
		System.err.println ("Error : " + ioe);
	   }
	}	
   }
}