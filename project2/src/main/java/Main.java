import controllers.BlockController;
import services.BlockDbService;
import services.BlockRESTService;
import services.SignService;
import views.BlockChainFrame;


/**
 * Created by IntelliJ IDEA
 * Date: 29.11.2021
 * Time: 12:07 AM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
public class Main {

    public static void main(String[] args) {
        SignService service = new SignService();
        BlockDbService db = new BlockDbService();
        BlockRESTService api = new BlockRESTService();
        BlockController controller = new BlockController(service, db, api);
        new BlockChainFrame(controller);
    }
}
