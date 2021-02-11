import org.voltdb.*;
import java.io.File;
import java.io.RandomAccessFile;;

/* 
 * usage:
 * exec CreateUser home_partition, user_name;
 */

public class CreateUser extends VoltProcedure {
    public final SQLStmt createDir =
        // p_key, directory_name, content_name, content_type, user_name
        new SQLStmt("INSERT INTO Directory VALUES (?, ?, ?, 1, ?);");
    public final SQLStmt createUser =
        // home_partition, user_name, current_directory
        new SQLStmt("INSERT INTO UserInfo VALUES (?, ?, ?);");

    public long run (int partition, String user_name)
        throws VoltAbortException {

        String base_dir = "/";
        String user_dir = user_name + '/';

        voltQueueSQL(createDir,
                     partition,
                     base_dir,
                     user_dir,
                     user_name);                     
        voltQueueSQL(createUser,
                     partition,
                     user_name,
                     base_dir + user_dir);
        voltExecuteSQL(true);

        return 0;
    }
}
             
