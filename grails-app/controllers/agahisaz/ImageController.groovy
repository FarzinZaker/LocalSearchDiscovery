package agahisaz

import java.text.DateFormat
import java.text.SimpleDateFormat

class ImageController {

    def springSecurityService

    def get() {
        renderImage(Image.get(params.id))
    }

    def profile() {
        if(params.id)
            renderImage(Image.findByTypeAndOwnerIdAndSize('profile', params.id, params.size?:0))
        else
            renderImage(Image.findByTypeAndOwnerIdAndSize('profile', springSecurityService.currentUser?.id, params.size?:0))

    }

    private void renderImage(Image image) {
        if (image?.content) {
            def seconds = 3600 * 24
            DateFormat httpDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
            httpDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            Calendar cal = new GregorianCalendar();
            cal.add(Calendar.SECOND, seconds);
            response.setHeader("Cache-Control", "PUBLIC, max-age=" + seconds + ", must-revalidate");
            response.setHeader("Expires", httpDateFormat.format(cal.getTime()));
            response.contentType = 'image/png'
            response.setStatus(200)
            response.outputStream << (image?.content as byte[])
            response.outputStream.flush()
        } else render ''
    }
}
