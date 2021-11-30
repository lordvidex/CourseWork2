package models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

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
public class BlockChain {
    private List<Block> blocks;

    public static BlockChain fromJson(String json) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            Block[] blocks = mapper.readValue(json, Block[].class);
            return new BlockChain(List.of(blocks));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }

    }

    public Block getLastBlock() {
        if (blocks == null) return null;
        else return blocks.get(blocks.size()-1);
    }

    public String[][] getData() {
        return blocks.stream().map(Block::toRowData).toArray(String[][]::new);
    }
}
