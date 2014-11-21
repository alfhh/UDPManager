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
   	* Method used to check if the ID used as source is correct
    **/
   public static boolean checkID(String id) {
		boolean flag = false;

		for (int i = 0; i < link.size(); i++) {
			String  l = new String(link.get(i).toString());
			String [] temp = l.split(":");
			if(id.equals(temp[0]))
				flag = true; // DUPLICATED ID!
   		}
   		return flag;
	}



   /**
	* To return the Address of an ID
	* @d ID of the destination
   	**/
   public static String searchAddress(String d) {
   		String sAddress = "";	
   		String[] temp2 = null;
   		for (int i = 0; i < link.size(); i++) {
			String l = new String((link.get(i).toString()).getBytes(), 0, 4);
			if(d.equals(l)){
				String temp = link.get(i).toString();
				sAddress = temp.substring(5, temp.length());
				temp2 = sAddress.split(":");
				System.out.println(temp2[0]);
   			}
		}
		return temp2[0];
   }


   public static String getPort(String d) {
   		String sAddress = "";	
   		String[] temp2 = null;
   		for (int i = 0; i < link.size(); i++) {
			String l = new String((link.get(i).toString()).getBytes(), 0, 4);
			if(d.equals(l)){
				String temp = link.get(i).toString();
				sAddress = temp.substring(5, temp.length());
				temp2 = sAddress.split(":");
				//System.out.println(temp2[1]);
   			}
		}
		return temp2[1];
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

   /**
   	* Method used to send the message of a client to the server
   	* @s ID of the source
   	* @d ID of the destination
   	* @m message to be sent to the server, with source and destination IDs
    **/
   public static void sendToServer(String s, String d, String m) {
   		try{

	   		DatagramSocket nuevo = new DatagramSocket();
	   		InetAddress addr = InetAddress.getByName(searchAddress(d));
	   		nuevo.connect(addr, Integer.parseInt(getPort(d))+1);
	   		//DatagramPacket ms = new DatagramPacket((s+m).getBytes(), (s+m).length(), addr, Integer.parseInt(getPort(d)));
	   		DatagramPacket ms = new DatagramPacket((s+m).getBytes(), (s+m).length(), addr, Integer.parseInt(getPort(d))+1);
        	nuevo.send(ms);
		}
		catch (UnknownHostException uhe){}
     	catch (SocketException se) {System.err.println(se);}
     	catch (IOException e) {System.err.println(e);}
     	catch (Exception e) {}	
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
			//String pData = new String(packet.getData(), 0, packet.getLength()-4);
			String pData = "";
			String register = "";
			String slink = "";
			boolean badrequest = false;
			try {
				pData = new String(packet.getData(), 0, packet.getLength());
				register = pData.substring(0, 8);
				slink = pData.substring(0, 8);
			} catch (StringIndexOutOfBoundsException e) {
				badrequest = true;
				System.out.println("..Bad Request");
			}
			

			if (register.equals("REGISTER")) {
				String newID = new String(packet.getData(), 8, packet.getLength()-8);

				if(checkDuplicate(newID))
					System.out.println("ID ALREADY EXISTS..");

				else {
					link.add(newID + packet.getSocketAddress()); // Address of the Socket
					InetAddress addr = InetAddress.getByName(searchAddress(newID));
					DatagramPacket port = new DatagramPacket(getPort(newID).getBytes(), getPort(newID).length(), addr, Integer.parseInt(getPort(newID)));
					socket.send(port);
					System.out.println("ID " + newID + " added..");
				}
				
			} // Try to register an ID

			else if (slink.equals("SHOWLINK"))
						showElementsofLink();
			else if(!badrequest) {
				String source = pData.substring(0, 4);
				String destination = pData.substring(4, 8);
				String mensaje = pData.substring(8, pData.length());

				if(!checkID(source+packet.getAddress()))
					System.out.println("You are not " + source);

				else if(!checkDuplicate(destination))
					System.out.println("Target not found");
				
				else {
					sendToServer(source, destination, mensaje);
					System.out.println("Message sent");
				}
			}

	   }catch (IOException ioe){
		System.err.println ("Error : " + ioe);
	   }
	}	
   }
}