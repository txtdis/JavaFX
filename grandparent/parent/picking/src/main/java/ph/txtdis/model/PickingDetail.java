package ph.txtdis.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class PickingDetail extends AbstractAudited {

    private static final long serialVersionUID = -295635229976937036L;

    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    private Picking picking;

    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    private Booking booking;

    @Transient
    private int bookingId;

    @Transient
    private String partnerName;

    @Transient
    private LocalDate bookingDate;

    protected PickingDetail() {
    }

    public PickingDetail(Picking picking, Booking booking) {
        this.picking = picking;
        this.booking = booking;
    }

    public Picking getPicking() {
        return picking;
    }

    public void setPicking(Picking picking) {
        this.picking = picking;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public int getBookingId() {
        return booking == null ? 0 : booking.getId();
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public String getPartnerName() {
        return booking == null ? null : booking.getPartner().getName();
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public LocalDate getBookingDate() {
        return booking == null ? null : booking.getOrderDate();
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    @Override
    public String toString() {
        return picking + ": " + booking;
    }
}
