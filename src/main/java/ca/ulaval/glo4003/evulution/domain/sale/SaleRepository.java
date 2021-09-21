package ca.ulaval.glo4003.evulution.domain.sale;
import java.util.List;

public interface SaleRepository {

    void registerSale(Sale sale);

    List<Sale> getSales();

    Sale getSale(TransactionId transactionId);
}
