import org.voltdb.*;
import java.io.File;
import java.io.RandomAccessFile;;

/* 
 * usage:
 * exec Delete;
 */

public class Delete extends VoltProcedure {
    public final SQLStmt deleteAll =
        new SQLStmt("DELETE FROM file;");

    public long run ()
        throws VoltAbortException {
        
        voltQueueSQL(deleteAll);
        voltExecuteSQL(true);
        return 0;
    }
}
             
