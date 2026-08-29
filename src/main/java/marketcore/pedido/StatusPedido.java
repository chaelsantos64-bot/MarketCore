package marketcore.pedido;

public enum StatusPedido {
    CANCELADO(1),
    PENDENTE(2),
    PROCESSANDO(3),
    CONCLUIDO(4);

    private final int codigo;
    StatusPedido(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }


}
