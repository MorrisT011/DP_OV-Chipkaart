package LES1.P2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDAO{

    private Connection conn;

    @Override
    public boolean save(Reiziger reiziger) {

        //de SQL statement
        String SQL = "INSERT INTO reiziger(" +
                "reiziger_id, " +
                "voorletters, " +
                "tussenvoegsel, " +
                "achternaam, " +
                "geboortedatum) " +
                "VALUES(?, ?, ?, ? ,?)";

        try {
            PreparedStatement prestat = conn.prepareStatement(SQL);

            //De SQL statement invullen
            prestat.setInt(1, reiziger.getId());
            prestat.setString(2, reiziger.getVoorletters());
            prestat.setString(3, reiziger.getTussenvoegsels());
            prestat.setString(4, reiziger.getAchternaam());
            prestat.setDate(5, reiziger.getGeboortedatum());

            prestat.executeUpdate();
            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean update(Reiziger reiziger) {
        String SQL = "UPDATE reiziger "+ "SET achternaam = ? " + "WHERE reiziger_id = ?";

        try {
            PreparedStatement prestat = conn.prepareStatement(SQL);

            prestat.setString(1, "Tular");
            prestat.setInt(2, reiziger.getId());

            prestat.executeUpdate();

            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        String SQL = "DELETE FROM reiziger WHERE reiziger_id = ?";

        try {
            PreparedStatement prestat = conn.prepareStatement(SQL);

            prestat.setInt(1, reiziger.getId());

            prestat.executeUpdate();

            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    @Override
    public Reiziger findById(int id) {
        String SQL = "SELECT * FROM reiziger WHERE reiziger_id = ?";

        try {
            PreparedStatement prestat = conn.prepareStatement(SQL);

            prestat.setInt(1, id);

            ResultSet rs = prestat.executeQuery();

            while (rs.next()) {
                int rid = rs.getInt("reiziger_id");
                String voorletters = rs.getString("voorletters");
                String tussenvoegsel = rs.getString("tussenvoegsel");
                String achternaam = rs.getString("achternaam");
                Date geboortedatum = rs.getDate("geboortedatum");
                return new Reiziger(rid, voorletters, tussenvoegsel, achternaam, geboortedatum);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Reiziger> findByGbdatum(String datum) {
        String SQL = "SELECT * FROM reiziger WHERE geboortedatum = ?";

        try {
            PreparedStatement prestat = conn.prepareStatement(SQL);
            Date gbdatum = java.sql.Date.valueOf(datum);
            prestat.setDate(1, gbdatum);

            ResultSet rs = prestat.executeQuery();
            List<Reiziger> reizigers2 = new ArrayList<>();

            while (rs.next()) {
                int rid = rs.getInt("reiziger_id");
                String voorletters = rs.getString("voorletters");
                String tussenvoegsel = rs.getString("tussenvoegsel");
                String achternaam = rs.getString("achternaam");
                Date geboortedatum = rs.getDate("geboortedatum");

                Reiziger brent = new Reiziger(rid, voorletters, tussenvoegsel, achternaam, geboortedatum);
                reizigers2.add(brent);
            }
            return reizigers2;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Reiziger> findAll() {
        //SQL statement voor alle reizigers
        String SQL = "SELECT * FROM reiziger";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);

            List<Reiziger> reizigers = new ArrayList<>();

            while (rs.next()) {
                int rid = rs.getInt("reiziger_id");
                String voorletters = rs.getString("voorletters");
                String tussenvoegsel = rs.getString("tussenvoegsel");
                String achternaam = rs.getString("achternaam");
                Date geboortedatum = rs.getDate("geboortedatum");

                Reiziger brent = new Reiziger(rid, voorletters, tussenvoegsel, achternaam, geboortedatum);
                reizigers.add(brent);
            }

            return reizigers;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public ReizigerDAOPsql(Connection conn) {
        this.conn = conn;
    }
}
