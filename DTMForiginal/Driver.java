
import java.net.*;
import java.io.*;
import javax.swing.JOptionPane;
public class Driver {
        public static void main(String args[]) throws IOException {
                String hostname = args[0];
                InetAddress addr = null;
                try {
                        addr = InetAddress.getByName(hostname);
                } catch (UnknownHostException uhe) {
                        System.err.println ("Unable to resolve host");
                        return;
                }
               
                try {
                        String numero;
                        InetAddress server = InetAddress.getByName(hostname);
                        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
                        DatagramSocket socket = new DatagramSocket();
                        numero = JOptionPane.showInputDialog(null,"Marcar","Marcar",JOptionPane.PLAIN_MESSAGE);
                        if (numero.equals(".")) break;
                        byte[] dataOut = new byte[numero.length()];
                        dataOut = numero.getBytes();
                        DatagramPacket sendPacket = new DatagramPacket(dataOut, dataOut.length, server, 7);
                        socket.send(sendPacket);
                }  // end try
                catch (UnknownHostException e) {System.err.println(e);}
                catch (SocketException se) {System.err.println(se);}
                catch (IOException e) {System.err.println(e);}
        }  // end main
}