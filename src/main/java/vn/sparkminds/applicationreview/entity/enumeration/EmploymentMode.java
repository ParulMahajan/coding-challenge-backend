package vn.sparkminds.applicationreview.entity.enumeration;

public enum EmploymentMode {
    FREELANCE, EMPLOYED;

    public String toReadableString() {
        switch (this) {
            case FREELANCE: return "Freelance";
            default: return "Employed";
        }
    }
}
