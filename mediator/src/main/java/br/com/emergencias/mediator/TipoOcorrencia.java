package br.com.emergencias.mediator;

public enum TipoOcorrencia {

    INCENDIO("Incêndio"),

    ACIDENTE("Acidente de Trânsito"),

    CRIME("Crime / Segurança Pública"),

    MEDICO("Emergência Médica"),

    DESASTRE("Desastre Natural");

    private final String descricao;

    TipoOcorrencia(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
