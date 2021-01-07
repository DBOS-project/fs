import org.voltdb.*;
import java.io.File;
import java.io.FileOutputStream; 
import java.io.OutputStream; 
import java.io.IOException;
import java.util.Arrays;
import java.nio.charset.StandardCharsets;

/* 
 * usage:
 * exec Populate_Big main_memory_capacity, threshold;
 */

public class Check_Storage extends VoltProcedure {
    public final SQLStmt check_thresh =
		new SQLStmt("select sum(file_size) as total from file where block_number = 1;");

	public final SQLStmt get_oldest =
		new SQLStmt("select * from file order by last_access desc limit 1;");

	public final SQLStmt create_big =
		new SQLStmt("exec Create_Big ? ?;");

	public final SQLStmt write_big =
		new SQLStmt("exec Write_Big ? ? ?;");

	public final SQLStmt delete_from_mm =
		new SQLStmt("delete from file where user_name = ? and file_name = ?;");

    public long run (int threshold)
		throws VoltAbortException {
		
		voltQueueSQL(check_thresh);
		VoltTable[] thresh_results = voltExecuteSQL();

		long total = results[0].fetchRow(0).getLong("total");
		if (total > threshold) {
			voltQueueSQL(get_oldest);
			VoltTable[] oldest_results = voltExecuteSQL();
			VoltTableRow oldest = oldest_results.fetchRow(0);
			byte[] bytes = oldest.getVarbinary("bytes");
			String data = new String(bytes, StandardCharsets.UTF_8);
			String user = oldest.getString("user_name");
			String file_name = oldest.getString("file_name");
			String file = file_name.substring(file_name.indexOf(user) + user.length() + 1);

			voltQueueSQL(create_big, user, file);
			voltQueueSQL(write_big, user, file, data);
			voltQueueSQL(delete_from_mm, user, file_name);
		}

		
		return 0;
    }
}
		     
