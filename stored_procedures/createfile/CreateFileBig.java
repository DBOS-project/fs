import org.voltdb.*;
import java.io.File;
import java.io.RandomAccessFile;;

public class CreateFile extends VoltProcedure {
    public final SQLStmt insertFile =
	new SQLStmt("INSERT INTO file VALUES (?,?,1,?);");

    public VoltTable[] run (String file_name, long Mbytes, String... user)
	throws VoltAbortException {

	try {
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

	} catch (Exception e) {
	    System.out.println("[Error] Create file");
	}
	
	// if user_name is not given as an argument, derive it
	String user_name = user.length > 0 ? user[0] : System.getProperty("user.name");

	// new rule: all new files contain number 42
	byte[] bytes_array = new byte[1];
	bytes_array[0] = 42;
	    
	// populate DB
	voltQueueSQL(insertFile,
		     user_name,
		     file_name,
		     bytes_array);

	return voltExecuteSQL();
    }
}
		     
