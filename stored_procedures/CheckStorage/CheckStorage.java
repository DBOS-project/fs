import org.voltdb.*;
import java.io.File;
import java.io.FileOutputStream; 
import java.io.OutputStream; 
import java.io.IOException;
import java.util.Arrays;
import java.nio.charset.StandardCharsets;

/* 
 * usage:
 * exec CheckStorage threshold;
 */

public class CheckStorage extends VoltProcedure {
    public final SQLStmt check_thresh =
		new SQLStmt("select sum(file_size) as total from file where block_number = 1 and present = 1;");

	public final SQLStmt get_oldest =
		new SQLStmt("select * from file where present = 1 order by last_access asc limit 1;");

    public VoltTable[] run (int threshold)
		throws VoltAbortException {
		
		voltQueueSQL(check_thresh);
		VoltTable[] thresh_results = voltExecuteSQL();

		long total = thresh_results[0].fetchRow(0).getLong("total");
		if (total > threshold) {
			voltQueueSQL(get_oldest);
			VoltTable[] oldest_results = voltExecuteSQL();
			return oldest_results;
		}
		
		return new VoltTable[0];
    }
}
		 
