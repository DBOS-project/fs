import org.voltdb.*;
import java.io.File;
import java.io.FileOutputStream; 
import java.io.OutputStream; 
import java.io.IOException;
import java.util.Arrays;

/* 
 * usage:
 * exec Write_Big user_name, file_name, data;
 */

public class Write_Big extends VoltProcedure {
    public final SQLStmt get =
		new SQLStmt("SELECT file_ptr FROM big_file WHERE user_name = ? AND file_name = ?;");

    public long run (String user_name, String file_name, String data)
		throws VoltAbortException {
	    
		byte[] bytes = data.getBytes();
	
		voltQueueSQL(get,
					 user_name,
					 file_name);
		VoltTable[] results = voltExecuteSQL();

		String file_ptr = results[0].fetchRow(0).getString("file_ptr");
		try {
			File file = new File(file_ptr);
			OutputStream os = new FileOutputStream(file);
            os.write(bytes);
            os.close(); 

		} catch (IOException e) {
			System.out.println("failed to write");
		}

		
		return 0;
    }
}
		     
