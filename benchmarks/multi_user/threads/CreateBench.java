import org.voltdb.*;
import org.voltdb.client.*;

public class CreateBench implements Runnable {
    private String username;
    private int numFiles;
   
    CreateBench(String name, int files) {
      username = name;
      numFiles = files;
   }

    public void run() {
	org.voltdb.client.Client client;
	try {
      	 	client = ClientFactory.createClient();
        	client.createConnection("localhost");
	} catch (Exception e) {
		System.out.println("failed to connect");
		return;
	}

        for (int i = 0; i < numFiles; i++) {
            // VoltTable[] results;
            try {
                client.callProcedure("CreateFile",
                                       "file" + Integer.toString(i),
                                       1,
                                      username);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }

    // takes number of users and number of files to be created as input
    public static void main(String args[]) {
        for (int i = 0; i < Integer.parseInt(args[0]); i++) {
            (new Thread(new CreateBench("user" + Integer.toString(i), Integer.parseInt(args[1])))).start();    
        }
    }

}
