import org.voltdb.*;
import java.io.File;
import java.io.RandomAccessFile;;

/* 
 * usage:
 * exec CreateBlock p_key, file_name, block_number user_name;
 */

public class CreateBlock extends VoltProcedure {
    public final SQLStmt getCurrDir =
        new SQLStmt("SELECT current_directory FROM UserInfo " +
                    "WHERE user_name = ?;");
    public final SQLStmt insertToDir =
        // p_key, directory_name, content_name, content_type, user_name
        new SQLStmt("INSERT INTO Directory VALUES (?, ?, ?, 0, ?);");
    public final SQLStmt createFile =
        // p_key, user_name, file_name, block_number, file_size, bytes
        new SQLStmt("INSERT INTO file VALUES (?, ?, ?, ?, 0, ?);");

    public long run (int p_key, String file_name, int block_number, String user_name)
        throws VoltAbortException {

        if (file_name.startsWith("/"))
            return -1;

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
             
