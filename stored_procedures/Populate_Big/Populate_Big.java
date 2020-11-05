import org.voltdb.*;
import java.io.File;
import java.io.FileOutputStream; 
import java.io.OutputStream; 
import java.util.Arrays;

/* 
 * usage:
 * exec Populate_Big user_name, file_name, bytes;
 */

public class Populate_Big extends VoltProcedure {
    public final SQLStmt write =
		new SQLStmt("UPDATE big_file SET bytes = ? WHERE user_name = ? AND file_name = ?;");

    public long run (String user_name, String file_name, int bytes)
		throws VoltAbortException {
	    
	    if (!file_name.startsWith("/")) {
			// since files are only in the DB, this is totally arbitrary
			// file_name = "/home/gridsan/groups/DBOS/fs_testfiles/" + file_name;
			file_name = "/" + user_name + "/" + file_name;
	    }

		byte[] data = new byte[bytes];
		Arrays.fill(data, (byte) 1);
	
		voltQueueSQL(write,
					 data,
					 user_name,
					 file_name);
		VoltTable[] results = voltExecuteSQL();
		String file_ptr = results[0].fetchRow(0).getString("file_ptr");
		OutputStream os = new FileOutputStream(file_ptr);
		os.write(data);
		os.close();

		
		return 0;
    }
}
		     
