import org.voltdb.*;
import java.io.File;
import java.io.RandomAccessFile;;

/* 
 * usage:
 * exec WriteFile file_name, data, user_name;
 */

public class WriteFile extends VoltProcedure {
    public final SQLStmt writeFile =
		new SQLStmt("UPDATE file SET bytes = bytes || ? WHERE file_name = ?;");

    public VoltTable[] run (String file_name, String data, String user_name)
		throws VoltAbortException {
	    
	    if (!file_name.startsWith("/")) {
			// since files are only in the DB, this is totally arbitrary
			// file_name = "/home/gridsan/groups/DBOS/fs_testfiles/" + file_name;
			file_name = "/" + user_name + "/" + file_name;
	    }

		byte[] bytes = data.getBytes();
		
		// run query
		voltQueueSQL(writeFile,
					 bytes,
					 file_name);
		
		return voltExecuteSQL();
    }
}
		     
