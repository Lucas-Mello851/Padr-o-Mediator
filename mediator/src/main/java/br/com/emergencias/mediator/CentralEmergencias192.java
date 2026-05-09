package br.com.emergencias.mediator;

import java.util.ArrayList;
import java.util.List;

public class CentralEmergencias192 implements CentralEmergencias {

    private final String identificador;
    private final List<Policia>    unidadesPolicia   = new ArrayList<>();
    private final List<Bombeiros>  unidadesBombeiros = new ArrayList<>();
    private final List<Samu>       unidadesSamu      = new ArrayList<>();

    public CentralEmergencias192(String identificador) {
        this.identificador = identificador;
        System.out.printf(" Central de Emergências '%s' inicializada. \n%n", identificador);
    }

    @Override
    public void registrarUnidade(UnidadeEmergencia unidade) {
        unidade.setCentral(this);
        if (unidade instanceof Policia) {
            unidadesPolicia.add((Policia) unidade);
        } else if (unidade instanceof Bombeiros) {
            unidadesBombeiros.add((Bombeiros) unidade);
        } else if (unidade instanceof Samu) {
            unidadesSamu.add((Samu) unidade);
        }
        System.out.printf("[CENTRAL] Unidade registrada: %s%n", unidade);
    }

    @Override
    public void reportarOcorrencia(TipoOcorrencia tipo, String local, UnidadeEmergencia remetente) {
        System.out.printf("%n[CENTRAL] Ocorrência recebida — Tipo: '%s' | Local: '%s'%n",
                tipo.getDescricao(), local);

        switch (tipo) {
            case CRIME:
                despacharPolicia(tipo, local);
                break;

            case MEDICO:
                despacharSamu(tipo, local);
                break;

            case INCENDIO:
                despacharBombeiros(tipo, local);
                despacharSamu(tipo, local);
                break;

            case ACIDENTE:
                despacharPolicia(tipo, local);
                despacharSamu(tipo, local);
                break;

            case DESASTRE:
                System.out.println("[CENTRAL] ALERTA MÁXIMO — Acionando todas as unidades!");
                despacharPolicia(tipo, local);
                despacharBombeiros(tipo, local);
                despacharSamu(tipo, local);
                break;

            default:
                System.out.println("[CENTRAL] Tipo de ocorrência desconhecido. Investigando...");
        }
    }

    @Override
    public void alertarTodasUnidades(String mensagem) {
        System.out.printf("%n[CENTRAL] ALERTA GERAL: %s%n", mensagem);
        List<UnidadeEmergencia> todas = new ArrayList<>();
        todas.addAll(unidadesPolicia);
        todas.addAll(unidadesBombeiros);
        todas.addAll(unidadesSamu);
        for (UnidadeEmergencia u : todas) {
            System.out.printf("  >> [%s] Mensagem recebida.%n", u.getCodigo());
        }
    }

    private void despacharPolicia(TipoOcorrencia tipo, String local) {
        Policia disponivel = unidadesPolicia.stream()
                .filter(UnidadeEmergencia::isDisponivel)
                .findFirst()
                .orElse(null);
        if (disponivel != null) {
            disponivel.receberChamado(tipo, local);
        } else {
            System.out.println("[CENTRAL] Todas as viaturas da Polícia estão ocupadas!");
        }
    }

    private void despacharBombeiros(TipoOcorrencia tipo, String local) {
        Bombeiros disponivel = unidadesBombeiros.stream()
                .filter(UnidadeEmergencia::isDisponivel)
                .findFirst()
                .orElse(null);
        if (disponivel != null) {
            disponivel.receberChamado(tipo, local);
        } else {
            System.out.println("[CENTRAL] Todas as viaturas dos Bombeiros estão ocupadas!");
        }
    }

    private void despacharSamu(TipoOcorrencia tipo, String local) {
        Samu disponivel = unidadesSamu.stream()
                .filter(UnidadeEmergencia::isDisponivel)
                .findFirst()
                .orElse(null);
        if (disponivel != null) {
            disponivel.receberChamado(tipo, local);
        } else {
            System.out.println("[CENTRAL] Todas as ambulâncias do SAMU estão ocupadas!");
        }
    }

    public List<Policia>   getUnidadesPolicia()   { return unidadesPolicia; }
    public List<Bombeiros> getUnidadesBombeiros() { return unidadesBombeiros; }
    public List<Samu>      getUnidadesSamu()      { return unidadesSamu; }
    public String          getIdentificador()     { return identificador; }
}
