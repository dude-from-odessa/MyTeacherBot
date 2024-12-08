package app;

import chatgpt.ChatGPTService;
import telegram.DialogMode;
import telegram.MultiSessionTelegramBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


public class MyTeacherBotApp extends MultiSessionTelegramBot {
    public static final String TELEGRAM_BOT_NAME = "my_school_teacher_bot";
    public static final String TELEGRAM_BOT_TOKEN = "7721464104:AAH4I_cga8uACycDcIiRRdVcu186gsVlV7M";

    public static final String OPEN_AI_TOKEN = "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";
    public DialogMode mode = DialogMode.MAIN;

    public ChatGPTService gptService = new ChatGPTService(OPEN_AI_TOKEN);

    public MyTeacherBotApp() {
        super(TELEGRAM_BOT_NAME, TELEGRAM_BOT_TOKEN);
    }

    @Override
    public void onUpdateEventReceived(Update update) {

        String message = getMessageText();

        switch (message) {
            case "/start" -> {
                mode = DialogMode.MAIN;
                showMainMenu(
                        "головне меню бота", "/start",
                        "поставити запитання чату GPT \uD83E\uDDE0", "/gpt");

                String menu = loadMessage("main");
                sendTextMessage(menu);
                return;
            }

            case "/gpt" -> {
                mode = DialogMode.GPT;

                sendPhotoMessage("gpt");
                String gptMessage = loadMessage("gpt");
                sendTextMessage(gptMessage);
                return;
            }

        }

        switch (mode) {
            case GPT -> {
                String prompt = loadPrompt("gpt");
                Message msg = sendTextMessage("Вчитель друкує.....");

                String answer = gptService.sendMessage(prompt, message);
                updateTextMessage(msg, answer);
            }
        }

    }

    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(new MyTeacherBotApp());
    }
}
