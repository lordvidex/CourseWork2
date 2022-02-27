package services;

import ru.itis.models.Block;
import ru.itis.models.BlockChain;
import ru.itis.models.NewBlockResponse;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA
 * Date: 29.11.2021
 * Time: 11:34 AM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
public class BlockRESTService implements BlockService {
    @Override
    public BlockChain getChain() {
        try {
            URL chainUrl = new URL("http://188.93.211.195/dis/chain");
            HttpURLConnection conn = (HttpURLConnection) chainUrl.openConnection();
            conn.connect();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String result = br.lines().collect(Collectors.joining("\n"));
                return BlockChain.fromJson(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Block addBlock(Block newBlock) {
        String url = "http://188.93.211.195/dis/newblock?block=";


        try {
            url += URLEncoder.encode(newBlock.toJsonString(), StandardCharsets.UTF_8);
            URL newBlockUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) newBlockUrl.openConnection();
            conn.connect();
            NewBlockResponse res = NewBlockResponse.fromInputStream(conn.getInputStream());
            if (res == null || res.getStatus() == 2) {
                return null;
            }
            return res.getBlock();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
