package agahisaz

import com.pars.agahisaz.User

import java.text.DateFormat
import java.text.SimpleDateFormat

class ImageController {

    def springSecurityService

    def get() {
        renderImage(Image.get(params.id)?.bytes?.data)
    }

    def profile() {
        def content
        def gender = 'male'
        if (params.id) {
            content = Image.findByTypeAndOwnerIdAndSize('profile', params.id, params.size ?: 0)?.bytes?.data
            if (!content)
                gender = User.get(params.id)?.gender ?: 'male'
        } else {
            content = Image.findByTypeAndOwnerIdAndSize('profile', springSecurityService.currentUser?.id, params.size ?: 0)?.bytes?.data
            if (!content)
                gender = springSecurityService.currentUser?.gender ?: 'male'
        }

        if (!content)
            content = ImageController.classLoader.getResourceAsStream("images/profile/blank_${gender}_${params.size ?: 200}.png")?.bytes

        renderImage(content)

    }

    def placeSearch() {
        def content = Image.findByTypeAndOwnerIdAndSize('placeLogo', params.id, params.size ?: 0)?.bytes?.data
        if (!content) {
            def tipIds = Place.get(params.id)?.tips?.collect { it.id }
            if (tipIds && tipIds?.size())
                content = Image.findByTypeAndOwnerIdInListAndSize('tip', tipIds, params.size ?: 200)?.bytes?.data
        }
        if(!content && params.mobile)
            content = ImageController.classLoader.getResourceAsStream("images/mobile-no-image.jpg")?.bytes
        if (!content)
            content = ImageController.classLoader.getResourceAsStream("images/categories/${Place.get(params.id)?.category?.iconPath}${params.size ?: 88}.png")?.bytes
        if (!content)
            content = ImageController.classLoader.getResourceAsStream("images/no-image.png")?.bytes
        renderImage(content)
    }

    def placeLogo() {
        def content = Image.findByTypeAndOwnerIdAndSize('placeLogo', params.id, params.size ?: 0)?.bytes?.data
        if (!content)
            content = ImageController.classLoader.getResourceAsStream("images/categories/${Place.get(params.id)?.category?.iconPath}${params.size ?: 88}.png")?.bytes
        if (!content)
            content = ImageController.classLoader.getResourceAsStream("images/no-image.png")?.bytes
        renderImage(content)
    }

    def category() {
        def content
        if (params.id?.toString() != '0')
            content = ImageController.classLoader.getResourceAsStream("images/categories/${Category.get(params.id)?.iconPath}${params.size ?: 88}.png")?.bytes
        if (!content)
            content = ImageController.classLoader.getResourceAsStream("images/no-image.png")?.bytes
        renderImage(content)
    }

    def tip() {
        def place
        def content = Image.findByTypeAndOwnerIdAndSize('tip', params.id, params.size ?: 0)?.bytes?.data
        if (!content)
            content = Image.findByTypeAndOwnerIdAndSize('placeLogo', params.placeId, params.size ?: 0)?.bytes?.data
        if (!content) {
            place = Place.get(params.placeId)
            def tipIds = place?.tips?.collect { it.id }
            if (tipIds && tipIds?.size())
                content = Image.findByTypeAndOwnerIdInListAndSize('tip', tipIds, params.size ?: 0)?.bytes?.data
        }
        if (!content)
            content = ImageController.classLoader.getResourceAsStream("images/categories/${place?.category?.iconPath}88.png")?.bytes
        if (!content)
            content = ImageController.classLoader.getResourceAsStream("images/no-image.png")?.bytes
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
