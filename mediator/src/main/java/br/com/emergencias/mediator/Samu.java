package br.com.emergencias.mediator;

public class Samu extends UnidadeEmergencia {

    public Samu(String codigo) {
        super("SAMU 192", codigo);
    }

    @Override
    public void receberChamado(TipoOcorrencia tipo, String local) {
        if (!disponivel) {
            System.out.printf("[%s] SAMU já está atendendo. Tentando acionar próxima ambulância.%n", codigo);
            return;
        }
        System.out.printf("[%s] >>> SAMU despachado! Ocorrência: '%s' em '%s'. Equipe médica a caminho!%n",
                codigo, tipo.getDescricao(), local);
        disponivel = false;
    }

    public void finalizarAtendimento() {
        System.out.printf("[%s] Paciente estabilizado. SAMU disponível novamente.%n", codigo);
        disponivel = true;
    }
}
