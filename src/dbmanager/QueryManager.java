package dbmanager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import static dbmanager.dbGide.conect;
//formats sql text before sending it off to sql
/**
 * QueryManger
 * formats sql text before sending it off to sql
 */
public class QueryManager {
    /**
     * q is a static string used to hold sql commands
     */
    private static String q;
    /**
     *Statement sttmt
     * uses createStatement to send sql commands
     */
//    private static preaperS
    private static Statement sttmt;

    private static PreparedStatement preState;
    /**
     * ResultSet reslt
     * Executes the given SQL statement, which returns a single ResultSet object.
     *
     */
    private static ResultSet reslt;
//q =query executes query if "select" and execute update if delete update or insert
    /**
     * Q help clean up sql statements
     * q is a query executes query if "select" and execute update if delete update or insert
     * @param Q
     */
    public static void manifestQuery(String Q){
        q = Q;
        try{
            PreparedStatement ps = dbGide.conect.prepareStatement(q);
//            sttm = conect.createStatement();
            if (q.toLowerCase().startsWith("select"))
                reslt=ps.executeQuery(Q);
            if ((q.toLowerCase().startsWith("insert")) || (q.toLowerCase().startsWith("update")) || (q.toLowerCase().startsWith("delete")))
                ps.executeUpdate(Q);

        } catch (Exception e){
            System.out.println("error" +e.getMessage());
        }

    }

    /**
     * getReslt
     * receives the response from mysql sets that answer to result,
     * and shares the information to the appropriate classes and functions.
     * @return reslt
     */
    public static ResultSet getReslt(){
        return reslt;
    }
}


/**
 * @souces C195 Code Repository\DAODemo2021\DAODemo2021\src\sample\DAO\Query
 */