package kaizen7.j2c;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.gson.JsonObject;
import java.util.List;
import org.junit.jupiter.api.Test;

class FlattenJsonTest {

    private FlattenJson flatten = new FlattenJson();

    @Test
    void jsonToCsv() {
        List<String> csv = flatten.jsonToCsv(new JsonObject());

        assertThat(csv.size()).isGreaterThan(0);
    }
}
