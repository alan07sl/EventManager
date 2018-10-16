package com.utn.tacs.eventmanager.services;

import com.utn.tacs.eventmanager.dao.User;
import com.utn.tacs.eventmanager.errors.CustomException;
import com.utn.tacs.eventmanager.errors.InvalidCredentialsException;
import com.utn.tacs.eventmanager.services.dto.EventsResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TelegramIntegrationService extends TelegramLongPollingBot {

    long chatID;

    @Autowired
    private EventbriteService eventbriteService;

    @Autowired
    private EventListService eventListService;

    @Autowired
    private UserService UserService;

    private Map authenticatedChats = new HashMap<Long, User>();

    public void onUpdateReceived(Update update){


        chatID = update.getMessage().getChatId();
        String commandLine = update.getMessage().getText();

        List<String> parametros = ParsearComando(commandLine);

        switch(commandLine.split(" ")[0]) {
            case "/login":
                login(parametros);
                break;
            case "/start":
                mandarMensaje("Bienvenido, ingrese su usuario y contraseña para poder loguearse y acceder a los servicios de EventManager. \n" +
                        "Los comandos disponibles son :\n" +
                        "login - Loguearse\n" +
                        "buscarevento - Buscar Evento\n" +
                        "agregarevento - Agregar evento\n" +
                        "revisareventos - Revisar eventos de una lista de eventos\n" +
                        "logout - Desloguearse ");
                break;
            case "/buscarevento":
                buscarEvento(parametros);
                break;
            case "/agregarevento":
                agregarEvento(parametros);
                break;
            case "/revisareventos":
                revisarEventosDeUnaLista(parametros);
                break;
            case "/logout":
                logout();
                break;
            default:
                mandarMensaje("Comando incorrecto");
                break;
        }

    }

    private void logout() {
        if(authenticatedChats.containsKey(chatID)) {
            authenticatedChats.remove(chatID);
            mandarMensaje("Logout exitoso.");
        } else {
            mandarMensaje("No estabas logeado.");
        }
    }

    private void revisarEventosDeUnaLista(List<String> parametros) {
        if(authenticatedChats.containsKey(chatID)){
            if (parametros.size() < 1) {
                long eventId = Long.parseLong(parametros.get(0));
                Map<String,Object> event = new HashMap<>();
                try {
                    event = eventbriteService.getEvent(eventId);
                    mandarMensaje(event.values().toString());

                }catch(CustomException e){

                    mandarMensaje(e.getMessage());

                }
            } else
                mandarMensaje("Falta un parametros");

        } else
            mandarMensaje("Debe loguearse primero");
    }

    private void agregarEvento(List<String> parametros) {
        if(authenticatedChats.containsKey(chatID)){
            if(parametros.size() == 2){

                Integer EventListId =  Integer.parseInt(parametros.get(0));
                Long EventID=  Long.parseLong(parametros.get(1));

                try{

                    eventListService.addEvent(EventListId,EventID, (User) authenticatedChats.get(chatID));
                    mandarMensaje("Evento agregado correctamente a tu lista.");

                }catch(CustomException e){
                    mandarMensaje(e.getMessage());
                }

            }
            else
                mandarMensaje("Agregar evento recibe solo 2 parametros, donde el primero es el id de la lista y el segundo es el id del evento a agregar");

        }else
            mandarMensaje("Debe loguearse primero");
    }

    private void buscarEvento(List<String> parametros) {
        if(authenticatedChats.containsKey(chatID)) {

            if(parametros.size() == 1){
                try{

                    EventsResponseDTO response = eventbriteService.getEvents("1",parametros.get(0));
                    if(response.events.isEmpty()){
                        mandarMensaje("No hay eventos con ese criterio de busqueda.");
                    } else {

                    	StringBuilder sb = new StringBuilder();
                    	response.events.forEach(e -> appendRelevant(e,sb));
                    	sb.append("Recuerde que puede guardar cualquier evento que le interese utilizando el comando " +
								"/agregarevento seguido del ID de su lista de eventos y el ID del evento que le interese " +
								"que podra encontrar en esta misma lista.\n" +
								"Por ejemplo: /agregarevento 1234123 23567892");

						mandarMensaje(sb.toString());
                    }

                }catch (CustomException e){

                    mandarMensaje(e.getMessage());

                }
            }else {
                mandarMensaje("Buscar evento recibe solo 1 parametro, que indica el criterio de busqueda");

            }
        }else{

            mandarMensaje("Debe loguearse primero");
        }
    }

	private void appendRelevant(Map<String,Object> e, StringBuilder sb) {
		sb.append("Nombre: ");
    	sb.append(((Map)e.get("name")).get("text"));
		sb.append("\n");
		sb.append("Id: ");
		sb.append(e.get("id"));
		sb.append("\n");
		sb.append("Link: ");
		sb.append(e.get("url"));
		sb.append("\n");
		sb.append("---------------------------------");
		sb.append("\n");
	}

	private void login(List<String> parametros) {
        mandarMensaje("Intentando loguearse");
        System.out.println(parametros.get(0)+" " +parametros.get(1));
        if( parametros.get(0).isEmpty() || parametros.get(1).isEmpty())
            mandarMensaje("Please set your username and password to login");
        else{

            try{

                User user = UserService.authenticateUser(parametros.get(0),parametros.get(1));
                authenticatedChats.put(chatID, user);

                mandarMensaje("Login Exitoso");

            }catch(InvalidCredentialsException e){

                mandarMensaje("Credenciales erroneas, por favor vuelva a ingresarlas");
            }

        }
    }

    public void mandarMensaje(String message){

        SendMessage msg = new SendMessage();
        msg.setText(message);
        msg.setChatId(chatID);
        try {
            execute(msg);
        }catch (TelegramApiException e){
            e.printStackTrace();
        }

    }

    public String getBotUsername() {
        return "EventManager" ;
    }

    public String getBotToken() {
        return "625563171:AAFyoxqMiAua2gLEGVRYcYF00KhAa2aYyG0";
    }

    private List<String> ParsearComando(String comando){
        List<String> parametros = new ArrayList<String>() ;
        String[] param = comando.split(" ");
        for(String item : param) {
            if (!item.contains("/"))
                parametros.add(item);
        }

        return parametros;
    }


}

