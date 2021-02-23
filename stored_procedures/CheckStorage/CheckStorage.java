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
 * exec CheckStorage threshold;
 */

public class CheckStorage extends VoltProcedure {
    public final SQLStmt check_thresh =
        new SQLStmt("SELECT SUM(file_size) AS TOTAL FROM File WHERE present = 1;");

    public final SQLStmt get_oldest =
        new SQLStmt("SELECT p_key, user_name, file_name, block_number FROM File " +
                    "WHERE present = 1 ORDER BY last_access ASC LIMIT ?;");

    public VoltTable[] run (long threshold, int batch_size)
        throws Exception {
        // check how much of MM is being used
        voltQueueSQL(check_thresh);
        VoltTable[] thresh_results = voltExecuteSQL();

        // compare against the threshold
        long total = thresh_results[0].fetchRow(0).getLong("total");
        // check if we need to move files to the disk
        if (total > threshold * .8) {
            // get file metadata in date order
            voltQueueSQL(get_oldest, batch_size);
            return voltExecuteSQL();
        }

        return new VoltTable[0];
    }
}
         
