import org.voltdb.*;
import java.io.File;
import java.io.FileInputStream; 
import java.io.InputStream; 
import java.io.IOException;
import java.io.RandomAccessFile;;

/* 
 * usage:
 * exec Read p_key, file_name, block_number, user_name;
 */

public class Read extends VoltProcedure {
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
        new SQLStmt("UPDATE file SET bytes = ?, present = 1, last_access = CURRENT_TIMESTAMP " +
                    "WHERE p_key = ? AND file_name = ? AND block_number = ? AND user_name = ? ;");
    public final SQLStmt update_time =
        new SQLStmt("UPDATE file SET last_access = CURRENT_TIMESTAMP " +
                    "WHERE p_key = ? AND file_name = ? AND block_number = ? AND user_name = ? ;");

    public VoltTable[] run (int p_key, String file_name, int block_number, String user_name)
        throws VoltAbortException {
        
        // get file absolute path
        voltQueueSQL(getCurrDir,
                     user_name);

        VoltTable[] user_query = voltExecuteSQL();
        VoltTable user_info = user_query[0];
        if (user_info.getRowCount() < 1)
            return user_query;
        String current_directory = user_info.fetchRow(0).getString(0);
        file_name = current_directory + file_name;
        
        // read file present and size info
        voltQueueSQL(read_info,
                     p_key,
                     file_name,
                     block_number,
                     user_name);
        
        VoltTable[] file_query = voltExecuteSQL();
        if (file_query[0].getRowCount() < 1)
            return file_query;
        VoltTableRow file_info = file_query[0].fetchRow(0);

        // load file from disk
        if (file_info.getLong("present") == 0) {
            // read file content
            voltQueueSQL(read_content,
                         p_key,
                         file_name,
                         block_number,
                         user_name);

            // get content from disk
            byte[] bytes = new byte[(int) file_info.getLong("file_size")];
            VoltTableRow bytes_ptr = voltExecuteSQL()[0].fetchRow(0);            
            String file_ptr = new String(bytes_ptr.getVarbinary("bytes"));
            try {
                File disk_file = new File(file_ptr);
                FileInputStream fis = new FileInputStream(disk_file);
                fis.read(bytes);
                fis.close();
                disk_file.delete();
            } catch (IOException e) {
                System.out.println("failed to restore file from disk");
                e.printStackTrace();
            }

            // restore file from disk and update timestamp
            voltQueueSQL(write,
                         bytes,
                         p_key,
                         file_name,
                         block_number,
                         user_name);
            voltExecuteSQL();

        }
        // file is present, only update timestamp
        else {
            voltQueueSQL(update_time,
                         p_key,
                         file_name,
                         block_number,
                         user_name);
            voltExecuteSQL();
        }

        // read (full) file content
        voltQueueSQL(read_content,
                     p_key,
                     file_name,
                     block_number,
                     user_name);
        return voltExecuteSQL(true);
    }
}
             
