import org.voltdb.*;
import java.io.File;
import java.io.RandomAccessFile;;

/* 
 * usage:
 * exec Empty
 */

public class Empty extends VoltProcedure {
    public VoltTable[] run ()
        throws VoltAbortException {

        return voltExecuteSQL();
    }
}
             
