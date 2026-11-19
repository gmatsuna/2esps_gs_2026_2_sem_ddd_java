package models;

import java.time.LocalDate;

/**
 * Representa a entidade de controle e status do usuário.
 * O ID é gerado automaticamente pelo sistema.
 *
 * @author Gilberto Hideaki Matsunaga
 * @version 1.0
 * @since 2026-11
 */

public class Usuario {

    private static int contadorId = 1;

    /** Propriedade do tipo INT - Identificador único do usuário. */
    private int idUsuario;

    /** Propriedade do tipo String - E-mail de acesso do usuário. */
    private String email;

    /** Propriedade do tipo LocalDate - Data de nascimento do usuário. */
    private LocalDate dataNascimento;

    /** Propriedade do tipo LocalDate - Data em que o usuário realizou o cadastro. */
    private LocalDate dataCadastro;

    /** Propriedade do tipo StatusConta - Situação atual da conta (ATIVA, SUSPENSA, EXCLUIDA).
     * @see StatusConta
     */
    private StatusConta statusConta;

    /**
     * Construtor da classe Usuario.
     * O ID e a data de cadastro são gerados automaticamente pelo sistema.
     *
     * @param email E-mail obrigatório de cadastro do usuário
     * @param dataNascimento Data de nascimento do usuário
     * @param statusConta Situação atual da conta (ATIVA, SUSPENSA, EXCLUIDA)
     */
    public Usuario(String email, LocalDate dataNascimento, StatusConta statusConta) {
        this.idUsuario = contadorId++;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.dataCadastro = LocalDate.now();
        this.statusConta = statusConta;
    }

    // Getters
    public int getIdUsuario() {
        return idUsuario;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public StatusConta getStatusConta() {
        return statusConta;
    }

    // Setters
    public void setEmail(String email) {
        this.email = email;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public void setStatusConta(StatusConta statusConta) {
        this.statusConta = statusConta;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario=" + idUsuario +
                ", email='" + email + '\'' +
                ", dataNascimento=" + dataNascimento +
                ", dataCadastro=" + dataCadastro +
                ", statusConta=" + statusConta +
                '}';
    }
}