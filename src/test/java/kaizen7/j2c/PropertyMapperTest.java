package kaizen7.j2c;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStreamReader;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PropertyMapperTest {

    private PropertyMapper mapper = new PropertyMapper();
    private JsonParser parser = new JsonParser();

    @Test
    @DisplayName("Empty JSON")
    void toList() {
        mapper.map(new JsonObject());

        assertThat(mapper.toList().size()).isGreaterThan(0);
    }

    @Test
    @DisplayName("Simple JSON")
    void simpleJsonToList() {
        JsonElement element = parser.parse(new InputStreamReader(getClass().getResourceAsStream("/some.json")));
        mapper.map(element);

        List<String> rows = mapper.toList();
        assertThat(rows.size()).isEqualTo(2);
        assertThat(rows.get(0)).isEqualTo("url,name,size,date");
        assertThat(rows.get(1)).isEqualTo("https://www.google.com,Google,100,Mon 16:14 4/3/2019");
    }

    @Test
    @DisplayName("JSON array")
    void jsonArrayToList() {
        JsonElement element = parser.parse(new InputStreamReader(getClass().getResourceAsStream("/json-array.json")));
        mapper.map(element);

        List<String> rows = mapper.toList();
        assertThat(rows.size()).isEqualTo(3);
        assertThat(rows.get(0)).isEqualTo("url,name,size,date");
        assertThat(rows.get(1)).isEqualTo("https://www.google.com,Google,100,Mon 16:14 4/3/2019");
        assertThat(rows.get(2)).isEqualTo("https://www.yahoo.com,Yahoo,59.7,2019/03/04T16:17:55.007Z");
    }

    @Test
    @DisplayName("More complicated")
    void jsonNestedToList() {
        JsonElement element = parser.parse(new InputStreamReader(getClass().getResourceAsStream("/json-array-nested.json")));
        mapper.map(element);

        List<String> rows = mapper.toList();
        assertThat(rows.size()).isEqualTo(6);
        assertThat(rows.get(0)).isEqualTo("url,name,size,date,childOrganisations.name,parent.name,searchPartners.url,searchPartners.name");
        assertThat(rows.get(1)).isEqualTo("https://www.google.com,Google,100,Mon 16:14 4/3/2019,g-mail,Alphabet,,");
        assertThat(rows.get(3)).isEqualTo("https://www.yahoo.com,Yahoo,59.7,2019/03/04T16:17:55.007Z,,https://www.google.com,Google");
    }
}
