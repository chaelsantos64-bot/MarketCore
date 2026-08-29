package marketcore.service;

import marketcore.cliente.Cliente;
import marketcore.exception.ClienteNaoEncontradoException;
import marketcore.produto.Produto;
import marketcore.repository.ClienteRepository;

import java.util.ArrayList;
import java.util.List;


public class ClienteService {
    ClienteRepository clienteRepository = new ClienteRepository();

    public void cadastrarCliente(Cliente cliente) {
        clienteRepository.cadastrarCliente(cliente);

    }

    public Cliente buscarCliente(Long id) {
        clienteRepository.buscarCliente(id);
        return clienteRepository.buscarCliente(id);
    }

    public void excluirCliente(Long id) {
        clienteRepository.excluirCliente(id);
    }

    public Cliente atualizarCliente(Long id, String novoNome, String novoEmail) {
      Cliente clienteEncontrado = clienteRepository.buscarCliente(id);
      clienteEncontrado.setNome(novoNome);
      clienteEncontrado.setEmail(novoEmail);
      clienteRepository.atualizarCliente(clienteEncontrado);
      return clienteRepository.buscarCliente(id);
    }

    public List<Cliente> listarClientes() {
        return clienteRepository.listarTodosClientes();
    }


}
