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

public class WriteNFiles1Block extends VoltProcedure {
    public final SQLStmt getCurrDir =
        new SQLStmt("SELECT current_directory FROM UserInfo " +
                    "WHERE user_name = ?;");
    public final SQLStmt write =
        new SQLStmt("UPDATE file SET bytes = ?, file_size = ?" +
                    "WHERE p_key = ? AND file_name IN (?,?,?,?,?) AND block_number = ? " +
                    "AND user_name = ? ;");

    // TODO: INLIST_OF_BIGINT instead of 5 hardcoded params
    public VoltTable[] run (int p_key, int size, byte[] data,
                            String fnm1, String fnm2, String fnm3, String fnm4, String fnm5,
                            int block_number, String user_name) throws VoltAbortException {
        
        // get file absolute path
        voltQueueSQL(getCurrDir,
                     user_name);

        VoltTable[] user_query = voltExecuteSQL();
        VoltTable user_info = user_query[0];
        if (user_info.getRowCount() < 1)
            return user_query;
        String current_directory = user_info.fetchRow(0).getString(0);

        fnm1 = current_directory + fnm1;
        fnm2 = current_directory + fnm2;
        fnm3 = current_directory + fnm3;
        fnm4 = current_directory + fnm4;
        fnm5 = current_directory + fnm5;

        voltQueueSQL(write,
                     data,
                     size,
                     p_key,
                     fnm1, fnm2, fnm3, fnm4, fnm5,
                     block_number,
                     user_name);
        return voltExecuteSQL(true);
    }
}
             
