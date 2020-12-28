import org.voltdb.*;
import java.io.File;
import java.io.RandomAccessFile;;

/* 
 * usage:
 * exec Create file_name, user_name;
 */

public class PartitionInfoInsert extends VoltProcedure {
    public final SQLStmt insertPartition =
        new SQLStmt("INSERT INTO PartitionInfo VALUES (?, ?, ?, ?);");

    public long run (int p_key, int p_id, int host_id, String host_name)
        throws VoltAbortException {

        voltQueueSQL(insertPartition,
                     p_key,
                     p_id,
                     host_id,
                     host_name);
        voltExecuteSQL();

        return 0;
    }
}
             
