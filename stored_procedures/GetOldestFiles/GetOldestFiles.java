import org.voltdb.*;

/* 
 * usage:
 * exec GetOldestFiles host batch_size;
 */

public class GetOldestFiles extends VoltProcedure {
    public final SQLStmt get_oldest =
        new SQLStmt("SELECT tmp.p_key, user_name, file_name, block_number FROM " + 
            "( SELECT p_key, user_name, file_name, block_number FROM File WHERE present = 1 and file_size > 0" + 
            "ORDER BY last_access ASC LIMIT ? ) AS tmp " + 
            "JOIN partitioninfo ON tmp.p_key = partitioninfo.p_key " + 
            "WHERE host_name = ?;");

    public VoltTable[] run (String host, int batch_size)
        throws Exception {
        voltQueueSQL(get_oldest, batch_size, host);
        return voltExecuteSQL();
    }
}
         
