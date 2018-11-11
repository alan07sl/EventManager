package com.utn.tacs.eventmanager.services;

import com.utn.tacs.eventmanager.dao.EventList;
import com.utn.tacs.eventmanager.dao.User;
import com.utn.tacs.eventmanager.errors.CustomException;
import com.utn.tacs.eventmanager.errors.InvalidCredentialsException;
import com.utn.tacs.eventmanager.services.dto.EventsResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TelegramIntegrationService extends TelegramLongPollingBot {

    long chatID;

    @Autowired
    private EventbriteService eventbriteService;

    @Autowired
    private EventListService eventListService;

    @Autowired
    private UserService UserService;

    private Map<Long,User> authenticatedChats = new HashMap<>();

    public void onUpdateReceived(Update update){


        if(update.hasMessage() || update.hasCallbackQuery()) {

            chatID = update.hasMessage() ? update.getMessage().getChatId() : update.getCallbackQuery().getFrom().getId();

            if(update.hasMessage()) {
                processMessage(update);
            } else {
                processCallback(update);
            }
        }

    }

    public void processMessage(Update update) {
        String commandLine = update.getMessage().getText();
        List<String> params = parseCommand(commandLine);
        String command = commandLine.split(" ")[0];

        switch(command) {
            case "/login":
                login(params);
                break;
            case "/start":
                sendMessage("Welcome to Event Manager Telegram Bot! \n" +
                        "Available commands are:\n" +
                        "- /login <username> <password> -> Login\n" +
                        "- /search <criteria> -> Search Events\n" +
                        "- /lists  -> Get own lists\n" +
                        "- /logout -> Logout ");
                break;
            case "/search":
                searchEvents(params);
                break;
            case "/lists":
                searchLists(null);
                break;
            case "/logout":
                logout();
                break;
            default:
                sendMessage("Command unknown");
                break;
        }
    }

    public void processCallback(Update update) {
        String commandLine = update.getCallbackQuery().getData();
        List<String> params = parseCommand(commandLine);
        String command = commandLine.split(" ")[0];


        switch(command) {
            case "/search":
                searchEvents(params);
                break;
            case "/getEvents":
                getEventsFromList(params);
                break;
            case "/addEvent":
                searchLists(params.get(0));
                break;
            case "/addEventToList":
                addEventToList(params.get(0), params.get(1));
                break;
            default:
                sendMessage("Callback unknown");
                break;
        }
    }

    private void logout() {
        if(authenticatedChats.containsKey(chatID)) {
            authenticatedChats.remove(chatID);
            sendMessage("Goodbye !");
        } else {
            sendMessage("You are already logout");
        }
    }

    private void searchEvents(List<String> params) {
        if(authenticatedChats.containsKey(chatID)) {
            if(params.size() >= 1){
                try{

                    EventsResponseDTO response = eventbriteService.getEvents(params.size() == 2 ? params.get(1) : "1", params.get(0));
                    if(response.getEvents().isEmpty()){
                        sendMessage("Events not found with criteria "+ params.get(0));
                    } else {
                    	response.getEvents().forEach(e -> {
                            List<InlineKeyboardButton> buttons = new ArrayList<>();

                            InlineKeyboardButton addEvent = new InlineKeyboardButton().setText("Add event to list").setCallbackData("/addEvent "+e.get("id"));
                            buttons.add(addEvent);

                            sendListMessageFormatted(
                                    getEventMessage(e),
                                    "/search "+params.get(0),
                                    buttons,
                                    response.getPagination().getPageNumber(),
                                    response.getPagination().hasMoreItems(),
                                    response.getEvents().indexOf(e) == (response.getEvents().size() - 1));
                    	});

                    }
                } catch (CustomException e){
                    sendErrorMessage();
                }
            } else {
                sendMessage("Search command require one parameter /search <criteria>");
            }
        } else {
            sendLoginRequired("/search");
        }
    }

    private void addEventToList(String listId, String eventId) {
        try{
            User user = authenticatedChats.get(chatID);
            eventListService.addEvent(listId, Long.valueOf(eventId), user);
            sendMessage("Event added to list !");
        } catch (CustomException e){
            sendErrorMessage();
        }
    }

    private void searchLists(String eventId) {
        if(authenticatedChats.containsKey(chatID)) {
            try{
                User user = authenticatedChats.get(chatID);
                List<EventList> lists = eventListService.getEventsLists(user.getId());
                if(lists.isEmpty()){
                    sendMessage("No lists found for "+user.getUsername());
                } else {
                    lists.forEach(list -> {

                        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
                        List<InlineKeyboardButton> rowInline = new ArrayList<>();

                        InlineKeyboardButton getEventsButton = new InlineKeyboardButton().setText("Get events").setCallbackData("/getEvents "+list.getId());
                        rowInline.add(getEventsButton);

                        if(eventId != null){
                            InlineKeyboardButton addEventToList = new InlineKeyboardButton().setText("Add event to list").setCallbackData("/addEventToList "+list.getId()+ " "+ eventId);
                            rowInline.add(addEventToList);
                        }

                        rowsInline.add(rowInline);
                        sendMessageWithButtons(getEventListMessage(list),rowsInline);
                    });

                }
            } catch (CustomException e){
                sendErrorMessage();
            }
        } else {
            sendLoginRequired("/lists");
        }
    }

    private void getEventsFromList(List<String> params) {
        try{
            EventList list = eventListService.findById(params.get(0));
            if(list.getEvents().isEmpty()){
                sendMessage("No events found for list "+list.getName());
            } else {
                eventbriteService.getEvents(list.getEvents()).forEach(e -> {
                    sendMessage(getEventMessage(e));
                });
            }
        } catch (CustomException e){
            sendErrorMessage();
        }
    }

    private String getEventListMessage(EventList e) {
        return "Name: *"+e.getName()+"*\n\n";
    }

    private String getEventMessage(Map<String,Object> e) {
        return "Name: ```" +((Map)e.get("name")).get("text") + "```\n[Link to Eventbrite]("+e.get("url")+")\n\n";
    }

	private void login(List<String> params) {
        if (params.size() < 2){
            sendMessage("Login command require two parameters /login <username> <password>");
        } else {
            try{
                User user = UserService.authenticateUser(params.get(0),params.get(1));
                authenticatedChats.put(chatID, user);
                sendMessage("Welcome "+user.getUsername()+ " !");
            }catch(InvalidCredentialsException e){
                sendMessage("Incorrect username/password");
            }
        }
    }

    public void sendListMessageFormatted(String message, String baseCommand, List<InlineKeyboardButton> inlineButtons, Integer actualPage, boolean hasMorePages, boolean lastItem) {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        if(lastItem){
            InlineKeyboardButton nextButton = new InlineKeyboardButton().setText("Next Page").setCallbackData(baseCommand + " " + (actualPage + 1));
            InlineKeyboardButton prevButton = new InlineKeyboardButton().setText("Prev Page").setCallbackData(baseCommand + " " + (actualPage - 1));

            if (actualPage > 1) {
                rowInline.add(prevButton);
            }

            if (hasMorePages) {
                rowInline.add(nextButton);
            }
        }

        rowsInline.add(inlineButtons);
        rowsInline.add(rowInline);

        sendMessageWithButtons(message, rowsInline);
    }

    public SendMessage getMessage(String message) {
        SendMessage msg = new SendMessage();
        msg.setText(message);
        msg.setChatId(chatID);
        msg.setParseMode(ParseMode.MARKDOWN);
        msg.disableNotification();

        return msg;
    }

    public void sendMessage(String message){
        SendMessage msg = getMessage(message);
        try {
            execute(msg);
        }catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

    public void sendMessageWithButtons(String message,List<List<InlineKeyboardButton>> rowsInline){
        SendMessage msg = getMessage(message);

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(rowsInline);
        msg.setReplyMarkup(markupInline);

        try {
            execute(msg);
        }catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

    public void sendErrorMessage() {
        sendMessage("Ops! Something fail, try later");
    }

    public void sendLoginRequired(String command) {
        sendMessage("Login required for command "+ command);
    }


    public String getBotUsername() {
        return "EventManager";
    }

    public String getBotToken() {
        return "630953912:AAEG844Bh3BFhUExnxOzKAPphwzF8LMq68k";
    }

    private List<String> parseCommand(String command){
        List<String> params = new ArrayList<>() ;
        String[] param = command.split(" ");
        for(String item : param) {
            if (!item.contains("/"))
                params.add(item);
        }

        return params;
    }


}

