/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import connection.ConnectionFactory;
import static dao.ChamadosDAO.sdfData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import vo.ChamadosVO;
import vo.ClientVO;
import vo.EnderecoVO;

/**
 *
 * @author etecja
 */
public class ClienteDAO {

    public List<ClientVO> pesquisaCliente(ClientVO vo) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<ClientVO> retorno = new ArrayList<>();
        int indice = 0;

        try {
            String comandoSQL = "SELECT * FROM cliente INNER JOIN endereco ON(cliente.id_endereco = endereco.id_endereco)";

            if (vo.getNome() != null) {

                comandoSQL += " where nome_cliente like ? ";

            }

            comandoSQL += "ORDER by cliente.nome_cliente asc";
            stmt = con.prepareStatement(comandoSQL);

            if (vo.getNome() != null) {
                stmt.setString(++indice, "%" + vo.getNome() + "%");
            }

            rs = stmt.executeQuery();

            while (rs.next()) {
                ClientVO clientVO = new ClientVO();

                clientVO.setId(rs.getInt("id_cliente"));
                clientVO.setNome(rs.getString("nome_cliente"));
                clientVO.seteMail(rs.getString("email_cliente"));
                clientVO.setLogin(rs.getString("login_cliente"));
                clientVO.setSenha(rs.getString("senha_cliente"));

                EnderecoVO enderecoVO = new EnderecoVO();
                enderecoVO.setRua(rs.getString("rua_endereco"));
                enderecoVO.setNumero(rs.getString("numero_endereco"));
                enderecoVO.setBairro(rs.getString("bairro_endereco"));
                enderecoVO.setReferencia(rs.getString("referencia_endereco"));
                enderecoVO.setComplemento(rs.getString("complemento_endereco"));
enderecoVO.setCep(rs.getString("cep_endereco"));
                clientVO.setEnderecoVO(enderecoVO);
                clientVO.setTelefone(rs.getString("telefone_cliente"));
                clientVO.setCelular(rs.getString("celular_cliente"));
                clientVO.setTipodeCabo(rs.getString("cabo_cliente"));
                retorno.add(clientVO);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } // TODO Auto-generated catch block
        finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return retorno;

    }

    public ClientVO passarDados(ClientVO clienteVO) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ClientVO retorno = new ClientVO();
        int indice = 0;
        boolean montaWhere = true;
        try {
            String comandoSQL = "select * from cliente INNER JOIN endereco on(cliente.id_endereco = endereco.id_endereco) ";

            if (clienteVO.getNome() != null) {

                comandoSQL += " where nome_cliente like ? ";
                montaWhere = false;
            }

            if (clienteVO.getEnderecoVO().getRua() != null) {
                if (montaWhere) {
                    comandoSQL += " where endereco.rua_endereco like ? ";
                } else {
                    comandoSQL += " and endereco.rua_endereco like ? ";

                }
            }

            comandoSQL += " ORDER by cliente.id_cliente asc";
            stmt = con.prepareStatement(comandoSQL);

            if (clienteVO.getNome() != null) {
                stmt.setString(++indice, "%" + clienteVO.getNome() + "%");
            }

            if (clienteVO.getEnderecoVO().getRua() != null) {
                stmt.setString(++indice, "%" + clienteVO.getEnderecoVO().getRua() + "%");
            }

            rs = stmt.executeQuery();

            if (rs.next()) {
                retorno.setId(rs.getInt("id_cliente"));
                retorno.setNome(rs.getString("nome_cliente"));
                retorno.seteMail(rs.getString("email_cliente"));
                retorno.setLogin(rs.getString("login_cliente"));
                retorno.setSenha(rs.getString("senha_cliente"));

                EnderecoVO enderecoVO = new EnderecoVO();
                enderecoVO.setRua(rs.getString("rua_endereco"));
                enderecoVO.setNumero(rs.getString("numero_endereco"));
                enderecoVO.setBairro(rs.getString("bairro_endereco"));
                enderecoVO.setCep(rs.getString("cep_endereco"));
                enderecoVO.setReferencia(rs.getString("referencia_endereco"));
                enderecoVO.setComplemento(rs.getString("complemento_endereco"));

                retorno.setEnderecoVO(enderecoVO);
                retorno.setRoteador(rs.getString("equipamento_cliente"));
                retorno.setTelefone(rs.getString("telefone_cliente"));
                retorno.setCelular(rs.getString("celular_cliente"));
                retorno.setTipodeCabo(rs.getString("cabo_cliente"));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } // TODO Auto-generated catch block
        finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return retorno;

    }

}
