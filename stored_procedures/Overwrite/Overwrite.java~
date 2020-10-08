import org.voltdb.*;
import java.io.File;
import java.io.RandomAccessFile;;

/* 
 * usage:
 * exec OverwriteFile file_name, data, user_name;
 */

public class OverwriteFile extends VoltProcedure {
    public final SQLStmt overwriteFile =
		new SQLStmt("UPDATE file SET bytes=? WHERE file_name=?;");

    public VoltTable[] run (String file_name, String data, String user_name)
		throws VoltAbortException {
	    
	    if (!file_name.startsWith("/")) {
			// since files are only in the DB, this is totally arbitrary
			// file_name = "/home/gridsan/groups/DBOS/fs_testfiles/" + file_name;
			file_name = "/" + user_name + "/" + file_name;
	    }

		byte[] bytes = data.getBytes();
		
		// run query
		voltQueueSQL(overwriteFile,
					 bytes,
					 file_name);
		
		return voltExecuteSQL();
    }
}
		     
