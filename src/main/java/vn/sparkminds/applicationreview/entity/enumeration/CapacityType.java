package vn.sparkminds.applicationreview.entity.enumeration;

public enum CapacityType {
    PART_TIME, FULL_TIME;

    public String toReadableString() {
        switch (this) {
            case FULL_TIME: return "Full Time";
            default: return "Part Time";
        }
    }
}
