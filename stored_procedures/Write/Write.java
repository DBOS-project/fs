import org.voltdb.*;
import java.io.File;
import java.io.FileInputStream; 
import java.io.InputStream; 
import java.io.IOException;
import java.io.RandomAccessFile;;

/* 
 * usage:
 * exec Write p_key, user_name, file_name, file_contents;
 */

public class Write extends VoltProcedure {
    public final SQLStmt read =
        new SQLStmt("SELECT * FROM file WHERE p_key = ? AND user_name = ? AND file_name = ?;");
        
    public final SQLStmt write =
        new SQLStmt("UPDATE file SET present = 1, bytes = ?, file_size = ?, last_access = CURRENT_TIMESTAMP " +
                    "WHERE p_key = ? AND user_name = ? AND file_name = ?;");

    public long run (int p_key, String user_name, String file_name, String data)
        throws VoltAbortException {

        voltQueueSQL(read,
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

            }
        }
            
        byte[] bytes = data.getBytes();
        int size = bytes.length;
    
        voltQueueSQL(write,
                     data,
                     size,
                     p_key,
                     user_name,
                     file_name);
        voltExecuteSQL();
        
        return 0;
    }
}
             
