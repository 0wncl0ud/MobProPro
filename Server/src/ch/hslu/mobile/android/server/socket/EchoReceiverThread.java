package ch.hslu.mobile.android.server.socket;

import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Iterator;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
/**
 * A receiver thread for a single socket. Returns received request strings (echo
 * behavior). Terminates when it receives the message "EXIT" and closes the
 * socket.
 */
public class EchoReceiverThread extends Thread {
	private final static String EXIT = "EXIT";
	TCPSocket tcpSocket = null;

	// Initialize thread with socket
	public EchoReceiverThread(TCPSocket socket) {
		this.tcpSocket = socket;
	}

	public void run() {

		String address = tcpSocket.getAddress();
		while (tcpSocket != null) { 
			// loop untill exit received
			try {
				System.out.println(address + " wait for request ....");
                                StringWriter out = new StringWriter();
                                try{
                                    JSONParser parser = new JSONParser();
                                    Object obj = parser.parse(new FileReader("test.txt"));
                                    JSONObject jsonObject = (JSONObject) obj;
                                    System.out.print(obj);
                                    jsonObject.writeJSONString(out);
                                    
                                    String name = (String) jsonObject.get("Name");
                                    String author = (String) jsonObject.get("Studiengang");
                                    JSONArray companyList = (JSONArray) jsonObject.get("Position");

                                    System.out.println("\nName: " + name);
                                    System.out.println("\nStudienrichtung: " + author);
                                    System.out.println("\nPosition:");
                                    Iterator<String> iterator = companyList.iterator();
                                    while (iterator.hasNext()) {
                                        System.out.println(iterator.next());
                                    }
                                }
                                catch(Exception e){
                                    
                                }
                                
                                //warte auf antwort
				String request = tcpSocket.receiveLine();
				String response = /*createResponseFor(request) +*/ out.toString();
				if (response == null) {
					tcpSocket.sendLine(EXIT);
					tcpSocket.close();
					tcpSocket = null; // cancels the loop
				} else {
                                        //antwort zurück
					tcpSocket.sendLine(response);
				}
			} catch (IOException e) {
				System.out.println("error socket.receive() " + e.toString());
				tcpSocket = null;
			}
		}
		System.out.println(address + " Receiver-Thread terminated !");
	}

	// Create response for request
	private String createResponseFor(String request) {
		String response = null;
		
		// ECHO message
		if (request != null) {
			if (request.equals(EXIT)) {
				response = null;
			} else {
				System.out.println(tcpSocket.getAddress() + " received '" + request + "'");
				response = "Response from Server: " + request;
			}
		} else {
			System.out.println(tcpSocket.getAddress() + " received = null");
			response = "empty";
		}
		
		return response;
	}
}
