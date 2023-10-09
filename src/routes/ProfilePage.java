package routes;

import controllers.CredentialManager;
import controllers.Navigator;
import controllers.ProfilePageController;
import controllers.Tasker;
import interfaces.Route;
import views.ProfilePageView;


public class ProfilePage implements Route {
    @Override
    public void build() {
        ProfilePageView view = new ProfilePageView();
        view.showProfilePage(CredentialManager.getLoggedInUser());
        view.printDashSeparator();
        view.showProfileOptions();
        ProfilePageController controller = new ProfilePageController(view);
        Tasker tasker = new Tasker(this.toString());
        tasker.addTask(1, controller::cashIn);
        tasker.addTask(0, Navigator::gotoLastRoute);
        tasker.runPrompt();
    }
}
