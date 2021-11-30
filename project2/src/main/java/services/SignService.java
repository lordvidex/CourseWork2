package services;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import models.Block;
import models.BlockData;
import org.bouncycastle.util.encoders.Hex;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by IntelliJ IDEA
 * Date: 29.11.2021
 * Time: 11:10 PM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
public class SignService {
    public static final String DIGEST_ALGORITHM = "SHA-256";
    public static final String KEY_ALGORITHM = "RSA";
    public static final String SIGN_ALGORITHM = "SHA256withRSA";
    private PrivateKey privateKey;
    private String publicKey;

    public SignService() {
        try(BufferedReader br = new BufferedReader(new FileReader("project2/src/main/private.key"));
        BufferedReader br2 = new BufferedReader(new FileReader("project2/src/main/public.key"))
        ) {
            this.privateKey = convertArrayToPrivateKey(Hex.decode(br.readLine()),KEY_ALGORITHM);
            this.publicKey = br2.readLine();
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getPublicKey() {
        return publicKey;
    }


    public static PublicKey convertArrayToPublicKey(byte[] encoded, String algorithm) throws Exception {
        X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encoded);
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);

        return keyFactory.generatePublic(pubKeySpec);
    }

    public static PrivateKey convertArrayToPrivateKey(byte[] encoded, String algorithm) throws Exception {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        return keyFactory.generatePrivate(keySpec);
    }


    private byte[] getByteHash(Block block) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(DIGEST_ALGORITHM);
        return digest.digest(block.toBytes());
    }

    public String getHash(Block block) {
        try {
            return new String(Hex.encode(getByteHash(block)));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] generateSignature(String jsonData) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        Signature signature = Signature.getInstance(SIGN_ALGORITHM);
        signature.initSign(privateKey);
        signature.update(jsonData.getBytes(StandardCharsets.UTF_8));
        return signature.sign();
    }

    public String sign(BlockData data) {
        try {
            return new String(Hex.encode(generateSignature(data.toJsonString())));
        } catch (JsonProcessingException | NoSuchAlgorithmException | SignatureException | InvalidKeyException e) {
            e.printStackTrace();
            return null;
        }
    }
}
