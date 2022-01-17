import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
     static long chat_id;
    public static void main(String[] args) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new Bot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }



    @Override
    public String getBotUsername() {
        return "Kulm_Bot";
    }

    @Override
    public String getBotToken() {
        return "5074183347:AAFFRFQhOP4rP3Kw7J03pIpe-bb0eoqjg6A";
    }

    public void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try {
            getButtons(sendMessage);
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }



    public void getButtons(SendMessage sendMessage){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);


        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboarFirstdRow = new KeyboardRow();

        keyboarFirstdRow.add(new KeyboardButton("/help"));
        keyboarFirstdRow.add(new KeyboardButton("/settings"));


        keyboardRowList.add(keyboarFirstdRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);


    }



    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            switch (message.getText()) {
                case "/start" -> sendMsg(message, "Добро пожаловать мешки с костями!");
                case "/settings" -> sendMsg(message, "Что будем настраивать?");
                case "/help" -> sendMsg(message, "Чем могу помочь?");
                default -> {
                }
            }
        }
    }






    /*@Override
    public void onUpdateReceived(Update update) {
        chat_id = update.getMessage().getChatId();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chat_id));
        String text = update.getMessage().getText();

        try{
            sendMessage.setText("Вы ввели: "+ text);
            execute(sendMessage);

            sendMessage.setText("Результат: "+ (getMsg(text)));
            execute(sendMessage);
        }catch (TelegramApiException e){
            e.printStackTrace();
        }
    }
    private int getMsg(String msg){
    try{
        return Integer.parseInt(msg)*5;
    }catch (NumberFormatException e){
        e.printStackTrace();
    } return 0;

    }

     */


}

