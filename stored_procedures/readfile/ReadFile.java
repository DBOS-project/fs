import org.voltdb.*;
import java.io.File;
import java.io.RandomAccessFile;;

public class ReadFile extends VoltProcedure {
    public final SQLStmt readFile =
	new SQLStmt("SELECT bytes FROM file WHERE file_name=?;");

    public VoltTable[] run (String file_name)
	throws VoltAbortException {
	    
	if (!file_name.startsWith("/")) {
	    file_name = "/home/gridsan/daniel/DBOS_shared/bench_files/" + file_name;
	}

	// run query
	voltQueueSQL(readFile,
		     file_name);

	return voltExecuteSQL();
    }
}
		     
