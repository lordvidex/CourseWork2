package services;

import models.Block;
import models.BlockChain;

/**
 * Created by IntelliJ IDEA
 * Date: 29.11.2021
 * Time: 11:45 AM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
public interface BlockService {
    BlockChain getChain();
    Block addBlock(Block newBlock);
}
