package br.com.emergencias.mediator;

public class Policia extends UnidadeEmergencia {

    public Policia(String codigo) {
        super("Polícia Militar", codigo);
    }

    @Override
    public void receberChamado(TipoOcorrencia tipo, String local) {
        if (!disponivel) {
            System.out.printf("[%s] Polícia já está em atendimento. Aguardando próxima unidade.%n", codigo);
            return;
        }
        System.out.printf("[%s] >>> Polícia a caminho! Ocorrência: '%s' em '%s'. Código 10-4!%n",
                codigo, tipo.getDescricao(), local);
        disponivel = false;
    }

    public void finalizarAtendimento() {
        System.out.printf("[%s] Ocorrência encerrada. Polícia disponível novamente.%n", codigo);
        disponivel = true;
    }
}
