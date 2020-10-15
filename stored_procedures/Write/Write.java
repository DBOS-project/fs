import org.voltdb.*;
import java.io.File;
import java.io.RandomAccessFile;;

/* 
 * usage:
 * exec Write file_name, data, user_name;
 */

public class Write extends VoltProcedure {
    public final SQLStmt write =
		new SQLStmt("UPDATE file SET bytes = bytes || ? WHERE user_name = ? AND file_name = ?;");

    public long run (String user_name, String file_name, String data)
		throws VoltAbortException {
	    
	    if (!file_name.startsWith("/")) {
			// since files are only in the DB, this is totally arbitrary
			// file_name = "/home/gridsan/groups/DBOS/fs_testfiles/" + file_name;
			file_name = "/" + user_name + "/" + file_name;
	    }

		byte[] bytes = data.getBytes();
		
		// run query
		voltQueueSQL(write,
					 bytes,
					 user_name,
					 file_name);
		voltExecuteSQL();
		
		return 0;
    }
}
		     
