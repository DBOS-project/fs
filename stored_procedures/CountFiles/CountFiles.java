import org.voltdb.*;
import java.io.File;
import java.io.RandomAccessFile;;

/* 
 * usage:
 * exec CountFiles user_name;
 */

public class CountFiles extends VoltProcedure {
    public final SQLStmt countFiles =
        new SQLStmt("SELECT COUNT(file_name) FROM file WHERE user_name = ? ;");
    public VoltTable[] run (String user_name)
        throws VoltAbortException {
        
        voltQueueSQL(countFiles,
                     user_name);
        return voltExecuteSQL();
    }
}
             
