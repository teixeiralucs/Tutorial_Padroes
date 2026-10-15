package techlearn;

import techlearn.composite.*;
import techlearn.iterator.Iterator;
import techlearn.logging.LoggerService;

public class Main {
    public static void main(String[] args) {
        LoggerService logger = LoggerService.getInstance();
        logger.log("Iniciando a montagem da estrutura de cursos...");

        Modulo cursoEngenharia = new Modulo("Engenharia de Software Moderna");

        Modulo moduloOO = new Modulo("Módulo 1: Orientação a Objetos Avançada");
        moduloOO.adicionar(new Aula("Aula 1.1: Coesão e Coplamento", 45));
        moduloOO.adicionar(new Aula("Aula 1.2: Interfaces e Polimorfismo", 60));

        Modulo moduloPadroes = new Modulo("Módulo 2: Padrões de Projeto (GoF)");
        moduloPadroes.adicionar(new Aula("Aula 2.1: Singleton na Prática", 30));
        moduloPadroes.adicionar(new Aula("Aula 2.2: Composite Hierarquico", 50));
        moduloPadroes.adicionar(new Aula("Aula 2.3 Iterator para Coleções", 40));

        cursoEngenharia.adicionar(moduloOO);
        cursoEngenharia.adicionar(moduloPadroes);

        logger.log("Estrutura montada com sucesso. Exibindo detalhes da ementa");
        cursoEngenharia.exibirDetalhes();

        System.out.println("\n==================================================");
        System.out.println("Varredura do Curso Principal via Iterator");
        System.out.println("==================================================");

        Iterator<ComponenteCatalogo> iterator = cursoEngenharia.criarIterator();
        while (iterator.hasNext()) {
            ComponenteCatalogo subItem = iterator.next();
            logger.log("Navegando no Item Raiz: " + subItem.getNome() + " | Duração acumulada "
                    + subItem.getDuracaoMinutos() + " min)");
        }

        logger.log("Execução da atividade finalizada.");

        System.out.println("\n==================================================");
        System.out.println("Aulas Rápidas (menos de 45 min.");
        System.out.println("==================================================");
        listarAulasCurtas(cursoEngenharia, 45);

    }

    // Percorre a estrutura usando apenas os métodos Composite e Iterator
    public static void listarAulasCurtas(ComponenteCatalogo componente, int limiteMinutos) {
        // Se for uma aula:
        if (componente instanceof Aula) {
            if (componente.getDuracaoMinutos() <= limiteMinutos) {
                System.out.println(
                        " -> [Aula Curta] " + componente.getNome() + " (" + componente.getDuracaoMinutos() + " min)");
            }
            return;
        }

        // Se for um módulo, usa-se o Itaretor para navegar pelos filhos sem expor a
        // lista interna
        Iterator<ComponenteCatalogo> it = componente.criarIterator();
        while (it.hasNext()) {
            ComponenteCatalogo filho = it.next();
            listarAulasCurtas(filho, limiteMinutos);
        }

    }

}
