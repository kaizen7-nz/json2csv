package kaizen7.j2c;

import static kaizen7.collection.Collections.cloneList;
import static kaizen7.collection.Collections.cloneMap;
import static kaizen7.collection.Collections.newList;
import static kaizen7.collection.Collections.newMap;
import static kaizen7.collection.Collections.newSet;

import com.google.gson.JsonElement;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.annotation.Nonnull;

class PropertyMapper {

    private Set<String> columns = newSet();
    private List<String> requiredColumns = newList();
    private List<Map<String, String>> mappedJson = newList();

    void map(@Nonnull JsonElement node) {
        if (node.isJsonArray()) {
            for (JsonElement jsonElement : node.getAsJsonArray()) {
                Map<String, String> row = newMap();
                mappedJson.add(row);
                mappedJson.addAll(buildElement(newList(), "#", jsonElement, row));
            }
        } else if (node.isJsonObject()) {
            Map<String, String> row = newMap();
            mappedJson.add(row);

            traverseObjectNode(newList(), node, row);
        } else {
            // Valid JSON would either be object or array.
        }
    }

    /**
     * @param path The journey so far travelled directly from the root element to this element.
     * @param row Is a representation of the row that needs to be displayed.
     */
    private List<Map<String, String>> buildElement(List<String> path, String elementKey, JsonElement element, Map<String, String> row) {
        // Record depth. Ignore arrays.
        if (!"#".equals(elementKey)) {
            path.add(elementKey);
        }

        if (element.isJsonArray()) {
            return traverseArrayNode(path, element, row);
        } else if (element.isJsonObject()) {
            return traverseObjectNode(path, element, row);
        } else {
            String fullPath = String.join(".", path);

            // If this column is new then easy add it.
            if (columns.add(fullPath)) {
                requiredColumns.add(fullPath);
            }
            row.put(fullPath, element.getAsString());
        }

        return newList();
    }

    private List<Map<String, String>> traverseObjectNode(List<String> path, JsonElement element, Map<String, String> row) {
        List<Map<String, String>> rows = newList();
        for (Entry<String, JsonElement> entry : element.getAsJsonObject().entrySet()) {
            List<Map<String, String>> rowsToAdd = cloneList(rows);
            rowsToAdd.addAll(buildElement(cloneList(path), entry.getKey(), entry.getValue(), row));
            for (Map<String, String> mappedRow : rows) {
                rowsToAdd.addAll(buildElement(cloneList(path), entry.getKey(), entry.getValue(), mappedRow));
            }
            rows.addAll(rowsToAdd);
        }
        return rows;
    }

    private List<Map<String, String>> traverseArrayNode(List<String> path, JsonElement element, Map<String, String> row) {
        int i = 0;
        List<Map<String, String>> rows = newList();
        for (JsonElement jsonElement : element.getAsJsonArray()) {
            if (i == 0) {
                // Add first as this row.
                buildElement(path, "#", jsonElement, row);
            } else {
                // Add next as following row.
                Map<String, String> clonedRow = cloneMap(row);
                rows.add(clonedRow);
                rows.addAll(buildElement(cloneList(path), "#", jsonElement, clonedRow));
            }
            i++;
        }
        return rows;
    }

    @Nonnull
    List<String> toList() {
        List<String> result = newList();

        // Add column headers.
        result.add(String.join(",", requiredColumns));

        for (Map<String, String> row : mappedJson) {
            result.add(rowToString(row));
        }
        return result;
    }

    private String rowToString(Map<String, String> row) {
        List<String> rowList = newList();
        rowList.add(row.toString());

        for (String column : requiredColumns) {
            rowList.add(getValue(row.get(column)));
        }
        return String.join(",", rowList);
    }

    private String getValue(String value) {
        return value == null ? "" : value;
    }
}
