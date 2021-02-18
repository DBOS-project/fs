import org.voltdb.*;
import java.io.File;
import java.io.FileInputStream; 
import java.io.InputStream; 
import java.io.IOException;
import java.io.RandomAccessFile;;

/* 
 * usage:
 * exec GetPartitionInfo ;
 */

public class GetPartitionInfo extends VoltProcedure {
    public final SQLStmt getPartition =
        new SQLStmt("SELECT * FROM PartitionInfo ORDER BY partition_id;");

    public VoltTable[] run ()
        throws VoltAbortException {
        
        voltQueueSQL(getPartition);
        return voltExecuteSQL(true);
    }
}
             
