package modles;

import dbmanager.QueryManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
// div getters and setters

/**
 * DivisionsCRUD class
 */
public class DivisionsCRUD {
    /**
     *getDivisions
     * adds all data in the Division table to the division list.
     * During the while loop set each division variable equal to its sql counterpart
     * @param reslt
     * @return divisions List
     * @throws SQLException 
     * @throws ClassNotFoundException
     */
    private static ObservableList<Divisions> getDivisions(ResultSet reslt) throws SQLException, ClassNotFoundException {
        try {
            ObservableList<Divisions> divisionList = FXCollections.observableArrayList();

            while (reslt.next()) {
                Divisions division = new Divisions();
                division.setDivisionsId(reslt.getInt("Division_ID"));
                division.setCountryId(reslt.getInt("COUNTRY_ID"));
                division.setDivisionsName(reslt.getString("Division"));
                divisionList.add(division);
            }
            return divisionList;

        } catch (SQLException e) {
            System.out.println("Error while getting division :" + e);
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * getEveryDivisions
     * gets all division data and saves it in divisionList
     * @return divisionList
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static ObservableList<Divisions> getEveryDivisions() throws ClassNotFoundException, SQLException {
        String query = "select * from first_level_divisions";

        try{
            QueryManager.manifestQuery(query);
            ResultSet rs = QueryManager.getReslt();
            ObservableList<Divisions> divisionList = getDivisions(rs);

            return divisionList;

        }  catch (SQLException e) {
            System.out.println("Error while getting division data" + e);
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * getDivisionsId
     * gets division ID using division name as the key
     * @param divisionName
     * @return id
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static int getDivisionsId(String divisionName) throws ClassNotFoundException, SQLException {
        String query = "select Division_ID from first_level_divisions where Division = '"+divisionName+"' ";

        try {
            QueryManager.manifestQuery(query);
            ResultSet rs = QueryManager.getReslt();
            if(rs.next()) {
                int id = Integer.parseInt(rs.getString(1));
                return id;

            }
            //TODO: THROW EXCEPTION/ERROR
            return 0;

        } catch (SQLException e) {
            System.out.println("Error getting division id " + e);
            e.printStackTrace();
            throw e;
        }
    }

    /**
     *getDivisionName
     * gets division name using division id as the key
     * @param divId
     * @return divisionName
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static String getDivisionName(int divId) throws ClassNotFoundException, SQLException {
        String q = "select Division from first_level_divisions where  Division_ID =" +divId+"";

        try {
            QueryManager.manifestQuery(q);
            ResultSet rs = QueryManager.getReslt();
            if(rs.next()) {
                String divisionName = rs.getString(1);         ;
                return  divisionName;
            }
            return "";

        } catch (SQLException e) {
            System.out.println("Error while getting the name of this division " + e);
            e.printStackTrace();
            throw e;
        }
    }
}
