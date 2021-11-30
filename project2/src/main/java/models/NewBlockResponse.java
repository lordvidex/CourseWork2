package models;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by IntelliJ IDEA
 * Date: 29.11.2021
 * Time: 7:20 PM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewBlockResponse {
    private int status; // 0 - ok, 2 - error
    private String statusString; // error description
    private Block block;    // new block data

    public static NewBlockResponse fromInputStream(InputStream is) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            return mapper.readValue(is, NewBlockResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
