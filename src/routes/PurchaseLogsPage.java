package routes;

import controllers.PurchaseLogsController;
import interfaces.Route;
import views.PurchaseLogsView;

public class PurchaseLogsPage implements Route {

    @Override
    public void build() {
        PurchaseLogsView view = new PurchaseLogsView();
        PurchaseLogsController controller = new PurchaseLogsController(view);
        controller.showPurchasedLogs();
    }
}
