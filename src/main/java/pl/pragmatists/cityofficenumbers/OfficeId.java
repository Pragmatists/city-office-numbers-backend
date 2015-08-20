package pl.pragmatists.cityofficenumbers;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class OfficeId {
    private final String officeIdAsString;

    public OfficeId(String officeIdAsString) {
        this.officeIdAsString = officeIdAsString;
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
