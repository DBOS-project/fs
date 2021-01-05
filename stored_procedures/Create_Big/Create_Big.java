import org.voltdb.*;
import java.io.File;
import java.util.Map;
import java.io.RandomAccessFile;;

/* 
 * usage:
 * exec Create_Big file_name, user_name;
 */

public class Create_Big extends VoltProcedure {
    public final SQLStmt createFile =
    new SQLStmt("INSERT INTO big_file VALUES (?, ?, ?);");

    public long run (String user_name, String file_name)
        throws Exception {
        Map<String, String> env = System.getenv();
        if (!env.containsKey("TMPDIR")) {
            throw new Exception("path to disk could not be found for Create_Big");
        }
        String disk_path = env.get("TMPDIR");
        String file_ptr = disk_path + "/" + user_name + "/" + file_name;
        File new_file = new File(file_ptr);
        new_file.createNewFile();

        // populate DB
        voltQueueSQL(createFile,
                     user_name,
                     file_name,
                     file_ptr);
        voltExecuteSQL();

        return 0;
    }
}
             
