import java.util.*;
import javax.mail.*;

public class FetchingEmail {

    public static void main(String[] args)  {
        String host = "imap.gmail.com";// change accordingly
        String username = "#";// change accordingly
        String password = "#";// change accordingly

        check(host, username, password);
    }

    public static void check(String host, String user, String password){

        try {
            Properties properties = new Properties();
            properties.put("mail.imaps.host", host);
            properties.put("mail.imaps.port", "993");
            properties.put("mail.imaps.starttls.enable", "true");
            Session emailSession = Session.getDefaultInstance(properties);

            Store store = emailSession.getStore("imaps");
            store.connect(host, user, password);

            Folder emailFolder = store.getFolder("Inbox");
            emailFolder.open(Folder.READ_ONLY);

            Message[] messages = emailFolder.getMessages();

            for (int i = 0, n = messages.length; i < n; i++) {

                Message message = messages[i];
                if (!message.getSubject().contains("mailovi")){
                    continue;
                }

                Object obj = message.getContent();
                Multipart mp = (Multipart)obj;
                BodyPart bp = mp.getBodyPart(0);

                System.out.println("---------------------------------");
                System.out.println("Email Number " + (i + 1));
                System.out.println("Subject: " + message.getSubject());
                System.out.println("From: " + message.getFrom()[0]);
                System.out.println("To: " + message.getAllRecipients().toString());
                System.out.println("Received Date:" + message.getReceivedDate());
                System.out.println("---------------------------------");
               // System.out.println("Text: " + bp.getContent().toString());

                String text = bp.getContent().toString();
                String [] mailovi = text.split(",");
                for (int j = 0; j < mailovi.length; j++) {
                    System.out.println("TEST " + mailovi[j].trim().replaceAll("<mailto.*",""));
                }
            }
            emailFolder.close(false);
            store.close();

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
