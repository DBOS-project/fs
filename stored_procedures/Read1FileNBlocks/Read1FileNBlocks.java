import org.voltdb.*;
import java.io.File;
import java.io.FileInputStream; 
import java.io.InputStream; 
import java.io.IOException;
import java.io.RandomAccessFile;;

/* 
 * usage:
 * exec Read p_key, file_name, block_list, user_name;
 */

public class Read1FileNBlocks extends VoltProcedure {
    public final SQLStmt getCurrDir =
        new SQLStmt("SELECT current_directory FROM UserInfo " +
                    "WHERE user_name = ?;");
    public final SQLStmt read_content =
        new SQLStmt("SELECT bytes from file " +
                    "WHERE p_key = ? AND file_name = ? AND block_number IN (?,?,?,?,?) " +
                    "AND user_name = ? ;");

    // TODO: INLIST_OF_BIGINT instead of 5 hardcoded params
    public VoltTable[] run (int p_key, String file_name,
                            int blk1, int blk2, int blk3, int blk4, int blk5,
                            String user_name) throws VoltAbortException {
        
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
                     blk1, blk2, blk3, blk4, blk5,
                     user_name);
        return voltExecuteSQL(true);
    }
}
             
