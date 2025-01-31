package com.example.sistema_servicos.model;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailService {

	public void enviarEmail(String para, String assunto, String conteudo) throws MessagingException {
	    // Configurações do servidor SMTP
	    String host = "smtp.gmail.com";
	    String porta = "587"; // Porta padrão para TLS
	    String email = "andrade.claudinez@gmail.com"; // Substitua pelo seu e-mail
	    String senha = "ulqg dxld tlrw xogl"; // Substitua pela sua senha ou token de app

	    // Propriedades do e-mail
	    Properties props = new Properties();
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.smtp.host", host);
	    props.put("mail.smtp.port", porta);
	    props.put("mail.smtp.ssl.trust", host); // Opcional, mas recomendado

	    // Autenticação
	    Session session = Session.getInstance(props, new Authenticator() {
	        @Override
	        protected PasswordAuthentication getPasswordAuthentication() {
	            return new PasswordAuthentication(email, senha);
	        }
	    });

	    try {
	        // Criação da mensagem
	        Message mensagem = new MimeMessage(session);
	        mensagem.setFrom(new InternetAddress(email));
	        mensagem.setRecipient(Message.RecipientType.TO, new InternetAddress(para));
	        mensagem.setSubject(assunto);
	        mensagem.setText(conteudo);

	        // Envia o e-mail
	        Transport.send(mensagem);
	        System.out.println("E-mail enviado com sucesso para: " + para);
	    } catch (MessagingException e) {
	        System.err.println("Erro ao enviar e-mail: " + e.getMessage());
	        throw new RuntimeException("Erro ao enviar e-mail", e);
	    }
	}
}