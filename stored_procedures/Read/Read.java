import org.voltdb.*;
import java.io.File;
import java.io.FileInputStream; 
import java.io.InputStream; 
import java.io.IOException;
import java.io.RandomAccessFile;;

/* 
 * usage:
 * exec Read p_key, file_name, block_number, user_name;
 */

public class Read extends VoltProcedure {
    public final SQLStmt getCurrDir =
        new SQLStmt("SELECT current_directory FROM UserInfo " +
                    "WHERE user_name = ?;");
    public final SQLStmt read_content =
        new SQLStmt("SELECT bytes FROM file " +
                    "WHERE p_key = ? AND file_name = ? AND block_number = ? AND user_name = ? ;");

    public VoltTable[] run (int p_key, String file_name, int block_number, String user_name)
        throws VoltAbortException {
        
        // get file absolute path
        voltQueueSQL(getCurrDir,
                     user_name);

        VoltTable[] user_query = voltExecuteSQL();
        VoltTable user_info = user_query[0];
        if (user_info.getRowCount() < 1)
            return user_query;
        String current_directory = user_info.fetchRow(0).getString(0);
        file_name = current_directory + file_name;

        // read file content
        voltQueueSQL(read_content,
                     p_key,
                     file_name,
                     block_number,
                     user_name);
        return voltExecuteSQL(true);
    }
}
             
