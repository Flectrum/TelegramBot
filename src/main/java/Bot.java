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
    //comment
     static final String TOKEN = System.getenv("TOKEN");
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
        return TOKEN;
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

        keyboarFirstdRow.add(new KeyboardButton("Help"));

        keyboardRowList.add(keyboarFirstdRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);


    }



    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            switch (message.getText()) {
                case "/start" -> sendMsg(message, "Добро пожаловать мешок с костями!");
                case "Help" -> sendMsg(message, "Напиши количество рядов");
                default -> sendMsg(message, salary(message) );
            }
            }
        }










    public String salary(Message message) {
        try {
            double x = (Double.parseDouble(message.getText()) * 0.08+580.00)-(Double.parseDouble(message.getText()) * 0.08)/5 ;
            return x +" eur";
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return "пока работаю только на вычисление... Введи количество рядов";
    }
    }








