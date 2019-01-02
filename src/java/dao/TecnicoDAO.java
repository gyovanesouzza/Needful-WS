package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import connection.ConnectionFactory;
import vo.TecnicoVO;

public class TecnicoDAO {

    public boolean create(TecnicoVO t) {
        boolean retorno = false;
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        int indice = 0;

        try {
            stmt = con.prepareStatement("INSERT INTO tecnico (id_tecnico,nome_tecnico,id_usuario) VALUES (null,?,?)");
            stmt.setString(++indice, t.getTecnico());
            stmt.setInt(++indice, t.getId_usuario());

            retorno = stmt.execute();

        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
        return retorno;

    }

    public List<TecnicoVO> read() {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<TecnicoVO> tecnicos = new ArrayList<>();

        try {
            stmt = con.prepareStatement("select * from tecnico WHERE tecnico.status_tecnico = 1");
            rs = stmt.executeQuery();

            while (rs.next()) {
                TecnicoVO tec = new TecnicoVO();

                tec.setId(rs.getInt("id_tecnico"));
                tec.setTecnico(rs.getString("nome_tecnico"));

                tecnicos.add(tec);
            }

        } catch (SQLException e) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, e);

        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return tecnicos;
    }

    public int update(TecnicoVO t) {

        Connection con = ConnectionFactory.getConnection();
        int retorno = 0;
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("Update tecnico set nome_tecnico = ? where id_usuario = ? ");
            stmt.setString(1, t.getTecnico());
            stmt.setInt(2, t.getId_usuario());

            retorno = stmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
        return retorno;
    }

    public void statusTecnico(TecnicoVO tecnicoVO,int statusAD) {
Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE tecnico SET tecnico.status_tecnico = ? where id_tecnico = ? ");
            stmt.setInt(1, statusAD);
            stmt.setInt(2, tecnicoVO.getId());

            stmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
