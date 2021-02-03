import org.voltdb.*;
import java.io.File;
import java.io.RandomAccessFile;;

/* 
 * usage:
<<<<<<< HEAD
 * exec Read p_key, user_name, file_name;
=======
 * exec Read p_key, file_name, user_name;
>>>>>>> 84a93e906dbd0856623e6b16194fba5bf76b556c
 */

public class Read extends VoltProcedure {
    public final SQLStmt read =
        new SQLStmt("SELECT bytes FROM file WHERE p_key = ? AND user_name = ? AND file_name = ?;");

    public VoltTable[] run (int p_key, String user_name, String file_name)
        throws VoltAbortException {
        
        if (!file_name.startsWith("/"))
            file_name = "/" + user_name + "/" + file_name;
        
        // run query
        voltQueueSQL(read,
                     p_key,
                     user_name,
                     file_name);
        VoltTable[] results = voltExecuteSQL(true);

        return results;
    }
}
             
