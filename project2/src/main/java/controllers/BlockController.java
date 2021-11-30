package controllers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import models.Block;
import models.BlockChain;
import models.BlockData;
import models.NewBlockResponse;
import services.BlockDbService;
import services.BlockRESTService;
import services.SignService;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA
 * Date: 29.11.2021
 * Time: 1:11 PM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */

@AllArgsConstructor
public class BlockController {
    @Getter
    @Setter
    private SignService signer;
    private BlockDbService db;
    private BlockRESTService api;

    public BlockChain getBlockChain() {
        return api.getChain();
    }

    public BlockChain getUserBlocks() {
        return db.getChain();
    }

    public void createNewBlock(BlockData data) {
        String prevHash = signer.getHash(getBlockChain().getLastBlock());
        String signature = signer.sign(data);
        Block block = new Block();
        block.setPrevhash(prevHash);
        block.setData(data);
        block.setSignature(signature);
        block.setPublickey(signer.getPublicKey());

        Block result = api.addBlock(block);
        if (result != null) {
            JOptionPane.showMessageDialog(null,
                    "New Block successfully created and added to chain");
            db.addBlock(result);
        } else {
            JOptionPane.showMessageDialog(null,
                    "An error occured while creating new Block",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        // notifyListeners()
    }
}
