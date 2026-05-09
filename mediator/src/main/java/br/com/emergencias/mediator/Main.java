package br.com.emergencias.mediator;

public class Main {

    public static void main(String[] args) {

        CentralEmergencias192 central = new CentralEmergencias192("Central SP - Zona Sul");

        Policia   pm01  = new Policia("PM-001");
        Policia   pm02  = new Policia("PM-002");
        Bombeiros cb01  = new Bombeiros("CB-001");
        Samu      samu1 = new Samu("SAMU-001");
        Samu      samu2 = new Samu("SAMU-002");

        central.registrarUnidade(pm01);
        central.registrarUnidade(pm02);
        central.registrarUnidade(cb01);
        central.registrarUnidade(samu1);
        central.registrarUnidade(samu2);

        System.out.println("\n SIMULAÇÃO DE OCORRÊNCIAS \n");

        System.out.println(" OCORRÊNCIA 1: Assalto a banco");
        central.reportarOcorrencia(TipoOcorrencia.CRIME, "Av. Paulista, 1578", null);

        System.out.println("\n OCORRÊNCIA 2: Infarto em via pública");
        central.reportarOcorrencia(TipoOcorrencia.MEDICO, "Rua Augusta, 300", null);

        System.out.println("\n OCORRÊNCIA 3: Incêndio em edifício residencial");
        central.reportarOcorrencia(TipoOcorrencia.INCENDIO, "Rua Vergueiro, 900", null);

        System.out.println("\n OCORRÊNCIA 4: Acidente grave na marginal");
        central.reportarOcorrencia(TipoOcorrencia.ACIDENTE, "Marginal Pinheiros, km 12", null);

        System.out.println("\n OCORRÊNCIA 5: PM-001 avista perseguição em andamento");
        pm01.finalizarAtendimento();
        pm01.reportarOcorrencia(TipoOcorrencia.CRIME, "Av. dos Bandeirantes, 5000");

        System.out.println();
        central.alertarTodasUnidades("Neblina densa prevista. Redobrar atenção nas vias.");

        System.out.println("\n OCORRÊNCIA 6: Deslizamento de terra após chuva");
        samu1.finalizarAtendimento();
        cb01.finalizarAtendimento();
        pm02.finalizarAtendimento();
        central.reportarOcorrencia(TipoOcorrencia.DESASTRE, "Estrada da Represa, km 8", null);

        System.out.println("\n FIM DA SIMULAÇÃO ");
    }
}
