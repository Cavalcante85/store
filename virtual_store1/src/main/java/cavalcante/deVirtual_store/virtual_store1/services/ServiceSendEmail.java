package cavalcante.deVirtual_store.virtual_store1.services;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;


@Service
public class ServiceSendEmail {

    private final String userName = "springjava85@gmail.com";
    private final String senha = "lael obam cgxg lilw";

    @Async
    public void enviarEmaihtml(String assunto, String mensagem, String emailDestino)
            throws MessagingException, UnsupportedEncodingException {

        // Tornar as variáveis finais efetivas para uso no Authenticator
        final String finalUserName = this.userName;
        final String finalSenha = this.senha;

        Properties properties = new Properties();
        properties.put("mail.smtp.ssl.trust", "*");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.fallback", "false");
        properties.put("mail.smtp.ssl.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // Usar as variáveis finais efetivas
                System.out.println("Tentando autenticar com usuário: " + finalUserName);
                System.out.println("Senha não nula: " + (finalSenha != null));
                return new PasswordAuthentication(finalUserName, finalSenha);
            }
        });

        session.setDebug(true);
        try {
            Address[] toUser = InternetAddress.parse(emailDestino);

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(finalUserName, "Samuel", "UTF-8"));
            message.setRecipients(Message.RecipientType.TO, toUser);
            message.setSubject(assunto);
            message.setContent(mensagem, "text/html; charset=utf-8");

            Transport.send(message);
            System.out.println("Email enviado com sucesso para: " + emailDestino);

        } catch (Exception e) {
            System.err.println("Erro ao enviar email: " + e.getMessage());
            throw e;
        }
    }
}