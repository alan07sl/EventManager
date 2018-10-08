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

    static String BotToken = "625563171:AAFyoxqMiAua2gLEGVRYcYF00KhAa2aYyG0";
    long ChatID ;
    boolean LOGIN = false;


    @Autowired
    private EventbriteService eventbriteService;

    @Autowired
    private EventListService eventListService;

    @Autowired
    private UserService UserService;

    public void onUpdateReceived(Update update){
        String username = "Username";
        String password ="Password" ;

        ChatID = update.getMessage().getChatId();
        String command = update.getMessage().getText();
        SendMessage message = new SendMessage();

        if(command.equals("/login")) {
            if((username.equals("Username")) || (password.equals("Password"))  )
                MandarMensaje("Please set your username and password to login");
            else{

                try{

                UserService.authenticateUser(username,password);
                LOGIN = true;
                MandarMensaje("Login Exitoso");

                }catch(InvalidCredentialsException e){

                    username = "Username";
                    password = "Password";
                    MandarMensaje("Credenciales erroneas, por favor vuelva a ingresarlas");
                }

            }
        }

        if(command.equals("/setusername")) {

            List<String> parametros = ParsearComando(command);
            password = parametros.get(0);
            MandarMensaje("Username guardado");
        }



        if(command.equals("/setpassword")) {
            if(!username.isEmpty()){
            List<String> parametros = ParsearComando(command);

            password = parametros.get(0);
            MandarMensaje("Contraseña guardada");

            }else
                MandarMensaje("Falta Ingresar username");
        }

        if(command.equals("/start")) {

            MandarMensaje("Bienvenido, por favor, ingrese su usuario y contraseña para poder loguearse y acceder a los servicios de EventManager");

        }
        if(command.contains("/buscarevento")){

            if(LOGIN){
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

            if(LOGIN){
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

            if(LOGIN){
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

