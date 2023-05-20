package org.bottleProject.entity;

import java.util.Objects;

public class Invoice {
    private long invoiceId;
    private int orderId;
    private String fileName;
    private String fileId;

    public Invoice(long invoiceId,int orderId, String fileName, String fileId) {
        this.invoiceId = invoiceId;
        this.fileName = fileName;
        this.fileId = fileId;
    }

    public Invoice() {

    }

    public long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "invoiceId=" + invoiceId +
                ", fileName=" + fileName +
                ", fileId=" + fileId +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Invoice invoice = (org.bottleProject.entity.Invoice) obj;

        boolean idMatches = Objects.equals(invoiceId,invoice.invoiceId);
        boolean allFieldsMach = Objects.equals(fileName,invoice.fileName);

        return idMatches && allFieldsMach;
    }
}
