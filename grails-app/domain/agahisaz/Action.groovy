package agahisaz

class Action {
    static mapWith = "mongo"

    String name
    Integer grantScore

    static constraints = {
    }

    public static final String BROWSE = "browse"
    public static final String ADD_TIP = "addTip"
    public static final String LIKE_TIP = "likeTip"
    public static final String TIP_REPORT_ACCEPTED = "tipReportAccepted"
    public static final String TIP_REPORT_REJECTED = "tipReportRejected"
    public static final String ACCEPT_TIP_REPORT = "acceptTipReport"
    public static final String REJECT_TIP_REPORT = "rejectTipReport"
    public static final String ADD_PLACE = "addPlace"
    public static final String RATE_PLACE = "ratePlace"
    public static final String EDIT_ACCEPTED = "editAccepted"
    public static final String EDIT_REJECTED = "editRejected"
    public static final String ACCEPT_EDIT = "acceptEdit"
    public static final String REJECT_EDIT = "rejectEdit"
    public static final String LOGIN = "login"
}
