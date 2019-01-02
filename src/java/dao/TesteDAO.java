/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import connection.ConnectionFactory;
import static dao.ChamadosDAO.sdfData;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import vo.ChamadosVO;
import vo.ClientVO;
import vo.EnderecoVO;
import vo.StatusVO;
import vo.TecnicoVO;

/**
 *
 * @author aluno14
 */
public class TesteDAO {

    private Connection con = null;

    public TesteDAO() {
        con = ConnectionFactory.getConnection();

    }

    public List<ChamadosVO> lista(ChamadosVO c) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection con = ConnectionFactory.getConnection();

        List<ChamadosVO> retorno = new ArrayList<>();
        boolean montaWhere = true;
        boolean AndDate = true;
        int indice = 0;
        String comandoSQL = "select * from chamado INNER JOIN tecnico on (chamado.id_tecnico = tecnico.id_tecnico)"
                + "                                         INNER JOIN cliente ON(chamado.id_cliente = cliente.id_cliente)"
                + "                                         INNER JOIN endereco ON(cliente.id_endereco = endereco.id_endereco)"
                + "                                         INNER JOIN status on (chamado.id_status_chamado = status.id_status)"
                + "                                         INNER JOIN tipo_chamado on (chamado.id_tipo_chamado = tipo_chamado.id_tipo_chamado)";

        if (c.getID() > 0) {
            comandoSQL += " Where chamado.id_chamado = ?";
            montaWhere = false;

        } else if (c.getClientVO().getNome() != null) {
            comandoSQL += " Where cliente.nome_cliente like ? ";
            montaWhere = false;

        } else if (c.getTipo() != null) {
            comandoSQL += " where tipo_chamado.tipo_chamado = ? ";
            montaWhere = false;

        }

        if (montaWhere) {
            montaWhere = false;
            AndDate = false;
            if (c.getData() != null) {
                comandoSQL += " Where chamado.data_do_chamado BETWEEN'" + new SimpleDateFormat("yyy/MM/dd").format(c.getData()) + "' and  (select date_add('" + new SimpleDateFormat("yyy/MM/dd").format(c.getData()) + "' , interval 30 day)) ";
                montaWhere = false;

            } else {
                comandoSQL += " Where chamado.data_do_chamado BETWEEN CURRENT_DATE() and  (select date_add(CURRENT_DATE() , interval 30 day)) ";
            }
        }

        if (!montaWhere) {

            if (c.getClientVO().getNome() != null) {
                comandoSQL += " And cliente.nome_cliente like ? ";

            }
            if (c.getTipo() != null) {
                comandoSQL += " And tipo_chamado.tipo_chamado = ? ";
            }
            if (AndDate) {
                if (c.getData() != null) {
                    comandoSQL += " AND chamado.data_do_chamado BETWEEN'" + new SimpleDateFormat("yyy/MM/dd").format(c.getData())
                            + "' and  (select date_add('" + new SimpleDateFormat("yyy/MM/dd").format(c.getData()) + "' , interval 30 day)) ";

                }
            }
        }

        try {
            stmt = con.prepareStatement(comandoSQL);
            System.out.println(comandoSQL);
            if (c.getID() > 0) {
                stmt.setInt(++indice, c.getID());
            }
            if (c.getClientVO().getNome() != null) {
                stmt.setString(++indice, "%" + c.getClientVO().getNome().trim() + "%");
            }

            if (c.getTipo() != null) {
                stmt.setString(++indice, c.getTipo());
            }

            rs = stmt.executeQuery();
            System.out.println(comandoSQL);
            while (rs.next()) {
                ChamadosVO chamadosVO = new ChamadosVO();

                chamadosVO.setID(rs.getInt("id_chamado"));

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
                retorno.add(chamadosVO);

            }
        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return retorno;

    }

    @SuppressWarnings("resource")
    public boolean alterarChamadoInstalacao(ChamadosVO chamadosVO) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int indice = 0;
        boolean retorno = false;
        String comandoSQL = "UPDATE chamado set ";
        try {

            stmt = con.prepareStatement("select id_tecnico from tecnico where nome_tecnico = ? ");
            stmt.setString(1, chamadosVO.getTecnicoVO().getTecnico());
            rs = stmt.executeQuery();

            if (rs.next()) {
                TecnicoVO t = new TecnicoVO();
                t.setId(rs.getInt("id_tecnico"));
                chamadosVO.setTecnicoVO(t);
            }

            stmt = con.prepareStatement("select id_status from status where status = ? ");
            stmt.setString(1, chamadosVO.getStatusVO().getTipo());
            rs = stmt.executeQuery();

            if (rs.next()) {
                StatusVO s = new StatusVO();
                s.setId(rs.getInt("id_status"));
                chamadosVO.setStatusVO(s);

            }

            if (chamadosVO.getClientVO().getId() > 0) {
                comandoSQL += "id_cliente = ?,";
            }
            if (chamadosVO.getAgendamento_Data() != null) {
                comandoSQL += "agendamentoData_chamado = ?,";

            }
            if (chamadosVO.getAgendamento_horas() != null) {
                comandoSQL += "AgendamentoHora_chamado = ?,";

            }
            if (chamadosVO.getDescricao() != null) {
                comandoSQL += "observacao_chamado = ?,";

            }
            if (chamadosVO.getTecnicoVO().getId() > 0) {
                comandoSQL += "id_tecnico = ?,";

            }
            if (chamadosVO.getStatusVO().getId() > 0) {
                comandoSQL += "id_status_chamado = ?,";

            }
            if (chamadosVO.getJustificativa() != null) {
                comandoSQL += "justificativa_chamado = ? ";
            }
                        comandoSQL += " where id_chamado = ? ";
            System.out.println(comandoSQL);
            stmt = con.prepareStatement(
                    comandoSQL);

            if(chamadosVO.getClientVO().getId() >0){
            stmt.setInt(++indice, chamadosVO.getClientVO().getId());
            }
            if(chamadosVO.getAgendamento_Data() != null){
            stmt.setDate(++indice, new Date(chamadosVO.getAgendamento_Data().getTime()));

            }
            if(chamadosVO.getAgendamento_horas() != null){  
            stmt.setTime(++indice, chamadosVO.getAgendamento_horas());

            }  
             if(chamadosVO.getDescricao()!= null) {
            stmt.setString(++indice, chamadosVO.getDescricao());

             }
            if(chamadosVO.getTecnicoVO().getId() >0){
            stmt.setInt(++indice, chamadosVO.getTecnicoVO().getId());

            }
            if(chamadosVO.getStatusVO().getId()>0){
            stmt.setInt(++indice, chamadosVO.getStatusVO().getId());

            }
            if(chamadosVO.getJustificativa() != null){
            stmt.setString(++indice, chamadosVO.getJustificativa());
            }
            stmt.setInt(++indice, chamadosVO.getID());
            retorno = stmt.execute();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return !retorno;

    }
}
