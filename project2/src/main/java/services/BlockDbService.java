package services;

import models.Block;
import models.BlockChain;
import models.BlockData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
public class BlockDbService implements BlockService {
    private Connection conn;
    public BlockDbService() {
        DbWorker.getInstance();
        conn = DbWorker.getConnection();
        preConnect(conn);
    }
    @Override
    public BlockChain getChain() {
        ArrayList<Block> blocks = new ArrayList<>();
        try {
            ResultSet rs = conn.createStatement()
                    .executeQuery("SELECT us.prevhash, us.signature, d.data, d.name, us.ts, us.publickey FROM user_blocks"
                            + " AS us JOIN user_block_data as d ON d.id = us.data_id");

            while (rs.next()) {
                Block b = new Block();
                b.setPrevhash(rs.getString("prevhash"));
                b.setSignature(rs.getString("signature"));
                String data = rs.getString("data");
                String name = rs.getString("name");
                b.setData(new BlockData(data, name));
                b.setTs(rs.getString("ts"));
                b.setPublickey(rs.getString("publickey"));
                blocks.add(b);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new BlockChain(blocks);
    }

    @Override
    public Block addBlock(Block newBlock) {
        try {
            String sql = "INSERT INTO user_block_data(data,name) " +
                    "VALUES ('"+newBlock.getData().getData()+"','"+newBlock.getData().getName()+"');";
            int dataId = 0;
            PreparedStatement statement =conn.prepareStatement(sql,new String[]{"id"});
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating new Block data failed, no rows affected.");
            }

            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    dataId = rs.getInt(1);
                }
            } catch (SQLException e) {
                throw e;
            }
            // add block
            sql = "INSERT INTO user_blocks(prevhash, signature, data_id, ts, publickey) VALUES ('" +
                    newBlock.getPrevhash()+"'," +
                    "'"+newBlock.getSignature()+"',"+dataId+","+
                    "'"+newBlock.getTs()+"',"
                    +"'"+newBlock.getPublickey()+"');";
            int updated = conn.createStatement().executeUpdate(sql);
            if (updated != 0) {
                return newBlock;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void preConnect(Connection conn) {
        try {
            conn.createStatement().execute("CREATE TABLE user_block_data(" +
                    "id SERIAL NOT NULL PRIMARY KEY," +
                    "data TEXT," +
                    "name TEXT" +
                    ");");
            conn.createStatement().execute("CREATE TABLE user_blocks(" +
                    "prevhash VARCHAR(64)," +
                    "signature VARCHAR(256)," +
                    "data_id INTEGER REFERENCES user_block_data(id)," +
                    "ts VARCHAR," +
                    "publickey TEXT" +
                    ");");
        } catch (SQLException ignored) {
        }
    }
}
