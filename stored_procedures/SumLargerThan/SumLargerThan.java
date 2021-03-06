import org.voltdb.*;
import java.io.File;
import java.io.RandomAccessFile;;

/* 
 * usage:
 * exec SumLargerThan p_key, size, user_name;
 */

public class SumLargerThan extends VoltProcedure {
    public final SQLStmt sumLargerThan =
        new SQLStmt("SELECT SUM(file_size) FROM file " +
                    "WHERE user_name = ? AND file_size >= ? ;");
    public VoltTable[] run (int p_key, int size, String user_name)
        throws VoltAbortException {
        
        voltQueueSQL(sumLargerThan,
                     user_name,
                     size);
        return voltExecuteSQL();
    }
}
             
