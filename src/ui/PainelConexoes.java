package ui;

import exceptions.RegraNegocioException;
import models.Conexao;
import models.Perfil;
import models.StatusConexao;
import services.ConexaoService;
import services.PerfilService;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Painel gráfico responsável pelo gerenciamento de solicitações e status de Conexões.
 *
 * @author Gilberto Hideaki Matsunaga
 * @version 1.0
 * @since 2026-11
 */
public class PainelConexoes extends JPanel {

    private final ConexaoService conexaoService;
    private final PerfilService perfilService;

    private JComboBox<String> cbSolicitante;
    private JComboBox<String> cbDestinatario;

    private JComboBox<String> cbConexoesPendentes;
    private final List<Conexao> listaPendentesCache = new ArrayList<>();

    private JTextArea areaListagem;

    /**
     * Construtor que recebe os serviços de Conexão e Perfil e monta os componentes visuais.
     *
     * @param conexaoService Serviço de gerenciamento de conexões
     * @param perfilService Serviço de gerenciamento de perfis
     */
    public PainelConexoes(ConexaoService conexaoService, PerfilService perfilService) {
        this.conexaoService = conexaoService;
        this.perfilService = perfilService;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel painelSuperior = new JPanel(new GridLayout(1, 2, 15, 0));
        painelSuperior.add(criarPainelSolicitacao());
        painelSuperior.add(criarPainelResposta());

        add(painelSuperior, BorderLayout.NORTH);
        add(criarPainelListagem(), BorderLayout.CENTER);
    }

    private JPanel criarPainelSolicitacao() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 8, 8));
        panel.setBorder(BorderFactory.createTitledBorder("1. Enviar Solicitação de Conexão"));

        panel.add(new JLabel("Perfil Solicitante:"));
        cbSolicitante = new JComboBox<>();
        panel.add(cbSolicitante);

        panel.add(new JLabel("Perfil Destinatário:"));
        cbDestinatario = new JComboBox<>();
        panel.add(cbDestinatario);

        panel.add(new JLabel(""));
        JButton btnSolicitar = new JButton("Solicitar Conexão");
        btnSolicitar.addActionListener(e -> solicitarConexao());
        panel.add(btnSolicitar);

        return panel;
    }

    private JPanel criarPainelResposta() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 8, 8));
        panel.setBorder(BorderFactory.createTitledBorder("2. Responder Solicitação Pendente"));

        panel.add(new JLabel("Solicitação Pendente:"));
        cbConexoesPendentes = new JComboBox<>();
        panel.add(cbConexoesPendentes);

        panel.add(new JLabel("Ação:"));
        JPanel painelBotoes = new JPanel(new GridLayout(1, 2, 5, 0));
        JButton btnAceitar = new JButton("Aceitar");
        JButton btnRecusar = new JButton("Recusar");

        btnAceitar.addActionListener(e -> responderConexao(StatusConexao.ACEITA));
        btnRecusar.addActionListener(e -> responderConexao(StatusConexao.RECUSADA));

        painelBotoes.add(btnAceitar);
        painelBotoes.add(btnRecusar);
        panel.add(painelBotoes);

        panel.add(new JLabel(""));
        JButton btnRecarregar = new JButton("Recarregar Perfis");
        btnRecarregar.addActionListener(e -> atualizarDados());
        panel.add(btnRecarregar);

        return panel;
    }

    private JPanel criarPainelListagem() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Histórico de Conexões"));

        areaListagem = new JTextArea();
        areaListagem.setEditable(false);
        areaListagem.setFont(new Font("Monospaced", Font.PLAIN, 13));

        JButton btnAtualizar = new JButton("Atualizar Listagem");
        btnAtualizar.addActionListener(e -> atualizarDados());

        panel.add(new JScrollPane(areaListagem), BorderLayout.CENTER);
        panel.add(btnAtualizar, BorderLayout.SOUTH);

        return panel;
    }

    private void solicitarConexao() {
        try {
            List<Perfil> perfis = perfilService.listarTodos();
            int idxSol = cbSolicitante.getSelectedIndex();
            int idxDest = cbDestinatario.getSelectedIndex();

            if (idxSol < 0 || idxDest < 0 || perfis.size() < 2) {
                JOptionPane.showMessageDialog(this,
                        "É necessário ter pelo menos dois perfis cadastrados para solicitar conexão.",
                        "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Perfil solicitante = perfis.get(idxSol);
            Perfil destinatario = perfis.get(idxDest);

            conexaoService.solicitarConexao(solicitante.getIdPerfil(), destinatario.getIdPerfil());
            JOptionPane.showMessageDialog(this,
                    "Solicitação de conexão enviada com sucesso!",
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            atualizarDados();

        } catch (RegraNegocioException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Regra de Negócio", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void responderConexao(StatusConexao novoStatus) {
        try {
            int idx = cbConexoesPendentes.getSelectedIndex();
            if (idx < 0 || listaPendentesCache.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Não há solicitações pendentes selecionadas.",
                        "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Conexao conexaoSelecionada = listaPendentesCache.get(idx);
            conexaoService.responderConexao(conexaoSelecionada.getIdConexao(), novoStatus);

            JOptionPane.showMessageDialog(this,
                    "Solicitação marcada como " + novoStatus + "!",
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            atualizarDados();

        } catch (RegraNegocioException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Regra de Negócio", JOptionPane.WARNING_MESSAGE);
        }
    }

    private String obterNomePerfil(int idPerfil) {
        try {
            return perfilService.buscarPorId(idPerfil).getNomeApelido();
        } catch (RegraNegocioException e) {
            return "Perfil Desconhecido";
        }
    }

    /**
     * Atualiza os combos de perfis, solicitações pendentes e o histórico de conexões.
     */
    public void atualizarDados() {
        List<Perfil> perfis = perfilService.listarTodos();
        cbSolicitante.removeAllItems();
        cbDestinatario.removeAllItems();

        for (Perfil p : perfis) {
            cbSolicitante.addItem(p.getNomeApelido());
            cbDestinatario.addItem(p.getNomeApelido());
        }
        if (cbDestinatario.getItemCount() > 1) {
            cbDestinatario.setSelectedIndex(1);
        }

        cbConexoesPendentes.removeAllItems();
        listaPendentesCache.clear();
        for (Conexao c : conexaoService.listarTodas()) {
            if (c.getStatus() == StatusConexao.PENDENTE) {
                listaPendentesCache.add(c);
                cbConexoesPendentes.addItem(String.format("%s -> %s",
                        obterNomePerfil(c.getIdPerfilSolicitante()),
                        obterNomePerfil(c.getIdPerfilDestinatario())));
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== CONEXÕES REGISTRADAS ===\n");
        for (Conexao c : conexaoService.listarTodas()) {
            String nomeSol = obterNomePerfil(c.getIdPerfilSolicitante());
            String nomeDest = obterNomePerfil(c.getIdPerfilDestinatario());
            String respStr = (c.getDataResposta() == null) ? "Aguardando" : c.getDataResposta().toString();
            boolean msgLiberada = conexaoService.podeEnviarMensagem(c.getIdPerfilSolicitante(), c.getIdPerfilDestinatario());

            sb.append(String.format("De: %s -> Para: %s | Status: %s | Solicitação: %s | Resposta: %s | Chat Liberado: %s\n",
                    nomeSol, nomeDest, c.getStatus(), c.getDataSolicitacao(), respStr, msgLiberada ? "SIM" : "NÃO"));
        }

        areaListagem.setText(sb.toString());
    }
}