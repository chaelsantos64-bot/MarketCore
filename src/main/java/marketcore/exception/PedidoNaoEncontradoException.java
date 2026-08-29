package marketcore.exception;

public class PedidoNaoEncontradoException extends RuntimeException{
    public PedidoNaoEncontradoException(String mensagem){
        super(mensagem);
    }
}
