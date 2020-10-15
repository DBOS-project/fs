import org.voltdb.*;
import java.io.File;
import java.io.RandomAccessFile;;

/* 
 * usage:
 * exec Read file_name, user_name;
 */

public class Read extends VoltProcedure {
    public final SQLStmt read =
	new SQLStmt("SELECT bytes FROM file WHERE user_name = ? AND file_name = ?;");

    public long run (String user_name, String file_name)
		throws VoltAbortException {
	    
	    if (!file_name.startsWith("/")) {
			// since files are only in the DB, this is totally arbitrary
			// file_name = "/home/gridsan/groups/DBOS/fs_testfiles/" + file_name;
			file_name = "/" + user_name + "/" + file_name;
	    }
		
		// run query
		voltQueueSQL(read,
					 user_name,
					 file_name);
		voltExecuteSQL();

		return 0;
    }
}
		     
