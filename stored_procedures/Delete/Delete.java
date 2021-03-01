import org.voltdb.*;
import java.io.File;
import java.io.RandomAccessFile;;

/* 
 * usage:
 * exec Delete p_key, file_name, user_name;
 */

public class Delete extends VoltProcedure {
    public final SQLStmt getCurrDir =
        new SQLStmt("SELECT current_directory FROM  UserInfo " +
                    "WHERE user_name = ?;");
    public final SQLStmt deleteFile =
        new SQLStmt("DELETE FROM file WHERE p_key = ? AND user_name = ? AND file_name = ?;");
    public final SQLStmt deleteFromDir =
        new SQLStmt("DELETE FROM directory WHERE p_key = ? AND user_name = ? " +
                    "AND directory_name = ? AND content_name = ?");

    public long run (int p_key, String file_name, String user_name)
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

        voltQueueSQL(deleteFile,
                     p_key,
                     user_name,
                     current_directory + file_name);
        voltQueueSQL(deleteFromDir,
                     p_key,
                     user_name,
                     current_directory,
                     file_name);
        voltExecuteSQL(true);

        return 0;
    }
}
             
