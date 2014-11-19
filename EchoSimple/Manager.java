import java.net.*;
import java.io.*;
import java.util.LinkedList;
import java.lang.String;

public class Manager 
{
   // Linked List where all the ID's are stored
   static LinkedList link = new LinkedList();
   static DatagramSocket socket;

   /**
   	* Method used to check id the id already exists.
   	* @flag Flag used to check a duplicated ID
   	* @l is an element of link to string and then to Bytes[]
    **/
   public static boolean checkDuplicate(String id) {
		boolean flag = false;

		for (int i = 0; i < link.size(); i++) {
			String l = new String((link.get(i).toString()).getBytes(), 0, 4);
			if(id.equals(l))
				flag = true; // DUPLICATED ID!
   		}
   		return flag;
	}

   /**
	* Method used to display all the elements of the
	* LinkedList.
   	**/
   public static void showElementsofLink() {
   		System.out.println("Recorded ID's: ");
		for (int i = 0; i < link.size(); i++)
			System.out.println(link.get(i));
   }


   public static void main(String args[]) {
	byte[] buffer = new byte[4096];
      try{
           socket = new DatagramSocket(7); // Port number to be connected
      } catch (Exception e){
	    System.err.println ("Unable to bind port");
	}
	for (;;){
	   try{

			DatagramPacket packet = new DatagramPacket(buffer, 4096);
			socket.receive(packet); // Received packet

			// Check the packet data, divide it by checking a new register
			String pData = new String(packet.getData(), 0, packet.getLength()-4);

			if (pData.equals("REGISTER")) {
				String newID = new String(packet.getData(), 8, packet.getLength()-8);

				if(checkDuplicate(newID))
					System.out.println("ID ALREADY EXISTS..");

				else {
					link.add(newID + packet.getSocketAddress());
					System.out.println("ID " + newID + " added..");
				}
				
			} // Try to register an ID

			else if (pData.equals("SHOW"))
						showElementsofLink();
			else
				System.out.println("Other operation..");

			socket.send(packet); // Packet resent to the source

	   }catch (IOException ioe){
		System.err.println ("Error : " + ioe);
	   }
	}	
   }
}