package LES1.P2;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Main {

    private static Connection connection;

    public static void main(String[] args) throws SQLException {
        getConnection();
        ReizigerDAO reizigerDAO = new ReizigerDAOPsql(connection);
        testReizigerDAO(reizigerDAO);
        closeConnection();
    }

    private static void getConnection(){
        String url = "jdbc:postgresql://localhost/ovchip?user=postgres&password=Tular112!";
        try {
            Connection conn = DriverManager.getConnection(url);
            connection = conn;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void closeConnection() throws SQLException {
        connection.close();
    }

    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers");
        System.out.println(rdao.findById(sietske.getId()));

        // Update een gebruiker
        System.out.println("\n[Test] ReizigerDAO.update() geeft de volgende reiziger:");
        rdao.update(sietske);
        System.out.println(rdao.findById(sietske.getId()));

        // Delete een gebruiker
        System.out.print("\n[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.delete() ");
        rdao.delete(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // Find een reiziger by ID
        System.out.println("[Test] ReizigerDAO.findById() geeft de volgende reiziger:");
        System.out.println(rdao.findById(2));

        // Find een reiziger by geboortedatum
        System.out.println("\n[Test] ReizigerDAO.findByGbdatum() geeft de volgende reiziger:");
        String gbdatum2 = "1998-08-11";
        System.out.println(rdao.findByGbdatum(gbdatum2));

    }
}
