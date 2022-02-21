package modles;

import dbmanager.QueryManager;
import dbmanager.dbGide;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
//customers getters and setters

/**
 * CustomerCRUD class used to get customer data
 */
public class CustomersCRUD {

    /**
     *
     * @param rs
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private static ObservableList<Customers> getCustomerObjects(ResultSet rs) throws SQLException, ClassNotFoundException {
        try {
            ObservableList<Customers> customerList = FXCollections.observableArrayList();

            while (rs.next()) {
                Customers customer = new Customers();
                customer.setCusId(rs.getInt("Customer_ID"));
                customer.setCusname(rs.getString("Customer_Name"));
                customer.setCusAddress(rs.getString("Address"));
                customer.setZipCode(rs.getString("Postal_Code"));
                customer.setTelephoneNumber(rs.getString("Phone"));
                customer.setfirstLvLDivision(rs.getInt("Division_ID"));

                customerList.add(customer);
            }
            return customerList;

        } catch (SQLException e) {
            System.out.println("Error while getting customer" + e);
            e.printStackTrace();
            throw e;
        }
    }


    /**
     * getEveryCustomer
     * gets all customers data from the DB via Sql statement
     * @return everyCustomer
     * @throws SQLException
     */
    public static ObservableList<Customers> getEveryCustomer() throws SQLException {
        ObservableList<Customers> everyCustomer = FXCollections.observableArrayList();
        dbGide.winConnection();
        String sqlCommand = "SELECT * FROM client_schedule.customers";
        QueryManager.manifestQuery(sqlCommand);
        ResultSet reslt = QueryManager.getReslt();
        while (reslt.next()){
                int customer_id = reslt.getInt("Customer_ID");
            String customer_name = reslt.getString("Customer_Name");
            String address = reslt.getString("Address");
            String zipcode = reslt.getString("Postal_Code");
            String telephone = reslt.getString("Phone");
               int division_id = reslt.getInt("Division_ID");


            Customers customers = new Customers();
               customers.setCusId(customer_id);
             customers.setCusname(customer_name);
              customers.setCusAddress(address);
              customers.setTelephoneNumber(telephone);
            customers.setfirstLvLDivision(division_id);
             customers.setZipCode(zipcode);

            everyCustomer.add(customers);
        }
        return everyCustomer;
    }

    /**
     * Gets customerId from customer name
     * uses name as a key
     * @param name  customer Name
     * @return  customerId
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static int getCustomersIdFromName(String name) throws ClassNotFoundException, SQLException {
        String q = "select Customer_ID from customers where Customer_Name = '"+name+"'";

        try {
            QueryManager.manifestQuery(q);
            ResultSet reslt = QueryManager.getReslt();
            if(reslt.next()) {
                int customerId = Integer.parseInt(reslt.getString(1));
                return customerId;
            }
            else {
                return 0;
            }

        } catch (SQLException e) {
            System.out.println("Error customer id" + e);
            e.printStackTrace();
            throw e;
        }
    }

    public static String getCustomerName(int customerId) throws ClassNotFoundException, SQLException {
        String q = "select Customer_Name from customers where Customer_ID =" +customerId+"";

        try {
            QueryManager.manifestQuery(q);
            ResultSet rs = QueryManager.getReslt();
            if(rs.next()) {
                String customerName = rs.getString(1);
                return  customerName;
            }
            return "";

        } catch (SQLException e) {
            System.out.println("Error while getting customer name" + e);
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * getAllRecordsThisWeek
     * @return customerList
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static ObservableList<Customers> getAllRecordsThisWeek() throws ClassNotFoundException, SQLException {
        String query = "SELECT * FROM customers WHERE create_date BETWEEN NOW() - INTERVAL 7 DAY AND NOW()";

        try {
            QueryManager.manifestQuery(query);
            ResultSet rs = QueryManager.getReslt();
            ObservableList<Customers> customerList = getCustomerObjects(rs);

            return customerList;
        } catch (SQLException e) {
            System.out.println("Error while getting the newly added customers data from the database this week" + e);
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * inserts new customers into the database
     * cusQ equals the sql command used to insert data into the customer table
     * @param cusid id
     * @param cusname name
     * @param cusaddress address
     * @param zipCode  zip
     * @param telephone phone
     * @param cusdivision division
     * @throws SQLException
     */
    public static void insertCus(int cusid, String cusname, String cusaddress, String zipCode, String telephone, int cusdivision) throws  SQLException{
        String CusQ ="INSERT INTO client_schedule.customers(Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Division_ID) VALUES('"+cusid+"', '"+cusname+"', '"+cusaddress+"','"+zipCode+"', '"+telephone+"', '"+ LocalDateTime.now()+"', '"+cusdivision+"')";
        QueryManager.manifestQuery(CusQ);
    }

    /**
     * deletes customer from DB and table
     *uses cusid as a key
     * @param cusid id
     */
    public  static void  deleteCus(int cusid){
        String CusQ= "delete FROM customers WHERE Customer_ID = '"+cusid+"'";
        QueryManager.manifestQuery(CusQ);
    }


    /**
     * update customer data in the by calling mysqlDB
     * used in homepage controller, its activated when the update button gets pressed.
     * @param cusid id
     * @param cusname name
     * @param cusaddress address
     * @param zipCode zip
     * @param telephone phone
     * @param cusdivision division
     */
//use in homepage
    public static void  updateCus(int cusid, String cusname, String cusaddress, String zipCode, String telephone, int cusdivision) {
        String CusQ ="UPDATE client_schedule.customers set Customer_Name ='"+cusname+"'," +"Address ='"+cusaddress+"', Postal_Code ='"+zipCode+"', Phone ='"+telephone+"', Division_ID ='"+cusdivision+"' WHERE Customer_ID ='"+cusid+"'";
        QueryManager.manifestQuery(CusQ);
    }



}


