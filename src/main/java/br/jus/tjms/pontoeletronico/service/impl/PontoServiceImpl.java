package br.jus.tjms.pontoeletronico.service.impl;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;

import br.jus.tjms.comuns.exceptions.ServiceException;
import br.jus.tjms.pontoeletronico.bean.FuncionarioEjb;
import br.jus.tjms.pontoeletronico.bean.HoraPonto;
import br.jus.tjms.pontoeletronico.bean.HoraPontoOriginal;
import br.jus.tjms.pontoeletronico.bean.Ponto;
import br.jus.tjms.pontoeletronico.bean.PontoDia;
import br.jus.tjms.pontoeletronico.bean.PontoEjb;
import br.jus.tjms.pontoeletronico.bean.PontoMes;
import br.jus.tjms.pontoeletronico.bean.PontoMesId;
import br.jus.tjms.pontoeletronico.client.Constantes;
import br.jus.tjms.pontoeletronico.factory.CalendarFactory;
import br.jus.tjms.pontoeletronico.service.FuncionarioServiceRemoto;
import br.jus.tjms.pontoeletronico.service.PontoServiceRemoto;
import br.jus.tjms.pontoeletronico.util.ComparatorPonto;

@Stateless(name = "pontoService")
@Path("ponto")
@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
@LocalBean
public class PontoServiceImpl implements PontoServiceRemoto, Serializable {

    private static final long serialVersionUID = 1L;

    @PersistenceContext(unitName = "PontoEletronicoEmbDS")
    private EntityManager em;

    @Inject
    private Logger log;
    
    @Inject
    private FuncionarioServiceRemoto funcionarioService;


    @GET
    @Path("buscar/empresa/{empresa}/matricula/{matricula}/mes/{mes}/ano/{ano}")
    public PontoMes buscarPontoMes(@PathParam("empresa") int empresa,
	    			   @PathParam("matricula") int matricula,
                                   @PathParam("mes") int mes, 
                                   @PathParam("ano") int ano) throws ServiceException {
        PontoMes pontoMes = null;
        try {
            pontoMes = em.find(PontoMes.class, new PontoMesId(empresa, matricula, mes, ano));
            /*
            Query q = em.createQuery("SELECT p FROM PontoMes p JOIN FETCH p.pontoDiaCollection pd where p.codigoEmpresa = :empresa " +
                    "and p.matricula = :matricula and p.ano = :ano and p.mes  = :mes");
            q.setParameter("empresa",empresa);
            q.setParameter("matricula",matricula);
            q.setParameter("mes",mes);
            q.setParameter("ano",ano);

            pontoMes = (PontoMes) q.getSingleResult();
            */

        } catch (Exception e) {
            log.error("Erro em PontoServiceImpl.buscarPorId(): " + e.getMessage(), e);
            throw new ServiceException(e);
        }
        /**IMPRIMIR ANTES DE MANDAR*/

        System.out.print(pontoMes);

        return pontoMes;
    }

    @Override
    @POST
    @Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Path("processar")
    public boolean processarPonto(List<PontoEjb> pontos) {

        int dia, mes, ano;
        PontoEjb pontoEjb = null, pontoEjbAnterior;
        PontoDia pontoDia = null;
        FuncionarioEjb f = null;

        Iterator<PontoEjb> iterator;
        String funcionariosListaDePonto;

        PontoMes pontoMes = null;
        
        // Marcos, 13/10/2021: ignorar pontos
        /*
            SPE-1005
            Restringir a importa????o dos registros de ponto at?? o dia 17/10/2021.
         */        
        //pontos = limparLista(pontos);
        
        if (pontos.size() == 0) {
            log.info("Pontos processados com sucesso !!");
            return true;
        }
        
        // Ordena lista de Pontos por matricula, empresa, ano, mes, dia, hora
        ordenaListaPonto(pontos);
        
        guardarPontos(pontos);
        
        //imprimeListaPonto(pontos);

        //	Identifica os funcionarios que estao na lista de pontos. Monta-se essa
        //	lista numa String. Essa String sao as matriculas separadas por virgula.
        // 	ex(123, 234, 889, ...)
        funcionariosListaDePonto = identificaFuncionarios(pontos);

        //Buscar funcionarios do SGP
        List<FuncionarioEjb> funcionarios = funcionarioService.buscarFuncionarios(funcionariosListaDePonto);

        //seta um iterador para lista de pontos que sera percorrida
        iterator = pontos.iterator();

        //Fazer itera????o na lista de Pontos recebida do cliente(SPE)
        while (iterator.hasNext()) {

            //guardar o objeto pontoEjb antes de avan??ar para o pr??ximo ponto da lista
            pontoEjbAnterior = pontoEjb;

            //pr??ximo ponto da lista
            pontoEjb = iterator.next();

            //obtem dia da data do Ponto
            dia = obtemDia(pontoEjb.getDataHora());

            //obtem mes da data do Ponto
            mes = obtemMes(pontoEjb.getDataHora());

            //obtem ano da data do Ponto
            ano = obtemAno(pontoEjb.getDataHora());

            //----------------------------------------------------------------
            //Invoca a funcao compare para verificar se os objetos
            //pontoEjbAnterior e pontoEjb possuem o mesmo
            //funcionario, empresa, ano e mes. Caso nao possuem, gravar o
            //pontoMes j?? processado e buscar o novo ponto mes.
            //---------------------------------------------------------------
            if (!compare(pontoEjbAnterior, pontoEjb)) {

                //-----------------------------------------------------------
                //grava os pontos de um funcionario em um determinado mes/ano
                //caso pontoMes nao seja null
                //-----------------------------------------------------------
                if (pontoMes != null) {
                    try {

            			
                        /*
                    	System.out.println("-----------------------------------------");
            			System.out.println("Horarios do M??s Depois do Processamento");
            			System.out.println("-----------------------------------------");
            			imprimeHorariosDoMes(pontoMes.getPontoDiaCollection());
            			*/

                        salvarPontoMes(pontoMes);
                    } catch (Exception e) {
                        String msg = "Erro executando PontoEjbServiceRemoteImpl.processarPonto(): " + e.getMessage();
                        log.error(msg, e);
                        throw new RuntimeException(msg);
                    }
                }

                //variavel f recebe o funcionario do objeto pontoEjb
                f = pontoEjb.getFuncionarioEjb();

                //busca a nova informa????o do Ponto Mes do funcion??rio.
                //Parametro mes ?? somado com +1 pois a API JAVA considera
                //os meses comecando com zero
                pontoMes = buscarPontoMes(f.getEmpresa(), f.getId(), (mes + 1), ano);
	            
                /*
                System.out.println("---------------------------");
    			System.out.println("Horarios do M??s Antes");
    			System.out.println("----------------------------");
    			imprimeHorariosDoMes(pontoMes.getPontoDiaCollection());
				*/
                
                //se o funcion??rio ainda nao tem dados para o mes/ano,
                //gerar o mes e os dias deste mes(pontoMes, pontoDia) para o mesmo.
                if (pontoMes == null) {
                    pontoMes = geraGradeDeDias(f, mes, ano);
                }
            }// fim if(compare..)

            //busca um pontoDia passando o dia como par??metro
            pontoDia = buscarPontoDia(dia, pontoMes);

            if (pontoDia != null) {

                //Configura informa????es do Ponto Dia do funcion??rio como lota????o, cargo, provimento
                //e a localidade onde se registrou o ponto.
                configuraPontoDia(f, pontoDia, pontoEjb, funcionarios);

                //configura Horario do Ponto caso o horario nao exista ainda para o dia.
                if (!existePontoNoDia(pontoEjb, pontoDia)) {
                    configuraHorario(pontoEjb, pontoDia);
                }

                //configura Horario do Ponto Original caso o horario nao exista ainda para o dia.
                if (!existePontoOriginalNoDia(pontoEjb, pontoDia)) {
                    configuraHorarioOriginal(pontoEjb, pontoDia);
                }
            }
        }//fim while(iterator.hasNext())

        if (pontoMes != null) {
            try {


            	/*
            	System.out.println("---------------------------");
    			System.out.println("Horarios do M??s Depois Processamento");
    			System.out.println("----------------------------");
    			imprimeHorariosDoMes(pontoMes.getPontoDiaCollection());
    			*/

                salvarPontoMes(pontoMes);
            } catch (Exception e) {
                String msg = "Erro executando processarPonto(): " + e.getMessage();
                log.error(msg, e);
                throw new RuntimeException(msg);
            }
        }

        log.info("Pontos processados com sucesso !!");
        return true;

    }
    
    /*
        SPE-1005
        Restringir a importa????o dos registros de ponto at?? o dia 17/10/2021.
     */
	private List<PontoEjb> limparLista(List<PontoEjb> pontos) {
	    List<PontoEjb> pontosRetorno = new ArrayList<PontoEjb>();
	    int dia, mes, ano;
	    List<Integer> matriculas = Arrays.asList(4248, 11099, 6570, 9925, 10279, 6223, 18058, 10484, 16046, 11293, 5169, 10402);
	    for (PontoEjb p : pontos) {
	        
            dia = obtemDia(p.getDataHora());
            mes = obtemMes(p.getDataHora());
            ano = obtemAno(p.getDataHora());

	        if ((dia>17&&mes==10&&ano==2021)||(mes>10&&ano>=2021)||(p.getFuncionarioEjb()!=null&&matriculas.contains(p.getFuncionarioEjb().getMatricula()))) {
	            pontosRetorno.add(p);
	        } else {
	            log.info("Ignorando Ponto: "+p.toString());
	        }
	        
        }
        return pontosRetorno;
    }

    private void guardarPontos(List<PontoEjb> pontos) throws ServiceException {
	    
	    try {
            log.info("PontoServiceImpl.guardarPontos()");
    	    for (PontoEjb pontoEjb : pontos) {
                
    	        Ponto p = new Ponto(pontoEjb.getDataHora(), pontoEjb.getDataHoraLocal(), pontoEjb.getLocalidade(), pontoEjb.getFuncionarioEjb().getEmpresa(), pontoEjb.getFuncionarioEjb().getMatricula(), pontoEjb.getTimeZone(), pontoEjb.getHorarioVerao());
                
                p.setProcessado(true);
                
                p = em.merge(p);
                
                log.info("Ponto guardado: "+p.toString());
                        
            }
	    } catch (Exception e) {
            e.printStackTrace();
            String msg = "PontoServiceImpl.guardarPontos(): Falha: "+e.getMessage();
            log.error(msg, e);
            throw new ServiceException(e);
        }
    }

    @SuppressWarnings("unused")
	private void imprimeHorariosDoMes(Collection<PontoDia> listaDias) {

		for (PontoDia dia : listaDias) {

			Collection<HoraPonto> horas = dia.getHoraPontoCollection();

			// se tem horas no dia
			if ((horas != null)) {
				System.out.println(" ");
				System.out.println(dia.getDia());
				System.out.println("-------");

				for (HoraPonto horario : horas) {
					System.out.println(horario.getHoraEntrada() + "--- " + horario.getHoraSaida());
				}
			}

		}

	} 

    @POST
    @Path("salvar")
    public void salvarPontoMes(@FormParam("pontoMes") PontoMes pontoMes) throws ServiceException {
        try {
        	log.info("PontoServiceImpl.salvarPontoMes(): Registrar ponto no SGP do funcionario: "+ pontoMes.getMatricula());
            em.merge(pontoMes);
        } catch (Exception e) {
        	e.printStackTrace();
        	String msg = "PontoServiceImpl.salvarPontoMes(): Falha ao registrar ponto no SGP do funcionario "+ pontoMes.getMatricula()+": "+e.getMessage();
            log.error(msg, e);
            throw new ServiceException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private void ordenaListaPonto(List<PontoEjb> pontos){
        ComparatorPonto comparator = new ComparatorPonto();
        Collections.sort(pontos, comparator);
    }


    @SuppressWarnings("unused")
	private void imprimeListaPonto(List<PontoEjb> pontos){

        String msgMudou="";

        Iterator<PontoEjb> iterator;

        iterator = pontos.iterator();

        while(iterator.hasNext()){

            PontoEjb ponto = iterator.next();

            System.out.println(ponto.getFuncionarioEjb().getId() + "  " +
                    ponto.getFuncionarioEjb().getEmpresa() + "  " +
                    ponto.getDataHora().toString() + " "+ msgMudou);
        }
    }

    //----------------------------------------------------------------------
    //M??todo que retorna uma String contendo os Id??s dos funcionarios que
    //vieram na lista de ponto enviados pelo cliente.
    //----------------------------------------------------------------------
    private String identificaFuncionarios(List<PontoEjb> pontos) {

        int id , idAnterior=-999999;
        Iterator<PontoEjb> iterator;
        PontoEjb pontoEjb;
        String funcionarios = "";
        String sep = "";

        iterator = pontos.iterator();

        while (iterator.hasNext()) {

            pontoEjb = iterator.next();
            id = pontoEjb.getFuncionarioEjb().getId();

            if (id != idAnterior) {
                funcionarios = funcionarios + sep + String.valueOf(id);
                sep = ", ";
            }

            idAnterior = id;
        }

        return funcionarios;
    }

    //----------------------------------------------------------------------
    //Obtem o dia de uma data
    //----------------------------------------------------------------------
    private int obtemDia(Date data){
        int dia;

        // cria o calendario
        Calendar c = CalendarFactory.getCalendar();

        // define a data no calendario
        c.setTime(data);

        // retorna o campo m??s
        dia = c.get(Calendar.DAY_OF_MONTH);

        return dia;
    }


    //----------------------------------------------------------------------
    //Obtem o mes de uma data
    //----------------------------------------------------------------------
    private int obtemMes(Date data){

        int mes;

        // cria o calendario
        Calendar c = CalendarFactory.getCalendar();

        // define a data no calendario
        c.setTime(data);

        // retorna o campo m??s
        mes = c.get(Calendar.MONTH);

        return mes;
    }


    //----------------------------------------------------------------------
    //obtem o ano de uma data
    //----------------------------------------------------------------------
    private int obtemAno(Date data){

        int ano;

        // cria o calendario
        Calendar c = CalendarFactory.getCalendar();

        // define a data no calendario
        c.setTime(data);

        // retorna o campo m??s
        ano = c.get(Calendar.YEAR);

        return ano;
    }

    //----------------------------------------------------------------------
    //Retorna o dia da semana. Segunda, Terca, ....., Domingo
    //----------------------------------------------------------------------
    private int obtemDiaDaSemana(int dia, int mes, int ano){

        Calendar  c = CalendarFactory.getCalendar();
        c.set(Calendar.DAY_OF_MONTH, dia);
        c.set(Calendar.MONTH, (mes));
        c.set(Calendar.YEAR, (ano));

        return c.get(Calendar.DAY_OF_WEEK);

    }


    //----------------------------------------------------------------------
    //Calcula o numero de dias no mes passado como parametro
    //----------------------------------------------------------------------
    private int obtemNumeroDiasNoMes(int mes, int ano){

        Calendar c = CalendarFactory.getCalendar();
        c.set(Calendar.MONTH,(mes));
        c.set(Calendar.YEAR,ano);

        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    //----------------------------------------------------------------------
    //Cria a lista de Dias para o mes/ano passado como parametro.
    //----------------------------------------------------------------------
    private PontoMes geraGradeDeDias(FuncionarioEjb f, int mes, int ano){

        List<PontoDia> listaPontoDia = new ArrayList<PontoDia>();

        PontoDia pontoDia = null;
        int numDiasNoMes = 0, diaDaSemana;

        //obtem o numero de dias que tem o mes
        numDiasNoMes = obtemNumeroDiasNoMes(mes,ano);

        //Cria um novo ponto do Mes
        PontoMes pontoMes = new PontoMes(f.getEmpresa(), f.getId(), (mes + 1), ano);

        //la??o para criar os dias do mes. Verificar se o dia nao ?? um s??bado ou domingo
        for (int i = 1; i <= numDiasNoMes; i++) {

            //obtem o dia da semana(seg, ter, .., dom) que cai o dia/mes/ano
            diaDaSemana = obtemDiaDaSemana(i, mes, ano);

            //se o dia da semana est?? entre segunda e sexta, criar o dia
            if (Arrays.binarySearch(new int[] {2,3,4,5,6}, diaDaSemana) >= 0) {

                //cria um Ponto Dia e adiciona na lista de dias
                pontoDia = new PontoDia(f.getEmpresa(), f.getId(), i, (mes+1),ano);
                
                pontoDia.setPontoMes(pontoMes);
                listaPontoDia.add(pontoDia);
            }// end if
        }//end for

        pontoMes.setPontoDiaCollection(listaPontoDia);
        
        return pontoMes;
    }

    //----------------------------------------------------------------------
    //Busca um PontoDia na lista de dias de um pontoMes.
    //----------------------------------------------------------------------
    private PontoDia buscarPontoDia(int dia, PontoMes pontoMes){
        for (PontoDia pontoDia : pontoMes.getPontoDiaCollection()) {
            if (pontoDia.getDia() == dia) {
                return pontoDia;
            }
        }

        //nao encontrou um dia, verificar se pode existir

        //obtem o dia da semana(seg, ter, .., dom) que cai o dia/mes/ano
        //int diaDaSemana = obtemDiaDaSemana(dia, this.pontoMes.getMes()-1, this.pontoMes.getAno());

        //se o dia da semana est?? entre segunda e sexta, criar o dia
        //	if (Arrays.binarySearch(new int[] {2,3,4,5,6}, diaDaSemana) >= 0) {

        //cria um Ponto Dia e adiciona na lista de dias
        PontoDia pontoDia = new PontoDia(pontoMes.getCodigoEmpresa(),
                pontoMes.getMatricula(),
                dia,
                pontoMes.getMes() ,
                pontoMes.getAno());

         
        pontoDia.setPontoMes(pontoMes);
        pontoMes.getPontoDiaCollection().add(pontoDia);
        return pontoDia;
        //	}// fim if(Arrays)
        //	return  null;
    }

    //----------------------------------------------------------------------
    //M??todo para setar a lota????o, provimento e cargo do funcionario e o muni-
    //c??pio onde foi registrado o ponto no dia.
    //----------------------------------------------------------------------
    private void configuraPontoDia(FuncionarioEjb funcionarioEjb, PontoDia pontoDia,
                                   PontoEjb pontoEjb, List<FuncionarioEjb> listaFuncionarios){

        FuncionarioEjb funcionario;

        //informa a localidade do municipio onde se registrou o ponto
        //if (pontoDia.getCodigoMunicipio() == null) {

        // TODO corrigir no cliente do ponto o envio do c??digo de localide default correto...
        if (pontoEjb.getLocalidade() == Constantes.LOCALIDADE_DEFAULT_ERRO) {
            pontoDia.setCodigoMunicipio(Constantes.LOCALIDADE_DEFAULT);
            log.info("PontoEjbServiceRemoteImpl.configuraPontoDia(): c??digo de localidade errado --> "+pontoEjb.getLocalidade()+", alterando para "+Constantes.LOCALIDADE_DEFAULT);
        } else {
            pontoDia.setCodigoMunicipio(pontoEjb.getLocalidade());
        }
        //}

        //busca um funcionario da lista retornado pelo SGP
        funcionario = localizaFuncionario(funcionarioEjb.getId(), listaFuncionarios);

        if (funcionario != null) {
            pontoDia.setCodigoProvimento(funcionario.getCodigoTipoProvimento());
            pontoDia.setCodigoNomeCargo(funcionario.getCodigoNomeCargo());
            pontoDia.setCodigoReduzidoOrganograma(funcionario.getCodigoReduzido());
        }

		/* Busca informacoes da view v_fncDia para obter informacoes
		 * do cargo, lotacao e provimento
		*/

        //NomeacaoExoneracao nomeacaoExoneracao;

        //Date data=null;
        //DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        //formata uma data com os campos de PontoDia(nr_dia, nr_mes, nr_ano)

		/*try {
			 data = df.parse(pontoDia.getDia() + "/" + pontoDia.getMes() + "/"
					+ pontoDia.getAno());
		} catch (ParseException e) {
			e.printStackTrace();
		}*/


		/*
		// se nao tem provimento ou cargo ou locaocao setado para o dia
	  	if ((pontoDia.getCodigoProvimento() == null)
				|| (pontoDia.getCodigoNomeCargo() == null)
				|| (pontoDia.getCodigoReduzidoOrganograma() == null) ) {

			// busca uma nomeacaoExoneracao para o funcionario f num determinado
			// dia, obtendo assim o provimento, cargo e lotacao no dia.
			//nomeacaoExoneracao = localizaNomeacaoExoneracao(funcionarioEjb, pontoDia);
			nomeacaoExoneracao = ServiceFactory.getFuncionarioService().buscarNomeacaoExoneracao(data, pontoDia.getMatricula());

			//se retornou uma nomeacaoExoneracao
			if (nomeacaoExoneracao != null) {
				pontoDia.setCodigoProvimento(nomeacaoExoneracao
						.getCodigoTipoProvimento());
				pontoDia.setCodigoNomeCargo(nomeacaoExoneracao
						.getCodigoNomeCargo());
				pontoDia.setCodigoReduzidoOrganograma(nomeacaoExoneracao.getCodigoReduzido());
			} else {

				//busca um funcionario da lista retornado pelo SGP
				funcionario = localizaFuncionario(funcionarioEjb.getId());

				if (funcionario != null) {
					pontoDia.setCodigoProvimento(funcionario.getCodigoTipoProvimento());
					pontoDia.setCodigoNomeCargo(funcionario.getCodigoNomeCargo());
					pontoDia.setCodigoReduzidoOrganograma(funcionario.getCodigoReduzido());
				}
			}
		}// fim if*/

    }

    //----------------------------------------------------------------------
    //M??todo que identifica se ja existe um horario(entrada ou saida) para
    //um determinado dia.
    //----------------------------------------------------------------------
    private boolean existePontoNoDia(PontoEjb pontoEjb, PontoDia pontoDia) {

        Collection<HoraPonto> listaHorarios;

        //obtem os horarios do dia ja existentes
        listaHorarios = pontoDia.getHoraPontoCollection();

        for (HoraPonto horaPonto : listaHorarios) {

            //verifica antes se hora de entrada nao ?? null
            if (horaPonto.getHoraEntrada() != null) {
                if ( obtemHoraemSegundos(pontoEjb.getDataHora()) == horaPonto.getHoraEntrada())
                    return true;
            }

            //verifica antes se hora de sa??da nao ?? null
            if (horaPonto.getHoraSaida() != null) {
                if (obtemHoraemSegundos(pontoEjb.getDataHora()) == horaPonto.getHoraSaida()) {
                    return true;
                }
            }

        }//end for

        return false;
    }

    //----------------------------------------------------------------------
    //Obtem a hora de uma data
    //----------------------------------------------------------------------
    private int obtemHoraemSegundos(Date data){

        int hora, minuto, segundo;

        //cria o calendario
        Calendar c = CalendarFactory.getCalendar();

        //define a data no calendario
        c.setTime(data);

        //pega a hora, minuto e segundos da hora
        hora = c.get(Calendar.HOUR_OF_DAY);
        minuto = c.get(Calendar.MINUTE);
        segundo = c.get(Calendar.SECOND);

        return (hora * 3600) + (minuto * 60) + segundo;
    }

    private void configuraHorarioOriginal(PontoEjb pontoEjb, PontoDia pontoDia){

        HoraPontoOriginal horaPontoOriginal;

        int horaVerificada, horaEntrada, horaSaida, auxHoraEntrada, auxHoraSaida;
        boolean achouPosicaoVazia = false;

        //se nao tem lista de horarios para o pontoDia
        if (pontoDia.getHoraPontoOriginalCollection().size() == 0) {

            //cria um objeto horaPontoOriginal
            criaHorarioOriginalNoDia(obtemHoraemSegundos(pontoEjb.getDataHora()), pontoEjb.getTimeZone(), pontoEjb.getHorarioVerao(), pontoDia);
            
        } else {
        	
            Iterator<HoraPontoOriginal> iterator = pontoDia.getHoraPontoOriginalCollection().iterator();
            horaVerificada = obtemHoraemSegundos(pontoEjb.getDataHora());

            //percorre a lista de horarios
            while (iterator.hasNext()) {

                //obtem um horario da lista
                horaPontoOriginal = iterator.next();

                //obtem hora de entrada
                if (horaPontoOriginal.getHoraEntrada() != null) {
                    horaEntrada = horaPontoOriginal.getHoraEntrada();
                } else {
                    horaEntrada = 0;
                }

                //obtem hora de saida
                if (horaPontoOriginal.getHoraSaida() != null) {
                    horaSaida = horaPontoOriginal.getHoraSaida();
                } else {
                    horaSaida = 0;
                }

                // se hora de entrada esta zero
                if (horaEntrada == 0) {

                    //verificar se tem um valor na hora saida
                    if ((horaVerificada > horaSaida) && (horaSaida > 0)) {
                        horaEntrada = horaSaida;
                        horaSaida = horaVerificada;
                    } else {
                        horaEntrada = horaVerificada;
                    }

                    achouPosicaoVazia = true;

                } else {

                    //se nao tem hora de saida
                    if (horaSaida == 0) {

                        //achou uma posi????o vazia para inserir a hora
                        achouPosicaoVazia = true;

                        //se a hora analisada no la??o ?? menor que a horaEntrada
                        if (horaVerificada < horaEntrada) {
                            //horaSaida passa a ser horaEntrada
                            horaSaida = horaEntrada;

                            //horaEntrada ?? a horaVerificada
                            horaEntrada = horaVerificada;
                        } else {
                            horaSaida = horaVerificada;
                        }

                    }
                    //j?? tem preenchido a entrada e saida
                    else {
                        //guarda os valores da horaEntrada e horaSaida,
                        //pois eles poder??o ser modificados
                        auxHoraEntrada = horaEntrada;
                        auxHoraSaida = horaSaida;

                        if (horaVerificada < horaEntrada) {

                            horaEntrada = horaVerificada;
                            horaSaida = auxHoraEntrada;

                            //nova hora a ser verificada no proximo la??o
                            horaVerificada = auxHoraSaida;

                        } else if (horaVerificada < horaSaida) {
                            horaSaida = horaVerificada;
                            horaVerificada = auxHoraSaida;
                        }

                        //nao achou posi????o vazia
                        achouPosicaoVazia = false;
                    }
                }

                //-----------------------------------------------
                //-----------------------------------------------

                //atualiza o objeto horaPonto no fim da itera????o
                horaPontoOriginal.setHoraEntrada(horaEntrada);
                if (horaSaida == 0) {
                    horaPontoOriginal.setHoraSaida(null);
                    horaPontoOriginal.setTimeZoneSaida(null);
                    horaPontoOriginal.setHorarioVeraoSaida(null);
                } else {
                    horaPontoOriginal.setHoraSaida(horaSaida);
                    horaPontoOriginal.setTimeZoneSaida(pontoEjb.getTimeZone());
                    horaPontoOriginal.setHorarioVeraoSaida(pontoEjb.getHorarioVerao());
                }

            }//fim while

            //se no final do la??o while nao encontrou uma posi????o vazia,
            //insere um novo objeto hor??rio na lista
            if (!achouPosicaoVazia) {
                criaHorarioOriginalNoDia(horaVerificada, pontoEjb.getTimeZone(), pontoEjb.getHorarioVerao(), pontoDia);
            }
        }//fim else lista de horarios nao ?? nula
    }

    //----------------------------------------------------------------------
    //Cria um objeto hor??rio Original
    //----------------------------------------------------------------------
    private void criaHorarioOriginalNoDia(int hora, String timeZone, Boolean horarioVerao, PontoDia pontoDia){
        HoraPontoOriginal horaPontoOriginal;

        horaPontoOriginal = new HoraPontoOriginal(pontoDia.getCodigoEmpresa(),
                pontoDia.getMatricula(),
                pontoDia.getDia(),
                pontoDia.getMes(),
                pontoDia.getAno(),
                hora, 
                timeZone,
                horarioVerao);

        horaPontoOriginal.setHoraSaida(null);
 
        horaPontoOriginal.setPontoDia(pontoDia);

        if (pontoDia.getHoraPontoOriginalCollection() != null) {
            pontoDia.getHoraPontoOriginalCollection().add(horaPontoOriginal);
        }
    }

    //----------------------------------------------------------------------
    //Busca um Funcionario na lista de Funcionarios retornado pelo Legado.
    //----------------------------------------------------------------------
    private FuncionarioEjb localizaFuncionario(int matricula, List<FuncionarioEjb> listaFuncionarios){

        for (FuncionarioEjb funcionario : listaFuncionarios) {
            if (funcionario.getId() == matricula) {
                return funcionario;
            }
        }
        return null;
    }

    //----------------------------------------------------------------------
    //Compara se os objetos possuem o mesmo funcionario, empresa, mes e ano
    //----------------------------------------------------------------------
    private boolean compare(PontoEjb pontoEjbAnterior, PontoEjb pontoEjb) {

        if(pontoEjbAnterior == null) {
            return false;
        } else {


            if (  (  pontoEjbAnterior.getFuncionarioEjb().getId().equals(pontoEjb.getFuncionarioEjb().getId())  ) &&
                    (  pontoEjbAnterior.getFuncionarioEjb().getEmpresa().equals(pontoEjb.getFuncionarioEjb().getEmpresa())   ) &&
                    (  obtemAno(pontoEjbAnterior.getDataHora()) ==  obtemAno(pontoEjb.getDataHora())   ) &&
                    (  obtemMes(pontoEjbAnterior.getDataHora()) ==  obtemMes(pontoEjb.getDataHora()))
                    ) {
                return true;
            }	else {
                return false;
            }

        }

    }

    //----------------------------------------------------------------------
    //M??todo para configurar a lista de hor??rios de um pontoDia.
    //Identifica onde o hor??rio vai se encaixar, verificando se o mesmo ?? uma
    //nova entrada ou sa??da de uma entrada j?? existente. Nesse segundo caso a
    //??ltima sa??da passa a ser uma nova entrada.
    //----------------------------------------------------------------------

    private void configuraHorario(PontoEjb pontoEjb, PontoDia pontoDia){
        HoraPonto horaPonto;

        int horaVerificada, horaEntrada, horaSaida, auxHoraEntrada, auxHoraSaida;
        boolean achouPosicaoVazia = false;

        //se nao tem lista de horarios para o pontoDia
        if (pontoDia.getHoraPontoCollection().size() == 0) {

            //cria um objeto horaPonto
            criaHorarioNoDia(obtemHoraemSegundos(pontoEjb.getDataHora()), pontoEjb.getTimeZone(), pontoEjb.getHorarioVerao(),
                    pontoDia);
        }
        else {
            //obtem os horarios batidos no dia
            Iterator<HoraPonto> iterator = pontoDia.getHoraPontoCollection().iterator();

            // a cada itera????o, horaVerificada pode ser trocada. Come??a com o ponto batido
            horaVerificada = obtemHoraemSegundos(pontoEjb.getDataHora());

            //percorre a lista de horarios do dia
            while (iterator.hasNext()) {

                //obtem um horario da lista
                horaPonto = iterator.next();


                //------------------------------------------------
                //Verificar se o ponto j?? existe
                //------------------------------------------------
                //verifica antes se hora de entrada nao ?? null
				    /*
					  if (horaPonto.getHoraEntrada() != null) {
						 if ( obtemHoraemSegundos(pontoEjb.getDataHora()) == horaPonto.getHoraEntrada())
						 	 return;
					  }

					  //verifica antes se hora de sa??da nao ?? null
					 if (horaPonto.getHoraSaida() != null) {
						if (obtemHoraemSegundos(pontoEjb.getDataHora()) == horaPonto.getHoraSaida())
							return;
					 }

				  	//----------------------------------------------------------
				  	 * Fim Verifica
				    //----------------------------------------------------------
					*/



                //obtem hora de entrada
                if (horaPonto.getHoraEntrada() != null) {
                    horaEntrada = horaPonto.getHoraEntrada();
                } else {
                    horaEntrada = 0;
                }

                //obtem hora de saida
                if (horaPonto.getHoraSaida() != null) {
                    horaSaida = horaPonto.getHoraSaida();
                } else {
                    horaSaida = 0;
                }

                // se hora de entrada esta zero
                if (horaEntrada == 0) {

                    //verificar se tem um valor na hora saida
                    if ((horaVerificada > horaSaida) && (horaSaida > 0)) {
                        horaEntrada = horaSaida;
                        horaSaida = horaVerificada;
                    } else {
                        horaEntrada = horaVerificada;
                    }

                    //achou uma posi????o vazia para inserir a hora
                    achouPosicaoVazia = true;

                } else {

                    //se nao tem hora de saida
                    if (horaSaida == 0) {

                        //achou uma posi????o vazia para inserir a hora
                        achouPosicaoVazia = true;

                        //se a hora analisada no la??o ?? menor que a horaEntrada
                        if (horaVerificada < horaEntrada) {
                            //horaSaida passa a ser horaEntrada
                            horaSaida = horaEntrada;

                            //horaEntrada ?? a horaVerificada
                            horaEntrada = horaVerificada;
                        } else {
                            horaSaida = horaVerificada;
                        }

                    }
                    //j?? tem preenchido a entrada e saida
                    else {
                        //guarda os valores da horaEntrada e horaSaida,
                        //pois eles poder??o ser modificados
                        auxHoraEntrada = horaEntrada;
                        auxHoraSaida = horaSaida;

                        if (horaVerificada < horaEntrada) {

                            horaEntrada = horaVerificada;
                            horaSaida = auxHoraEntrada;

                            //nova hora a ser verificada no proximo la??o
                            horaVerificada = auxHoraSaida;

                        } else if (horaVerificada < horaSaida) {
                            horaSaida = horaVerificada;
                            horaVerificada = auxHoraSaida;
                        }

                        //nao achou posi????o vazia
                        achouPosicaoVazia = false;
                    }
                }

                //-----------------------------------------------
                //-----------------------------------------------

                //atualiza o objeto horaPonto no fim da itera????o
                horaPonto.setHoraEntrada(horaEntrada);
                if (horaSaida == 0) {
                    horaPonto.setHoraSaida(null);
                    horaPonto.setTimeZoneSaida(null);
                    horaPonto.setHorarioVeraoSaida(null);
                } else {
                    horaPonto.setHoraSaida(horaSaida);
                    horaPonto.setTimeZoneSaida(pontoEjb.getTimeZone());
                    horaPonto.setHorarioVeraoSaida(pontoEjb.getHorarioVerao());
                    
                }

            }//fim while


            //se no final do la??o while nao encontrou uma posi????o vazia,
            //insere um novo objeto hor??rio na lista
            if (!achouPosicaoVazia) {
                criaHorarioNoDia(horaVerificada, pontoEjb.getTimeZone(), pontoEjb.getHorarioVerao(), pontoDia);
            }//fim if(!achouPosicaoVazia)

        }//fim else lista de horarios nao ?? nula
    }

    //----------------------------------------------------------------------
    //Cria um objeto hor??rio
    //----------------------------------------------------------------------
    private void criaHorarioNoDia(int hora, String timeZone, Boolean horarioVerao, PontoDia pontoDia){
        HoraPonto horaPonto;

        horaPonto = new HoraPonto(pontoDia.getCodigoEmpresa(),
                pontoDia.getMatricula(),
                pontoDia.getDia(),
                pontoDia.getMes(),
                pontoDia.getAno(),
                hora, 
                timeZone,
                horarioVerao);

        horaPonto.setHoraSaida(null);

        horaPonto.setPontoDia(pontoDia);

        if (pontoDia.getHoraPontoCollection() != null) {
            pontoDia.getHoraPontoCollection().add(horaPonto);
        }
    }

    //----------------------------------------------------------------------
    //M??todo que identifica se ja existe um horario(entrada ou saida) para
    //um determinado dia.
    //----------------------------------------------------------------------
    private boolean existePontoOriginalNoDia(PontoEjb pontoEjb, PontoDia pontoDia) {

        Collection<HoraPontoOriginal> listaHorarios;

        listaHorarios = pontoDia.getHoraPontoOriginalCollection();

        for (HoraPontoOriginal horaPontoOriginal : listaHorarios) {

            //verifica antes se hora de entrada nao ?? null
            if (horaPontoOriginal.getHoraEntrada() != null) {
                if ( obtemHoraemSegundos(pontoEjb.getDataHora()) == horaPontoOriginal.getHoraEntrada())
                    return true;
            }

            //verifica antes se hora de sa??da nao ?? null
            if (horaPontoOriginal.getHoraSaida() != null) {
                if (obtemHoraemSegundos(pontoEjb.getDataHora()) == horaPontoOriginal.getHoraSaida()) {
                    return true;
                }
            }

        }//end for

        return false;
    }

}