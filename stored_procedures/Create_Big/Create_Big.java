import org.voltdb.*;
import java.io.File;
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

        String file_ptr = "/home/gridsan/dhong98/DBOS_shared/daniel/fs/tmpfiles/" + user_name + "_" + file_name;
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
             
