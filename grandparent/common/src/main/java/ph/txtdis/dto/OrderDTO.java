package ph.txtdis.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import ph.txtdis.model.CreditDetail;
import ph.txtdis.model.Customer;
import ph.txtdis.model.ItemDetailed;
import ph.txtdis.model.Ordered;
import ph.txtdis.model.Route;

public interface OrderDTO<E extends Ordered<D>, D extends ItemDetailed> extends Audited<E>, Spun, Detailed<E, D> {

    void setPartner(Customer partner);

    int getPartnerId();

    String getPartnerName();

    String getPartnerAddress();

    LocalDate getOrderDate();

    void setOrderDate(LocalDate orderDate);

    String getRemarks();

    void setRemarks(String remarks);

    void setRoute(Route route);

    void setCredit(CreditDetail credit);

    BigDecimal getTotalValue();

    void setTotalValue(BigDecimal value);
}
