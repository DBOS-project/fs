import org.voltdb.*;
import java.io.File;
import java.io.RandomAccessFile;;

/* 
 * usage:
 * exec Create p_key, user_name, file_name;
 */

public class Create extends VoltProcedure {
    public final SQLStmt createFile =
        // p_key, user_name, file_name, block_number, file_size, bytes, present, timestamp
        new SQLStmt("INSERT INTO file VALUES (?, ?, ?, 1, 0, ?, 1, CURRENT_TIMESTAMP);");

    public long run (int p_key, String user_name, String file_name)
        throws VoltAbortException {

        if (!file_name.startsWith("/"))
            file_name = "/" + user_name + "/" + file_name;

        byte[] bytes_array = new byte[0];
        
        voltQueueSQL(createFile,
                     p_key,
                     user_name,
                     file_name,
                     bytes_array);
        voltExecuteSQL(true);

        return 0;
    }
}
             
