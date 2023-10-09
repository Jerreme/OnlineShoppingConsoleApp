package routes;

import controllers.CredentialManager;
import controllers.Navigator;
import interfaces.Route;
import views.HomePageView;

public class HomePage implements Route {

    @Override
    public void init() {
        Route.super.init();
        final String username = CredentialManager.getLoggedInUser().username();
        final HomePageView view = new HomePageView();
        view.welcomeUser(username);
    }

    @Override
    public void build() {
        final HomePageView view = new HomePageView();
        view.showHomePageOptions();

        final Navigator navigator = new Navigator();
        navigator.addRoute(1, new ProductsPage());
        navigator.addRoute(2, new PurchaseLogsPage());
        navigator.addRoute(3, new ProfilePage());
        navigator.addRoute(0, new IntroPage());
        navigator.addPreCallback(0, this::logout);
        navigator.runPrompt();
    }

    private void logout() {
        CredentialManager.logout();
        final HomePageView view = new HomePageView();
        view.showLogoutMessage();
    }

}
