package org.bottleProject.dao;

import org.bottleProject.entity.Invoice;

public interface InvoiceDao extends Dao<Invoice>{
    Invoice findByOrderId(Long orderId);
}
