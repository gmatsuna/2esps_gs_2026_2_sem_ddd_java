package models;

/**
 * Representa uma obra musical dentro da plataforma Afina.
 * Utiliza o conceito de constructor overloading para suporte a campos opcionais.
 *
 * @author Gilberto Hideaki Matsunaga
 * @version 1.0
 * @since 2026-11
 */

public class Musica {

    private static int contadorId = 1;

    /** Propriedade do tipo INT - Identificador único da música. */
    private int idMusica;

    /** Propriedade do tipo String - Título da música. */
    private String titulo;

    /** Propriedade do tipo String - Artista ou banda intérprete. */
    private String artista;

    /** Propriedade do tipo GeneroMusical - Gênero musical da obra.
     * @see GeneroMusical
     */
    private GeneroMusical genero;

    /** Propriedade do tipo String - Nome do álbum de lançamento (opcional). */
    private String album;

    /** Propriedade do tipo Integer - Ano de lançamento da obra (opcional). */
    private Integer anoLancamento;

    /** Propriedade do tipo String - Link de streaming externo (opcional). */
    private String linkExterno;

    /**
     * Construtor 1 (Simplificado): Utilizado para cadastrar a música informando
     * apenas os campos obrigatórios. Os opcionais iniciam vazias.
     *
     * @param titulo Título da música
     * @param artista Artista ou banda intérprete
     * @param genero Gênero musical (enum GeneroMusical)
     */
    public Musica(String titulo, String artista, GeneroMusical genero) {
        this.idMusica = contadorId++;
        this.titulo = titulo;
        this.artista = artista;
        this.genero = genero;
        this.album = "";
        this.anoLancamento = null;
        this.linkExterno = "";
    }

    /**
     * Construtor 2 (Completo): Utilizado para cadastrar a música informando
     * todos os dados, incluindo os campos opcionais.
     *
     * @param titulo Título da música
     * @param artista Artista ou banda intérprete
     * @param genero Gênero musical (enum GeneroMusical)
     * @param album Nome do álbum de lançamento
     * @param ano_lancamento Ano de lançamento da obra
     * @param link_externo Link para ouvir em serviço de streaming
     */
    public Musica(String titulo, String artista, GeneroMusical genero, String album, Integer ano_lancamento, String link_externo) {
        this.idMusica = contadorId++;
        this.titulo = titulo;
        this.artista = artista;
        this.genero = genero;
        this.album = album;
        this.anoLancamento = ano_lancamento;
        this.linkExterno = link_externo;
    }

    // Getters
    public int getIdMusica() {
        return idMusica;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getArtista() {
        return artista;
    }

    public GeneroMusical getGenero() {
        return genero;
    }

    public String getAlbum() {
        return album;
    }

    public Integer getAnoLancamento() {
        return anoLancamento;
    }

    public String getLinkExterno() {
        return linkExterno;
    }

    // Setters
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public void setGenero(GeneroMusical genero) {
        this.genero = genero;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setAnoLancamento(Integer anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    public void setLinkExterno(String linkExterno) {
        this.linkExterno = linkExterno;
    }

    @Override
    public String toString() {
        return "Musica{" +
                "idMusica=" + idMusica +
                ", titulo='" + titulo + '\'' +
                ", artista='" + artista + '\'' +
                ", genero=" + genero +
                ", album='" + album + '\'' +
                ", anoLancamento=" + anoLancamento +
                ", linkExterno='" + linkExterno + '\'' +
                '}';
    }
}
