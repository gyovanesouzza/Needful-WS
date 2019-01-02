package dao;

import connection.ConnectionFactory;
import vo.*;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

public class UsuarioDAO {

    TecnicoVO tecnicoVO = new TecnicoVO();
    TecnicoDAO tecnicoDAO = new TecnicoDAO();

    @SuppressWarnings("resource")
    public boolean create(UsuarioVO u) {

        Connection con = ConnectionFactory.getConnection();

        ResultSet rs = null;
        PreparedStatement stmt = null;
        int tipoDeUsuario = 0;
        int indice = 0;
        boolean retorno = true;
        try {
            stmt = con.prepareStatement(
                    "SELECT id_tipo_usuario, LEFT(tipo_de_usuario, 256) FROM tipo_usuario where tipo_usuario.tipo_de_usuario = ? "
                    + "GROUP BY id_tipo_usuario ");
            stmt.setString(1, u.getTipo());
            rs = stmt.executeQuery();

            if (rs.next()) {
                tipoDeUsuario = rs.getInt("id_tipo_usuario");
            }

            stmt = con.prepareStatement(
                    "INSERT INTO usuario (id_usuario,nome_usuario,email_usuario,cpf_usuario,login_usuario,senha_usuario,id_tipo_usuario) VALUES (null,?,?,?,?,?,?)");
            stmt.setString(++indice, u.getNome());
            stmt.setString(++indice, u.getEmail());
            stmt.setString(++indice, u.getCPF());
            stmt.setString(++indice, u.getLogin());
            stmt.setString(++indice, u.getSenha());
            stmt.setInt(++indice, tipoDeUsuario);
            retorno = stmt.execute();

            if (u.getTipo().equals("Tecnico")) {

                stmt = con.prepareStatement("SELECT id_usuario FROM usuario where login_usuario = ? ");
                stmt.setString(1, u.getLogin());
                rs = stmt.executeQuery();

                if (rs.next()) {
                    tecnicoVO.setId_usuario(rs.getInt("id_usuario"));
                }
                tecnicoVO.setTecnico(u.getNome());
                tecnicoDAO.create(tecnicoVO);
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
        return !retorno;

    }

    @SuppressWarnings("resource")
    public boolean alterarConta(UsuarioVO u) {

        Connection con = ConnectionFactory.getConnection();
        boolean retorno = true;
        ResultSet rs = null;
        int tipoDeUsuario = 0;
        int indice = 0;
        PreparedStatement stmt = null;
        String comandoSQL = "Update usuario set ";

        if (u.getNome() != null) {

            comandoSQL += "nome_usuario = ? ,";
        }
        if (u.getEmail() != null) {
            comandoSQL += "email_usuario = ? ,";

        }
        if (u.getLogin() != null) {
            comandoSQL += "login_usuario = ? ,";

        }
        if (u.getSenha() != null) {
            comandoSQL += "senha_usuario = ?,";
        }
        if (u.getTipo() != null) {
            comandoSQL += "id_tipo_usuario = (SELECT tipo_usuario.id_tipo_usuario FROM tipo_usuario WHERE tipo_usuario.tipo_de_usuario ='" + u.getTipo() + "')";

        }
        comandoSQL += " where id_usuario = ?";

        try {

            stmt = con.prepareStatement(comandoSQL);

            if (u.getNome() != null) {
                stmt.setString(++indice, u.getNome());
            }
            if (u.getEmail() != null) {
                stmt.setString(++indice, u.getEmail());

            }
            if (u.getLogin() != null) {
                stmt.setString(++indice, u.getLogin());

            }
            if (u.getSenha() != null) {

                stmt.setString(++indice, u.getSenha());
            }
            stmt.setInt(++indice, u.getId());

            retorno = stmt.execute();

            if (u.getTipo().equals("Tecnico")) {
                stmt = con.prepareStatement(
                        "SELECT * FROM usuario inner join tecnico on (usuario.id_usuario = tecnico.id_tecnico) where tecnico.id_usuario = ? ");
                stmt.setInt(1, u.getId());
                rs = stmt.executeQuery();

                if (rs.next()) {
                    tecnicoVO.setId_usuario(rs.getInt("tecnico.id_usuario"));
                }
                tecnicoVO.setTecnico(u.getNome());
                tecnicoDAO.update(tecnicoVO);
            }
            System.out.println(comandoSQL);
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
        return retorno;
    }

    public List<UsuarioVO> readTablea() {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<UsuarioVO> user = new ArrayList<>();

        String comandoSQL = "SELECT * FROM usuario  inner join tipo_usuario on (usuario.id_tipo_usuario = tipo_usuario.id_tipo_usuario) where (tipo_usuario.tipo_de_usuario = \"Administrador\" \n"
                + "                	or tipo_usuario.tipo_de_usuario = \"Atendentede\" or tipo_usuario.tipo_de_usuario = \"Tecnico\") and usuario.status_usuario = 1 ORDER BY usuario.id_usuario";

        try {

            stmt = con.prepareStatement(comandoSQL);

            rs = stmt.executeQuery();

            while (rs.next()) {

                UsuarioVO usuarioVO = new UsuarioVO();
                usuarioVO.setId(rs.getInt("id_usuario"));
                usuarioVO.setNome(rs.getString("nome_usuario"));
                usuarioVO.setEmail(rs.getString("email_usuario"));
                usuarioVO.setCPF(rs.getString("cpf_usuario"));
                usuarioVO.setLogin(rs.getString("login_usuario"));
                usuarioVO.setTipo(rs.getString("tipo_usuario.tipo_de_usuario"));
                usuarioVO.setIdTipoUsuario(rs.getInt("tipo_usuario.id_tipo_usuario"));

                user.add(usuarioVO);

            }

        } catch (SQLException e) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, e);

        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return user;
    }

    public List<UsuarioVO> readTableAD() {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<UsuarioVO> user = new ArrayList<>();

        String comandoSQL = "SELECT * FROM usuario  inner join tipo_usuario on (usuario.id_tipo_usuario = tipo_usuario.id_tipo_usuario) where tipo_usuario.tipo_de_usuario = \"Administrador\" "
                + "	or tipo_usuario.tipo_de_usuario = \"Atendentede\" or tipo_usuario.tipo_de_usuario = \"Tecnico\" ORDER BY usuario.id_usuario ";

        try {

            stmt = con.prepareStatement(comandoSQL);

            rs = stmt.executeQuery();

            while (rs.next()) {

                UsuarioVO usuarioVO = new UsuarioVO();
                usuarioVO.setId(rs.getInt("id_usuario"));
                usuarioVO.setNome(rs.getString("nome_usuario"));
                usuarioVO.setEmail(rs.getString("email_usuario"));
                usuarioVO.setCPF(rs.getString("cpf_usuario"));
                usuarioVO.setLogin(rs.getString("login_usuario"));
                usuarioVO.setTipo(rs.getString("tipo_usuario.tipo_de_usuario"));

                user.add(usuarioVO);

            }

        } catch (SQLException e) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, e);

        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return user;
    }

    @SuppressWarnings("resource")
    public UsuarioVO pesquisaUsuario(UsuarioVO vo) {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        UsuarioVO usuarioVO = new UsuarioVO();

        String comandoSQL = "SELECT * FROM usuario  inner join tipo_usuario on (usuario.id_tipo_usuario = tipo_usuario.id_tipo_usuario) where (tipo_usuario.tipo_de_usuario = \"Administrador\" "
                + "	or tipo_usuario.tipo_de_usuario = \"Atendentede\" or tipo_usuario.tipo_de_usuario = \"Tecnico\") ";

        try {

            if (vo.getId() > 0) {
                comandoSQL += " and id_usuario = ? ";
            }

            stmt = con.prepareStatement(comandoSQL);

            if (vo.getId() > 0) {
                stmt.setInt(1, vo.getId());
            }
            rs = stmt.executeQuery();

            while (rs.next()) {

                usuarioVO.setId(rs.getInt("id_usuario"));
                usuarioVO.setNome(rs.getString("nome_usuario"));
                usuarioVO.setEmail(rs.getString("email_usuario"));
                usuarioVO.setCPF(rs.getString("cpf_usuario"));
                usuarioVO.setLogin(rs.getString("login_usuario"));
                usuarioVO.setTipo(rs.getString("tipo_usuario.tipo_de_usuario"));

            }

        } catch (SQLException e) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, e);

        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return usuarioVO;
    }

    public boolean permissaoDaAreaRestrita(UsuarioVO usuarioVO) {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean check = false;
        boolean montaWhere = true;
        int indice = 0;

        String comandoSQL = "SELECT * FROM usuario  inner join tipo_usuario on (usuario.id_tipo_usuario = tipo_usuario.id_tipo_usuario)";

        if (usuarioVO.getEmail() != null) {
            montaWhere = false;
            comandoSQL += "WHERE email_usuario = ? and senha_usuario = ?";

        }
        if (montaWhere) {
            if (usuarioVO.getLogin() != null) {
                comandoSQL += "WHERE login_usuario = ? and senha_usuario = ?";
            }
        }

        try {
            stmt = con.prepareStatement(comandoSQL);

            if (usuarioVO.getEmail() != null) {
                stmt.setString(++indice, usuarioVO.getEmail());

            }
            if (montaWhere) {
                if (usuarioVO.getLogin() != null) {
                    stmt.setString(++indice, usuarioVO.getLogin());

                }
            }

            stmt.setString(++indice, usuarioVO.getSenha());
            rs = stmt.executeQuery();

            while (rs.next()) {

                if (rs.getString("tipo_usuario.tipo_de_usuario").equals("Desenvolvedor")
                        || rs.getString("tipo_usuario.tipo_de_usuario").equals("Administrador")) {
                    check = true;
                }

            }

        } catch (SQLException e) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, e);

        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return check;
    }

    public boolean usuarioAD(UsuarioVO usuarioVO) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean check = false;
        boolean montaWhere = true;
        int indice = 0;

        String comandoSQL = "SELECT * FROM usuario";

        if (usuarioVO.getEmail() != null) {
            montaWhere = false;
            comandoSQL += " WHERE email_usuario = ? ";

        }
        if (montaWhere) {
            if (usuarioVO.getLogin() != null) {
                comandoSQL += " WHERE login_usuario = ? ";
            }
        }
        comandoSQL += " and usuario.status_usuario = 1";
        try {
            stmt = con.prepareStatement(comandoSQL);

            if (usuarioVO.getEmail() != null) {
                stmt.setString(++indice, usuarioVO.getEmail());

            }
            if (montaWhere) {
                if (usuarioVO.getLogin() != null) {
                    stmt.setString(++indice, usuarioVO.getLogin());

                }
            }

            rs = stmt.executeQuery();

            while (rs.next()) {
                check = true;
            }

        } catch (SQLException e) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, e);

        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return check;

    }

    public UsuarioVO permissaoDeLogin(UsuarioVO usuarioVO) {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        UsuarioVO retorno = new UsuarioVO();
        boolean montaWhere = true;
        int indice = 0;

        String comandoSQL = "SELECT * FROM usuario u inner join tipo_usuario tu on(u.id_tipo_usuario = tu.id_tipo_usuario)";

        if (usuarioVO.getEmail() != null) {
            montaWhere = false;
            comandoSQL += " WHERE email_usuario = ? and senha_usuario = ?";

        }
        if (montaWhere) {
            if (usuarioVO.getLogin() != null) {
                comandoSQL += " WHERE login_usuario = ? and senha_usuario = ?";
            }
        }

        try {
            stmt = con.prepareStatement(comandoSQL);

            if (usuarioVO.getEmail() != null) {
                stmt.setString(++indice, usuarioVO.getEmail());

            }
            if (montaWhere) {
                if (usuarioVO.getLogin() != null) {
                    stmt.setString(++indice, usuarioVO.getLogin());

                }
            }

            stmt.setString(++indice, usuarioVO.getSenha());

            rs = stmt.executeQuery();

            if (rs.next()) {

                retorno.setNome(rs.getString("nome_usuario"));
                retorno.setTipo(rs.getString("tu.tipo_de_usuario"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return retorno;
    }

    public static boolean checkLogin(UsuarioVO usuarioVO) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean check = false;
        boolean montaWhere = true;
        int indice = 0;

        String comandoSQL = "SELECT * FROM usuario ";

        if (usuarioVO.getEmail() != null) {
            montaWhere = false;
            comandoSQL += "WHERE email_usuario = ? and senha_usuario = ?";

        }
        if (montaWhere) {
            if (usuarioVO.getLogin() != null) {
                comandoSQL += "WHERE login_usuario = ? and senha_usuario = ?";
            }
        }

        try {
            //comandoSQL += " AND status_usuario = 1 ";
            stmt = con.prepareStatement(comandoSQL);

            if (usuarioVO.getEmail() != null) {
                stmt.setString(++indice, usuarioVO.getEmail());

            }
            if (montaWhere) {
                if (usuarioVO.getLogin() != null) {
                    stmt.setString(++indice, usuarioVO.getLogin());

                }
            }
            stmt.setString(++indice, usuarioVO.getSenha());
            rs = stmt.executeQuery();
            if (rs.next()) {
                check = true;
            }
            System.out.println(comandoSQL);
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return check;

    }

    public boolean existeEmailLogin(UsuarioVO usuarioVO) {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        boolean retorno = false;
        boolean montaWhere = true;
        int indice = 0;
        String comandoSQL = "SELECT * FROM usuario";

        try {
            if (usuarioVO.getLogin() != null) {
                comandoSQL += " WHERE login_usuario = ?";
            }
            if (usuarioVO.getEmail() != null) {
                if (montaWhere) {
                    comandoSQL += " WHERE email_usuario = ? ";
                } else {
                    comandoSQL += " And email_usuario = ?";
                }
            }

            stmt = con.prepareStatement(comandoSQL);
            if (usuarioVO.getLogin() != null) {
                stmt.setString(++indice, usuarioVO.getLogin());
            }
            if (usuarioVO.getEmail() != null) {
                stmt.setString(++indice, usuarioVO.getEmail());
            }
            rs = stmt.executeQuery();
            while (rs.next()) {
                retorno = true;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return retorno;
    }

    public boolean existerContaa(UsuarioVO usuarioVO) {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        boolean retorno = false;
        boolean montaWhere = true;
        int indice = 0;
        String comandoSQL = "SELECT * FROM usuario where usuario.cpf_usuario = ? and usuario.status_usuario = 1";

        try {

            stmt = con.prepareStatement(comandoSQL);
            if (usuarioVO.getCPF() != null) {
                stmt.setString(++indice, usuarioVO.getCPF());
            }

            rs = stmt.executeQuery();
            while (rs.next()) {
                retorno = true;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return retorno;
    }

    public boolean existerContaAD(UsuarioVO usuarioVO) {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        boolean retorno = false;
        boolean montaWhere = true;
        int indice = 0;
        String comandoSQL = "SELECT * FROM usuario";
        if (usuarioVO.getCPF() != null) {
            comandoSQL += " where usuario.cpf_usuario = ? and usuario.status_usuario = 2";

        } else if (usuarioVO.getEmail() != null) {
            comandoSQL += " where usuario.email_usuario = ? and usuario.status_usuario = 2";

        } else if (usuarioVO.getLogin() != null) {
            comandoSQL += " where usuario.login_usuario = ? and usuario.status_usuario = 2";

        }
        try {

            stmt = con.prepareStatement(comandoSQL);
            if (usuarioVO.getCPF() != null) {
                stmt.setString(++indice, usuarioVO.getCPF());

            } else if (usuarioVO.getEmail() != null) {
                stmt.setString(++indice, usuarioVO.getEmail());

            } else if (usuarioVO.getLogin() != null) {
                stmt.setString(++indice, usuarioVO.getLogin());

            }

            rs = stmt.executeQuery();
            while (rs.next()) {
                retorno = true;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return retorno;
    }

    public boolean permissaoAlterar(UsuarioVO usuarioVO) {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        boolean retorno = true;
        boolean montaWhere = true;
        int indice = 0;
        String comandoSQL = "SELECT * FROM usuario where usuario.id_usuario = ? and usuario.status_usuario = 2";

        try {

            stmt = con.prepareStatement(comandoSQL);
            stmt.setInt(++indice, usuarioVO.getId());

            rs = stmt.executeQuery();
            while (rs.next()) {
                retorno = false;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return retorno;
    }

    public boolean statusConta(UsuarioVO u) {
        Connection con = ConnectionFactory.getConnection();
        boolean retorno = true;
        ResultSet rs = null;
        int tipoDeUsuario = 0;
        int indice = 0;
        PreparedStatement stmt = null;

        String comandoSQL = "UPDATE usuario SET usuario.status_usuario = ? ";
        if (u.getCPF() != null) {
            comandoSQL += " where cpf_usuario = ?";
        } else if (u.getId() > 0) {
            comandoSQL += " where id_usuario = ?";

        }
        try {

            stmt = con.prepareStatement(comandoSQL);

            stmt.setInt(++indice, u.getStatusAD());
            if (u.getCPF() != null) {
                stmt.setString(++indice, u.getCPF());
            } else if (u.getId() > 0) {
                stmt.setInt(++indice, u.getId());

            }

            retorno = stmt.execute();

            if (u.getTipo().equals("Tecnico")) {
                stmt = con.prepareStatement(
                        "SELECT * FROM usuario inner join tecnico on (usuario.id_usuario = tecnico.id_tecnico) where tecnico.id_usuario = ? ");
                stmt.setInt(1, u.getId());
                rs = stmt.executeQuery();

                if (rs.next()) {
                    tecnicoVO.setId(rs.getInt("tecnico.id_tecnico"));
                }
                tecnicoDAO.statusTecnico(tecnicoVO, u.getStatusAD());
            }
            System.out.println(comandoSQL);
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
        return !retorno;

    }

    public List<UsuarioVO> readTipoUsuario() {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<UsuarioVO> retorno = new ArrayList<UsuarioVO>();
        boolean montaWhere = true;
        int indice = 0;
        String comandoSQL = "SELECT * FROM tipo_usuario where tipo_usuario.id_tipo_usuario in (\n"
                + "select tipo_usuario.id_tipo_usuario from tipo_usuario where tipo_usuario.tipo_de_usuario != \"Desenvolvedor\"); ";

        try {

            stmt = con.prepareStatement(comandoSQL);

            rs = stmt.executeQuery();

            while (rs.next()) {
                UsuarioVO usuarioVO = new UsuarioVO();
                usuarioVO.setTipo(rs.getString("tipo_de_usuario"));
                retorno.add(usuarioVO);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return retorno;
    }

    public boolean alterarSenha(UsuarioVO usuarioVO) {

        Connection con = ConnectionFactory.getConnection();
        boolean retorno = false;

        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("Update usuario set senha_usuario = ? where id_usuario = ? ");

            stmt.setString(1, usuarioVO.getSenha());
            stmt.setInt(2, usuarioVO.getId());
            retorno = stmt.execute();

        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
        return !retorno;

    }

    public UsuarioVO buscarEmailLogin(UsuarioVO usuarioVO) {
        UsuarioVO retorno = new UsuarioVO();

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean montaWhere = true;
        int indice = 0;
        String comandoSQL = "SELECT * FROM usuario ";

        try {
            if (usuarioVO.getLogin() != null) {
                comandoSQL += " WHERE login_usuario = ? ";
                montaWhere = false;
            }
            if (usuarioVO.getEmail() != null) {
                if (montaWhere) {
                    comandoSQL += " where email_usuario = ? ";
                } else {
                    comandoSQL += " and   email_usuario = ? ";
                }

            }
            stmt = con.prepareStatement(comandoSQL);
            if (usuarioVO.getLogin() != null) {
                stmt.setString(++indice, usuarioVO.getLogin());

            }
            if (usuarioVO.getEmail() != null) {
                stmt.setString(++indice, usuarioVO.getEmail());

            }
            rs = stmt.executeQuery();

            if (rs.next()) {
                retorno.setId(rs.getInt("id_usuario"));
                retorno.setLogin(rs.getString("login_usuario"));
                retorno.setNome(rs.getString("nome_usuario"));
                retorno.setEmail(rs.getString("email_usuario"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return retorno;
    }

    public List<UsuarioVO> listaadm() {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<UsuarioVO> retorno = new ArrayList<>();
        try {
            Connection con = ConnectionFactory.getConnection();

            String comandoSQL = "select usuario.nome_usuario,usuario.email_usuario from usuario where usuario.id_tipo_usuario = 2 and usuario.status_usuario = 1";
            stmt = con.prepareStatement(comandoSQL);
            rs = stmt.executeQuery();

            while (rs.next()) {
                UsuarioVO usuarioVO = new UsuarioVO();

                usuarioVO.setNome(rs.getString("usuario.nome_usuario"));
                usuarioVO.setEmail(rs.getString("email_usuario"));
                retorno.add(usuarioVO);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;

    }
}
