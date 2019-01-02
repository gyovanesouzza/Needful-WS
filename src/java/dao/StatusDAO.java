package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionFactory;
import vo.*;

public class StatusDAO {

    public List<StatusVO> readChamadoDesktop() {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<StatusVO> status = new ArrayList<>();

        try {
            stmt = con.prepareStatement(
                    "SELECT * FROM status inner join tipo_status on ( `status`.id_tipo_status = tipo_status.id_tipo_status) where tipo_status.id_tipo_status = 1 "
                            + "AND `status`.id_status IN (SELECT status.id_status FROM STATUS WHERE `status`.`status` != \"Andamento\" AND `status`.`status` != \"Bloqueado\") \n" );
            rs = stmt.executeQuery();

            while (rs.next()) {
                StatusVO vo = new StatusVO();

                vo.setId(rs.getInt("id_tipo_status"));
                vo.setTipo(rs.getString("status"));

                status.add(vo);
            }

        } catch (SQLException e) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, e);

        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return status;
    }
    public List<StatusVO> readChamado() {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<StatusVO> status = new ArrayList<>();

        try {
            stmt = con.prepareStatement(
                    "SELECT * FROM status inner join tipo_status on ( `status`.id_tipo_status = tipo_status.id_tipo_status) where tipo_status.id_tipo_status = 1");
            rs = stmt.executeQuery();

            while (rs.next()) {
                StatusVO vo = new StatusVO();

                vo.setId(rs.getInt("id_tipo_status"));
                vo.setTipo(rs.getString("status"));

                status.add(vo);
            }

        } catch (SQLException e) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, e);

        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return status;
    }
    public List<StatusVO> readEstoque() {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<StatusVO> status = new ArrayList<>();

        try {
            stmt = con.prepareStatement(
                    "SELECT * FROM status inner join tipo_status on ( `status`.id_tipo_status = tipo_status.id_tipo_status) where tipo_status.id_tipo_status = 2");
            rs = stmt.executeQuery();

            while (rs.next()) {
                StatusVO vo = new StatusVO();

                vo.setId(rs.getInt("id_status"));
                vo.setTipo(rs.getString("status"));

                status.add(vo);
            }

        } catch (SQLException e) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, e);

        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return status;
    }

    public StatusVO statusNoCampoEstoque(StatusVO statusVO) {
        StatusVO retorno = new StatusVO();
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("SELECT * FROM status where status = ? and id_tipo_status = 2");
            stmt.setString(1, statusVO.getTipo());
            rs = stmt.executeQuery();

            while (rs.next()) {

                retorno.setId(rs.getInt("id_status"));
                retorno.setTipo(rs.getString("status"));

            }

        } catch (SQLException e) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, e);

        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return retorno;
    }
}
