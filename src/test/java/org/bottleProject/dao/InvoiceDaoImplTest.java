package org.bottleProject.dao;

import org.bottleProject.entity.Invoice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("integration_test")
public class InvoiceDaoImplTest {

    @Autowired
    InvoiceDao invoiceDao;

    @Test
    public void createTest(){
        Invoice invoice = new Invoice();
        invoice.setFileId("sd4f6vd6v5fv");
        invoice.setFileName("TestInvoice.xlsx");
        invoice.setOrderId(1);
        System.out.println(invoiceDao.create(invoice));
    }
}
