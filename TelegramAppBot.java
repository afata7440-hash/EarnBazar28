import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class MyTelegramAppBot extends TelegramLongPollingBot {

    @Override
    public String getBotUsername() {
        return "YOUR_BOT_USERNAME"; // আপনার বটের ইউজারনেম এখানে দিন
    }

    @Override
    public String getBotToken() {
        return "YOUR_BOT_TOKEN"; // BotFather থেকে পাওয়া টোকেন দিন
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            if (messageText.startsWith("/start")) {
                // রেফারেল কোড চেক করা (যদি থাকে)
                String[] parts = messageText.split(" ");
                if (parts.length > 1) {
                    String refCode = parts[1];
                    System.out.println("User invited by code: " + refCode);
                    // এখানে ডাটাবেসে রেফারেল পয়েন্ট যোগ করার লজিক লিখবেন
                }
                
                sendStartMenu(chatId, update.getMessage().getFrom().getFirstName());
            }
        }
    }

    private void sendStartMenu(long chatId, String name) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("👋 স্বাগতম " + name + "!\nআমাদের প্রফেশনাল মিনি অ্যাপে জয়েন করতে নিচের বাটনে ক্লিক করুন।");

        // ইনলাইন কিবোর্ড এবং ওয়েব অ্যাপ বাটন
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        InlineKeyboardButton webAppBtn = new InlineKeyboardButton();
        webAppBtn.setText("🎮 Play Now (ট্যাপ করুন)");
        
        // আপনার হোস্ট করা HTML অ্যাপের লিঙ্ক এখানে দিন
        webAppBtn.setWebApp(new WebAppInfo("https://your-app-link.vercel.app"));

        rowInline.add(webAppBtn);
        rowsInline.add(rowInline);
        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
