package nc.backend.jsonBackstopParser;

import lombok.Getter;
import lombok.Setter;

public class Scenario {
    public String label;
    @Getter
    @Setter
    public String url;
    @Getter
    @Setter
    public String referenceUrl;
    public boolean selectorExpansion;
    public float misMatchThreshold;
    public boolean requireSameDimensions;
}
