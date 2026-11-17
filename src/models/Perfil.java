package models;

/**
 * Representa a identidade pública do usuário dentro da rede social Afina.
 * Utiliza o conceito de constructor overloading (sobrecarga de construtores)
 * para permitir a criação do perfil tanto de forma simplificada (apenas com
 * os dados obrigatórios) quanto de forma completa, acomodando os campos opcionais.
 *
 * @author Gilberto Hideaki Matsunaga
 * @version 1.0
 * @since 2026-11
 */
public class Perfil {

    private static int contadorId = 1;

    /** Propriedade do tipo INT - Identificador único do perfil. */
    private int idPerfil;

    /** Propriedade do tipo INT - Identificador do usuário proprietário deste perfil. */
    private int idUsuario;

    /** Propriedade do tipo String - Nome ou apelido exibido publicamente. */
    private String nomeApelido;

    /** Propriedade do tipo String - Texto curto de apresentação (opcional). */
    private String bio;

    /** Propriedade do tipo String - Cidade onde o usuário reside. */
    private String cidade;

    /** Propriedade do tipo VisibilidadePerfil - Nível de visibilidade do perfil.
     * @see VisibilidadePerfil
     */
    private VisibilidadePerfil visibilidade;

    /** Propriedade do tipo String - Endereço ou link da foto de perfil (opcional). */
    private String fotoPerfil;

    /**
     * Construtor 1 (Simplificado): Utilizado quando o usuário deseja criar o perfil
     * informando apenas os campos obrigatórios. Os campos opcionais (bio e foto_perfil)
     * são inicializados como vazios por padrão.
     *
     * @param idUsuario ID do usuário dono deste perfil
     * @param nomeApelido Nome ou apelido exibido para outros usuários
     * @param cidade Cidade onde a pessoa mora
     * @param visibilidade Nível de visibilidade do perfil
     */
    public Perfil(int idUsuario, String nomeApelido, String cidade, VisibilidadePerfil visibilidade) {
        this.idPerfil = contadorId++;
        this.idUsuario = idUsuario;
        this.nomeApelido = nomeApelido;
        this.cidade = cidade;
        this.visibilidade = visibilidade;
        this.bio = "";
        this.fotoPerfil = "";
    }

    /**
     * Construtor 2 (Completo): Utilizado quando o usuário deseja informar todos
     * os dados, incluindo os campos opcionais (bio e foto_perfil), logo no momento da criação.
     *
     * @param idUsuario ID do usuário dono deste perfil
     * @param nomeApelido Nome ou apelido exibido para outros usuários
     * @param bio Texto curto de apresentação
     * @param cidade Cidade onde a pessoa mora
     * @param visibilidade Nível de visibilidade do perfil
     * @param fotoPerfil Endereço ou link da foto de perfil
     */
    public Perfil(int idUsuario, String nomeApelido, String bio, String cidade, VisibilidadePerfil visibilidade, String fotoPerfil) {
        this.idPerfil = contadorId++;
        this.idUsuario = idUsuario;
        this.nomeApelido = nomeApelido;
        this.bio = bio;
        this.cidade = cidade;
        this.visibilidade = visibilidade;
        this.fotoPerfil = fotoPerfil;
    }

    // Getters
    public int getIdPerfil() {
        return idPerfil;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNomeApelido() {
        return nomeApelido;
    }

    public String getBio() {
        return bio;
    }

    public String getCidade() {
        return cidade;
    }

    public VisibilidadePerfil getVisibilidade() {
        return visibilidade;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    // Setters
    public void setNomeApelido(String nomeApelido) {
        this.nomeApelido = nomeApelido;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public void setVisibilidade(VisibilidadePerfil visibilidade) {
        this.visibilidade = visibilidade;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    @Override
    public String toString() {
        return "Perfil{" +
                "idPerfil=" + idPerfil +
                ", idUsuario=" + idUsuario +
                ", nomeApelido='" + nomeApelido + '\'' +
                ", bio='" + bio + '\'' +
                ", cidade='" + cidade + '\'' +
                ", visibilidade=" + visibilidade +
                ", fotoPerfil='" + fotoPerfil + '\'' +
                '}';
    }
}