import org.voltdb.*;
import java.io.File;
import java.io.RandomAccessFile;;

/* 
 * usage:
 * exec CreateDir partition, directory_name, user_name;
 */

public class CreateDir extends VoltProcedure {
    public final SQLStmt getCurrDir =
        new SQLStmt("SELECT current_directory FROM UserInfo " +
                    "WHERE user_name = ?;");
    public final SQLStmt createDir =
        // p_key, directory_name, content_name, content_type, user_name
        new SQLStmt("INSERT INTO Directory VALUES (?, ?, ?, 1, ?);");

    public long run (int partition, String directory_name, String user_name)
        throws VoltAbortException {

        voltQueueSQL(getCurrDir,
                     user_name);
        VoltTable user_info = voltExecuteSQL()[0];
        if (user_info.getRowCount() < 1)
            return -1;

        String current_directory = user_info.fetchRow(0).getString(0);
        
        voltQueueSQL(createDir,
                     partition,
                     current_directory,
                     directory_name + '/',
                     user_name);
        voltExecuteSQL(true);

        return 0;
    }
}
             
