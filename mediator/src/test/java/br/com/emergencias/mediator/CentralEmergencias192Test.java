package br.com.emergencias.mediator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes - Padrão Mediator: Central de Emergências")
class CentralEmergencias192Test {

    private CentralEmergencias192 central;
    private Policia   pm01, pm02;
    private Bombeiros cb01;
    private Samu      samu01, samu02;

    @BeforeEach
    void setUp() {
        central = new CentralEmergencias192("Central Teste");
        pm01    = new Policia("PM-001");
        pm02    = new Policia("PM-002");
        cb01    = new Bombeiros("CB-001");
        samu01  = new Samu("SAMU-001");
        samu02  = new Samu("SAMU-002");

        central.registrarUnidade(pm01);
        central.registrarUnidade(pm02);
        central.registrarUnidade(cb01);
        central.registrarUnidade(samu01);
        central.registrarUnidade(samu02);
    }

    @Test
    @DisplayName("Deve registrar unidades corretamente na central")
    void deveRegistrarUnidadesNaCentral() {
        assertEquals(2, central.getUnidadesPolicia().size());
        assertEquals(1, central.getUnidadesBombeiros().size());
        assertEquals(2, central.getUnidadesSamu().size());
    }

    @Test
    @DisplayName("Registro deve associar a central à unidade")
    void deveAssociarCentralAoRegistrar() {
        Policia pm99 = new Policia("PM-099");
        assertDoesNotThrow(() -> pm99.reportarOcorrencia(TipoOcorrencia.CRIME, "Local X"));
    }

    @Test
    @DisplayName("Ocorrência CRIME deve tornar uma viatura de polícia indisponível")
    void crimeDeveAcionarPolicia() {
        assertTrue(pm01.isDisponivel());
        central.reportarOcorrencia(TipoOcorrencia.CRIME, "Rua A", null);
        assertFalse(pm01.isDisponivel());
        assertTrue(pm02.isDisponivel());
    }

    @Test
    @DisplayName("Ocorrência MEDICO deve tornar uma ambulância indisponível")
    void medicoDeveAcionarSamu() {
        assertTrue(samu01.isDisponivel());
        central.reportarOcorrencia(TipoOcorrencia.MEDICO, "Rua B", null);
        assertFalse(samu01.isDisponivel());
        assertTrue(samu02.isDisponivel());
    }

    @Test
    @DisplayName("Ocorrência INCENDIO deve acionar Bombeiros e SAMU")
    void incendioDeveAcionarBombeirosESamu() {
        central.reportarOcorrencia(TipoOcorrencia.INCENDIO, "Rua C", null);
        assertFalse(cb01.isDisponivel(),   "Bombeiros devem estar ocupados");
        assertFalse(samu01.isDisponivel(), "SAMU deve estar ocupado");
    }

    @Test
    @DisplayName("Ocorrência ACIDENTE deve acionar Polícia e SAMU")
    void acidenteDeveAcionarPoliciaESamu() {
        central.reportarOcorrencia(TipoOcorrencia.ACIDENTE, "Rodovia X", null);
        assertFalse(pm01.isDisponivel(),   "Polícia deve estar ocupada");
        assertFalse(samu01.isDisponivel(), "SAMU deve estar ocupado");
    }

    @Test
    @DisplayName("Ocorrência DESASTRE deve acionar todas as unidades disponíveis")
    void desastreDeveAcionarTodasUnidades() {
        central.reportarOcorrencia(TipoOcorrencia.DESASTRE, "Morro Z", null);
        assertFalse(pm01.isDisponivel());
        assertFalse(cb01.isDisponivel());
        assertFalse(samu01.isDisponivel());
        assertTrue(pm02.isDisponivel());
        assertTrue(samu02.isDisponivel());
    }

    @Test
    @DisplayName("Com PM-001 ocupada, próximo crime deve ser atendido por PM-002")
    void deveUsarSegundaViaturaQuandoPrimeiraOcupada() {
        central.reportarOcorrencia(TipoOcorrencia.CRIME, "Local 1", null);
        central.reportarOcorrencia(TipoOcorrencia.CRIME, "Local 2", null);
        assertFalse(pm01.isDisponivel());
        assertFalse(pm02.isDisponivel());
    }

    @Test
    @DisplayName("Finalizar atendimento deve liberar a unidade novamente")
    void finalizarAtendimentoDeveDisponibilizarUnidade() {
        central.reportarOcorrencia(TipoOcorrencia.CRIME, "Local 1", null);
        assertFalse(pm01.isDisponivel());
        pm01.finalizarAtendimento();
        assertTrue(pm01.isDisponivel());
    }

    @Test
    @DisplayName("Enum TipoOcorrencia deve retornar descrição correta")
    void tipoOcorrenciaDeveRetornarDescricao() {
        assertEquals("Incêndio",             TipoOcorrencia.INCENDIO.getDescricao());
        assertEquals("Acidente de Trânsito", TipoOcorrencia.ACIDENTE.getDescricao());
        assertEquals("Crime / Segurança Pública", TipoOcorrencia.CRIME.getDescricao());
        assertEquals("Emergência Médica",    TipoOcorrencia.MEDICO.getDescricao());
        assertEquals("Desastre Natural",     TipoOcorrencia.DESASTRE.getDescricao());
    }

    @Test
    @DisplayName("Deve retornar identificador correto da central")
    void deveRetornarIdentificadorDaCentral() {
        assertEquals("Central Teste", central.getIdentificador());
    }

    @Test
    @DisplayName("toString da unidade deve conter nome e código")
    void toStringDaUnidadeDeveConterNomeECodigo() {
        String str = pm01.toString();
        assertTrue(str.contains("PM-001"));
        assertTrue(str.contains("Polícia Militar"));
    }
}
