package services;

import exceptions.RegraNegocioException;
import models.Perfil;
import models.VisibilidadePerfil;

import java.util.ArrayList;
import java.util.List;

/**
 * Serviço responsável pelas regras de negócio e gerenciamento de perfis públicos na plataforma Afina.
 *
 * @author Gilberto Hideaki Matsunaga
 * @version 1.0
 * @since 2026-11
 */
public class PerfilService {

    /** Lista em memória para armazenar os perfis cadastrados. */
    private List<Perfil> perfis = new ArrayList<>();

    /** Referência ao serviço de usuários para validar a existência da conta vinculada. */
    private UsuarioService usuarioService;

    /**
     * Construtor que recebe o UsuarioService para validar a integridade entre Usuário e Perfil (1:1).
     *
     * @param usuarioService Serviço de usuários
     */
    public PerfilService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Cria um novo perfil validando se o usuário existe, se já não possui perfil e se o nome foi informado.
     *
     * @param idUsuario ID do usuário dono do perfil
     * @param nomeApelido Nome ou apelido exibido na rede
     * @param cidade Cidade do usuário
     * @param visibilidade Nível de privacidade do perfil
     * @return O objeto Perfil criado
     * @throws RegraNegocioException Caso o usuário não exista, já tenha perfil ou o nome seja inválido
     */
    public Perfil criarPerfil(int idUsuario, String nomeApelido, String cidade, VisibilidadePerfil visibilidade) throws RegraNegocioException {
        // Valida se o usuário dono existe (lança exceção se não existir)
        usuarioService.buscarPorId(idUsuario);

        if (nomeApelido == null || nomeApelido.trim().isEmpty()) {
            throw new RegraNegocioException("O nome ou apelido do perfil é obrigatório.");
        }

        // Garante o relacionamento 1:1 (um usuário não pode ter dois perfis)
        for (Perfil p : perfis) {
            if (p.getIdUsuario() == idUsuario) {
                throw new RegraNegocioException("Este usuário já possui um perfil cadastrado.");
            }
        }

        Perfil novoPerfil = new Perfil(idUsuario, nomeApelido, cidade, visibilidade);
        perfis.add(novoPerfil);
        return novoPerfil;
    }

    /**
     * Busca um perfil pelo seu identificador único.
     *
     * @param idPerfil ID do perfil desejado
     * @return O objeto Perfil encontrado
     * @throws RegraNegocioException Caso o perfil não seja encontrado
     */
    public Perfil buscarPorId(int idPerfil) throws RegraNegocioException {
        for (Perfil perfil : perfis) {
            if (perfil.getIdPerfil() == idPerfil) {
                return perfil;
            }
        }
        throw new RegraNegocioException("Perfil não encontrado com o ID: " + idPerfil);
    }

    /**
     * Verifica se um perfil pode ser visualizado com base nas regras de visibilidade (LGPD).
     *
     * @param idPerfilAlvo ID do perfil que se deseja visualizar
     * @param saoConexoesAceitas Indica se já existe conexão aceita entre os perfis
     * @return true se a visualização for permitida, false caso contrário
     * @throws RegraNegocioException Caso o perfil alvo não exista
     */
    public boolean podeVisualizarPerfil(int idPerfilAlvo, boolean saoConexoesAceitas) throws RegraNegocioException {
        Perfil perfil = buscarPorId(idPerfilAlvo);

        if (perfil.getVisibilidade() == VisibilidadePerfil.PUBLICO) {
            return true;
        }
        if (perfil.getVisibilidade() == VisibilidadePerfil.SO_CONEXOES && saoConexoesAceitas) {
            return true;
        }
        return false;
    }

    /**
     * Retorna a lista de todos os perfis cadastrados.
     *
     * @return Lista de perfis
     */
    public List<Perfil> listarTodos() {
        return perfis;
    }
}