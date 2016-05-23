package mobpro.hslu.ch.teamsrmf;


import java.io.IOException;
import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
/**
 * A receiver thread for a single socket. Returns received request strings (echo
 * behavior). Terminates when it receives the message "EXIT" and closes the
 * socket.
 */
public class EchoReceiverThread extends Thread {
	private final static String EXIT = "EXIT";
	Socket tcpSocket = null;
        BenutzerManager manager;

	// Initialize thread with socket
	public EchoReceiverThread(Socket socket) {
		this.tcpSocket = socket;
                manager = new BenutzerManager();
	}

	public void run() {

		String address = tcpSocket.getRemoteSocketAddress().toString();
		while (tcpSocket != null) { 
			// loop untill exit received
			try {
                            //System.out.println("[DEBUG] " + address + " wait for request ....");    
                            ObjectInputStream inObj = new ObjectInputStream(tcpSocket.getInputStream());
                            ObjectOutputStream outObj = new ObjectOutputStream(tcpSocket.getOutputStream());
                            
                            //wait for request
                            User request = (User) inObj.readObject();
                            System.out.print("[DEBUG]Request received!\n");
                            
                            if(request != null){
                                if(request.getOldName() == null){
                                    manager.addBenutzer(request);
                                } else{
                                    manager.editBenutzer(request);
                                }
                            }
                            
                            //send answerd                     
                            outObj.writeObject(manager.getList());
                            outObj.flush();
                            outObj.close();
                            inObj.close();
			} catch (IOException e) {
                            System.out.println("[ERROR] socket.receive() " + e.toString());
                            tcpSocket = null;
			} catch (ClassNotFoundException ex){
                            System.out.println("[ERROR] receiving " + ex.toString());
                            tcpSocket = null;
                        }
		}
		System.out.println(address + " Receiver-Thread terminated !");
	}
}
