package br.com.emergencias.mediator;

public abstract class UnidadeEmergencia {

    protected final String nome;
    protected final String codigo;
    protected CentralEmergencias central;
    protected boolean disponivel;

    protected UnidadeEmergencia(String nome, String codigo) {
        this.nome = nome;
        this.codigo = codigo;
        this.disponivel = true;
    }

    public void setCentral(CentralEmergencias central) {
        this.central = central;
    }

    public abstract void receberChamado(TipoOcorrencia tipo, String local);

    public void reportarOcorrencia(TipoOcorrencia tipo, String local) {
        if (central != null) {
            System.out.printf("[%s] reportando ocorrência '%s' em '%s' para a central.%n",
                    codigo, tipo.getDescricao(), local);
            central.reportarOcorrencia(tipo, local, this);
        } else {
            System.out.printf("[%s] Sem central registrada! Impossível reportar.%n", codigo);
        }
    }

    public String getNome() {
        return nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    @Override
    public String toString() {
        return String.format("%s [%s] - %s", nome, codigo, disponivel ? "Disponível" : "Em ocorrência");
    }
}
