import org.voltdb.*;
import java.io.File;
import java.io.FileInputStream; 
import java.io.InputStream; 
import java.io.IOException;
import java.io.RandomAccessFile;;

/* 
 * usage:
 * exec GetUserPartition user_name;
 */

public class GetUserPartition extends VoltProcedure {
    public final SQLStmt getPartition =
        new SQLStmt("SELECT home_partition FROM UserInfo " +
                    "WHERE user_name = ?;");

    public VoltTable[] run (String user_name)
        throws VoltAbortException {
        
        voltQueueSQL(getPartition,
                     user_name);
        return voltExecuteSQL(true);
    }
}
             
