package services;

import exceptions.RegraNegocioException;
import models.Comunidade;

import java.util.ArrayList;
import java.util.List;

/**
 * Serviço responsável pelas regras de negócio e gerenciamento de comunidades.
 *
 * @author Gilberto Hideaki Matsunaga
 * @version 1.0
 * @since 2026-11
 */
public class ComunidadeService {

    /** Lista em memória para armazenar as comunidades criadas. */
    private List<Comunidade> comunidades = new ArrayList<>();

    /**
     * Referência ao serviço de perfis para validar os membros que entram nas comunidades.
     * @see PerfilService
     */
    private PerfilService perfilService;

    /**
     * Construtor que recebe o PerfilService para validar os membros da comunidade.
     *
     * @param perfilService Serviço de perfis
     * @see PerfilService
     */
    public ComunidadeService(PerfilService perfilService) {
        this.perfilService = perfilService;
    }

    /**
     * Cria uma nova comunidade sem descrição inicial.
     *
     * @param nomeComunidade Nome da comunidade
     * @return O objeto Comunidade criado
     * @throws RegraNegocioException Caso o nome seja inválido ou já exista uma comunidade com o mesmo nome
     */
    public Comunidade criarComunidade(String nomeComunidade) throws RegraNegocioException {
        return criarComunidade(nomeComunidade, null);
    }

    /**
     * Cria uma nova comunidade com nome e descrição.
     *
     * @param nomeComunidade Nome da comunidade
     * @param descricao Descrição da comunidade (opcional)
     * @return O objeto Comunidade criado
     * @throws RegraNegocioException Caso o nome seja inválido ou já exista uma comunidade com o mesmo nome
     */
    public Comunidade criarComunidade(String nomeComunidade, String descricao) throws RegraNegocioException {
        if (nomeComunidade == null || nomeComunidade.trim().isEmpty()) {
            throw new RegraNegocioException("O nome da comunidade é obrigatório.");
        }

        for (Comunidade c : comunidades) {
            if (c.getNomeComunidade().equalsIgnoreCase(nomeComunidade.trim())) {
                throw new RegraNegocioException("Já existe uma comunidade cadastrada com o nome: " + nomeComunidade);
            }
        }

        Comunidade novaComunidade;
        if (descricao == null || descricao.trim().isEmpty()) {
            novaComunidade = new Comunidade(nomeComunidade);
        } else {
            novaComunidade = new Comunidade(nomeComunidade, descricao);
        }

        comunidades.add(novaComunidade);
        return novaComunidade;
    }

    /**
     * Busca uma comunidade pelo seu identificador único.
     *
     * @param idComunidade ID da comunidade desejada
     * @return O objeto Comunidade encontrado
     * @throws RegraNegocioException Caso a comunidade não seja encontrada
     */
    public Comunidade buscarPorId(int idComunidade) throws RegraNegocioException {
        for (Comunidade comunidade : comunidades) {
            if (comunidade.getIdComunidade() == idComunidade) {
                return comunidade;
            }
        }
        throw new RegraNegocioException("Comunidade não encontrada com o ID: " + idComunidade);
    }

    /**
     * Registra a entrada de um perfil em uma comunidade, incrementando o total de membros.
     *
     * @param idComunidade ID da comunidade
     * @param idPerfil ID do perfil que deseja participar
     * @throws RegraNegocioException Caso a comunidade ou o perfil não existam
     */
    public void entrarNaComunidade(int idComunidade, int idPerfil) throws RegraNegocioException {
        Comunidade comunidade = buscarPorId(idComunidade);
        perfilService.buscarPorId(idPerfil);

        comunidade.setTotalMembros(comunidade.getTotalMembros() + 1);
    }

    /**
     * Retorna a lista de todas as comunidades cadastradas.
     *
     * @return Lista de comunidades
     */
    public List<Comunidade> listarTodas() {
        return comunidades;
    }
}