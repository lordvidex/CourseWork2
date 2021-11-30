package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA
 * Date: 29.11.2021
 * Time: 12:45 PM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Block {
    private String prevhash;
    private String signature;
    private BlockData data;
    private String ts;
    private String publickey;
    @JsonIgnore private boolean verified = true;

    public String[] toRowData() {
        return new String[] {prevhash, signature, data.getName(), data.getData(), ts, publickey, verified ? "Yes": "No"};
    }

    public String toJsonString() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }

    public byte[] toBytes() {
        ArrayList<Byte> bytes = new ArrayList<>();
        byte[] temp;
        if(prevhash != null) {
          temp  = prevhash.getBytes(StandardCharsets.UTF_8);
           for(byte x: temp) {
               bytes.add(x);
           }
        }
        if (data != null) {
            try {
                temp = data.toJsonString().getBytes(StandardCharsets.UTF_8);
                for(byte x: temp) {
                   bytes.add(x);
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        if (ts != null) {
            temp = ts.getBytes(StandardCharsets.UTF_8);
            for(byte x: temp) {
                bytes.add(x);
            }
        }
        byte[] result = new byte[bytes.size()];
        for (int i = 0; i< bytes.size(); i++) {
            result[i] = bytes.get(i);
        }
        return result;
    }
}
