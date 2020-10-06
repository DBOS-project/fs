import org.voltdb.*;
import java.io.File;
import java.io.RandomAccessFile;;

public class CreateBench extends VoltProcedure {
    public final SQLStmt insertFile =
	new SQLStmt("INSERT INTO file VALUES (?,?,1,?);");
    // public final SQLStmt createMany =
    // 	new SQLStmt("EXEC CreateFile ? ?;");

    public VoltTable[] run (int number_of_files, long Mbytes)
	throws VoltAbortException {

	String file_name;
	String user_name = System.getProperty("user.name");
	byte[] bytes_array = new byte[1];
	bytes_array[0] = 42;

	try {
	    for (int i=0; i<number_of_files; i++) {
		file_name = "dummy_" + String.valueOf(i);

		// create file 
		if (!file_name.startsWith("/")) {
		    file_name = "/home/gridsan/askiad/DBOS_shared/bench_files/" + file_name;
		}
		File new_file = new File(file_name);
		new_file.createNewFile();

		// set file size
		RandomAccessFile raf = new RandomAccessFile(new_file, "rw");
		raf.setLength(Mbytes * 1024 * 1024);
		raf.close();

		voltQueueSQL(insertFile,
			     user_name,
			     file_name,
			     bytes_array);
	    }
	} catch (Exception e) {
	    System.out.println("[Error] Create file");
	}
	return voltExecuteSQL();
    }
}
