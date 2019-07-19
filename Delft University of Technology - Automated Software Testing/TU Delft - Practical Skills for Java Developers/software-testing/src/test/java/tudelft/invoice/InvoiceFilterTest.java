package tudelft.invoice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class InvoiceFilterTest {
    @Test
    void filterInvoices() {

        InvoiceDao dao = new InvoiceDao();
        Invoice mauricio = new Invoice("Mauricio", 20.0);
        Invoice arie = new Invoice("Arie", 300.0);

        dao.save(mauricio);
        dao.save(arie);

        InvoiceFilter filter = new InvoiceFilter();
        List<Invoice> result = filter.filter();

        Assertions.assertEquals(mauricio, result.get(0));
        Assertions.assertEquals(1, result.size());

        dao.close();
    }

}
