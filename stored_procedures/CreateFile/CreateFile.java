import org.voltdb.*;
import java.io.File;
import java.io.RandomAccessFile;;

/* 
 * usage:
 * exec CreateFile file_name, Mbytes, user_name;
 */

public class CreateFile extends VoltProcedure {
    public final SQLStmt insertFile =
	new SQLStmt("INSERT INTO file VALUES (?, ?, 1, ?);");

    public VoltTable[] run (String file_name, long Mbytes, String user_name)
		throws VoltAbortException {

	    if (!file_name.startsWith("/")) {
			// since files are only in the DB, this is totally arbitrary
			// file_name = "/home/gridsan/groups/DBOS/fs_testfiles/" + file_name;
			file_name = "/" + user_name + "/" + file_name;
	    }

		// new rule: all new files contain number 42
		byte[] bytes_array = new byte[1];
		bytes_array[0] = 42;
	    
		// populate DB
		voltQueueSQL(insertFile,
					 user_name,
					 file_name,
					 bytes_array);

		return voltExecuteSQL();
    }
}
		     
