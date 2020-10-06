import org.voltdb.*;
import java.io.File;
import java.io.RandomAccessFile;;

public class WriteBench extends VoltProcedure {
    public final SQLStmt writeFile =
	new SQLStmt("UPDATE file SET bytes=? WHERE file_name=?;");
    // public final SQLStmt writeMany =
    // 	new SQLStmt("EXEC WriteFile ?;");

    public VoltTable[] run (int number_of_files, int number_of_writes, String data)
	throws VoltAbortException {

	String file_name;
	byte[] bytes = data.getBytes();

	for (int j=0; j<number_of_writes; j++) {
	    for (int i=0; i<number_of_files; i++) {
		file_name = "/home/gridsan/askiad/DBOS_shared/bench_files/dummy_" + String.valueOf(i);

		voltQueueSQL(writeFile,
			     bytes,
			     file_name);
	    }
	}
	
	return voltExecuteSQL();
    }
}
