import org.voltdb.*;
import java.io.File;
import java.io.RandomAccessFile;;

/* 
 * usage:
 * exec CountBytes user_name;
 */

public class CountBytes extends VoltProcedure {
    public final SQLStmt countBytes =
		new SQLStmt("SELECT SUM(file_size) FROM file WHERE user_name = ? ;");
    public VoltTable[] run (String user_name)
		throws VoltAbortException {
	    
		voltQueueSQL(countBytes,
					 user_name);
		return voltExecuteSQL();
    }
}
		     
