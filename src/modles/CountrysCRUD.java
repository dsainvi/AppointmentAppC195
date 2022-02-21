package modles;
import java.sql.SQLException;
import dbmanager.QueryManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;
//getter and setter countries

/**
 * CountryCRUD class hold functions that allow users to get Country data.
 */
public class CountrysCRUD {

    /**
     * gets all country data
     * the while loop goes throw the table till all country variables have a value.
     * then country is added to countrysList
     * @return countryList
     * @throws SQLException
     */
    public static ObservableList<Countrys> getEveryCountrys() throws SQLException {
        String q = "select * from countries ";
        ObservableList<Countrys> countrysList= FXCollections.observableArrayList();
        QueryManager.manifestQuery(q);
        ResultSet reslt = QueryManager.getReslt();
        while (reslt.next()){
            int countryId= reslt.getInt("Country_ID");
            String countryName= reslt.getString("Country");
            Countrys countrys = new Countrys();
            countrys.setCountryId(countryId);
            countrys.setCountryName(countryName);

            countrysList.add(countrys);

        }
        return countrysList;

    }
    /**
     * gets country Id
     * uses countryName as the key and countryId is the value
     * @param countryName
     * @return id
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static int getCountrysId(String countryName) throws ClassNotFoundException, SQLException {
        String q = "select Country_ID from countries where Country = '"+countryName+"' ";

        try {
            QueryManager.manifestQuery(q);
            ResultSet rs = QueryManager.getReslt();
            if(rs.next()) {
                int id = Integer.parseInt(rs.getString(1));
                return id;
            }
            else { return 0;}

        } catch (SQLException e) {
            System.out.println("Error while getting country id " + e);
            e.printStackTrace();
            throw e;
        }
    }



//
//    /**
//     *
//     * @param id
//     * @return
//     * @throws ClassNotFoundException
//     * @throws SQLException
//     */
//    public static String getCountrysName(int id) throws ClassNotFoundException, SQLException {
//        String q = "select Country from countries where Country_ID =" +id+"";
//
//        try {
//            QueryManager.manifestQuery(q);
//            ResultSet rs = QueryManager.getReslt();
//            if(rs.next()) {
//                String countryName = rs.getString(1);         ;
//                return  countryName;
//            }
//            return "";
//
//        } catch (SQLException e) {
//            System.out.println("Error while getting the name of a country " + e);
//            e.printStackTrace();
//            throw e;
//        }
//    }

}
