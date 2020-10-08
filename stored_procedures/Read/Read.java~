import org.voltdb.*;
import java.io.File;
import java.io.RandomAccessFile;;

/* 
 * usage:
 * exec ReadFile file_name, user_name;
 */

public class ReadFile extends VoltProcedure {
    public final SQLStmt readFile =
	new SQLStmt("SELECT bytes FROM file WHERE file_name = ?;");

    public VoltTable[] run (String file_name, String user_name)
		throws VoltAbortException {
	    
	    if (!file_name.startsWith("/")) {
			// since files are only in the DB, this is totally arbitrary
			// file_name = "/home/gridsan/groups/DBOS/fs_testfiles/" + file_name;
			file_name = "/" + user_name + "/" + file_name;
	    }
		
		// run query
		voltQueueSQL(readFile,
					 file_name);

		return voltExecuteSQL();
    }
}
		     
