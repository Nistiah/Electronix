package com.example.zai.mail;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.example.zai.dto.OrderDTO;
import com.example.zai.dto.OrderProductDTO;
import com.example.zai.models.Order;
import com.example.zai.models.OrderProduct;
import com.example.zai.models.User;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;


public class UserPDFExporter {
    private OrderDTO order;

    public UserPDFExporter(OrderDTO order2) {
        this.order = order2;
    }


    public void export() throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("src/main/resources/mail.pdf"));

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph p = new Paragraph("Your Order", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(p);

        List<OrderProductDTO> orderProductDTOList = order.getOrderProducts();

        for (OrderProductDTO orderProductDto : orderProductDTOList) {
             Paragraph p1 = new Paragraph("name "+ orderProductDto.getProductName() + " quantity" + orderProductDto.getQuantity() + " price" + orderProductDto.getPrice());
            p1.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(p1);
        } {

        }



        document.close();


    }
}
