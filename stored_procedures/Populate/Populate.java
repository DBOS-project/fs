import org.voltdb.*;
import java.io.File;
import java.io.FileInputStream; 
import java.io.InputStream; 
import java.io.IOException;
import java.util.Arrays;

/* 
 * usage:
 * exec Populate p_key, user_name, file_name, bytes_number;
 */

public class Populate extends VoltProcedure {
    public final SQLStmt read =
        new SQLStmt("SELECT * FROM file WHERE p_key = ? AND user_name = ? AND file_name = ?;");

    public final SQLStmt write =
        new SQLStmt("UPDATE file SET bytes = ?, file_size = ?, last_access = CURRENT_TIMESTAMP " +
                    "WHERE p_key = ? AND user_name = ? AND file_name = ?;");

    public long run (int p_key, String user_name, String file_name, int size)
        throws VoltAbortException {
        
        if (!file_name.startsWith("/")) {
            // since files are only in the DB, this is totally arbitrary
            // file_name = "/home/gridsan/groups/DBOS/fs_testfiles/" + file_name;
            file_name = "/" + user_name + "/" + file_name;
        }

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

        byte[] data = new byte[size];
        Arrays.fill(data, (byte) 1);
    
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
             
