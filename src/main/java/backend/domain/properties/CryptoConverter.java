package backend.domain.properties;

import java.nio.file.Path;
import java.nio.file.Paths;

import javax.crypto.SecretKey;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.ClassUtils;

import io.u2ware.common.oauth2.crypto.CryptoEncryptor;
import io.u2ware.common.oauth2.crypto.CryptoKeyStore;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CryptoConverter implements AttributeConverter<Crypto, String> {

    private SecretKey secretKey;

    private SecretKey secretKey(){
        if(secretKey != null) return secretKey;
        try{
            String name = ClassUtils.getShortName(getClass());
            String password = getClass().getName();
    		Resource resource = new ClassPathResource(name, getClass());
            Path keyPath = Paths.get(resource.getURI());
            this.secretKey = CryptoKeyStore.load(keyPath, null, password);
        }catch(Exception e){
            e.printStackTrace();
        }
        return secretKey;
    }


    @Override
    public String convertToDatabaseColumn(Crypto attribute) {
        try{
            return CryptoEncryptor.encrypt(attribute.toString(), secretKey());
        }catch(Exception e){
            return "";
        }
    }

    @Override
    public Crypto convertToEntityAttribute(String dbData) {
        try{
            return new Crypto(CryptoEncryptor.decrypt(dbData, secretKey()));
        }catch(Exception e){
            return new Crypto();
        }
    }



   
}
