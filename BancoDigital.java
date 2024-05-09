import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// Classe principal para simular o funcionamento do banco
public class BancoDigital {
    private Map<String, Conta> contas;
    private Scanner scanner;

    public BancoDigital() {
        contas = new HashMap<>();
        scanner = new Scanner(System.in);
    }

    // Método para criar uma nova conta corrente
    public void criarContaCorrente(String numeroConta, double taxaManutencao) {
        if (!contas.containsKey(numeroConta)) {
            ContaCorrente novaConta = new ContaCorrente(numeroConta, taxaManutencao);
            contas.put(numeroConta, novaConta);
            System.out.println("Conta corrente criada com sucesso: " + numeroConta);
        } else {
            System.out.println("Já existe uma conta com o número fornecido.");
        }
    }

    // Método para criar uma nova conta poupança
    public void criarContaPoupanca(String numeroConta, double taxaRendimento) {
        if (!contas.containsKey(numeroConta)) {
            ContaPoupanca novaConta = new ContaPoupanca(numeroConta, taxaRendimento);
            contas.put(numeroConta, novaConta);
            System.out.println("Conta poupança criada com sucesso: " + numeroConta);
        } else {
            System.out.println("Já existe uma conta com o número fornecido.");
        }
    }

    // Método para realizar um depósito em uma conta
    public void depositar(String numeroConta, double valor) {
        Conta conta = contas.get(numeroConta);
        if (conta != null) {
            conta.depositar(valor);
            System.out.println("Depósito de " + valor + " realizado com sucesso na conta " + numeroConta);
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    // Método para realizar um saque em uma conta
    public void sacar(String numeroConta, double valor) {
        Conta conta = contas.get(numeroConta);
        if (conta != null) {
            conta.sacar(valor);
            System.out.println("Saque de " + valor + " realizado com sucesso na conta " + numeroConta);
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    // Método para realizar uma transferência entre contas
    public void transferir(String numeroContaOrigem, String numeroContaDestino, double valor) {
        Conta contaOrigem = contas.get(numeroContaOrigem);
        Conta contaDestino = contas.get(numeroContaDestino);
        if (contaOrigem != null && contaDestino != null) {
            contaOrigem.transferir(contaDestino, valor);
            System.out.println("Transferência de " + valor + " realizada com sucesso da conta " + numeroContaOrigem + " para a conta " + numeroContaDestino);
        } else {
            System.out.println("Uma das contas não foi encontrada.");
        }
    }

    // Método para exibir o saldo de uma conta
    public void exibirSaldo(String numeroConta) {
        Conta conta = contas.get(numeroConta);
        if (conta != null) {
            System.out.println("Saldo da conta " + numeroConta + ": " + conta.getSaldo());
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    // Método para iniciar a interação com o usuário através do terminal
    public void iniciarTerminal() {
        System.out.println("Bem-vindo ao Banco Digital!");
        System.out.println("Para ver os comandos disponíveis, digite 'ajuda'.");
        System.out.println("Para sair, digite 'sair'.");
        System.out.println();

        while (true) {
            System.out.print("Digite um comando: ");
            String comando = scanner.nextLine();

            if (comando.equals("sair")) {
                System.out.println("Obrigado por utilizar o Banco Digital. Até mais!");
                break;
            }

            processarComando(comando);
        }

        scanner.close();
    }

    // Método para processar os comandos inseridos pelo usuário
    private void processarComando(String comando) {
        String[] partes = comando.split(" ");
        String operacao = partes[0];

        switch (operacao) {
            case "ajuda":
                exibirAjuda();
                break;
            case "criar_conta_corrente":
                if (partes.length == 3) {
                    criarContaCorrente(partes[1], Double.parseDouble(partes[2]));
                } else {
                    System.out.println("Formato inválido. Use: criar_conta_corrente <numero_conta> <taxa_manutencao>");
                }
                break;
            case "criar_conta_poupanca":
                if (partes.length == 3) {
                    criarContaPoupanca(partes[1], Double.parseDouble(partes[2]));
                } else {
                    System.out.println("Formato inválido. Use: criar_conta_poupanca <numero_conta> <taxa_rendimento>");
                }
                break;
            case "depositar":
                if (partes.length == 3) {
                    depositar(partes[1], Double.parseDouble(partes[2]));
                } else {
                    System.out.println("Formato inválido. Use: depositar <numero_conta> <valor>");
                }
                break;
            case "sacar":
                if (partes.length == 3) {
                    sacar(partes[1], Double.parseDouble(partes[2]));
                } else {
                    System.out.println("Formato inválido. Use: sacar <numero_conta> <valor>");
                }
                break;
            case "transferir":
                if (partes.length == 4) {
                    transferir(partes[1], partes[2], Double.parseDouble(partes[3]));
                } else {
                    System.out.println("Formato inválido. Use: transferir <conta_origem> <conta_destino> <valor>");
                }
                break;
            case "saldo":
                if (partes.length == 2) {
                    exibirSaldo(partes[1]);
                } else {
                    System.out.println("Formato inválido. Use: saldo <numero_conta>");
                }
                break;
            default:
                System.out.println("Comando desconhecido. Digite 'ajuda' para ver os comandos disponíveis.");
        }
    }

    // Método para exibir os comandos disponíveis
    private void exibirAjuda() {
        System.out.println("Comandos disponíveis:");
        System.out.println("  - criar_conta_corrente <numero_conta> <taxa_manutencao>");
        System.out.println("  - criar_conta_poupanca <numero_conta> <taxa_rendimento>");
        System.out.println("  - depositar <numero_conta> <valor>");
        System.out.println("  - sacar <numero_conta> <valor>");
        System.out.println("  - transferir <conta_origem> <conta_destino> <valor>");
        System.out.println("  - saldo <numero_conta>");
        System.out.println("  - sair");
    }

    public static void main(String[] args) {
        BancoDigital banco = new BancoDigital();
        banco.iniciarTerminal();
    }
}

// Classe abstrata para representar uma conta genérica
abstract class Conta {
    private String numeroConta;
    private double saldo;

    public Conta(String numeroConta) {
        this.numeroConta = numeroConta;
        this.saldo = 0.0;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public double getSaldo() {
        return saldo;
    }

    public void depositar(double valor) {
        saldo += valor;
    }

    public abstract void sacar(double valor);

    public abstract void transferir(Conta destino, double valor);
}

// Classe para representar uma conta corrente
class ContaCorrente extends Conta {
    private double taxaManutencao;

    public ContaCorrente(String numeroConta, double taxaManutencao) {
        super(numeroConta);
        this.taxaManutencao = taxaManutencao;
    }

    @Override
    public void sacar(double valor) {
        // Implementação do saque para conta corrente
        if (valor <= getSaldo()) {
            double novoSaldo = getSaldo() - valor - taxaManutencao;
            if (novoSaldo >= 0) {
                depositar(-taxaManutencao);
                depositar(-valor);
            } else {
                System.out.println("Saldo insuficiente para efetuar o saque.");
            }
        } else {
            System.out.println("Saldo insuficiente para efetuar o saque.");
        }
    }

    @Override
    public void transferir(Conta destino, double valor) {
        // Implementação da transferência para conta corrente
        if (valor <= getSaldo()) {
            double novoSaldo = getSaldo() - valor - taxaManutencao;
            if (novoSaldo >= 0) {
                depositar(-taxaManutencao);
                depositar(-valor);
                destino.depositar(valor);
            } else {
                System.out.println("Saldo insuficiente para efetuar a transferência.");
            }
        } else {
            System.out.println("Saldo insuficiente para efetuar a transferência.");
        }
    }
}

// Classe para representar uma conta poupança
class ContaPoupanca extends Conta {
    private double taxaRendimento;

    public ContaPoupanca(String numeroConta, double taxaRendimento) {
        super(numeroConta);
        this.taxaRendimento = taxaRendimento;
    }

    @Override
    public void sacar(double valor) {
        // Implementação do saque para conta poupança
        if (valor <= getSaldo()) {
            depositar(-valor);
        } else {
            System.out.println("Saldo insuficiente para efetuar o saque.");
        }
    }

    @Override
    public void transferir(Conta destino, double valor) {
        // Implementação da transferência para conta poupança
        if (valor <= getSaldo()) {
            depositar(-valor);
            destino.depositar(valor);
        } else {
            System.out.println("Saldo insuficiente para efetuar a transferência.");
        }
    }
    public double getTaxaRendimento() {
            return taxaRendimento;
         }
    
         public void setTaxaRendimento(double taxaRendimento) {
             this.taxaRendimento = taxaRendimento;
        }
}
