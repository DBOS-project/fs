import org.voltdb.*;
import java.io.File;
import java.io.RandomAccessFile;;

public class WriteFile extends VoltProcedure {
    public final SQLStmt writeFile =
	new SQLStmt("UPDATE file SET bytes=? WHERE file_name=?;");

    public VoltTable[] run (String file_name, String data)
	throws VoltAbortException {
	    
	if (!file_name.startsWith("/")) {
	    file_name = "/home/gridsan/daniel/DBOS_shared/bench_files/" + file_name;
	}

	byte[] bytes = data.getBytes();

	// run query
	voltQueueSQL(writeFile,
		     bytes,
		     file_name);

	return voltExecuteSQL();
    }
}
		     
