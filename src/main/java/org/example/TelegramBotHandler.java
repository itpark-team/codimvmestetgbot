package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramBotHandler extends TelegramLongPollingBot {
    private String botUsername = "codimvmestetg_bot";
    private String botToken = "6934359674:AAHBNxU5xHUbL3quk7c0lD3OfDtlB0dZies";

    private JokesStorage jokesStorage = new JokesStorage();

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() == true) {
            Message messageFromUser = update.getMessage();

            if (messageFromUser.hasText() == true) {
                String textFromUser = messageFromUser.getText();
                long chatId = messageFromUser.getChatId();

                //String textToUser = "Получено: " + textFromUser;
                //String textToUser = jokesStorage.getRandomJoke();

                String textToUser = "";

                if (textFromUser.equals("/start") == true) {
                    textToUser = "Добро пожаловать в бот - выдачу шуток. Для выдачи новой шутки введите /joke";
                } else if (textFromUser.equals("/joke") == true) {
                    textToUser = jokesStorage.getRandomJoke();
                } else {
                    textToUser = "Команда не распознана. Введите /start для начала работы с ботом.";
                }


                SendMessage messageToUser = new SendMessage();
                messageToUser.setChatId(chatId);
                messageToUser.setText(textToUser);

                try {
                    execute(messageToUser);
                } catch (TelegramApiException e) {
                    System.out.println("Send message error. " + e);
                }
            }
        }
    }


    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}
