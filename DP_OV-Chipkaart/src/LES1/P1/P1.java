package LES1.P1;

import java.sql.*;

public class P1 {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost/ovchip?user=postgres&password=Tular112!";

        try{
            Connection conn = DriverManager.getConnection(url);

            Statement st = conn.createStatement();
            String query = "SELECT voorletters AS naam, tussenvoegsel AS tussenvoegsel, achternaam AS achternaam FROM reiziger";

            ResultSet rs = st.executeQuery(query);

            String naam;
            String achternaam;
            String tussenvoegsel;

            while(rs.next()) {
                naam = rs.getString("naam");
                achternaam = rs.getString("achternaam");
                tussenvoegsel = rs.getString("tussenvoegsel");
                if (tussenvoegsel != null) {
                    System.out.println(naam + ". " + tussenvoegsel + " " + achternaam);
                } else {
                    System.out.println(naam + ". " + achternaam);
                }

            }

            rs.close();
            st.close();
            conn.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
