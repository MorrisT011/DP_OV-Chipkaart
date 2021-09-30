package LES1.P5;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOPsql implements ProductDAO {

    private Connection conn;
    private OVChipkaartDAO ovcdao;

    public ProductDAOPsql(Connection conn) {
        this.conn = conn;
    }

    public ProductDAOPsql(Connection conn, OVChipkaartDAO ovcdao) {
        this.conn = conn;
        this.ovcdao = ovcdao;
    }

    public void setOvcdao(OVChipkaartDAO ovcdao) {
        this.ovcdao = ovcdao;
    }

    @Override
    public boolean save(Product product) {
        String SQL = "INSERT INTO product VALUES (?, ?, ?, ?)";
        String SQL2 = "INSERT INTO ov_chipkaart_product VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement prestat = conn.prepareStatement(SQL);

            prestat.setInt(1, product.getProduct_nummer());
            prestat.setString(2, product.getNaam());
            prestat.setString(3, product.getBeschrijving());
            prestat.setFloat(4, product.getPrijs());

            prestat.executeUpdate();


            for (OVChipkaart o : product.getOVChipkaarten()) {
                PreparedStatement prestat2 = conn.prepareStatement(SQL2);
                prestat2.setInt(1, o.getKaart_nummer());
                prestat2.setInt(2, product.getProduct_nummer());
                prestat2.setString(3, "actief");
                prestat2.setDate(4, Date.valueOf(LocalDate.now()));

                prestat2.executeUpdate();
            }
            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean update(Product product) {
        String SQL =  "UPDATE product SET prijs = prijs + 5 WHERE product_nummer = ?";
        String SQL2 = "UPDATE ov_chipkaart_product SET last_update = ? WHERE product_nummer = ?";

        try {
            PreparedStatement prestat = conn.prepareStatement(SQL);
            PreparedStatement prestat2 = conn.prepareStatement(SQL2);

            prestat.setInt(1, product.getProduct_nummer());
            prestat2.setDate(1, Date.valueOf(LocalDate.now()));
            prestat2.setInt(2, product.getProduct_nummer());

            prestat.executeUpdate();
            prestat2.executeUpdate();
            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Product product) {
        String SQL = "DELETE FROM product WHERE product_nummer = ?";
        String SQL2 = "DELETE FROM ov_chipkaart_product WHERE product_nummer = ?";

        try {
            PreparedStatement prestat = conn.prepareStatement(SQL);
            PreparedStatement prestat2 = conn.prepareStatement(SQL2);


            prestat.setInt(1, product.getProduct_nummer());
            prestat2.setInt(1, product.getProduct_nummer());

            prestat.executeUpdate();
            prestat2.executeUpdate();
            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    public List<Product> findByOVChipkaart(OVChipkaart ovChipkaart) {
        String SQL = "SELECT ov_chipkaart_product.kaart_nummer, product.product_nummer, product.naam, product.beschrijving, product.prijs " +
                "FROM product " +
                "JOIN ov_chipkaart_product " +
                "ON ov_chipkaart_product.product_nummer = product.product_nummer " +
                "WHERE ov_chipkaart_product.kaart_nummer = ? " +
                "ORDER BY kaart_nummer, product_nummer";

        try {
            PreparedStatement prestat = conn.prepareStatement(SQL);

            prestat.setInt(1, ovChipkaart.getKaart_nummer());

            ResultSet rs = prestat.executeQuery();

            List<Product> producten = new ArrayList<>();

            while (rs.next()) {
                int prnr = rs.getInt("product_nummer");
                String nm = rs.getString("naam");
                String besch = rs.getString("beschrijving");
                Float prijs = rs.getFloat("prijs");

                Product pr = new Product(prnr, nm, besch, prijs);
                producten.add(pr);
            }

            return producten;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public List<Product> findAll(){
        String SQL = "SELECT * FROM product";

        try {
            PreparedStatement prestat = conn.prepareStatement(SQL);

            ResultSet rs = prestat.executeQuery();

            List<Product> producten = new ArrayList<>();

            while(rs.next()) {
                int prnr = rs.getInt("product_nummer");
                String nm = rs.getString("naam");
                String besch = rs.getString("beschrijving");
                Float prijs = rs.getFloat("prijs");

                Product pr = new Product(prnr, nm, besch, prijs);
                producten.add(pr);
            }

            return producten;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }
}
