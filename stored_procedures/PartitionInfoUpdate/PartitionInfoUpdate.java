import org.voltdb.*;
import java.io.File;
import java.io.RandomAccessFile;;

/* 
 * usage:
 * exec Create file_name, user_name;
 */

public class PartitionInfoUpdate extends VoltProcedure {
	public final SQLStmt updatePartition =
		new SQLStmt("UPDATE PartitionInfo SET host_id = ?, host_name = ? WHERE partition_id = ?;");

	public long run (int p_id, int host_id, String host_name)
		throws VoltAbortException {

		voltQueueSQL(updatePartition,
					 host_id,
					 host_name,
					 p_id);
		voltExecuteSQL();

		return 0;
	}
}
			 
