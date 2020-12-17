import org.voltdb.*;
import java.io.File;
import java.util.Arrays;

/* 
 * usage:
 * exec Populate file_name, bytes_number, user_name;
 */

public class Populate extends VoltProcedure {
    public final SQLStmt write =
		new SQLStmt("UPDATE file SET bytes = ?, file_size = ?" +
					"WHERE p_key = ? AND user_name = ? AND file_name = ?;");

    public long run (int p_key, String user_name, String file_name, int size)
		throws VoltAbortException {
	    
	    if (!file_name.startsWith("/")) {
			// since files are only in the DB, this is totally arbitrary
			// file_name = "/home/gridsan/groups/DBOS/fs_testfiles/" + file_name;
			file_name = "/" + user_name + "/" + file_name;
	    }

		byte[] data = new byte[size];
		Arrays.fill(data, (byte) 1);
	
		voltQueueSQL(write,
					 data,
					 size,
					 p_key,
					 user_name,
					 file_name);
		voltExecuteSQL();

		return 0;
    }
}
		     
