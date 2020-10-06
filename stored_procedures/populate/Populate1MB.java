import org.voltdb.*;
import java.io.File;
import java.util.Arrays;

public class Populate1MB extends VoltProcedure {
    public final SQLStmt write =
	new SQLStmt("UPDATE file SET bytes=? WHERE file_name=?;");

    public VoltTable[] run (String file_name, int Mbytes)
	throws VoltAbortException {
	    
	if (!file_name.startsWith("/")) {
	    file_name = "/home/gridsan/askiad/DBOS_shared/bench_files/" + file_name;
	}

	byte[] data1M = new byte[1024*1024*Mbytes];
	Arrays.fill(data1M, (byte) 1);

	voltQueueSQL(write,
		     data1M,
		     file_name);
	
	return voltExecuteSQL();
    }
}
		     
