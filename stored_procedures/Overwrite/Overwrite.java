import org.voltdb.*;
import java.io.File;
import java.io.RandomAccessFile;;

/* 
 * usage:
 * exec Overwrite file_name, data, user_name;
 */

public class Overwrite extends VoltProcedure {
    public final SQLStmt overwrite =
		new SQLStmt("UPDATE file SET bytes=? WHERE user_name = ? AND file_name = ?;");

    public VoltTable[] run (String user_name, String file_name, String data)
		throws VoltAbortException {
	    
	    if (!file_name.startsWith("/")) {
			// since files are only in the DB, this is totally arbitrary
			// file_name = "/home/gridsan/groups/DBOS/fs_testfiles/" + file_name;
			file_name = "/" + user_name + "/" + file_name;
	    }

		byte[] bytes = data.getBytes();
		
		// run query
		voltQueueSQL(overwrite,
					 bytes,
					 user_name,
					 file_name);
		
		return voltExecuteSQL();
    }
}
		     
