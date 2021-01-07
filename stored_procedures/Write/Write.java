import org.voltdb.*;
import java.io.File;
import java.io.RandomAccessFile;;

/* 
 * usage:
 * exec Write p_key, user_name, file_name, file_contents;
 */

public class Write extends VoltProcedure {
    public final SQLStmt write =
        new SQLStmt("UPDATE file SET bytes = ?, file_size = ?" +
                    "WHERE p_key = ? AND user_name = ? AND file_name = ?;");

    public long run (String user_name, String file_name, String data)
        throws VoltAbortException {
            
        byte[] bytes = data.getBytes();
        int size = bytes.length;
    
        voltQueueSQL(write,
                     data,
                     size,
                     p_key,
                     user_name,
                     file_name);
        voltExecuteSQL();
        
        return 0;
    }
}
             
