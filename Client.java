import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.Socket;



public class Client {
	ObjectOutputStream out;
	ObjectInputStream in ;
	Socket clientSocket;
	Client(String ip){		
		System.out.println("wait");
			try {
				//"192.168.1.154"
		         clientSocket = new Socket(ip,1800); //ip locale  + port ouvert
		         out = new ObjectOutputStream(clientSocket.getOutputStream()); 
		         out.flush();         
		         in = new ObjectInputStream(clientSocket.getInputStream());	
	
			} catch (IOException e) {
		           e.printStackTrace();
		      }
		}
	
}
