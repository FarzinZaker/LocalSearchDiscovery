package agahisaz

import com.pars.agahisaz.User

import java.text.DateFormat
import java.text.SimpleDateFormat

class ImageController {

    def springSecurityService

    def get() {
        renderImage(Image.get(params.id)?.content)
    }

    def profile() {
        def content
        def gender = 'male'
        if(params.id){
            content = Image.findByTypeAndOwnerIdAndSize('profile', params.id, params.size?:0)?.content
            if(!content)
             gender = User.get(params.id)?.gender ?: 'male'
        }
        else {
            content = Image.findByTypeAndOwnerIdAndSize('profile', springSecurityService.currentUser?.id, params.size ?: 0)?.content
            if(!content)
                gender = springSecurityService.currentUser?.gender ?: 'male'
        }

        if(!content)
            content = ImageController.classLoader.getResourceAsStream("images/profile/blank_${gender}_${params.size?:200}.png")?.bytes

        renderImage(content)

    }

    private void renderImage(image) {
        if (image && image?.size()) {
            def seconds = 3600 * 24
            DateFormat httpDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
            httpDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            Calendar cal = new GregorianCalendar();
            cal.add(Calendar.SECOND, seconds);
            response.setHeader("Cache-Control", "PUBLIC, max-age=" + seconds + ", must-revalidate");
            response.setHeader("Expires", httpDateFormat.format(cal.getTime()));
            response.contentType = 'image/png'
            response.setStatus(200)
            response.outputStream << (image as byte[])
            response.outputStream.flush()
        } else render ''
    }
}
