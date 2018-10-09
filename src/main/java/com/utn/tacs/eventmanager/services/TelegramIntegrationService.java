package com.utn.tacs.eventmanager.services;

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

    long ChatID ;

    @Autowired
    private EventbriteService eventbriteService;

    @Autowired
    private EventListService eventListService;

    @Autowired
    private UserService UserService;

    public void onUpdateReceived(Update update){


        ChatID = update.getMessage().getChatId();
        String command = update.getMessage().getText();
        System.out.println(command);
        if(command.contains("/login")) {
            MandarMensaje("Intentando loguearse");
            List<String> parametros = ParsearComando(command);
            System.out.println(parametros.get(0)+" " +parametros.get(1));
            if( parametros.get(0).isEmpty() || parametros.get(1).isEmpty())
                MandarMensaje("Please set your username and password to login");
            else{

                try{

                UserService.authenticateUser(parametros.get(0),parametros.get(1));

                MandarMensaje("Login Exitoso");

                }catch(InvalidCredentialsException e){

                    MandarMensaje("Credenciales erroneas, por favor vuelva a ingresarlas");
                }
                
            }
        }


        if(command.equals("/start")) {

            MandarMensaje("Bienvenido, ingrese su usuario y contraseña para poder loguearse y acceder a los servicios de EventManager. \n" +
                    "Los comandos disponibles son :\n" +
                    "login - Loguearse\n" +
                    "buscarevento - Buscar Evento\n" +
                    "agregarevento - Agregar evento\n" +
                    "revisarevento - Revisar evento\n" +
                    "resetear - Volver a loguerse con otra cuenta ");

        }
        if(command.contains("/buscarevento")){
            MandarMensaje("Buscando evento");
            if((UserService.findCurrentUser())!= null) {
            List<String> parametros = ParsearComando(command);

            if(parametros.size() == 2){
                try{

                    EventsResponseDTO response = eventbriteService.getEvents(parametros.get(0),parametros.get(1));
                    MandarMensaje(response.events.toString());

                }catch (CustomException e){

                    MandarMensaje(e.getMessage());

                }
            }else {
                MandarMensaje("Falta un parametro");
            }
            }else{

                MandarMensaje("Debe loguearse primero");
            }
        }


        if(command.contains("/agregarevento")){
            MandarMensaje("Agregando evento");
            if((UserService.findCurrentUser())!= null){
            List <String> parametros = ParsearComando(command);
            if(parametros.size() < 1){

                Integer EventListId =  Integer.parseInt(parametros.get(0));
                Long EventID=  Long.parseLong(parametros.get(1));

                try{

                 eventListService.addEvent(EventListId,EventID,UserService.findCurrentUser());
                 MandarMensaje("Agregado Correctamente");

                }catch(CustomException e){
                    MandarMensaje(e.getMessage());
                }

            }
            else
                MandarMensaje("Falta un parametro");

            }else
                 MandarMensaje("Debe loguearse primero");

        }


        if(command.contains("/revisarevento")) {
            MandarMensaje("Revisando evento");
            if((UserService.findCurrentUser())!= null){
            List<String> parametros = ParsearComando(command);
            if (parametros.size() < 1) {
                long eventId = Long.parseLong(parametros.get(0));
                Map<String,Object> Event = new HashMap<>();
                try {
                    Event = eventbriteService.getEvent(eventId);
                    MandarMensaje(Event.values().toString());

                }catch(CustomException e){

                    MandarMensaje(e.getMessage());

                }
            } else
                MandarMensaje("Falta un parametros");

        } else
            MandarMensaje("Debe loguearse primero");


    }

    }

    public void MandarMensaje(String message){

        SendMessage msg = new SendMessage();
        msg.setText(message);
        msg.setChatId(ChatID);
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

    public List<String> ParsearComando(String comando){
        List<String> parametros = new ArrayList<String>() ;
        String[] param = comando.split(" ");
        for(String item : param) {
            if (!item.contains("/"))
                parametros.add(item);
        }

        return parametros;
    }


}

