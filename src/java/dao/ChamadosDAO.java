package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import connection.ConnectionFactory;
import java.sql.Date;
import vo.ChamadosVO;
import vo.ClientVO;
import vo.EnderecoVO;
import vo.GraficoVO;
import vo.StatusVO;
import vo.TecnicoVO;
import vo.UsuarioVO;

public class ChamadosDAO {

    static SimpleDateFormat sdfData = new SimpleDateFormat("dd/MM/yyyy");
    static SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm");

    @SuppressWarnings("resource")
    public boolean abreChamadoManuntecao(ChamadosVO c) {

        Connection con = ConnectionFactory.getConnection();
        boolean retorno = false;
        int idTipoChamado = 0;
        int indice = 0;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.prepareStatement("SELECT cliente.id_cliente FROM cliente where cliente.nome_cliente = ? and cliente.login_cliente = ?");
            stmt.setString(1, c.getClientVO().getNome());
            stmt.setString(2, c.getClientVO().getLogin());

            rs = stmt.executeQuery();

            while (rs.next()) {
                ClientVO clientVO = new ClientVO();
                clientVO.setId(rs.getInt("id_cliente"));
            }

            stmt = con.prepareStatement("select id_tecnico from tecnico where nome_tecnico = ? ");
            stmt.setString(1, c.getTecnicoVO().getTecnico());
            rs = stmt.executeQuery();

            while (rs.next()) {
                TecnicoVO t = new TecnicoVO();
                t.setId(rs.getInt("id_tecnico"));
                c.setTecnicoVO(t);
            }

            stmt = con.prepareStatement("select id_status from status where status = ? ");
            stmt.setString(1, c.getStatusVO().getTipo());
            rs = stmt.executeQuery();

            while (rs.next()) {
                StatusVO s = new StatusVO();
                s.setId(rs.getInt("id_status"));

                c.setStatusVO(s);
            }

            stmt = con.prepareStatement("select id_tipo_chamado from tipo_chamado where tipo_chamado.tipo_chamado = ? ");
            stmt.setString(1, c.getTipo());
            rs = stmt.executeQuery();

            while (rs.next()) {
                idTipoChamado = rs.getInt("id_tipo_chamado");
            }
            if (c.getAgendamento_Data() == null) {
                c.setAgendamento_Data(new java.util.Date());
            }
            stmt = con.prepareStatement(
                    "INSERT INTO chamado (id_chamado, id_cliente,  data_do_chamado,  hora_do_chamado,agendamentoData_chamado,AgendamentoHora_chamado"
                    + ",observacao_chamado,id_tecnico,id_tipo_chamado,"
                    + " id_status_chamado) VALUES (null,?,?,?,?,?,?,?,?,?)");
            stmt.setInt(++indice, c.getClientVO().getId());
            stmt.setDate(++indice, new java.sql.Date(c.getData().getTime()));
            stmt.setTime(++indice, c.getHoras());

            stmt.setDate(++indice, new java.sql.Date(c.getAgendamento_Data().getTime()));
            stmt.setTime(++indice, c.getAgendamento_horas());
            stmt.setString(++indice, c.getDescricao());
            stmt.setInt(++indice, c.getTecnicoVO().getId());
            stmt.setInt(++indice, idTipoChamado);
            stmt.setInt(++indice, c.getStatusVO().getId());
            retorno = stmt.execute();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR" + ex.getMessage(), "", JOptionPane.ERROR_MESSAGE);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return !retorno;

    }

    @SuppressWarnings("resource")
    public boolean abreChamadoInstalacao(ChamadosVO c) {

        Connection con = ConnectionFactory.getConnection();
        boolean retorno = true;
        int idTipoChamado = 0;
        int indice = 0;

        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.prepareStatement("SELECT cliente.id_cliente FROM cliente where cliente.nome_cliente = ? and cliente.login_cliente = ?");
            stmt.setString(1, c.getClientVO().getNome());
            stmt.setString(2, c.getClientVO().getLogin());
            rs = stmt.executeQuery();
            while (rs.next()) {
                ClientVO clientVO = new ClientVO();
                clientVO.setId(rs.getInt("id_cliente"));
                c.setClientVO(clientVO);
            }

            stmt = con.prepareStatement("select id_tecnico from tecnico where nome_tecnico = ? ");
            stmt.setString(1, c.getTecnicoVO().getTecnico());
            rs = stmt.executeQuery();

            while (rs.next()) {
                TecnicoVO t = new TecnicoVO();

                t.setId(rs.getInt("id_tecnico"));
                c.setTecnicoVO(t);
            }

            stmt = con.prepareStatement("select id_status from status where status = ? ");
            stmt.setString(1, c.getStatusVO().getTipo());
            rs = stmt.executeQuery();

            while (rs.next()) {
                StatusVO s = new StatusVO();
                s.setId(rs.getInt("id_status"));

                c.setStatusVO(s);
            }

            stmt = con.prepareStatement("select id_tipo_chamado from tipo_chamado where tipo_chamado.tipo_chamado = ? ");
            stmt.setString(1, c.getTipo());
            rs = stmt.executeQuery();

            while (rs.next()) {
                idTipoChamado = rs.getInt("id_tipo_chamado");
            }
            stmt = con.prepareStatement("select id_tipo_chamado from tipo_chamado where tipo_chamado = ? ");
            stmt.setString(1, c.getTipo());
            rs = stmt.executeQuery();

            while (rs.next()) {
                idTipoChamado = rs.getInt("id_tipo_chamado");
            }
            if (c.getAgendamento_Data() == null) {
                c.setAgendamento_Data(new java.util.Date());
            }

            stmt = con.prepareStatement(
                    "INSERT INTO chamado (id_chamado, id_cliente,  data_do_chamado,  hora_do_chamado, "
                    + " agendamentoData_chamado,AgendamentoHora_chamado,  observacao_chamado,id_tecnico,id_tipo_chamado, id_status_chamado)"
                    + " VALUES (null,?,?,?,?,?,?,?,?,?)");

            stmt.setInt(++indice, c.getClientVO().getId());
            stmt.setDate(++indice, new java.sql.Date(c.getData().getTime()));
            stmt.setTime(++indice, c.getHoras());

            stmt.setDate(++indice, new java.sql.Date(c.getAgendamento_Data().getTime()));

            stmt.setTime(++indice, c.getAgendamento_horas());
            stmt.setString(++indice, c.getDescricao());
            stmt.setInt(++indice, c.getTecnicoVO().getId());
            stmt.setInt(++indice, idTipoChamado);
            stmt.setInt(++indice, c.getStatusVO().getId());
            retorno = stmt.execute();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return !retorno;

    }

    public List<ChamadosVO> listagemDesktop(ChamadosVO c) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection con = ConnectionFactory.getConnection();

        List<ChamadosVO> retorno = new ArrayList<>();
        boolean montaWhere = true;
        boolean AndCliente = true, AndTipo = true, AndDate = true;
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
            AndCliente = false;

        } else if (c.getTipo() != null) {
            comandoSQL += " where tipo_chamado.tipo_chamado = ? ";
            montaWhere = false;
            AndTipo = false;
        }

        if (montaWhere) {
            montaWhere = false;
            AndDate = true;
            if (c.getData() != null) {
                comandoSQL += " Where chamado.data_do_chamado BETWEEN (select date_add('" + new SimpleDateFormat("yyy/MM/dd").format(c.getData()) + "' , interval -30 day))"
                        + " and '" + new SimpleDateFormat("yyy/MM/dd").format(c.getData()) + "'";
                montaWhere = false;

            } else {

                comandoSQL += " Where chamado.data_do_chamado BETWEEN (select date_add(CURRENT_DATE() , interval -30 day)) and CURRENT_DATE() ";
            }
        }

        if (!montaWhere) {
            if (AndCliente) {
                if (c.getClientVO().getNome() != null) {
                    comandoSQL += " And cliente.nome_cliente like ? ";

                }
            }
            if (AndTipo) {
                if (c.getTipo() != null) {
                    comandoSQL += " And tipo_chamado.tipo_chamado = ? ";
                }
            }
            if (AndDate) {
                if (c.getData() != null) {
                    comandoSQL += " AND chamado.data_do_chamado BETWEEN (select date_add('" + new SimpleDateFormat("yyy/MM/dd").format(c.getData()) + "' , interval -30 day))"
                            + " and '" + new SimpleDateFormat("yyy/MM/dd").format(c.getData()) + "'";

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
            while (rs.next()) {
                ChamadosVO chamadosVO = new ChamadosVO();
                chamadosVO.setID(rs.getInt("chamado.id_chamado"));
                String auxData = sdfData.format(rs.getDate("data_do_chamado"));
                java.util.Date data = sdfData.parse(auxData);
                chamadosVO.setData(data);
                chamadosVO.setHoras(rs.getTime("hora_do_chamado"));
                chamadosVO.setTipo(rs.getString("tipo_chamado.tipo_chamado"));
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
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
            // JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return retorno;

    }

    public List<ChamadosVO> listagemMobile() {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection con = ConnectionFactory.getConnection();

        List<ChamadosVO> retorno = new ArrayList<>();
        boolean montaWhere = true;
        int indice = 0;
        String comandoSQL = "select * from chamado INNER JOIN tecnico on (chamado.id_tecnico = tecnico.id_tecnico)"
                + "                                         INNER JOIN cliente ON(chamado.id_cliente = cliente.id_cliente)"
                + "                                         INNER JOIN endereco ON(cliente.id_endereco = endereco.id_endereco)"
                + "                                         INNER JOIN status on (chamado.id_status_chamado = status.id_status)"
                + "                                         INNER JOIN tipo_chamado on (chamado.id_tipo_chamado = tipo_chamado.id_tipo_chamado)";

        try {
            stmt = con.prepareStatement(comandoSQL);

            rs = stmt.executeQuery();
            System.out.println(comandoSQL);
            while (rs.next()) {
                ChamadosVO chamadosVO = new ChamadosVO();

                chamadosVO.setID(rs.getInt("id_chamado"));

                String auxData = sdfData.format(rs.getDate("data_do_chamado"));
                java.util.Date data = sdfData.parse(auxData);
                chamadosVO.setData(data);
                chamadosVO.setHoras(rs.getTime("hora_do_chamado"));

                String auxDataAgend = sdfData.format(rs.getDate("agendamentoData_chamado"));
                java.util.Date dataAgen = sdfData.parse(auxDataAgend);
                chamadosVO.setAgendamento_Data(dataAgen);
                chamadosVO.setAgendamento_horas(rs.getTime("AgendamentoHora_chamado"));
                chamadosVO.setConfirmacao_Data(rs.getDate("confirmacaoData_chamado"));

                chamadosVO.setFinalizacao_Data(rs.getDate("finalizacaoData_chamado"));
                chamadosVO.setConfirmacao_Horas(rs.getTime("confirmacaoHora_chamado"));

                chamadosVO.setFinalizacao_horas(rs.getTime("finalizacaoHora_chamado"));
                chamadosVO.setTipo(rs.getString("tipo_chamado.tipo_chamado"));
                chamadosVO.setDescricao(rs.getString("observacao_chamado"));
                chamadosVO.setJustificativa(rs.getString("justificativa_chamado"));

                ClientVO clientVO = new ClientVO();

                clientVO.setId(rs.getInt("cliente.id_cliente"));
                clientVO.setNome(rs.getString("cliente.nome_cliente"));
                clientVO.seteMail(rs.getString("cliente.email_cliente"));
                clientVO.setLogin(rs.getString("cliente.login_cliente"));
                clientVO.setSenha(rs.getString("cliente.senha_cliente"));
                clientVO.setTelefone(rs.getString("cliente.telefone_cliente"));
                clientVO.setCelular(rs.getString("cliente.celular_cliente"));
                clientVO.setRoteador(rs.getString("cliente.equipamento_cliente"));
                clientVO.setTipodeCabo(rs.getString("cliente.cabo_cliente"));

                EnderecoVO enderecoVO = new EnderecoVO();
                enderecoVO.setId(rs.getInt("endereco.id_endereco"));
                enderecoVO.setRua(rs.getString("endereco.rua_endereco"));
                enderecoVO.setBairro(rs.getString("endereco.bairro_endereco"));
                enderecoVO.setNumero(rs.getString("endereco.numero_endereco"));
                enderecoVO.setCep(rs.getString("endereco.cep_endereco"));
                enderecoVO.setComplemento(rs.getString("endereco.complemento_endereco"));
                enderecoVO.setReferencia(rs.getString("endereco.referencia_endereco"));

                clientVO.setEnderecoVO(enderecoVO);
                chamadosVO.setClientVO(clientVO);

                TecnicoVO tecnicoVO = new TecnicoVO();
                tecnicoVO.setId(rs.getInt("tecnico.id_tecnico"));
                tecnicoVO.setTecnico(rs.getString("tecnico.nome_tecnico"));
                //status tecnico...
                chamadosVO.setTecnicoVO(tecnicoVO);

                StatusVO statusVO = new StatusVO();
                statusVO.setId(rs.getInt("status.id_status"));
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

    @SuppressWarnings("deprecation")
    public static List<ChamadosVO> pesquisaChamados(ChamadosVO vo) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<ChamadosVO> retorno = new ArrayList<>();
        boolean montaWhere = true;
        int indice = 0;

        try {
            String comandoSQL = "select * from chamado INNER JOIN tecnico on (chamado.id_tecnico = tecnico.id_tecnico)"
                    + "                      INNER JOIN cliente ON(chamado.id_cliente = cliente.id_cliente)"
                    + "                      INNER JOIN endereco ON(cliente.id_endereco = endereco.id_endereco)"
                    + "                     inner join status on (chamado.id_status_chamado = status.id_status)"
                    + "                     inner join tipo_chamado on (chamado.id_tipo_chamado = tipo_chamado.id_tipo_chamado)";
            if (vo.getID() > 0) {
                comandoSQL += " where chamado.id_chamado = ? ";
                montaWhere = false;
            }

            if (vo.getClientVO().getNome() != null || vo.getClientVO().getNome().equals("")) {
                if (montaWhere) {
                    comandoSQL += " where cliente.nome_cliente like ? ";
                    montaWhere = false;
                } else {
                    comandoSQL += " and cliente.nome_cliente like ? ";
                }
            }
            if (vo.getTipo() != null) {
                if (montaWhere) {
                    comandoSQL += " where tipo_chamado.tipo_chamado= ? ";
                    montaWhere = false;
                } else {
                    comandoSQL += " and tipo_chamado.tipo_chamado = ? ";
                }
            }
            if (vo.getData() != null) {
                if (montaWhere) {
                    comandoSQL += " where data_do_chamado = ? ";
                    montaWhere = false;
                } else {
                    comandoSQL += " and data_do_chamado = ? ";
                }
            }
            comandoSQL += " ORDER by chamado.id_chamado asc ";
            stmt = con.prepareStatement(comandoSQL);

            if (vo.getID() > 0) {
                stmt.setInt(++indice, vo.getID());
                System.out.println("d");
            }
            if (vo.getClientVO().getNome() != null || vo.getClientVO().getNome().equals("")) {
                stmt.setString(++indice, "%" + vo.getClientVO().getNome() + "%");
            }
            if (vo.getTipo() != null) {
                stmt.setString(++indice, vo.getTipo());
            }
            if (vo.getData() != null) {

                stmt.setDate(++indice, new java.sql.Date(vo.getData().getTime()));
            }

            rs = stmt.executeQuery();

            while (rs.next()) {
                ChamadosVO chamadosVO = new ChamadosVO();

                chamadosVO.setID(rs.getInt("chamado.id_chamado"));

                String auxData = sdfData.format(rs.getDate("chamado.data_do_chamado"));
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
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return retorno;

    }

    public List<ChamadosVO> carregarTipoChamado() {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<String> Tipochamados = new ArrayList<>();
        List<ChamadosVO> Tipochamado = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM tipo_chamado");
            rs = stmt.executeQuery();

            while (rs.next()) {
                ChamadosVO chamadosVO = new ChamadosVO();
                chamadosVO.setTipo(rs.getString("tipo_chamado"));
                Tipochamado.add(chamadosVO);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);

            Logger.getLogger(ChamadosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return Tipochamado;

    }

    public String pesquisaTipoDeChamado(ChamadosVO chamadosVO) {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String retorno = null;
        try {
            stmt = con.prepareStatement(
                    "select * from chamado inner join tipo_chamado on (chamado.id_tipo_chamado = tipo_chamado.id_tipo_chamado) where chamado.id_chamado = ? ");
            stmt.setInt(1, chamadosVO.getID());
            rs = stmt.executeQuery();

            while (rs.next()) {
                retorno = rs.getString("tipo_chamado");
            }

        } catch (SQLException e) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, e);

        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return retorno;
    }

    public ChamadosVO carregarTelaEdicaoInstalacao(ChamadosVO chamadosVO) {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement(
                    "select * from chamado INNER JOIN tecnico on (chamado.id_tecnico = tecnico.id_tecnico)"
                    + "                      INNER JOIN cliente ON(chamado.id_cliente = cliente.id_cliente)"
                    + "                      INNER JOIN endereco ON(cliente.id_endereco = endereco.id_endereco)"
                    + "                     inner join status on (chamado.id_status_chamado = status.id_status)"
                    + "                     inner join tipo_chamado on (chamado.id_tipo_chamado = tipo_chamado.id_tipo_chamado) where chamado.id_chamado = ?");
            stmt.setInt(1, chamadosVO.getID());
            rs = stmt.executeQuery();

            if (rs.next()) {

                chamadosVO.setID(rs.getInt("chamado.id_chamado"));

                String auxData = sdfData.format(rs.getDate("chamado.data_do_chamado"));
                java.util.Date data = sdfData.parse(auxData);
                String auxDataAgen = sdfData.format(rs.getDate("chamado.agendamentoData_chamado"));
                java.util.Date dataAgen = sdfData.parse(auxDataAgen);
                chamadosVO.setData(data);
                chamadosVO.setAgendamento_horas(rs.getTime("AgendamentoHora_chamado"));
                chamadosVO.setHoras(rs.getTime("hora_do_chamado"));
                chamadosVO.setAgendamento_Data(dataAgen);
                chamadosVO.setDescricao(rs.getString("observacao_chamado"));
                chamadosVO.setAgendamento_horas(rs.getTime("AgendamentoHora_chamado"));
                chamadosVO.setJustificativa(rs.getString("justificativa_chamado"));
                ClientVO clientVO = new ClientVO();
                clientVO.setId(rs.getInt("cliente.id_cliente"));
                clientVO.setNome(rs.getString("cliente.nome_cliente"));
                clientVO.setTelefone(rs.getString("cliente.telefone_cliente"));
                clientVO.setCelular(rs.getString("celular_cliente"));
                clientVO.setRoteador(rs.getString("equipamento_cliente"));
                clientVO.setTipodeCabo(rs.getString("cabo_cliente"));
                EnderecoVO enderecoVO = new EnderecoVO();
                enderecoVO.setNumero(rs.getString("numero_endereco"));
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
            }

        } catch (SQLException e) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);

        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Error");
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return chamadosVO;
    }

    public ChamadosVO carregarTelaEdicaoManuntencao(ChamadosVO vo) {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ChamadosVO chamadosVO = new ChamadosVO();

        try {
            stmt = con.prepareStatement(
                    "select * from chamado INNER JOIN tecnico on (chamado.id_tecnico = tecnico.id_tecnico)"
                    + "                      INNER JOIN cliente ON(chamado.id_cliente = cliente.id_cliente)"
                    + "                      INNER JOIN endereco ON(cliente.id_endereco = endereco.id_endereco)"
                    + "                     inner join status on (chamado.id_status_chamado = status.id_status)"
                    + "                     inner join tipo_chamado on (chamado.id_tipo_chamado = tipo_chamado.id_tipo_chamado) where chamado.id_chamado = ?");
            stmt.setInt(1, vo.getID());
            rs = stmt.executeQuery();

            while (rs.next()) {

                chamadosVO.setID(rs.getInt("chamado.id_chamado"));

                String auxData = sdfData.format(rs.getDate("chamado.data_do_chamado"));
                java.util.Date data = sdfData.parse(auxData);
                String auxDataAgen = sdfData.format(rs.getDate("chamado.agendamentoData_chamado"));
                java.util.Date dataAgen = sdfData.parse(auxDataAgen);
                chamadosVO.setData(data);
                chamadosVO.setAgendamento_horas(rs.getTime("AgendamentoHora_chamado"));
                chamadosVO.setHoras(rs.getTime("hora_do_chamado"));
                chamadosVO.setAgendamento_Data(dataAgen);
                chamadosVO.setDescricao(rs.getString("observacao_chamado"));
                chamadosVO.setAgendamento_horas(rs.getTime("AgendamentoHora_chamado"));
                chamadosVO.setJustificativa(rs.getString("justificativa_chamado"));

                ClientVO clientVO = new ClientVO();
                clientVO.setId(rs.getInt("cliente.id_cliente"));
                clientVO.setNome(rs.getString("cliente.nome_cliente"));
                clientVO.setTelefone(rs.getString("cliente.telefone_cliente"));
                clientVO.setCelular(rs.getString("celular_cliente"));
                clientVO.setRoteador(rs.getString("equipamento_cliente"));
                clientVO.setTipodeCabo(rs.getString("cabo_cliente"));
                EnderecoVO enderecoVO = new EnderecoVO();
                enderecoVO.setNumero(rs.getString("numero_endereco"));
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
            }

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);

        } catch (ParseException ex) {
            Logger.getLogger(ChamadosDAO.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);

        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return chamadosVO;
    }

    @SuppressWarnings("resource")
    public boolean alterarChamadoManuntencao(ChamadosVO chamadosVO) {
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

            if (chamadosVO.getClientVO().getId() > 0) {
                stmt.setInt(++indice, chamadosVO.getClientVO().getId());
            }
            if (chamadosVO.getAgendamento_Data() != null) {
                stmt.setDate(++indice, new Date(chamadosVO.getAgendamento_Data().getTime()));

            }
            if (chamadosVO.getAgendamento_horas() != null) {
                stmt.setTime(++indice, chamadosVO.getAgendamento_horas());

            }
            if (chamadosVO.getDescricao() != null) {
                stmt.setString(++indice, chamadosVO.getDescricao());

            }
            if (chamadosVO.getTecnicoVO().getId() > 0) {
                stmt.setInt(++indice, chamadosVO.getTecnicoVO().getId());

            }
            if (chamadosVO.getStatusVO().getId() > 0) {
                stmt.setInt(++indice, chamadosVO.getStatusVO().getId());

            }
            if (chamadosVO.getJustificativa() != null) {
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

    public boolean fecharChamado(ChamadosVO chamadosVO) {
        Connection con = ConnectionFactory.getConnection();
        boolean retorno = false;
        PreparedStatement stmt = null;
        ResultSet rs;
        try {

            stmt = con.prepareStatement("SELECT status.id_status from status WHERE status.status = \"Finalizado\"");

            rs = stmt.executeQuery();

            while (rs.next()) {
                StatusVO statusVO = new StatusVO();
                statusVO.setId(rs.getInt("status.id_status"));
                chamadosVO.setStatusVO(statusVO);
            }

            stmt = con.prepareStatement("UPDATE chamado set id_status_chamado = ? where id_chamado = ?");
            stmt.setInt(1, chamadosVO.getStatusVO().getId());
            stmt.setInt(2, chamadosVO.getID());
            retorno = stmt.execute();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
        return !retorno;
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

            if (chamadosVO.getClientVO().getId() > 0) {
                stmt.setInt(++indice, chamadosVO.getClientVO().getId());
            }
            if (chamadosVO.getAgendamento_Data() != null) {
                stmt.setDate(++indice, new Date(chamadosVO.getAgendamento_Data().getTime()));

            }
            if (chamadosVO.getAgendamento_horas() != null) {
                stmt.setTime(++indice, chamadosVO.getAgendamento_horas());

            }
            if (chamadosVO.getDescricao() != null) {
                stmt.setString(++indice, chamadosVO.getDescricao());

            }
            if (chamadosVO.getTecnicoVO().getId() > 0) {
                stmt.setInt(++indice, chamadosVO.getTecnicoVO().getId());

            }
            if (chamadosVO.getStatusVO().getId() > 0) {
                stmt.setInt(++indice, chamadosVO.getStatusVO().getId());

            }
            if (chamadosVO.getJustificativa() != null) {
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

    @SuppressWarnings("resource")
    public boolean alterarStatusChamado(ChamadosVO chamadosVO) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int indice = 0;
        boolean retorno = false;
        String comandoSQL = "UPDATE chamado set id_status_chamado = ?  ";

        if (chamadosVO.getJustificativa() != null) {
            comandoSQL += " , justificativa_chamado = ? ,"
                    + " finalizacaoData_chamado = ?, finalizacaoHora_chamado= ?";
        }

        comandoSQL += " where id_chamado = ?";
        try {
            stmt = con.prepareStatement(comandoSQL);

            stmt.setInt(++indice, chamadosVO.getStatusVO().getId());
            if (chamadosVO.getJustificativa() != null) {
                stmt.setString(++indice, chamadosVO.getJustificativa());
                stmt.setDate(++indice, new Date(chamadosVO.getFinalizacao_Data().getTime()));
                stmt.setTime(++indice, chamadosVO.getFinalizacao_horas());
            }
            stmt.setInt(++indice, chamadosVO.getID());
            retorno = stmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ChamadosDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return !retorno;

    }
//    public boolean alterarChamadoInstalacao(ChamadosVO chamadosVO) {
//        Connection con = ConnectionFactory.getConnection();
//        PreparedStatement stmt = null;
//        ResultSet rs = null;
//        int indice = 0;
//        boolean retorno = false;
//        
//        try {
//            stmt = con.prepareStatement("select id_tecnico from tecnico where nome_tecnico = ? ");
//            stmt.setString(1, chamadosVO.getTecnicoVO().getTecnico());
//            rs = stmt.executeQuery();
//            
//            if (rs.next()) {
//                TecnicoVO t = new TecnicoVO();
//                t.setId(rs.getInt("id_tecnico"));
//                chamadosVO.setTecnicoVO(t);
//            }
//            
//            stmt = con.prepareStatement("select id_status from status where status = ? ");
//            stmt.setString(1, chamadosVO.getStatusVO().getTipo());
//            rs = stmt.executeQuery();
//            
//            if (rs.next()) {
//                StatusVO s = new StatusVO();
//                s.setId(rs.getInt("id_status"));
//                chamadosVO.setStatusVO(s);
//                
//            }
//            
//            stmt = con.prepareStatement(
//                    "UPDATE chamado set id_cliente = ?,agendamento_chamado = ?, observacao_chamado = ?, id_tecnico = ?, id_status_chamado = ?  where id_chamado = ?");
//            
//            stmt.setInt(++indice, chamadosVO.getClientVO().getId());
//            stmt.setDate(++indice, new Date(chamadosVO.getAgendamento_Data().getTime()));
//            stmt.setString(++indice, chamadosVO.getDescricao());
//            stmt.setInt(++indice, chamadosVO.getTecnicoVO().getId());
//            stmt.setInt(++indice, chamadosVO.getStatusVO().getId());
//            stmt.setInt(++indice, chamadosVO.getID());
//            retorno = stmt.execute();
//            
//        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
//        } finally {
//            ConnectionFactory.closeConnection(con, stmt, rs);
//        }
//        return !retorno;
//        
//    }
//    

    public List<GraficoVO> carregarDadosGrafico() {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<GraficoVO> retorno = new ArrayList<>();
        int id = 1;
        try {
            stmt = con.prepareStatement("select status.`status`, count(chamado.id_chamado) from chamado"
                    + " inner join status on(chamado.id_status_chamado = `status`.id_status) WHERE chamado.data_do_chamado BETWEEN (select DATE_ADD(CURRENT_DATE , INTERVAL -7 DAY)) and  (CURRENT_DATE()) group by status.`status` ORDER BY chamado.id_status_chamado");
            rs = stmt.executeQuery();

            while (rs.next()) {
                GraficoVO graficoVO = new GraficoVO();

                graficoVO.setId(id++);
                graficoVO.setTipo_chamado(rs.getString(1));
                graficoVO.setQtd_chamado(rs.getInt(2));
                retorno.add(graficoVO);

            }

        } catch (SQLException e) {
            new Throwable(e.getMessage());            //JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);

        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return retorno;
    }

   

    // https://www.devmedia.com.br/artigo-java-magazine-24-graficos-com-jfreechart/9038
}
