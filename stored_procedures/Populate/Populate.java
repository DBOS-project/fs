import org.voltdb.*;
import java.io.File;
import java.util.Arrays;

/* 
 * usage:
 * exec Populate file_name, user_name;
 */

public class Populate extends VoltProcedure {
    public final SQLStmt write =
		new SQLStmt("UPDATE file SET bytes = ? WHERE file_name= ?;");

    public VoltTable[] run (String file_name, int Mbytes, String user_name)
		throws VoltAbortException {
	    
	    if (!file_name.startsWith("/")) {
			// since files are only in the DB, this is totally arbitrary
			// file_name = "/home/gridsan/groups/DBOS/fs_testfiles/" + file_name;
			file_name = "/" + user_name + "/" + file_name;
	    }

		byte[] data1M = new byte[1024*1024*Mbytes];
		Arrays.fill(data1M, (byte) 1);
	
		voltQueueSQL(write,
					 data1M,
					 file_name);
		
		return voltExecuteSQL();
    }
}
		     
