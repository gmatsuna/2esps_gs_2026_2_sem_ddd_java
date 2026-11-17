package exceptions;

/**
 * Exceção personalizada lançada quando uma regra de negócio da plataforma Afina é violada.
 *
 * @author Gilberto Hideaki Matsunaga
 * @version 1.0
 * @since 2026-11
 */
public class RegraNegocioException extends Exception {

    /**
     * Construtor da exceção com mensagem descritiva do erro de negócio.
     *
     * @param mensagem Descrição clara da regra violada
     */
    public RegraNegocioException(String mensagem) {
        super(mensagem);
    }
}