package ui;

import exceptions.RegraNegocioException;
import models.Comunidade;
import models.Perfil;
import services.ComunidadeService;
import services.PerfilService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Painel gráfico responsável pela criação, ingresso de membros e listagem de Comunidades no Afina.
 *
 * @author Gilberto Hideaki Matsunaga
 * @version 1.0
 * @since 2026-11
 */
public class PainelComunidades extends JPanel {

    private final ComunidadeService comunidadeService;
    private final PerfilService perfilService;

    private JTextField txtNomeComunidade;
    private JTextField txtDescricao;

    private JComboBox<String> cbComunidade;
    private JComboBox<String> cbPerfil;

    private JTextArea areaListagem;

    /**
     * Construtor que recebe os serviços de Comunidade e Perfil e monta os componentes visuais.
     *
     * @param comunidadeService Serviço de gerenciamento de comunidades
     * @param perfilService Serviço de gerenciamento de perfis
     * @see ComunidadeService
     * @see PerfilService
     */
    public PainelComunidades(ComunidadeService comunidadeService, PerfilService perfilService) {
        this.comunidadeService = comunidadeService;
        this.perfilService = perfilService;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel painelSuperior = new JPanel(new GridLayout(1, 2, 15, 0));
        painelSuperior.add(criarPainelCriacao());
        painelSuperior.add(criarPainelIngresso());

        add(painelSuperior, BorderLayout.NORTH);
        add(criarPainelListagem(), BorderLayout.CENTER);
    }

    private JPanel criarPainelCriacao() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 8, 8));
        panel.setBorder(BorderFactory.createTitledBorder("1. Criar Nova Comunidade"));

        panel.add(new JLabel("Nome (Obrigatório):"));
        txtNomeComunidade = new JTextField();
        panel.add(txtNomeComunidade);

        panel.add(new JLabel("Descrição (Opcional):"));
        txtDescricao = new JTextField();
        panel.add(txtDescricao);

        panel.add(new JLabel(""));
        JButton btnCriar = new JButton("Criar Comunidade");
        btnCriar.addActionListener(e -> criarComunidade());
        panel.add(btnCriar);

        return panel;
    }

    private JPanel criarPainelIngresso() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 8, 8));
        panel.setBorder(BorderFactory.createTitledBorder("2. Ingressar em Comunidade"));

        panel.add(new JLabel("Comunidade:"));
        cbComunidade = new JComboBox<>();
        panel.add(cbComunidade);

        panel.add(new JLabel("Perfil Participante:"));
        cbPerfil = new JComboBox<>();
        panel.add(cbPerfil);

        panel.add(new JLabel(""));
        JButton btnEntrar = new JButton("Entrar na Comunidade");
        btnEntrar.addActionListener(e -> entrarNaComunidade());
        panel.add(btnEntrar);

        return panel;
    }

    private JPanel criarPainelListagem() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Comunidades Cadastradas"));

        areaListagem = new JTextArea();
        areaListagem.setEditable(false);
        areaListagem.setFont(new Font("Monospaced", Font.PLAIN, 13));

        JButton btnAtualizar = new JButton("Atualizar Listagem");
        btnAtualizar.addActionListener(e -> atualizarDados());

        panel.add(new JScrollPane(areaListagem), BorderLayout.CENTER);
        panel.add(btnAtualizar, BorderLayout.SOUTH);

        return panel;
    }

    private void criarComunidade() {
        try {
            String nome = txtNomeComunidade.getText().trim();
            String descricao = txtDescricao.getText().trim();

            if (descricao.isEmpty()) {
                comunidadeService.criarComunidade(nome);
            } else {
                comunidadeService.criarComunidade(nome, descricao);
            }

            JOptionPane.showMessageDialog(this,
                    "Comunidade criada com sucesso!",
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            txtNomeComunidade.setText("");
            txtDescricao.setText("");
            atualizarDados();

        } catch (RegraNegocioException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Regra de Negócio", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void entrarNaComunidade() {
        try {
            List<Comunidade> comunidades = comunidadeService.listarTodas();
            List<Perfil> perfis = perfilService.listarTodos();

            int idxCom = cbComunidade.getSelectedIndex();
            int idxPerf = cbPerfil.getSelectedIndex();

            if (idxCom < 0 || comunidades.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Crie pelo menos uma comunidade primeiro.",
                        "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (idxPerf < 0 || perfis.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Cadastre pelo menos um perfil primeiro.",
                        "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Comunidade comunidade = comunidades.get(idxCom);
            Perfil perfil = perfis.get(idxPerf);

            comunidadeService.entrarNaComunidade(comunidade.getIdComunidade(), perfil.getIdPerfil());

            JOptionPane.showMessageDialog(this,
                    String.format("%s entrou na comunidade '%s'!", perfil.getNomeApelido(), comunidade.getNomeComunidade()),
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            atualizarDados();

        } catch (RegraNegocioException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Regra de Negócio", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Atualiza os combos de comunidades e perfis e exibe a listagem atualizada.
     */
    public void atualizarDados() {
        List<Comunidade> comunidades = comunidadeService.listarTodas();
        cbComunidade.removeAllItems();
        for (Comunidade c : comunidades) {
            cbComunidade.addItem(c.getNomeComunidade());
        }

        List<Perfil> perfis = perfilService.listarTodos();
        cbPerfil.removeAllItems();
        for (Perfil p : perfis) {
            cbPerfil.addItem(p.getNomeApelido());
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== COMUNIDADES DO AFINA ===\n");
        for (Comunidade c : comunidades) {
            String descStr = (c.getDescricao() == null || c.getDescricao().isEmpty()) ? "Sem descrição" : c.getDescricao();
            sb.append(String.format("Comunidade: %s | Membros: %d | Criada em: %s\n   Descrição: %s\n\n",
                    c.getNomeComunidade(), c.getTotalMembros(), c.getDataCriacao(), descStr));
        }

        areaListagem.setText(sb.toString());
    }
}