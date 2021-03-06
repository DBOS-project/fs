import org.voltdb.*;
import java.io.File;
import java.io.RandomAccessFile;;

/* 
 * usage:
 * exec ChangeDir partition directory_name user_name;
 */

public class ChangeDir extends VoltProcedure {
    public final SQLStmt getCurrDir =
        new SQLStmt("SELECT current_directory FROM UserInfo " +
                    "WHERE user_name = ?;");
    // public final SQLStmt existAbsDir =
    //     new SQLStmt("SELECT content_name FROM Directory " +
    //                 "WHERE directory_name = ? ;");
    public final SQLStmt existRelDir =
        new SQLStmt("SELECT content_name FROM Directory " +
                    "WHERE directory_name = ? AND content_name = ? AND content_type = 1 ;");
    public final SQLStmt changeDir =
        new SQLStmt("UPDATE UserInfo SET current_directory = ? " +
                    "WHERE user_name = ? ;");

    public long run (int partition, String directory_name, String user_name)
        throws VoltAbortException {

        voltQueueSQL(getCurrDir,
                     user_name);
        VoltTable user_info = voltExecuteSQL()[0];
        if (user_info.getRowCount() < 1)
            return -1;

        String current_directory = user_info.fetchRow(0).getString(0);
        String destination_directory;

        if (directory_name.equals("..")) {
            if (current_directory.equals("/"))
                return 0;

            int idx = current_directory
                .substring(0, current_directory.length() - 1)
                .lastIndexOf('/');            

            destination_directory = current_directory.substring(0, idx) + '/';
        // } else if (directory_name.startsWith("/")) {
        //     voltQueueSQL(existAbsDir,
        //                  directory_name);
        //     VoltTable exist_dir = voltExecuteSQL()[0];
        //     if (exist_dir.getRowCount() < 1)
        //         return -1;
                        
        //     destination_directory = directory_name;
        } else {
            voltQueueSQL(existRelDir,
                         current_directory,
                         directory_name + '/');
            VoltTable exist_dir = voltExecuteSQL()[0];
            if (exist_dir.getRowCount() < 1)
                return -1;
                        
            destination_directory = current_directory + directory_name + '/';
        }
        
        voltQueueSQL(changeDir,
                     destination_directory,
                     user_name);
        voltExecuteSQL(true);

        return 0;
    }
}
             
