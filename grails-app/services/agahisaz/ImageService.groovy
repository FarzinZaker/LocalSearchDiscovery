package agahisaz

import org.apache.commons.codec.binary.Base64
import org.bson.types.Binary

import javax.imageio.ImageIO
import java.awt.Image
import java.awt.image.BufferedImage

class ImageService {

    def saveImage(String content, String type, String ownerId) {
        saveImage(Base64.decodeBase64(content.replace('data:image/png;base64,', '')), type, ownerId)
    }

    def saveImage(byte[] content, String type, String ownerId) {

//        def image = new agahisaz.Image()
//        image.ownerId = ownerId
//        image.type = type
//        image.size = 0
//        image.content = content
//        image.save(flush: true)
        def image
        [32, 44, 64, 88, 100, 120, 150, 200].each {
            def bytes = scaleImage(content, it, it)
            if (bytes) {
                image = new agahisaz.Image()
                image.ownerId = ownerId
                image.type = type
                image.size = it
                image.bytes = new Binary(bytes)
                image.save(flush: true)
            }
            }
    }


    def scaleImage(byte[] content, int width, int height) {

        BufferedImage sourceImage = ImageIO.read(new ByteArrayInputStream(content))
        if (sourceImage) {
            def w = sourceImage.width
            def h = sourceImage.height

            def thumbnail
            if (w > h)
                thumbnail = sourceImage.getScaledInstance(width, -1, Image.SCALE_SMOOTH);
            else
                thumbnail = sourceImage.getScaledInstance(-1, height, Image.SCALE_SMOOTH);

            BufferedImage bufferedThumbnail = new BufferedImage(thumbnail.getWidth(null),
                    thumbnail.getHeight(null), sourceImage.type);
            bufferedThumbnail.getGraphics().drawImage(thumbnail, 0, 0, null);
            bufferedThumbnail.getGraphics().dispose()
            def stream = new ByteArrayOutputStream()
            ImageIO.write(bufferedThumbnail, 'png', stream)
            return stream.toByteArray()
        }
    }
}
