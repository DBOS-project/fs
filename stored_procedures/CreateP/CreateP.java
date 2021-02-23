import org.voltdb.*;
import java.io.File;
import java.io.RandomAccessFile;;

/* 
 * usage:
 * exec Create block_number, file_name, user_name;
 */

public class CreateP extends VoltProcedure {
    public final SQLStmt getCurrDir =
        new SQLStmt("SELECT current_directory FROM UserInfo " +
                    "WHERE user_name = ?;");
    public final SQLStmt insertToDir =
        // p_key, directory_name, content_name, content_type, user_name
        new SQLStmt("INSERT INTO Directory VALUES (?, ?, ?, 0, ?);");
    public final SQLStmt createFile =
        // p_key, user_name, file_name, block_number, file_size, bytes, present, timestamp
        new SQLStmt("INSERT INTO file VALUES (?, ?, ?, ?, 0, ?, 1, CURRENT_TIMESTAMP);");

    public long run (int block_number, String file_name, String user_name)
        throws VoltAbortException {

        if (file_name.startsWith("/"))
            return -1;

        // PFiles are partitioned on block_id
        int p_key = block_number;

        // get file absolute path
        voltQueueSQL(getCurrDir,
                     user_name);
        VoltTable user_info = voltExecuteSQL()[0];
        if (user_info.getRowCount() < 1)
            return -1;

        String current_directory = user_info.fetchRow(0).getString(0);
        voltQueueSQL(insertToDir,
                     p_key,
                     current_directory,
                     file_name,
                     user_name);

        // create file
        byte[] bytes_array = new byte[0];
        voltQueueSQL(createFile,
                     p_key,
                     user_name,
                     current_directory + file_name,
                     block_number,
                     bytes_array);
        voltExecuteSQL(true);

        return 0;
    }
}
             
