package si.magerl.spending.tracker.metal.dto;

import java.util.Map;

public record MetalAPIResponse(Boolean success, String base, Long timestamp, Map<String, Double> rates) {}
