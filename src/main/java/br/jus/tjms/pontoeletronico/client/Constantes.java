package br.jus.tjms.pontoeletronico.client;

public class Constantes {
	
	public static final String TIME_ZONE = "GMT-4";	
	
	public static final int EMPRESA_DEFAULT = 1;
	
	public static final Boolean JOBS_ATIVOS = false;	
    
	// TODO corrigir no cliente do ponto eletronico o código default de localidade correto (40207) 
	public static final int LOCALIDADE_DEFAULT_ERRO = 1; // código errado (usado em alguns clientes)
    public static final int LOCALIDADE_DEFAULT = 40207; // código certo de campo grande
    
    public static final String SENHAMASTER_DEFAULT = "3c8407db4b2475a070d09cd39a87000c";
    
    public static final int MILISSEGUNDO = 1;
    public static final int SEGUNDO = 1000 * MILISSEGUNDO;
    public static final int MINUTO = 60 * SEGUNDO;
    public static final int HORA = 60 * MINUTO;
    public static final int DIA = 24 * HORA;
    
    public static final int NDIAS_ENVIO_DIGITAIS_DEFAULT = 30;
    public static final int NDIAS_RECEPCAO_DIGITAIS_DEFAULT = 30;    
    public static final int NIVEL_TOLERANCIA_VERIFICAO_DEFAULT = 365;    
    
    //constantes usadas pela importação das catracas
    public static final String SENIOR_DS = "SeniorEmbDS";
    public static final String PONTO_ELETRONICO_EJB_DS = "PontoEletronicoEmbDS";
    
    // constantes usadas pelo cliente
    public static final int CLIENTE_LARGURA_EXIBICAO_DIGITAL = 260;
    public static final int CLIENTE_QUARTZ_DEFAULT_PRIORITY = 5;
    public static final int CLIENTE_QUARTZ_HIGH_PRIORITY = 1;
    
    public static final String CLIENTE_VERSAO = "0.9.8";
    public static final String CLIENTE_VERSAOBD = "0.9.8.1";
    public static final String CLIENTE_UPDATEINFOFILE = "updateInfo.xml";   
    
    public static void imprimeVersao () {
        System.out.println(Constantes.class.getName()+" imprimeVersao:");
        System.out.println("CLIENTE_VERSAO = "+CLIENTE_VERSAO);
        System.out.println("CLIENTE_VERSAOBD = "+CLIENTE_VERSAOBD);        
    }
    
}