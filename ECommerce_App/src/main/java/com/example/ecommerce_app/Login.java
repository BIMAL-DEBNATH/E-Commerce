package com.example.ecommerce_app;


import java.sql.ResultSet;

public class Login {
    public Customer customerLogin(String userName, String password) {
 String loginQuery="select * from customer where email= '"+userName+"' and passwords='"+password+"'" ;//
        DbConnection connection =new DbConnection();

    //
        try {
            ResultSet rs=connection.getQueryTable(loginQuery);
            if(rs.next()){

                return new Customer(rs.getInt("id"), rs.getString("name"),
                        rs.getString("email"), rs.getString("mobile"));
            }


        } catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }


    public static void main(String[] args) {
        Login login=new Login();
        Customer customer=login.customerLogin("pabitrause111@gmail.com", "pab432");
        System.out.println("WellCome: "+customer.getName());
    }
}