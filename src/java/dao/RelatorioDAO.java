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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import vo.ChamadosVO;
import vo.ClientVO;
import vo.EnderecoVO;
import vo.RelatorioVO;
import vo.StatusVO;
import vo.TecnicoVO;

/**
 *
 * @author GYOVANEPEREIRADESOUZ
 */
public class RelatorioDAO {

    Connection connection = null;

    public RelatorioDAO() {
        this.connection = ConnectionFactory.getConnection();
    }

    public List<RelatorioVO> relatorioTeste() {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection con = ConnectionFactory.getConnection();

        List<RelatorioVO> retorno = new ArrayList<>();
        boolean montaWhere = true;
        boolean AndCliente = true, AndTipo = true, AndDate = true;
        int indice = 0;
        String comandoSQL = "select * from chamado INNER JOIN tecnico on (chamado.id_tecnico = tecnico.id_tecnico)\n"
                + "                                                        INNER JOIN cliente ON(chamado.id_cliente = cliente.id_cliente)\n"
                + "                                                        INNER JOIN endereco ON(cliente.id_endereco = endereco.id_endereco)\n"
                + "                                                        INNER JOIN status on (chamado.id_status_chamado = status.id_status)\n"
                + "                                                        INNER JOIN tipo_chamado on (chamado.id_tipo_chamado = tipo_chamado.id_tipo_chamado)";

        try {
            stmt = con.prepareStatement(comandoSQL);

            rs = stmt.executeQuery();
            while (rs.next()) {
                ChamadosVO chamadosVO = new ChamadosVO();
                chamadosVO.setID(rs.getInt("chamado.id_chamado"));
                String auxData = sdfData.format(rs.getDate("data_do_chamado"));
                java.util.Date data = sdfData.parse(auxData);
                chamadosVO.setData(data);
                chamadosVO.setHoras(rs.getTime("hora_do_chamado"));
                ClientVO clientVO = new ClientVO();
                clientVO.setNome(rs.getString("cliente.nome_cliente"));
                clientVO.setTelefone(rs.getString("cliente.telefone_cliente"));
                EnderecoVO enderecoVO = new EnderecoVO();
                enderecoVO.setRua(rs.getString("endereco.rua_endereco"));
                enderecoVO.setCep(rs.getString("endereco.cep_endereco"));
                clientVO.setEnderecoVO(enderecoVO);
                chamadosVO.setClientVO(clientVO);
                TecnicoVO tecnicoVO = new TecnicoVO();
                tecnicoVO.setTecnico(rs.getString("tecnico.nome_tecnico"));
                chamadosVO.setTecnicoVO(tecnicoVO);
                StatusVO statusVO = new StatusVO();
                statusVO.setTipo(rs.getString("status.status"));
                chamadosVO.setStatusVO(statusVO);
                RelatorioVO  relatorioVO = new RelatorioVO();
                relatorioVO.setChamadosVO(chamadosVO);
                retorno.add(relatorioVO);

            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
            // JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return retorno;

    }

}
