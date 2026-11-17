package services;

import exceptions.RegraNegocioException;
import models.GeneroMusical;
import models.Musica;

import java.util.ArrayList;
import java.util.List;

/**
 * Serviço responsável pelas regras de negócio e gerenciamento de músicas na plataforma Afina.
 *
 * @author Gilberto Hideaki Matsunaga
 * @version 1.0
 * @since 2026-11
 */
public class MusicaService {

    /** Lista em memória para armazenar as músicas cadastradas. */
    private List<Musica> musicas = new ArrayList<>();

    /**
     * Cadastra uma nova música validando os campos obrigatórios.
     *
     * @param titulo Nome da música
     * @param artista Artista ou banda
     * @param genero Gênero musical
     * @return O objeto Musica cadastrado
     * @throws RegraNegocioException Caso título, artista ou gênero sejam inválidos
     */
    public Musica cadastrarMusica(String titulo, String artista, GeneroMusical genero) throws RegraNegocioException {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new RegraNegocioException("O título da música é obrigatório.");
        }
        if (artista == null || artista.trim().isEmpty()) {
            throw new RegraNegocioException("O artista da música é obrigatório.");
        }
        if (genero == null) {
            throw new RegraNegocioException("O gênero musical é obrigatório.");
        }

        Musica novaMusica = new Musica(titulo, artista, genero);
        musicas.add(novaMusica);
        return novaMusica;
    }

    /**
     * Busca uma música pelo seu identificador único.
     *
     * @param idMusica ID da música desejada
     * @return O objeto Musica encontrado
     * @throws RegraNegocioException Caso a música não seja encontrada
     */
    public Musica buscarPorId(int idMusica) throws RegraNegocioException {
        for (Musica musica : musicas) {
            if (musica.getIdMusica() == idMusica) {
                return musica;
            }
        }
        throw new RegraNegocioException("Música não encontrada com o ID: " + idMusica);
    }

    /**
     * Filtra as músicas cadastradas por gênero musical para identificar afinidades.
     *
     * @param genero Gênero musical desejado
     * @return Lista de músicas do gênero informado
     */
    public List<Musica> buscarPorGenero(GeneroMusical genero) {
        List<Musica> resultado = new ArrayList<>();
        for (Musica musica : musicas) {
            if (musica.getGenero() == genero) {
                resultado.add(musica);
            }
        }
        return resultado;
    }

    /**
     * Retorna a lista de todas as músicas cadastradas.
     *
     * @return Lista de músicas
     */
    public List<Musica> listarTodas() {
        return musicas;
    }
}