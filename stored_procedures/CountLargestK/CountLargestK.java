import org.voltdb.*;
import java.io.File;
import java.io.RandomAccessFile;;

/* 
 * usage:
 * exec CountLargestK k;
 */

public class CountLargestK extends VoltProcedure {
    public final SQLStmt countLargestK =
		new SQLStmt("SELECT TOP ? file.file_name, file.file_size FROM file "+
					"ORDER BY file.file_size DESC ;");
    public VoltTable[] run (int file_cnt)
		throws VoltAbortException {
	    
		voltQueueSQL(countLargestK,
					 file_cnt);
		return voltExecuteSQL();
    }
}
		     
