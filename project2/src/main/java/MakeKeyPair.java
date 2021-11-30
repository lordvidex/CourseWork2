

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import java.io.FileWriter;
import java.io.Writer;
import java.security.*;

public class MakeKeyPair {

    public static void main(String[] args) {

        Security.addProvider(new BouncyCastleProvider());

        KeyPairGenerator rsa = null;
        try (Writer publicKeyWriter = new FileWriter("public.key");
             Writer privateKeyWriter = new FileWriter("private.key")) {
            rsa = KeyPairGenerator.getInstance("RSA",BouncyCastleProvider.PROVIDER_NAME);
            rsa.initialize(1024, new SecureRandom());
            KeyPair keyPair = rsa.generateKeyPair();

            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();

            privateKeyWriter.write(new String(Hex.encode(privateKey.getEncoded())));
            publicKeyWriter.write(new String(Hex.encode(publicKey.getEncoded())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
