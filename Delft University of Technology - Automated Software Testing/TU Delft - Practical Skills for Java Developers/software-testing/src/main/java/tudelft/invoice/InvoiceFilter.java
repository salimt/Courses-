package tudelft.invoice;

import java.util.ArrayList;
import java.util.List;

public class InvoiceFilter {

    public List<Invoice> filter() {

        InvoiceDao invoiceDao = new InvoiceDao();
        List<Invoice> allInvoices = invoiceDao.all();

        List<Invoice> filtered = new ArrayList<>();

        for(Invoice inv : allInvoices) {
            if(inv.getValue() < 100.0)
                filtered.add(inv);
        }

        return filtered;

    }
}