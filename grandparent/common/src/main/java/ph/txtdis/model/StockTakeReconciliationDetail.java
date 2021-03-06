package ph.txtdis.model;

import java.math.BigDecimal;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class StockTakeReconciliationDetail {

    private Item item;

    private Quality quality;

    private BigDecimal startQty = BigDecimal.ZERO;

    private BigDecimal startAdjustQty = BigDecimal.ZERO;

    private BigDecimal inQty = BigDecimal.ZERO;

    private BigDecimal outQty = BigDecimal.ZERO;

    private BigDecimal countQty = BigDecimal.ZERO;

    private BigDecimal adjustmentQty = BigDecimal.ZERO;

    private String justification;

    public BigDecimal getSystemQty() {
        return getStartQty().add(getStartAdjustQty()).add(getInQty()).subtract(getOutQty());
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }
}
