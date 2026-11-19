package services;

import exceptions.RegraNegocioException;
import models.StatusConta;
import models.Usuario;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

/**
 * Serviço responsável pelas regras de negócio e gerenciamento de usuários.
 *
 * @author Gilberto Hideaki Matsunaga
 * @version 1.0
 * @since 2026-11
 */
public class UsuarioService {

    /** Lista em memória para armazenar os usuários cadastrados. */
    private List<Usuario> usuarios = new ArrayList<>();

    /**
     * Cadastra um novo usuário validando a idade mínima de 18 anos.
     *
     * @param email E-mail obrigatório do usuário
     * @param dataNascimento Data de nascimento do usuário
     * @param statusConta Status inicial da conta
     * @return O objeto Usuario cadastrado
     * @throws RegraNegocioException Caso o usuário seja menor de 18 anos ou a data seja nula
     */
    public Usuario cadastrarUsuario(String email, LocalDate dataNascimento, StatusConta statusConta) throws RegraNegocioException {
        if (email == null || email.trim().isEmpty()) {
            throw new RegraNegocioException("O e-mail do usuário é obrigatório.");
        }

        String emailLimpo = email.trim().toLowerCase();
        if (!emailLimpo.contains("@") || !emailLimpo.contains(".")) {
            throw new RegraNegocioException("Informe um formato de e-mail válido.");
        }

        for (Usuario u : usuarios) {
            if (u.getEmail().equalsIgnoreCase(emailLimpo)) {
                throw new RegraNegocioException("Já existe um usuário cadastrado com o e-mail: " + emailLimpo);
            }
        }

        if (dataNascimento == null) {
            throw new RegraNegocioException("A data de nascimento é obrigatória.");
        }

        int idade = Period.between(dataNascimento, LocalDate.now()).getYears();
        if (idade < 18) {
            throw new RegraNegocioException("Cadastro não permitido: o usuário deve ter pelo menos 18 anos.");
        }

        Usuario novoUsuario = new Usuario(emailLimpo, dataNascimento, statusConta);
        usuarios.add(novoUsuario);
        return novoUsuario;
    }

    /**
     * Busca um usuário cadastrado pelo seu identificador único.
     *
     * @param idUsuario ID do usuário desejado
     * @return O objeto Usuario encontrado
     * @throws RegraNegocioException Caso o usuário não seja encontrado
     */
    public Usuario buscarPorId(int idUsuario) throws RegraNegocioException {
        for (Usuario usuario : usuarios) {
            if (usuario.getIdUsuario() == idUsuario) {
                return usuario;
            }
        }
        throw new RegraNegocioException("Usuário não encontrado com o ID: " + idUsuario);
    }

    /**
     * Atualiza o status da conta de um usuário existente.
     *
     * @param idUsuario ID do usuário
     * @param novoStatus Novo status da conta
     * @throws RegraNegocioException Caso o usuário não exista
     */
    public void alterarStatusConta(int idUsuario, StatusConta novoStatus) throws RegraNegocioException {
        Usuario usuario = buscarPorId(idUsuario);
        usuario.setStatusConta(novoStatus);
    }

    /**
     * Retorna a lista de todos os usuários cadastrados.
     *
     * @return Lista de usuários
     */
    public List<Usuario> listarTodos() {
        return usuarios;
    }
}