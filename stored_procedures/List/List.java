import org.voltdb.*;
import java.io.File;
import java.io.RandomAccessFile;;

/* 
 * usage:
 * exec List user_name;
 */

public class List extends VoltProcedure {
    public final SQLStmt getCurrDir =
        new SQLStmt("SELECT current_directory From UserInfo " +
                    "WHERE user_name = ?;");
    public final SQLStmt list =
        new SQLStmt("SELECT * FROM Directory WHERE directory_name = ? ;");

    public VoltTable[] run (String user_name)
        throws VoltAbortException {

        voltQueueSQL(getCurrDir,
                     user_name);
        VoltTable[] user_info = voltExecuteSQL();
        if (user_info[0].getRowCount() < 1)
            return user_info;

        String current_directory = user_info[0].fetchRow(0).getString(0);
        
        voltQueueSQL(list,
                     current_directory);
        return voltExecuteSQL(true);
    }
}
             
