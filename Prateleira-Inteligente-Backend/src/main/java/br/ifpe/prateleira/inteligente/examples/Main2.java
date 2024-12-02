package br.ifpe.prateleira.inteligente.examples;


import java.util.ArrayList;
import java.util.List;

public class Main2 {
    public static void main(String[] args) {
        PessoaDAO dao = new PessoaDAO();

        // Criar e salvar Pessoa com Endereços
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("João Silva");

        Endereco endereco1 = new Endereco();
        endereco1.setLogradouro("Rua A");
        endereco1.setCidade("Recife");
        endereco1.setPessoa(pessoa);

        Endereco endereco2 = new Endereco();
        endereco2.setLogradouro("Rua B");
        endereco2.setCidade("Olinda");
        endereco2.setPessoa(pessoa);

        pessoa.setEnderecos(new ArrayList<Endereco>());
        pessoa.getEnderecos().add(endereco1);
        pessoa.getEnderecos().add(endereco2);

        dao.salvar(pessoa);

        // Listar todas as Pessoas
        List<Pessoa> pessoas = dao.listar();
        for (Pessoa pessoa1 : pessoas) {
            System.out.println(pessoa1.getNome());
        }


        // Buscar Pessoa por ID
        Pessoa pessoaBuscada = dao.buscarPorId(1L);
        System.out.println("Pessoa encontrada: " + pessoaBuscada.getNome());
    }
}

