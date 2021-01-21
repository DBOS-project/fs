import org.voltdb.*;
import java.io.File;
import java.io.RandomAccessFile;;

/* 
 * usage:
 * exec Create file_name, user_name;
 */

public class PartitionInfoSelect extends VoltProcedure {
    public final SQLStmt getPartitionInfo =
        new SQLStmt("SELECT p_key, partition_id from PartitionInfo;");

    public VoltTable[] run ()
        throws VoltAbortException {

        voltQueueSQL(getPartitionInfo);
        VoltTable[] results = voltExecuteSQL(true);

        return results;
    }
}
             
