import org.voltdb.*;
import java.io.File;
import java.io.RandomAccessFile;;

/* 
 * usage:
 * exec Write user_name, file_name; ????????
 */

public class Write extends VoltProcedure {

    public final SQLStmt write =
		new SQLStmt("UPDATE file SET bytes .WRITE(0x42, NULL, NULL) WHERE user_name = ? AND file_name = ?;");
	//	new SQLStmt("UPDATE file SET bytes = bytes + data WHERE user_name = ? AND file_name = ?;");
    public long run (String user_name, String file_name, String data)
		throws VoltAbortException {
	    
	    if (!file_name.startsWith("/"))
			file_name = "/" + user_name + "/" + file_name;

		byte[] bytes = data.getBytes();
		
		// run query
		voltQueueSQL(write,
					 //	 data,
					 user_name,
					 file_name);
		voltExecuteSQL();
		
		return 0;
    }
}
		     
