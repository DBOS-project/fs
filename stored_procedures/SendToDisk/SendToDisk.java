import org.voltdb.*;
import java.io.File;
import java.io.FileOutputStream; 
import java.io.OutputStream; 
import java.io.IOException;
import java.util.Map;
import java.io.RandomAccessFile;;

/* 
 * usage:
 * exec SendToDisk p_key, file_name, user_name, block_number;
 */

public class SendToDisk extends VoltProcedure {
    public final SQLStmt read =
        new SQLStmt("SELECT * FROM file WHERE p_key = ? AND user_name = ? AND file_name = ? AND block_number = ?;");

    public final SQLStmt write =
        new SQLStmt("UPDATE file SET bytes = ?, present = 0 " +
                    "WHERE p_key = ? AND user_name = ? AND file_name = ? AND block_number = ?;");

    public long run (int p_key, String user_name, String file_name, int block_number)
        throws Exception {
        voltQueueSQL(read,
                     p_key,
                     user_name,
                     file_name,
		     block_number);
        VoltTable[] results = voltExecuteSQL();
        if (results.length > 0) {
            VoltTableRow file = results[0].fetchRow(0);
            if (file.getLong("present") == 1) {
                byte[] data = file.getVarbinary("bytes");
                // make file on raw device
                Map<String, String> env = System.getenv();
                if (!env.containsKey("TMPDIR")) {
                    throw new Exception("path to disk could not be found for Create_Big");
                }
                String disk_path = env.get("TMPDIR");
		String original_name = file_name.substring(file_name.indexOf(user_name) + user_name.length() + 1);
                String file_ptr = disk_path + "/" + user_name + "_" + original_name + "_" + Long.toString(block_number);
                File new_file = new File(file_ptr);
                new_file.createNewFile();
                // write data
                try {
                    OutputStream os = new FileOutputStream(new_file);
                    os.write(data);
                    os.close(); 

                } catch (IOException e) {
                    System.out.println("failed to write");
                }
                // update file table
                voltQueueSQL(write,
                             file_ptr.getBytes(),
                             p_key,
                             user_name,
                             file_name,
			     block_number);
                voltExecuteSQL();
            }
        }


        return 0;
    }
}
             
