import org.voltdb.*;
import java.io.File;
import java.io.FileInputStream; 
import java.io.InputStream; 
import java.io.IOException;
import java.io.RandomAccessFile;;

/* 
 * usage:
 * exec Read p_key, user_name, file_name;
 */

public class Read extends VoltProcedure {
    public final SQLStmt read_all =
        new SQLStmt("SELECT * FROM file WHERE p_key = ? AND user_name = ? AND file_name = ?;");

    public final SQLStmt read =
        new SQLStmt("SELECT bytes FROM file WHERE p_key = ? AND user_name = ? AND file_name = ?;");

    public final SQLStmt update_time =
        new SQLStmt("UPDATE file SET last_access = CURRENT_TIMESTAMP " +
                    "WHERE p_key = ? AND user_name = ? AND file_name = ?;");

    public final SQLStmt write =
        new SQLStmt("UPDATE file SET bytes = ?, present = 1, last_access = CURRENT_TIMESTAMP " +
                    "WHERE p_key = ? AND user_name = ? AND file_name = ?;");

    public VoltTable[] run (int p_key, String user_name, String file_name)
        throws VoltAbortException {
        
        if (!file_name.startsWith("/"))
            file_name = "/" + user_name + "/" + file_name;
        
        // run query
        voltQueueSQL(read_all,
                     p_key,
                     user_name,
                     file_name);
        VoltTable[] results = voltExecuteSQL();
        if (results.length > 0) {
            VoltTableRow file = results[0].fetchRow(0);
            if (file.getLong("present") == 0) {
                // load file from disk
                String file_ptr = new String(file.getVarbinary("bytes"));
                byte[] bytes = new byte[(int) file.getLong("file_size")];
                try {
                    File disk_file = new File(file_ptr);
                    FileInputStream fis = new FileInputStream(disk_file);
                    fis.read(bytes);
                    fis.close();
                    disk_file.delete();
                } catch (IOException e) {
                    System.out.println("failed to write");
                }

                // update file table
                voltQueueSQL(write,
                             bytes,
                             p_key,
                             user_name,
                             file_name);
                voltExecuteSQL();

            } else {
                voltQueueSQL(update_time,
                             p_key,
                             user_name,
                             file_name);
                voltExecuteSQL();
            }
        }

        voltQueueSQL(read,
                     p_key,
                     user_name,
                     file_name);

        return voltExecuteSQL();
    }
}
             
