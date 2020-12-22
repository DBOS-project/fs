import org.voltdb.*;
import java.io.File;
import java.io.RandomAccessFile;;

/* 
 * usage:
 * exec Create file_name, user_name;
 */

public class Register extends VoltProcedure {
	public final SQLStmt declareFile =
		new SQLStmt("INSERT INTO distribution VALUES (?, ?, ?, ?);");

	public long run (int p_key, String user_name, String file_name, int block_number)
		throws VoltAbortException {

		if (!file_name.startsWith("/"))
			file_name = "/" + user_name + "/" + file_name;

		voltQueueSQL(declareFile,
					 p_key,
					 user_name,
					 file_name,
					 block_number);
		voltExecuteSQL();

		return 0;
	}
}
			 
