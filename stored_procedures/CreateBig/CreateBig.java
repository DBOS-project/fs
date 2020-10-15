import org.voltdb.*;
import java.io.File;
import java.io.RandomAccessFile;;

/* 
 * usage:
 * exec Create file_name, user_name;
 */

public class Create extends VoltProcedure {
    public final SQLStmt createFile =
	new SQLStmt("INSERT INTO file VALUES (?, ?, 1, ?);");

    public long run (String user_name, String file_name)
		throws Exception {

		// create file on linux
		if (!file_name.startsWith("/")) {
			file_name = "/home/gridsan/askiad/DBOS_shared/fs/testing/tmpfiles/" + file_name;
		}
		File new_file = new File(file_name);
		new_file.createNewFile();

		// set file size
		RandomAccessFile raf = new RandomAccessFile(new_file, "rw");
		raf.setLength(Mbytes * 1024 * 1024);
		raf.close();
	
		// // populate DB
		// voltQueueSQL(createFile,
		// 			 user_name,
		// 			 file_name,
		// 			 bytes_array);
		// voltExecuteSQL();

		return 0;
    }
}
		     
