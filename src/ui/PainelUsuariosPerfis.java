package ui;

import exceptions.RegraNegocioException;
import models.Perfil;
import models.StatusConta;
import models.Usuario;
import models.VisibilidadePerfil;
import services.PerfilService;
import services.UsuarioService;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Painel gráfico responsável pelo cadastro e listagem de Usuários e Perfis no Afina.
 *
 * @author Gilberto Hideaki Matsunaga
 * @version 1.0
 * @since 2026-11
 */
public class PainelUsuariosPerfis extends JPanel {

    private final UsuarioService usuarioService;
    private final PerfilService perfilService;

    private JTextField txtEmail;
    private JTextField txtDataNascimento;
    private JComboBox<StatusConta> cbStatusConta;

    private JComboBox<String> cbUsuarioConta;
    private JTextField txtNomeApelido;
    private JTextField txtCidade;
    private JTextField txtBio;
    private JComboBox<VisibilidadePerfil> cbVisibilidade;

    private JTextArea areaListagem;

    /**
     * Construtor que recebe os serviços de Usuário e Perfil e monta os componentes visuais.
     *
     * @param usuarioService Serviço de gerenciamento de usuários
     * @param perfilService Serviço de gerenciamento de perfis
     */
    public PainelUsuariosPerfis(UsuarioService usuarioService, PerfilService perfilService) {
        this.usuarioService = usuarioService;
        this.perfilService = perfilService;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel painelFormularios = new JPanel(new GridLayout(1, 2, 15, 0));
        painelFormularios.add(criarPainelUsuario());
        painelFormularios.add(criarPainelPerfil());

        add(painelFormularios, BorderLayout.NORTH);
        add(criarPainelListagem(), BorderLayout.CENTER);
    }

    private JPanel criarPainelUsuario() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 8, 8));
        panel.setBorder(BorderFactory.createTitledBorder("1. Cadastrar Usuário (Conta)"));

        panel.add(new JLabel("E-mail (Obrigatório):"));
        txtEmail = new JTextField();
        panel.add(txtEmail);

        panel.add(new JLabel("Data Nasc. (AAAA-MM-DD):"));
        txtDataNascimento = new JTextField(LocalDate.now().toString());
        panel.add(txtDataNascimento);

        panel.add(new JLabel("Status Inicial:"));
        cbStatusConta = new JComboBox<>(StatusConta.values());
        panel.add(cbStatusConta);

        panel.add(new JLabel(""));
        JButton btnCadastrarUsuario = new JButton("Cadastrar Usuário");
        btnCadastrarUsuario.addActionListener(e -> cadastrarUsuario());
        panel.add(btnCadastrarUsuario);

        return panel;
    }

    private JPanel criarPainelPerfil() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 8, 8));
        panel.setBorder(BorderFactory.createTitledBorder("2. Criar Perfil Público"));

        panel.add(new JLabel("Conta de Usuário:"));
        cbUsuarioConta = new JComboBox<>();
        panel.add(cbUsuarioConta);

        panel.add(new JLabel("Nome / Apelido:"));
        txtNomeApelido = new JTextField();
        panel.add(txtNomeApelido);

        panel.add(new JLabel("Cidade:"));
        txtCidade = new JTextField();
        panel.add(txtCidade);

        panel.add(new JLabel("Bio (Opcional):"));
        txtBio = new JTextField();
        panel.add(txtBio);

        panel.add(new JLabel("Visibilidade:"));
        cbVisibilidade = new JComboBox<>(VisibilidadePerfil.values());
        panel.add(cbVisibilidade);

        panel.add(new JLabel(""));
        JButton btnCriarPerfil = new JButton("Criar Perfil");
        btnCriarPerfil.addActionListener(e -> criarPerfil());
        panel.add(btnCriarPerfil);

        return panel;
    }

    private JPanel criarPainelListagem() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Registros Cadastrados"));

        areaListagem = new JTextArea();
        areaListagem.setEditable(false);
        areaListagem.setFont(new Font("Monospaced", Font.PLAIN, 13));

        JButton btnAtualizar = new JButton("Atualizar Listagem");
        btnAtualizar.addActionListener(e -> atualizarListagem());

        panel.add(new JScrollPane(areaListagem), BorderLayout.CENTER);
        panel.add(btnAtualizar, BorderLayout.SOUTH);

        return panel;
    }

    private void cadastrarUsuario() {
        try {
            String email = txtEmail.getText().trim();
            LocalDate dataNasc = LocalDate.parse(txtDataNascimento.getText().trim());
            StatusConta status = (StatusConta) cbStatusConta.getSelectedItem();

            usuarioService.cadastrarUsuario(email, dataNasc, status);
            JOptionPane.showMessageDialog(this,
                    "Usuário cadastrado com sucesso!",
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            txtEmail.setText("");
            atualizarComboUsuarios();
            cbUsuarioConta.setSelectedIndex(cbUsuarioConta.getItemCount() - 1);
            atualizarListagem();

        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this,
                    "Formato de data inválido. Use AAAA-MM-DD (ex: 1998-08-20).",
                    "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (RegraNegocioException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Regra de Negócio", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void criarPerfil() {
        try {
            int indiceSelecionado = cbUsuarioConta.getSelectedIndex();
            if (indiceSelecionado < 0) {
                JOptionPane.showMessageDialog(this,
                        "Cadastre ou selecione uma conta de usuário primeiro.",
                        "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Usuario usuarioSelecionado = usuarioService.listarTodos().get(indiceSelecionado);
            String nome = txtNomeApelido.getText().trim();
            String cidade = txtCidade.getText().trim();
            String bio = txtBio.getText().trim();
            VisibilidadePerfil visibilidade = (VisibilidadePerfil) cbVisibilidade.getSelectedItem();

            Perfil novoPerfil = perfilService.criarPerfil(
                    usuarioSelecionado.getIdUsuario(), nome, cidade, visibilidade);
            if (!bio.isEmpty()) {
                novoPerfil.setBio(bio);
            }

            JOptionPane.showMessageDialog(this,
                    "Perfil criado com sucesso!",
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            txtNomeApelido.setText("");
            txtCidade.setText("");
            txtBio.setText("");
            atualizarListagem();

        } catch (RegraNegocioException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Regra de Negócio", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void atualizarComboUsuarios() {
        cbUsuarioConta.removeAllItems();
        List<Usuario> usuarios = usuarioService.listarTodos();
        for (int i = 0; i < usuarios.size(); i++) {
            Usuario u = usuarios.get(i);
            cbUsuarioConta.addItem(String.format("Conta #%d (%s)", i + 1, u.getEmail()));
        }
    }

    /**
     * Atualiza a área de texto com todos os usuários e perfis cadastrados na sessão.
     */
    public void atualizarListagem() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== USUÁRIOS CADASTRADOS ===\n");
        for (Usuario u : usuarioService.listarTodos()) {
            sb.append(String.format("E-mail: %s | Nascimento: %s | Cadastro: %s | Status: %s\n",
                    u.getEmail(), u.getDataNascimento(), u.getDataCadastro(), u.getStatusConta()));
        }

        sb.append("\n=== PERFIS PÚBLICOS ===\n");
        for (Perfil p : perfilService.listarTodos()) {
            sb.append(String.format("Nome: %s | Cidade: %s | Visibilidade: %s | Bio: %s\n",
                    p.getNomeApelido(), p.getCidade(), p.getVisibilidade(), p.getBio()));
        }

        areaListagem.setText(sb.toString());
    }
}