import org.voltdb.*;
import java.io.File;
import java.io.FileInputStream; 
import java.io.InputStream; 
import java.io.IOException;
import java.util.Arrays;

/* 
 * usage:
 * exec Populate p_key, file_name, block_number, bytes_number, data, user_name;
 */

public class PopulateWithBuffer extends VoltProcedure {
    public final SQLStmt getCurrDir =
        new SQLStmt("SELECT current_directory FROM UserInfo " +
                    "WHERE user_name = ?;");
    public final SQLStmt write =
        new SQLStmt("UPDATE file SET bytes = ?, file_size = ? " +
                    "WHERE p_key = ? AND file_name = ? AND block_number = ? AND user_name = ?; ");

    public long run (int p_key, String file_name, int block_number, int size, byte[] data, String user_name)
        throws VoltAbortException {
        
        // get file absolute path
        voltQueueSQL(getCurrDir,
                     user_name);

        VoltTable[] user_query = voltExecuteSQL();
        VoltTable user_info = user_query[0];
        if (user_info.getRowCount() < 1)
            return -3;
        String current_directory = user_info.fetchRow(0).getString(0);
        file_name = current_directory + file_name;

        voltQueueSQL(write,
                     data,
                     size,
                     p_key,
                     file_name,
                     block_number,
                     user_name);
        voltExecuteSQL(true);

        return 0;
    }
}
