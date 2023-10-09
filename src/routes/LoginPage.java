package routes;

import controllers.LoginController;
import controllers.Navigator;
import interfaces.Credential;
import interfaces.Route;
import models.Admin;
import models.User;
import views.LoginPageView;

public class LoginPage implements Route {
    @Override
    public void build() {
        final LoginPageView view = new LoginPageView();
        final LoginController controller = new LoginController(view);
        final Credential userForLogin = controller.promptLogin();

        if (userForLogin == null) {
            Navigator.gotoLastRoute();
            return;
        }

        final Credential user = controller.submitLoginCredential(userForLogin);
        if (user == null) {
            build();
            return;
        }

        if (user instanceof Admin) {
            Navigator.runRouteManually(new AdminPage());
        } else if (user instanceof User) {
            Navigator.runRouteManually(new HomePage());
        }
    }
}
