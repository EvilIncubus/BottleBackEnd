package org.bottleProject.dao;

import org.bottleProject.entity.Invoice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        Invoice invoice1 = invoiceDao.create(invoice);
        invoice.setInvoiceId(invoice1.getInvoiceId());
        assertEquals(invoice1, invoice);
        invoiceDao.removeById(invoice.getInvoiceId());
        assertNull(invoiceDao.findById(invoice.getInvoiceId()));
    }

    @Test
    public void testGetAll() {
        List<Invoice> invoices = invoiceDao.getAll(1,2);

        assertNotNull(invoices);
    }

    //Test to check if function correct find customer by id
    @Test
    public void testFindById() {
        Invoice invoice = invoiceDao.findById(1L);

        assertNotNull(invoice);
    }

    //Test to check in function make correct update for customer phone number
    @Test
    public void testUpdate() {
        Invoice invoice = invoiceDao.findById(1L);
        String oldName = invoice.getFileName();
        invoice.setFileName("Invoice2023-02-01");
        Invoice invoice1 = invoiceDao.update(invoice, 1L);

        assertNotNull(invoice1);
        assertEquals(invoice, invoice1);
        invoice.setFileName(oldName);
        invoiceDao.update(invoice, 1L);
    }
}
