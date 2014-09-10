package ph.txtdis.fx.tab;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import ph.txtdis.App;
import ph.txtdis.dto.ChannelDTO;
import ph.txtdis.dto.ItemDTO;
import ph.txtdis.fx.dialog.PricingDialog;
import ph.txtdis.fx.table.AbstractTable;
import ph.txtdis.fx.util.FX;
import ph.txtdis.model.Channel;
import ph.txtdis.model.Pricing;
import ph.txtdis.model.SystemUser;
import ph.txtdis.type.PricingType;

public class PricingTab extends AbstractTab<Pricing, ItemDTO> {
    private TableView<Pricing> table;

    public PricingTab(Stage stage, ItemDTO dto) {
        super("Pricing", stage, dto);
    }

    @Override
    protected Node[] addNodes(Stage stage, ItemDTO dto) {

        table = new AbstractTable<Pricing, ItemDTO>(stage, dto) {

            @Override
            @SuppressWarnings("unchecked")
            protected void addTableColumns() {

                ChannelDTO channel = App.getContext().getBean(ChannelDTO.class);

                TableColumn<Pricing, PricingType> typeCol = FX.addComboColumn("Type", "type", PricingType.values());
                TableColumn<Pricing, BigDecimal> priceCol = FX.addPriceColumn("Price", "price");
                TableColumn<Pricing, LocalDate> startCol = FX.addDateColumn("Start", "startDate");
                TableColumn<Pricing, Channel> channelLimitCol = FX.addComboColumn("Limited To", "channelLimit",
                        channel.list());
                TableColumn<Pricing, SystemUser> pricedByCol = FX.createColumn("Priced By", "createdBy", 120);
                TableColumn<Pricing, Timestamp> pricedDateCol = FX.createColumn("Priced On", "timeStamp", 180);
                table.getColumns().addAll(typeCol, priceCol, startCol, channelLimitCol, pricedByCol, pricedDateCol);
            }

            @Override
            protected void createInputDialog() {
                inputDialog = new PricingDialog(stage, dto);
            }

        }.getTable();

        return new Node[] { table };
    }
    
    public TableView<Pricing> getTable() {
        return table;
    }

    @Override
    public void save() {
        dto.setPriceHistory(table.getItems());
    }

    @Override
    public void refresh() {
        table.getItems().clear();
        table.getItems().addAll(dto.getPriceHistory());
    }
}
