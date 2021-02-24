import org.voltdb.*;
import java.io.File;
import java.io.FileOutputStream; 
import java.io.OutputStream; 
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.nio.charset.StandardCharsets;

/* 
 * usage:
 * exec CheckStorage host threshold batch_size;
 */

public class CheckStorage extends VoltProcedure {
    public final SQLStmt check_thresh =
        new SQLStmt("SELECT SUM(file_size) AS TOTAL FROM File WHERE present = 1;");

    public final SQLStmt get_oldest =
        new SQLStmt("SELECT tmp.p_key, user_name, file_name, block_number FROM " + 
            "( SELECT p_key, user_name, file_name, block_number FROM File WHERE present = 1 and file_size > 0" + 
            "ORDER BY last_access ASC LIMIT ? ) AS tmp " + 
            "JOIN partitioninfo ON tmp.p_key = partitioninfo.p_key " + 
            "WHERE host_name = ?;");

    public VoltTable[] run (String host, long threshold, int batch_size)
        throws Exception {
        // check how much of MM is being used
        voltQueueSQL(check_thresh);
        VoltTable[] thresh_results = voltExecuteSQL();

        // compare against the threshold
        long total = thresh_results[0].fetchRow(0).getLong("total");
        // check if we need to move files to the disk
        if (total > threshold * .8) {
            // get file metadata in date order
            voltQueueSQL(get_oldest, batch_size, host);
            return voltExecuteSQL();
        }

        return new VoltTable[0];
    }
}
         
