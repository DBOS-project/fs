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

public class ReadNFiles1Block extends VoltProcedure {
    public final SQLStmt getCurrDir =
        new SQLStmt("SELECT current_directory FROM UserInfo " +
                    "WHERE user_name = ?;");
    public final SQLStmt read =
        new SQLStmt("SELECT bytes from file " +
                    "WHERE p_key = ? AND file_name = ? AND block_number = ? " +
                    "AND user_name = ? ;");

    public VoltTable[] run (int p_key, String file_names[], int block_number,
                            String user_name, int rand_size) throws VoltAbortException {
        
        // get file absolute path
        voltQueueSQL(getCurrDir,
                     user_name);

        VoltTable[] user_query = voltExecuteSQL();
        VoltTable user_info = user_query[0];
        if (user_info.getRowCount() < 1)
            return user_query;
        String current_directory = user_info.fetchRow(0).getString(0);

        for (int i=0; i<rand_size; i++) {
            file_names[i] = current_directory + file_names[i];

            voltQueueSQL(read,
                         p_key,
                         file_names[i],
                         block_number,
                         user_name);
        }
        return voltExecuteSQL(true);
    }
}
             
