import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.*;
import java.util.*;

public class TimeServer extends Thread {
    private ServerSocket sock;

    public TimeServer() {
        super();
        try {
	    // Initialize sock on port 23, Telnet's port
            sock = new ServerSocket(23);
            System.out.println("TimeServer running ...");
        } catch (IOException e) {
	    // Can't do much here without the socket.
            System.out.println("Error: couldn't create socket.");
            System.exit(1);
        }
    }

    public void run() {
        Socket client = null;
        boolean exit = false;

        while (true) {
            if (sock == null)
                return;
            try {
		// accept client
                client = sock.accept();
		// create output stream
                BufferedOutputStream bos = new BufferedOutputStream(client.getOutputStream());
		// create printwriter with it
                PrintWriter pw = new PrintWriter(bos, false);
		// create date representing now
                Date now = new Date();
		// and print it
                pw.println(now);
                pw.flush();
                pw.close();
                client.close();
            } catch (IOException e) {
                System.out.println("Error: couldn't connect to client.");
                System.exit(1);
            }
        }
    }

    public static void main(String[] args) {
        TimeServer server = new TimeServer();
        server.start();
    }
}