package dao;

import connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import vo.*;

public class EstoqueDAO {

    @SuppressWarnings("resource")
    public boolean adcionarEstoque(EstoqueVO estoqueVO) {
        boolean retorno = false;
        int indice = 0;
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.prepareStatement("select * from status where status = ?");
            stmt.setString(1, estoqueVO.getStatusVO().getTipo());

            rs = stmt.executeQuery();

            if (rs.next()) {
                StatusVO statusVO = new StatusVO();

                statusVO.setId(rs.getInt("status.id_status"));
                estoqueVO.setStatusVO(statusVO);
            }

            stmt = con.prepareStatement(
                    "INSERT INTO material (id_material,nome_material,quantidade_material,quantidade_iniciar_material,preco_material,tipo_material,id_status)VALUES(null,?,?,?,?,?,?)");
            stmt.setString(++indice, estoqueVO.getMaterial());
            stmt.setString(++indice, estoqueVO.getQts());
            stmt.setString(++indice, estoqueVO.getQtsInicia());
            stmt.setBigDecimal(++indice, estoqueVO.getPreco());
            stmt.setString(++indice, estoqueVO.getTipo());
            stmt.setInt(++indice, estoqueVO.getStatusVO().getId());

            retorno = stmt.execute();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return !retorno;
    }

    public List<EstoqueVO> pesquisaEstoqueA(EstoqueVO estoqueVO) {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<EstoqueVO> retorno = new ArrayList<>();
        try {
            String comandoSQLUpdate = "UPDATE material SET material.id_status = (SELECT status.id_status FROM status WHERE status.`status` = \"Em Falta\") "
                    + "WHERE material.quantidade_material < material.quantidade_iniciar_material;";

            stmt = con.prepareStatement(comandoSQLUpdate);
            stmt.execute();
            comandoSQLUpdate = "UPDATE material SET material.id_status = (SELECT `status`.id_status FROM status WHERE `status`.`status` = \"Estoque Baixo\") "
                    + "WHERE material.quantidade_material = material.quantidade_iniciar_material;";
            stmt = con.prepareStatement(comandoSQLUpdate);
            stmt.execute();
            comandoSQLUpdate
                    = "UPDATE material SET material.id_status = (SELECT `status`.id_status FROM status WHERE `status`.`status` = \"Em Estoque\")"
                    + " WHERE material.quantidade_material > material.quantidade_iniciar_material;";

            stmt = con.prepareStatement(comandoSQLUpdate);
            stmt.execute();

            String comandoSQLSelect = "select * from material inner join status on (material.id_status = status.id_status)";
            boolean montaWhere = true;
            int indice = 0;

            if (estoqueVO.getCodigo() > 0) {
                comandoSQLSelect += " Where id_material = ? ";
                montaWhere = false;
            }
            if (estoqueVO.getMaterial() != null) {
                if (montaWhere) {
                    comandoSQLSelect += " Where nome_material like ? ";
montaWhere = false;
                } else {
                    comandoSQLSelect += " And nome_material like ? ";

                }
            }

            if (montaWhere) {
                comandoSQLSelect += "  WHERE material.id_material not IN (SELECT material.id_material FROM material WHERE material.status_material = 'Desativado')";
            } else {
                comandoSQLSelect += "  AND material.id_material not IN (SELECT material.id_material FROM material WHERE material.status_material = 'Desativado')";

            }
comandoSQLSelect += "  ORDER BY material.id_material ASC";
            stmt = con.prepareStatement(comandoSQLSelect);
            if (estoqueVO.getCodigo() > 0) {
                stmt.setInt(++indice, estoqueVO.getCodigo());

            }
            if (estoqueVO.getMaterial() != null) {
                stmt.setString(++indice, '%' + estoqueVO.getMaterial() + '%');
            }
            rs = stmt.executeQuery();

            while (rs.next()) {

                EstoqueVO vo = new EstoqueVO();
                StatusVO statusVO = new StatusVO();

                vo.setCodigo(rs.getInt("material.id_material"));
                vo.setMaterial(rs.getString("material.nome_material"));
                vo.setPreco(rs.getBigDecimal("material.preco_material"));
                vo.setTipo(rs.getString("tipo_material"));
                vo.setQts(rs.getString("material.quantidade_material"));
                statusVO.setTipo(rs.getString("status.status"));
                vo.setStatusVO(statusVO);

                retorno.add(vo);
            }

        } catch (SQLException e) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, e);

        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return retorno;
    }

    public List<EstoqueVO> pesquisaEstoqueAD(EstoqueVO estoqueVO) {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<EstoqueVO> retorno = new ArrayList<>();
        try {
            String comandoSQLUpdate = "UPDATE material SET material.id_status = (SELECT `status`.id_status FROM status WHERE `status`.`status` = \"Em Falta\") "
                    + "WHERE material.quantidade_material < material.quantidade_iniciar_material;";

            stmt = con.prepareStatement(comandoSQLUpdate);
            stmt.execute();
            comandoSQLUpdate = "UPDATE material SET material.id_status = (SELECT `status`.id_status FROM status WHERE `status`.`status` = \"Estoque Baixo\") "
                    + "WHERE material.quantidade_material = material.quantidade_iniciar_material;";
            stmt = con.prepareStatement(comandoSQLUpdate);
            stmt.execute();
            comandoSQLUpdate
                    = "UPDATE material SET material.id_status = (SELECT `status`.id_status FROM status WHERE `status`.`status` = \"Em Estoque\")"
                    + " WHERE material.quantidade_material > material.quantidade_iniciar_material;";

            stmt = con.prepareStatement(comandoSQLUpdate);
            stmt.execute();

            String comandoSQLSelect = "select * from material inner join status on (material.id_status = status.id_status)";
            boolean montaWhere = true;
            int indice = 0;

            if (estoqueVO.getCodigo() > 0) {
                comandoSQLSelect += " Where id_material = ? ";
            }
            if (estoqueVO.getMaterial() != null) {
                if (montaWhere) {
                    comandoSQLSelect += " Where nome_material like ? ";

                } else {
                    comandoSQLSelect += " And nome_material like ? ";

                }
            }
comandoSQLSelect += "  ORDER BY material.id_material ASC";

            stmt = con.prepareStatement(comandoSQLSelect);
            if (estoqueVO.getCodigo() > 0) {
                stmt.setInt(++indice, estoqueVO.getCodigo());

            }
            if (estoqueVO.getMaterial() != null) {
                stmt.setString(++indice, '%' + estoqueVO.getMaterial() + '%');
            }
            rs = stmt.executeQuery();

            while (rs.next()) {

                EstoqueVO vo = new EstoqueVO();
                StatusVO statusVO = new StatusVO();

                vo.setCodigo(rs.getInt("material.id_material"));
                vo.setMaterial(rs.getString("material.nome_material"));
                vo.setPreco(rs.getBigDecimal("material.preco_material"));
                vo.setTipo(rs.getString("tipo_material"));
                vo.setQts(rs.getString("material.quantidade_material"));
                statusVO.setTipo(rs.getString("status.status"));
                vo.setStatusVO(statusVO);

                retorno.add(vo);
            }

        } catch (SQLException e) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, e);

        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return retorno;
    }

    @SuppressWarnings("resource")
    public boolean alterarEstoque(EstoqueVO estoqueVO) {
        Connection con = ConnectionFactory.getConnection();
        boolean retorno = false;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int indice = 0;

        try {

            stmt = con.prepareStatement("select id_status from status where status.status = ? ");
            stmt.setString(1, estoqueVO.getStatusVO().getTipo());
            rs = stmt.executeQuery();

            while (rs.next()) {
                StatusVO statusVO = new StatusVO();
                statusVO.setId(rs.getInt("id_status"));
                estoqueVO.setStatusVO(statusVO);
            }

            stmt = con.prepareStatement(
                    "Update material set nome_material = ? ,quantidade_material = ?,quantidade_iniciar_material = ?,preco_material = ? ,tipo_material = ?,id_status = ? where id_material = ? ");
            stmt.setString(++indice, estoqueVO.getMaterial());
            stmt.setString(++indice, estoqueVO.getQts());
            stmt.setString(++indice, estoqueVO.getQtsInicia());
            stmt.setBigDecimal(++indice, estoqueVO.getPreco());
            stmt.setString(++indice, estoqueVO.getTipo());
            stmt.setInt(++indice, estoqueVO.getStatusVO().getId());
            stmt.setInt(++indice, estoqueVO.getCodigo());

            retorno = stmt.execute();

        } catch (SQLException ex) {
            retorno = false;
            JOptionPane.showMessageDialog(null, "Error ao Atualiza");
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return !retorno;

    }

    public boolean statusMaterial(EstoqueVO estoqueVO) {
        Connection con = ConnectionFactory.getConnection();
        boolean retorno = false;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int indice = 0;

        try {

            stmt = con.prepareStatement("UPDATE material set material.status_material = ? WHERE material.nome_material = ? ");

            stmt.setInt(++indice, estoqueVO.getStatusAD());
            stmt.setString(++indice, estoqueVO.getMaterial());

            retorno = stmt.execute();

        } catch (SQLException ex) {
            retorno = false;
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return !retorno;

    }

    public boolean delete(EstoqueVO e) {

        Connection con = ConnectionFactory.getConnection();
        boolean reotorno = false;

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("delete from material where id_material = ? ");

            stmt.setInt(1, e.getCodigo());

            reotorno = stmt.execute();

        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
        return !reotorno;
    }

    public EstoqueVO dadosNosCampos(EstoqueVO estoqueVO) {
        EstoqueVO retorno = new EstoqueVO();

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement(
                    "select * from material inner join status on (material.id_status = status.id_status) where material.id_material = ?");
            stmt.setInt(1, estoqueVO.getCodigo());
            rs = stmt.executeQuery();

            while (rs.next()) {
                StatusVO statusVO = new StatusVO();

                retorno.setCodigo(rs.getInt("material.id_material"));
                retorno.setMaterial(rs.getString("material.nome_material"));
                retorno.setPreco(rs.getBigDecimal("material.preco_material"));
                retorno.setQts(rs.getString("material.quantidade_material"));
                retorno.setQtsInicia(rs.getString("material.quantidade_iniciar_material"));
                retorno.setTipo(rs.getString("material.tipo_material"));
                statusVO.setTipo(rs.getString("status.status"));
                retorno.setStatusVO(statusVO);
            }

        } catch (SQLException e) {
            Logger.getLogger(EstoqueDAO.class.getName()).log(Level.SEVERE, null, e);

        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return retorno;
    }

    public boolean existerMaterialAtivadoInsert(EstoqueVO estoqueVO) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.prepareStatement(
                    "SELECT  EXISTS(SELECT * from material WHERE material.nome_material = ?  AND material.status_material = 1) AS existe");

            stmt.setString(1, estoqueVO.getMaterial());
            rs = stmt.executeQuery();
            if (rs.next()) {
                if (rs.getInt("existe") == 1) {

                    return true;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(EstoqueDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean existerMaterialDesativadoInsert(EstoqueVO estoqueVO) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.prepareStatement(
                    "SELECT  EXISTS(SELECT * from material WHERE material.nome_material = ?  AND material.status_material = 2) AS existe");

            stmt.setString(1, estoqueVO.getMaterial());

            rs = stmt.executeQuery();
            if (rs.next()) {
                if (rs.getInt("existe") == 1) {

                    return true;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(EstoqueDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean existerMaterialAtivadoUpdate(EstoqueVO estoqueVO) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.prepareStatement(
                    "SELECT  EXISTS(SELECT * from material WHERE material.nome_material = ?  AND material.status_material = 1) AS existe");

            stmt.setString(1, estoqueVO.getMaterial());
            rs = stmt.executeQuery();
            if (rs.next()) {
                if (rs.getRow() > 1) {

                    if (rs.getInt("existe") == 1) {

                        return true;
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(EstoqueDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean existerMaterialDesativadoUpdate(EstoqueVO estoqueVO) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.prepareStatement(
                    "SELECT  EXISTS(SELECT * from material WHERE material.nome_material = ?  AND material.status_material = 2) AS existe");

            stmt.setString(1, estoqueVO.getMaterial());

            rs = stmt.executeQuery();
            if (rs.next()) {
                if (rs.getRow() > 1) {
                    if (rs.getInt("existe") == 1) {

                        return true;
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(EstoqueDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
