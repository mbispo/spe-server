package br.jus.tjms.pontoeletronico.util;

public class CarregaMensagens {

	public static void carregar() throws Exception {

		try {
            // a carga de arquivos de mensagens e excecoes deve ser automatica, por hora estamos carregando na mao...
/*			System.out.println("CarregaMensagens.carregar(): tentando carregar mensagens...");
			Messages.addResourceBundle(ResourceBundle.getBundle("br.gov.ms.tj.ddsi.exceptions"));
			Messages.addResourceBundle(ResourceBundle.getBundle("br.gov.ms.tj.ddsi.organizacional"));*/
        } catch (Exception e) {
            throw new Exception("Impossivel carregar arquivos de mensagem: "+e.getMessage());
        }
		
	}

}
