import org.voltdb.*;
import java.io.File;
import java.io.FileInputStream; 
import java.io.InputStream; 
import java.io.IOException;
import java.io.RandomAccessFile;;

/* 
 * usage:
 * exec GetPartitionRange start_partition (inclusive) end_partition (exclusive) ;
 */

public class GetPartitionRange extends VoltProcedure {
    public final SQLStmt getPartition =
        new SQLStmt("SELECT p_key FROM PartitionInfo " +
                    "WHERE partition_id >= ? AND partition_id < ? " +
                    "ORDER BY partition_id;");

    public VoltTable[] run (int start_partition, int end_partition)
        throws VoltAbortException {
        
        voltQueueSQL(getPartition, start_partition, end_partition);
        return voltExecuteSQL(true);
    }
}
             
