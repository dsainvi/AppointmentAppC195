package dbmanager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//formats sql text before sending it off to sql
/**
 * QueryManger
 * formats sql text before sending it off to sql
 */
public class QueryManager {
    /**
     * ResultSet reslt
     * Executes the given SQL statement, which returns a single ResultSet object.
     *
     */
    private static String q;
    private static ResultSet reslt;
    static int rowAffected;
//Q =query executes query if "select" and execute update if delete update or insert
    /**
     * Q help clean up sql statements
     * q is a query executes query if "select" and execute update if delete update or insert
     * @param Q
     */
    public static void manifestQuery(String Q){
         q= Q;
        /**
         * q is a static string used to hold sql commands
         */
        try{
            PreparedStatement ps = dbGide.conect.prepareStatement(q);
            if (q.toLowerCase().startsWith("select")) {
                reslt=ps.executeQuery(q);}
            if ((q.toLowerCase().startsWith("insert")) || (q.toLowerCase().startsWith("update")) || (q.toLowerCase().startsWith("delete"))) {
                ps.executeUpdate(q);
//                rowAffected= ps.executeUpdate(q);
            }
        }catch (Exception e){System.out.println("error" +e.getMessage());}
    }
    /**
     * getReslt
     * receives the response from mysql sets that answer to result,
     * and shares the information to the appropriate classes and functions.
     * @return reslt
     */
    public static ResultSet getReslt(){return reslt;}
    public static int getRowAffected(){return rowAffected;}
}
/**
 * @souces C195 Code Repository\DAODemo2021\DAODemo2021\src\sample\DAO\Query
 */