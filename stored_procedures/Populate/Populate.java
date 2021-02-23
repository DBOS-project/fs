import org.voltdb.*;
import java.io.File;
import java.io.FileInputStream; 
import java.io.InputStream; 
import java.io.IOException;
import java.util.Arrays;

/* 
 * usage:
 * exec Populate p_key, file_name, block_number, bytes_number, user_name;
 */

public class Populate extends VoltProcedure {
    public final SQLStmt getCurrDir =
        new SQLStmt("SELECT current_directory FROM UserInfo " +
                    "WHERE user_name = ?;");
    public final SQLStmt read_info =
        new SQLStmt("SELECT present, file_size FROM file " +
                    "WHERE p_key = ? AND file_name = ? AND block_number = ? AND user_name = ? ;");
    public final SQLStmt read_content =
        new SQLStmt("SELECT bytes FROM file " +
                    "WHERE p_key = ? AND file_name = ? AND block_number = ? AND user_name = ? ;");
    public final SQLStmt write =
        new SQLStmt("UPDATE file SET present = 1, bytes = ?, file_size = ?, last_access = CURRENT_TIMESTAMP " +
                    "WHERE p_key = ? AND file_name = ? AND block_number = ? AND user_name = ?; ");

    public long run (int p_key, String file_name, int block_number, int size, String user_name)
        throws VoltAbortException {
        
        // get file absolute path
        voltQueueSQL(getCurrDir,
                     user_name);

        VoltTable[] user_query = voltExecuteSQL();
        VoltTable user_info = user_query[0];
        if (user_info.getRowCount() < 1)
            return -3;
        String current_directory = user_info.fetchRow(0).getString(0);
        file_name = current_directory + file_name;

        // read file present info
        voltQueueSQL(read_info,
                     p_key,
                     file_name,
                     block_number,
                     user_name);

        VoltTable[] file_query = voltExecuteSQL();
        if (file_query[0].getRowCount() < 1)
            return -2;
        VoltTableRow file_info = file_query[0].fetchRow(0);

        // delete old file from disk
        if (file_info.getLong("present") == 0) {
            // read file content
            voltQueueSQL(read_content,
                         p_key,
                         file_name,
                         block_number,
                         user_name);

            // delete file form disk
            VoltTableRow bytes_ptr = voltExecuteSQL()[0].fetchRow(0);            
            String file_ptr = new String(bytes_ptr.getVarbinary("bytes"));

            File disk_file = new File(file_ptr);
            disk_file.delete();
        }

        // fill file
        byte[] data = new byte[size];
        Arrays.fill(data, (byte) 1);
    
        voltQueueSQL(write,
                     data,
                     size,
                     p_key,
                     file_name,
                     block_number,
                     user_name);
        voltExecuteSQL(true);

        return 0;
    }
}
