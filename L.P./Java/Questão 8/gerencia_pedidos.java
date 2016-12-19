import java.util.*;

class Cliente {

  String nome;
  int telefone;
  String endereco;

  //construtor
  public Cliente (String n, int t, String e){
    this.nome = n;
    this.telefone = t;
    this.endereco = e;
  }

  public String toString() {
    return "Cliente: " + this.nome + "\n" +
           "Telefone: " + this.telefone + "\n" +
           "Endenreõ: " + this.endereco + "\n";
  }
}

class Pedido {

  int numero;
  Cliente cliente;
  Date entrada;
  double preco;

  //construtor
  public Pedido (int num, Cliente cli, int year, int month, int day, double pre){

    Calendar aux = GregorianCalendar.getInstance();

    // -1 pois o ano começa em 0 pra função
    aux.set(year, (month - 1), day);

    this.numero = num;
    this.cliente = cli;
    this.entrada = aux.getTime();
    this.preco = pre;
  }

  public String toString() {
    return "Nº do Pedido: " + this.numero + "\n" +
            this.cliente.toString() +
           "Data do Pedido: " + this.entrada.toString() + "\n" +
           "Preço: " + this.preco + "\n";
  }

  public void Entrega (int year, int month, int day){};
  public Boolean VerificaPrazo () {return false;};
}

class Delivery extends Pedido {

  Date saida;
  private static final int PRAZO = 5; //prazo de entrega
  Date entrega;

  //contrutor
  public Delivery (int num, Cliente cli, int year, int month, int day, double pre){

    super (num, cli, year, month, day, (pre + (pre * 0.2)));

    Calendar aux = GregorianCalendar.getInstance();

    // dia + o PRAZO
    aux.set(year, month, (day + PRAZO));
    this.saida = aux.getTime();

    aux.set(year, month, (day-1));
    this.entrega = aux.getTime();
  }

  public void Entrega (int year, int month, int day){
    Calendar aux = GregorianCalendar.getInstance();

    aux.set(year, month, day);

    this.entrega = aux.getTime();
  }

  public Boolean VerificaPrazo (){

    if (this.entrega.compareTo(this.saida) <= 0) {
      return true;
    }
    else {
      return false;
    }
  }

  public String toString () {
    return super.toString() +
          "Prazo de entrega: " + this.saida.toString() + "\n" +
          "Data da entrega: " + this.entrega.toString();
  }
}

class Vendas {

  private Pedido pedidos[];
  private int num_pedido;

  public Vendas (int tam){
    pedidos = new Pedido[tam];
    num_pedido = 0;
  }

  public void CriaPedido (Pedido p){
    pedidos[num_pedido] = p;
    num_pedido++;
  }

  public void ImprimePedidos () {

    for (int i = 0; i < num_pedido; i++){

      System.out.println (pedidos[i].toString());
      if (pedidos[i].VerificaPrazo() == true){
        System.out.println ("O pedido foi entregue no prazo!\n");
      }
    }
  }

  public void AtualizaEntrega (int num, int year, int month, int day){

    for (int i = 0; i < num_pedido; i++)

      if (num == pedidos[i].numero){

        pedidos[i].Entrega(year, month, day);
        i = num_pedido;
      }
  }
}

public class gerencia_pedidos {
  public static void main (String[] args) {

    Vendas inventario = new Vendas (50);
    Cliente cl;
    Delivery d;
    Pedido p;

    cl = new Cliente ("Fulano", 9999, "Morador da rua A");

    p = new Pedido (1, cl, 2016, Calendar.DECEMBER, 10, 500);

    inventario.CriaPedido (p);

    cl = new Cliente ("Ciclano", 88888, "Morador da rua B");

    d = new Delivery (2, cl, 2016, Calendar.DECEMBER, 10, 500);

    inventario.CriaPedido (d);

    inventario.AtualizaEntrega (2, 2016, Calendar.DECEMBER, 12);

    inventario.ImprimePedidos();

  }
}
