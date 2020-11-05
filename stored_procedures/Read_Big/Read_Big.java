import org.voltdb.*;
import java.io.File;
import java.io.RandomAccessFile;;

/* 
 * usage:
 * exec Read_Big file_name, user_name;
 */

public class Read_Big extends VoltProcedure {
    public final SQLStmt read =
	new SQLStmt("SELECT file_ptr FROM big_file WHERE user_name = ? AND file_name = ?;");

    public long run (String user_name, String file_name)
		throws VoltAbortException {
	    
		// run query
		voltQueueSQL(read,
					 user_name,
					 file_name);
		voltExecuteSQL();

		return 0;
    }
}
		     
