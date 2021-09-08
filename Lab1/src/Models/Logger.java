package Models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA
 * Date: 01.09.2021
 * Time: 2:52 PM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
public class Logger {
    private DataError error;
    private Long time;
    private boolean timeTracked;
    private Double result;

    public Logger(DataError error){
        this.error = error;
    }
    public Logger(DataError error, Long time, boolean timeTracked) {
        this(error);
        this.time = time;
        this.timeTracked = timeTracked;
    }

    public Logger(Double result) {
        this.result = result;
    }

    public Logger(Double result, Long time, boolean timeTracked) {
        this(result);
        this.time = time;
        this.timeTracked = timeTracked;
    }

    public void log() {
        Map<String,Object> json = new HashMap<>();
        if (error != null) {
            json.put("error", error.getError());
            json.put("description", error.getDescription());
        } else if (result != null) {
            json.put("r",result);
        }
        if (time != null && timeTracked) {
            json.put("time_in_nanosec", time);
        }
        prettyPrint(json);
        System.exit(0);
    }

    private void prettyPrint(Map<String,Object> json) {
        System.out.print("{");
        int counter = 1;
        int size = json.size();
        for(Map.Entry<String,Object> entry: json.entrySet()) {
            System.out.print(entry.getKey()+": "+entry.getValue());
            if(counter < size) {
                System.out.print(", ");
            }
            counter++;
        }
        System.out.println("}");
    }
}
