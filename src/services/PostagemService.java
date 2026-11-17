package services;

import exceptions.RegraNegocioException;
import models.Postagem;

import java.util.ArrayList;
import java.util.List;

/**
 * Serviço responsável pelas regras de negócio e gerenciamento de postagens.
 *
 * @author Gilberto Hideaki Matsunaga
 * @version 1.0
 * @since 2026-11
 */
public class PostagemService {

    /** Lista em memória para armazenar as postagens publicadas. */
    private List<Postagem> postagens = new ArrayList<>();

    /** Referência ao serviço de perfis para validar o autor da postagem.
     * @see PerfilService
     */
    private PerfilService perfilService;

    /** Referência ao serviço de músicas para validar a música compartilhada.
     * @see MusicaService
     * */
    private MusicaService musicaService;

    /**
     * Construtor que recebe PerfilService e MusicaService para validar integridade da postagem.
     *
     * @param perfilService Serviço de perfis
     * @param musicaService Serviço de músicas
     * @see PerfilService
     * @see MusicaService
     */
    public PostagemService(PerfilService perfilService, MusicaService musicaService) {
        this.perfilService = perfilService;
        this.musicaService = musicaService;
    }

    /**
     * Publica uma nova postagem sem imagem anexada.
     *
     * @param idPerfil ID do perfil autor
     * @param idMusica ID da música compartilhada
     * @param conteudo Texto da postagem
     * @return O objeto Postagem criado
     * @throws RegraNegocioException Caso o perfil ou a música não existam, ou o conteúdo esteja vazio
     */
    public Postagem publicar(int idPerfil, int idMusica, String conteudo) throws RegraNegocioException {
        return publicar(idPerfil, idMusica, conteudo, null);
    }

    /**
     * Publica uma nova postagem com opção de imagem anexada.
     *
     * @param idPerfil ID do perfil autor
     * @param idMusica ID da música compartilhada
     * @param conteudo Texto da postagem
     * @param imagem Caminho ou URL da imagem (opcional)
     * @return O objeto Postagem criado
     * @throws RegraNegocioException Caso o perfil ou a música não existam, ou o conteúdo esteja vazio
     */
    public Postagem publicar(int idPerfil, int idMusica, String conteudo, String imagem) throws RegraNegocioException {
        perfilService.buscarPorId(idPerfil);
        musicaService.buscarPorId(idMusica);

        if (conteudo == null || conteudo.trim().isEmpty()) {
            throw new RegraNegocioException("O conteúdo da postagem não pode estar vazio.");
        }

        Postagem novaPostagem;
        if (imagem == null || imagem.trim().isEmpty()) {
            novaPostagem = new Postagem(idPerfil, idMusica, conteudo);
        } else {
            novaPostagem = new Postagem(idPerfil, idMusica, conteudo, imagem);
        }

        postagens.add(novaPostagem);
        return novaPostagem;
    }

    /**
     * Lista todas as postagens publicadas por um determinado perfil.
     *
     * @param idPerfil ID do perfil autor
     * @return Lista de postagens do perfil
     */
    public List<Postagem> listarPorPerfil(int idPerfil) {
        List<Postagem> resultado = new ArrayList<>();
        for (Postagem postagem : postagens) {
            if (postagem.getIdPerfil() == idPerfil) {
                resultado.add(postagem);
            }
        }
        return resultado;
    }

    /**
     * Retorna todas as postagens cadastradas.
     *
     * @return Lista de postagens
     */
    public List<Postagem> listarTodas() {
        return postagens;
    }
}