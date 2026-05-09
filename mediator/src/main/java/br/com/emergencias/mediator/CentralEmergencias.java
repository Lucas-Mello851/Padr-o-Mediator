package br.com.emergencias.mediator;

public interface CentralEmergencias {

    void registrarUnidade(UnidadeEmergencia unidade);

    void reportarOcorrencia(TipoOcorrencia tipo, String local, UnidadeEmergencia remetente);

    void alertarTodasUnidades(String mensagem);
}
