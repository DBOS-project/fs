import org.voltdb.*;
import java.io.File;
import java.io.FileOutputStream; 
import java.io.OutputStream; 
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.nio.charset.StandardCharsets;

/* 
 * usage:
 * exec CheckStorage threshold;
 */

public class CheckStorage extends VoltProcedure {
    public final SQLStmt check_thresh =
		new SQLStmt("select sum(file_size) as total from file where present = 1;");

	public final SQLStmt get_oldest =
		new SQLStmt("select p_key, user_name, file_name, block_number, file_size from file where present = 1 order by last_access asc limit 10000;");

	public final SQLStmt read =
        new SQLStmt("SELECT present, bytes FROM file WHERE p_key = ? AND user_name = ? AND file_name = ? AND block_number = ?;");

    public final SQLStmt write =
        new SQLStmt("UPDATE file SET bytes = ?, present = 0 " +
                    "WHERE p_key = ? AND user_name = ? AND file_name = ? AND block_number = ?;");

    public long run (long threshold)
		throws Exception {
		// check how much of MM is being used
		voltQueueSQL(check_thresh);
		VoltTable[] thresh_results = voltExecuteSQL();

		// compare against the threshold
		long total = thresh_results[0].fetchRow(0).getLong("total");
		// check if we need to move files to the disk
		if (total > threshold * .8) {
			// get file metadata in date order
			voltQueueSQL(get_oldest);
			VoltTable[] oldest_results = voltExecuteSQL();
			// move files until we go back down to 80% of the threshold
			long amount_to_move = total - (long)(threshold * .8);
			long amount_moved = 0;
			VoltTableRow file_info = oldest_results[0].fetchRow(0);
			while (amount_moved < amount_to_move) {
				// get the contents of this file
				long p_key = file_info.getLong("p_key");
				String user_name = file_info.getString("user_name");
				String file_name = file_info.getString("file_name");
				long block_number = file_info.getLong("block_number");
				voltQueueSQL(read,
                     p_key,
                     user_name,
                     file_name,
		     block_number);
        		VoltTable[] read_results = voltExecuteSQL();
        		if (read_results.length > 0) {
		            VoltTableRow file_contents = read_results[0].fetchRow(0);
		            if (file_contents.getLong("present") == 1) {
		                byte[] data = file_contents.getVarbinary("bytes");
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
		                amount_moved += file_info.getLong("file_size");
		            }
		        }
		        if (!file_info.advanceRow()) {
		        	break;
		        }
			}
		}

		return 0;
    }
}
		 
