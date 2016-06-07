/**
 * Created by Farzin on 5/31/2016.
 */
import org.quartz.listeners.JobListenerSupport

class NopSessionBinderJobListener extends JobListenerSupport {
    String getName() { return "sessionBinderListener" }
}
