package com.example.zai.mail;

import com.example.zai.dto.OrderDTO;
import com.example.zai.dto.OrderProductDTO;
import com.example.zai.repositories.OrderRepository;
import com.example.zai.repositories.UserRepository;
import com.lowagie.text.Document;

import javax.mail.Transport;
import javax.mail.MessagingException;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.List;
import java.util.Properties;


public class ClientWithJakarta {
    private static final String mailFrom = "mailstworzonydozadaniazjavy@gmail.com";
    private String mailTo;
    private static final String host = "smtp.gmail.com";
    private static final String password = "csmrkztjouhftgkj";

    Session session;
    Properties properties = new Properties();

    public String setOrder(OrderDTO orderDto){

        StringBuilder stringBuilder = new StringBuilder(1000);

        stringBuilder.append("Witaj ");
        stringBuilder.append(orderDto.getUsername());
        stringBuilder.append("!\n");
        stringBuilder.append("Twoje zamówienie zostało złożone.");
        stringBuilder.append("Zamówione produkty: \n");
        List<OrderProductDTO> orderProductDTOList = orderDto.getOrderProducts();
        for (OrderProductDTO orderProductDto : orderProductDTOList) {
            stringBuilder.append("Product name: ");
            stringBuilder.append(orderProductDto.getProductName());
            stringBuilder.append("Quantity: ");
            stringBuilder.append(orderProductDto.getQuantity());
            stringBuilder.append("Price per item: ");
            stringBuilder.append(orderProductDto.getPrice());
            stringBuilder.append(" ");
            stringBuilder.append(" \n");
        }
        stringBuilder.append("Cena całkowita: ");
        stringBuilder.append(orderDto.getTotalPrice());
        stringBuilder.append(" \n");

        stringBuilder.append("Gratulujemy wybrania naszego sklepu!");
        return stringBuilder.toString();
        }



    public void sendMail(OrderDTO orderDTO, String mailAdress){
        try {
            mailTo = mailAdress;
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mailFrom));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
            message.setSubject("Zamówienie");
            message.setText(setOrder(orderDTO));



            UserPDFExporter userPDFExporter = new UserPDFExporter(orderDTO);
            userPDFExporter.export();


            Address[] recipientAddresses = InternetAddress.parse(mailTo);
            message.setRecipients(Message.RecipientType.TO, recipientAddresses);

            Transport transport = session.getTransport("smtp");
            transport.connect(host, 587, mailFrom, password);
            transport.sendMessage(message, recipientAddresses);
            transport.close();

        } catch (MessagingException mex) {
            System.out.println("Error: "+ mex.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ClientWithJakarta(){
        properties.setProperty("mail.smtp.socketFactory.port", "587");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.starttls.enable", "true");
        session = Session.getDefaultInstance(properties);
    }

}


//stmpmail
//mailstworzonydozadaniazjavy@gmail.com
