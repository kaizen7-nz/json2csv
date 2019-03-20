package kaizen7.j2c;

import com.google.gson.JsonObject;
import java.util.List;
import javax.annotation.Nonnull;

class FlattenJson {

    @Nonnull
    List<String> jsonToCsv(@Nonnull JsonObject node) {
        PropertyMapper mapper = new PropertyMapper();
        mapper.map(node);
        return mapper.toList();
    }
}
