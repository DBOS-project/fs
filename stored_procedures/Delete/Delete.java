import org.voltdb.*;
import java.io.File;
import java.io.RandomAccessFile;;

/* 
 * usage:
<<<<<<< HEAD
 * exec Delete;
 */

public class Delete extends VoltProcedure {
    public final SQLStmt deleteAll =
        new SQLStmt("DELETE FROM file;");

    public long run ()
        throws VoltAbortException {
        
        voltQueueSQL(deleteAll);
        voltExecuteSQL(true);
=======
 * exec Delete p_key, file_name, user_name;
 */

public class Delete extends VoltProcedure {
    public final SQLStmt delete =
        new SQLStmt("DELETE FROM file WHERE p_key = ? AND user_name = ? AND file_name = ?;");

    public long run (int p_key, String user_name, String file_name)
        throws VoltAbortException {
        
        voltQueueSQL(delete,
                     p_key,
                     user_name,
                     file_name);
        voltExecuteSQL();
>>>>>>> 84a93e906dbd0856623e6b16194fba5bf76b556c

        return 0;
    }
}
             
