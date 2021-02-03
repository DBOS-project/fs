import org.voltdb.*;
import java.io.File;
import java.io.RandomAccessFile;;

/* 
 * usage:
 * exec Create p_key, user_name, file_name;
 */

public class Create extends VoltProcedure {
    public final SQLStmt createFile =
<<<<<<< HEAD
        // p_key, user_name, file_name, block_number, file_size, bytes
        new SQLStmt("INSERT INTO file VALUES (?, ?, ?, 1, 0, ?);");
=======
        new SQLStmt("INSERT INTO file VALUES (?, ?, ?, 1, 0, ?, CURRENT_TIMESTAMP);");
>>>>>>> 84a93e906dbd0856623e6b16194fba5bf76b556c

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
             
