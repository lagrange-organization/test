import org.junit.Test;

import java.util.UUID;

/**
 * @author szy
 * @date 2022/9/29 10:22
 */
public class MyTools {
    @Test
    public void generateDictSql() {
        System.out.println(UUID.randomUUID().toString().replace("-", ""));
        String format = String.format("123%s", "2");
    }
}
