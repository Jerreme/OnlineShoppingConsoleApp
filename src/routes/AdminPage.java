package routes;

import controllers.AdminPageController;
import controllers.CredentialManager;
import controllers.Navigator;
import controllers.Tasker;
import interfaces.Route;
import views.AdminPageView;

public class AdminPage implements Route {

    @Override
    public void init() {
        Route.super.init();
        final String username = CredentialManager.getLoggedInAdmin().username();
        final AdminPageView view = new AdminPageView();
        view.welcomeAdmin(username);
    }

    @Override
    public void build() {
        final AdminPageView view = new AdminPageView();
        view.showAdminOptions();
        final AdminPageController controller = new AdminPageController(view);

        Tasker tasker = new Tasker(this.toString());
        tasker.addTask(1, controller::showProducts);
        tasker.addTask(2, controller::addProduct);
        tasker.addTask(3, controller::modifyProduct);
        tasker.addTask(4, controller::deleteProduct);
        tasker.addTask(5, controller::deleteAllProducts);
        tasker.addTask(6, controller::showRegisteredUsers);
        tasker.addTask(7, controller::showRegisteredAdmins);
        tasker.addTask(0, this::logout);
        tasker.runPrompt();
    }

    private void logout() {
        CredentialManager.logout();
        final AdminPageView view = new AdminPageView();
        view.showLogoutMessage();
        Navigator.runRouteManually(new IntroPage());
    }

}
