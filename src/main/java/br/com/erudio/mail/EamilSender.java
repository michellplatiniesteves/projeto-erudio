package br.com.erudio.mail;

import br.com.erudio.config.EmailConfig;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.apache.xmlbeans.impl.soap.MimeHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.StringTokenizer;

@Component
public class EamilSender implements Serializable {
    private final JavaMailSender sender;
    private String to;
    private String body;
    private String subject;
    private ArrayList<InternetAddress> recebedores = new ArrayList<>();
    private File file;
    private Logger logger = LoggerFactory.getLogger(EamilSender.class);
    public EamilSender(JavaMailSender sender) {
        this.sender = sender;
    }

    public EamilSender to(String to){
        this.to = to;
        this.recebedores = getRecebedores(to);
        return  this;
    }

    private ArrayList<InternetAddress> getRecebedores(String to)  {
        String removerEspacos = to.replaceAll("\\s","");
        StringTokenizer tokenizer = new StringTokenizer(removerEspacos,";");
        ArrayList<InternetAddress> recebedoresList = new ArrayList<>();
        while (tokenizer.hasMoreElements()){
            try {
                recebedoresList.add(new InternetAddress(tokenizer.nextElement().toString()));
            } catch (AddressException e) {
                throw new RuntimeException(e);
            }
        }
        return recebedoresList;
    }

    public EamilSender setBody(String body) {
        this.body = body;
        return this;
    }

    public EamilSender setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public void setRecebedores(ArrayList<InternetAddress> recebedores) {
        this.recebedores = recebedores;
    }

    public EamilSender setFile(String file) {
        this.file = new File(file);
        return this;
    }
    public  void sender(EmailConfig config){
        MimeMessage message = sender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,true);
            helper.setFrom(config.getUsername());
            helper.setTo(recebedores.toArray(new InternetAddress[0]));
            helper.setSubject(subject);
            helper.setText(body,true);
            if (file !=null){
                helper.addAttachment(file.getName(),file);
            }
            sender.send(message);
            logger.info("Email enviado com sucesso");
            reset();
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }
    public void reset(){
        this.to = null;
        this.body = null;
        this.subject = null;
        this.recebedores = null;
        this.file = null;
    }
}
