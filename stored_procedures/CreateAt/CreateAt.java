import org.voltdb.*;
import java.io.File;
import java.io.RandomAccessFile;;

/* 
 * usage:
 * exec CreateAt site_id, user_name, file_name;
 */

public class CreateAt extends VoltProcedure {
    public final SQLStmt getPkey =
        new SQLStmt("SELECT p_key FROM PartitionInfo WHERE partition_id = ?;");
    public final SQLStmt createFile =
        // p_key, user_name, file_name, block_number, file_size, bytes
        new SQLStmt("INSERT INTO File VALUES (?, ?, ?, 1, 0, ?, 1, CURRENT_TIMESTAMP);");

    public long run (int site_id, String user_name, String file_name)
        throws VoltAbortException {

        if (!file_name.startsWith("/"))
            file_name = "/" + user_name + "/" + file_name;

        byte[] bytes_array = new byte[0];

        voltQueueSQL(getPkey,
                     site_id);                                                   
        VoltTable partition_mapping = voltExecuteSQL()[0];
        long p_key = partition_mapping.fetchRow(0).getLong(0);
                     
        voltQueueSQL(createFile,
                     p_key,
                     user_name,
                     file_name,
                     bytes_array);
        voltExecuteSQL(true);

        return 0;
    }
}
             
