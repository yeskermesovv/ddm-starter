package kz.dara.service;

import java.util.List;
import java.util.Map;

public interface CatalogService {
    List<Map<String, Object>> getFromItems(String domain);
    Map<String, Object> getFromDdm(String domain, String item);
}
