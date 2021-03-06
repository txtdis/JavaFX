package ph.txtdis.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javafx.collections.ObservableList;
import ph.txtdis.model.Customer;
import ph.txtdis.model.Invoicing;
import ph.txtdis.model.Remittance;
import ph.txtdis.model.RemittanceDetail;
import ph.txtdis.type.RemittanceType;

public interface RemittanceDTO extends Spun, Audited<Remittance> {

    Customer getPartner();

    void setPartner(Customer partner);

    int getPartnerId();

    String getPartnerName();

    String getPartnerAddress();

    RemittanceType getType();

    void setType(RemittanceType type);

    String getReference();

    void setReference(String reference);

    BigDecimal getTotalValue();

    void setTotalValue(BigDecimal amount);

    LocalDate getOrderDate();

    void setOrderDate(LocalDate orderDate);

    String getRemarks();

    void setRemarks(String remarks);

    ObservableList<RemittanceDetail> getDetails();

    void setDetails(List<RemittanceDetail> details);

    BigDecimal getPayment(Invoicing invoice);
}
