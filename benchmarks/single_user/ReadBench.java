import org.voltdb.*;
import java.io.File;
import java.io.RandomAccessFile;;

public class ReadBench extends VoltProcedure {
    public final SQLStmt readFile =
	new SQLStmt("SELECT bytes FROM file WHERE file_name=?;");
    // public final SQLStmt readMany =
    // 	new SQLStmt("EXEC ReadFile ?;");

    public VoltTable[] run (int number_of_files, int number_of_reads)
	throws VoltAbortException {

	String file_name;

	for (int j=0; j<number_of_reads; j++) {
	    for (int i=0; i<number_of_files; i++) {
		file_name = "/home/gridsan/askiad/DBOS_shared/bench_files/dummy_" + String.valueOf(i);

		voltQueueSQL(readFile,
			     file_name);
	    }
	}
	
	return voltExecuteSQL();
    }
}
