package ph.txtdis.fx.tab;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import ph.txtdis.App;
import ph.txtdis.dto.PickingDTO;
import ph.txtdis.dto.SummaryDTO;
import ph.txtdis.exception.InvalidException;
import ph.txtdis.model.Truck;

public abstract class AbstractSettlementTab<D> extends AbstractTab<SummaryDTO> implements Mapped, SettlementTab {

    private List<Tab> tabs;
    private TabPane tabPane;

    protected D settlement;
    protected Map<String, Tabbed> tabMap;
    protected Truck truck;

    public AbstractSettlementTab(String name, String id, Stage stage, SummaryDTO dto) {
        super(name, id, stage, dto);
    }

    @Override
    protected Node addTabContent(Stage stage) {
        setTabMap();
        setTabs();
        setTabPane();
        return tabPane;
    }

    private void setTabMap() {
        tabMap = new LinkedHashMap<>();
        putLoadSettlementTabsToMap();
    }

    private void setTabs() {
        tabs = new ArrayList<>();
        tabMap.forEach((string, tabbed) -> tabs.add(tabbed.getTab()));
    }

    private void putLoadSettlementTabsToMap() {
        setSettlementDTO();
        for (Truck truck : getLoadedTrucks()) {
            this.truck = truck;
            tabMap.put(truck.getName(), getPerTruckTab());
        }
    }

    private List<Truck> getLoadedTrucks() {
        PickingDTO picking = App.context().getBean(PickingDTO.class);
        return picking.getLoadedTrucks(dto.getId());
    }

    private void setTabPane() {
        tabPane = new TabPane();
        tabPane.setStyle("-fx-tab-min-width: 80;");
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.getTabs().addAll(tabs);
    }

    @Override
    public boolean isDataReadyToBeSaved() {
        for (Tabbed tab : tabMap.values()) {
            if (!((SettlementTab) tab).isDataReadyToBeSaved())
                return false;
        }
        return true;
    }

    @Override
    public void save() throws InvalidException {
        if (isDataReadyToBeSaved()) {
            for (Tabbed tab : tabMap.values())
                tab.save();
        } else
            throw new InvalidException("Ensure all variances have action taken before saving.");
    }

    @Override
    public void refresh() {
        for (Tabbed tab : tabMap.values())
            tab.refresh();
    }

    @Override
    public BooleanBinding isAllReconciled() {
        if (tabsExist().get())
            return isReconciled();
        else
            return tabsExist();
    }

    private BooleanBinding isReconciled() {
        BooleanBinding isReconciled = tabsExist();
        for (Tabbed tab : tabMap.values())
            isReconciled = isReconciled.and(isReconciled(tab));
        return isReconciled;
    }

    private BooleanBinding tabsExist() {
        return Bindings.isEmpty(tabPane.getTabs()).not();
    }

    private BooleanBinding isReconciled(Tabbed tabbed) {
        return ((SettlementTab) tabbed).isAllReconciled();
    }

    @Override
    public BooleanBinding isAnyOpen() {
        if (tabsExist().get())
            return isOpen();
        else
            return tabsExist();
    }

    private BooleanBinding isOpen() {
        BooleanBinding isOpen = tabsExist().not();
        for (Tabbed tab : tabMap.values())
            isOpen = isOpen.or(isOpen(tab));
        return isOpen;
    }

    private BooleanBinding isOpen(Tabbed tabbed) {
        return ((SettlementTab) tabbed).isAnyOpen();
    }

    @Override
    public Map<String, Tabbed> getTabMap() {
        return tabMap;
    }

    protected abstract Tabbed getPerTruckTab();

    protected abstract void setSettlementDTO();
}
