import org.voltdb.*;
import java.io.File;
import java.io.RandomAccessFile;;

/* 
 * usage:
 * exec CountLargerThan user_name;
 */

public class CountLargerThan extends VoltProcedure {
    public final SQLStmt countLargerThan =
        new SQLStmt("SELECT COUNT(file_name) FROM file " +
                    "WHERE user_name = ? AND file_size >= ? ;");
    public VoltTable[] run (String user_name, int size)
        throws VoltAbortException {
        
        voltQueueSQL(countLargerThan,
                     user_name,
                     size);
        return voltExecuteSQL();
    }
}
             
