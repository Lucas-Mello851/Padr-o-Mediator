package br.com.emergencias.mediator;

public class Bombeiros extends UnidadeEmergencia {

    public Bombeiros(String codigo) {
        super("Corpo de Bombeiros", codigo);
    }

    @Override
    public void receberChamado(TipoOcorrencia tipo, String local) {
        if (!disponivel) {
            System.out.printf("[%s] Bombeiros já estão em atendimento. Central notificada.%n", codigo);
            return;
        }
        System.out.printf("[%s] Bombeiros mobilizados! Ocorrência: '%s' em '%s'. Sirenes a postos!%n",
                codigo, tipo.getDescricao(), local);
        disponivel = false;
    }

    public void finalizarAtendimento() {
        System.out.printf("[%s] Ocorrência controlada. Bombeiros disponíveis novamente.%n", codigo);
        disponivel = true;
    }
}
