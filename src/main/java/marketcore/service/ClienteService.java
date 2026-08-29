package marketcore.service;

import marketcore.cliente.Cliente;
import marketcore.exception.ClienteNaoEncontradoException;

import java.util.ArrayList;
import java.util.List;

public class ClienteService {
    private List<Cliente> clientes = new ArrayList<>();

    public Cliente cadastrarCliente(Cliente cliente) {
        clientes.add(cliente);
        return cliente;
    }

    public void listarClientes() {
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente encontrado");
        } else {
            for (Cliente cliente : clientes) {
                System.out.println(cliente);
            }
        }
    }

    public Cliente buscarCliente(Long id) {
        for (Cliente cliente : clientes) {
            if (cliente.getId().equals(id)) {
                return cliente;
            }
        }
        throw new ClienteNaoEncontradoException("ID do Cliente nao encontrado" + id);
    }


    public Cliente atualizarCliente(Long id, String novoNome, String novoEmail) {
        Cliente cliente = buscarCliente(id);
        cliente.setNome(novoNome);
        cliente.setEmail(novoEmail);
        return cliente;
    }

    public void excluirCliente(Long id) {
        Cliente cliente = buscarCliente(id);
        clientes.remove(cliente);
    }
}
