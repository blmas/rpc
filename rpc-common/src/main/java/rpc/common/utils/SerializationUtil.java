package rpc.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * FileName: SerializationUtil
 *
 * @author: zhanyigeng
 * Date:     2021/10/18
 * Description:
 */
public class SerializationUtil {

    public static byte[] serialize(Object obj) {

        byte[] bytes = null;

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {

            oos.writeObject(obj);
            bytes = bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("objectToByteArray serialization error", e);
        }
        return bytes;
    }

    public static <T> T deserialize(byte[] bytes, Class<T> tClass) {
        if (bytes == null || bytes.length == 0) {
            throw new RuntimeException("byteArrayToObject deserialization error, bytes is null or bytes.length is 0");
        }
        Object obj;
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            obj = ois.readObject();
        } catch (Exception e) {
            throw new RuntimeException("byteArrayToObject deserialization error", e);
        }
        return (T) obj;
    }
}
