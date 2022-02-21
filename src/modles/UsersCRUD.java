package modles;

import dbmanager.QueryManager;
import dbmanager.dbGide;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * UsersCRUD class
 */
public class UsersCRUD {
    //gets all user data copys data to user list
    /**
     * first it sends mysql a query command asking for all data held in UserTable
     * gets all user data from mysql set them equal to User variables
     * then it added all the user variables to a list called everyUser.
     * @return everyUser
     * @throws SQLException
     * @throws Exception
     */
    public static ObservableList<Users> getEveryUser() throws SQLException, Exception {
        ObservableList<Users> everyUser = FXCollections.observableArrayList();
        dbGide.winConnection();
        String sqlCommand = "SELECT * FROM client_schedule.users";
        QueryManager.manifestQuery(sqlCommand);
        ResultSet reslt = QueryManager.getReslt();
        while (reslt.next()){
            int usersiD = reslt.getInt("User_ID");
            String usersnamE = reslt.getString("User_Name");
            String passworD = reslt.getString("Password");
            Users usersReslt = new Users();
            usersReslt.setUsersId(usersiD);
            usersReslt.setUsersName(usersnamE);
            usersReslt.setPassWord(passworD);
            everyUser.add(usersReslt);
        }
        return everyUser;
    }
}

/**
 * @socures C195 Code Repository\DAODemo2021\DAODemo2021\src\sample\DAO\UserDaoImpl
 */