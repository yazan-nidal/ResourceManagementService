package exp.exalt.ps.models;

public enum State {
    ACTIVE,CREATING,STOPPED;
    public Long getState() {
        switch(this) {
            case ACTIVE:
                return 0L;
            case CREATING:
                return 1L;
            case STOPPED:
                return 2L;
            default:
                return null;
        }
    }

    public String getStatus() {
        switch (this) {
            case ACTIVE:
                return "ACTIVE";
            case CREATING:
                return "CREATING";
            case STOPPED:
                return "STOPPED";
            default:
                return null;
        }
    }
}
