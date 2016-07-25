package agahisaz

import agahisaz.old.OldAdvertisement
import agahisaz.old.OldAdvertisementTag
import agahisaz.old.OldCategory
import agahisaz.old.OldLocation
import agahisaz.old.OldTag
import agahisaz.old.OldUser
import com.pars.agahisaz.User
import grails.plugins.springsecurity.Secured
import security.Roles
import utils.StringHelper
import cache.CategoryCache

class OldAdsController {

    def springSecurityService
    def cityService
    def mongoService
    def placeService
    def actionService

    @Secured([Roles.AUTHENTICATED])
    def list() {
        def page = params.page as Integer ?: 1
        def user = springSecurityService.currentUser as User
        def oldUser = OldUser.findByEmailOrMobileNumber(user?.username, user?.username)
        def ads = OldAdvertisement.findAllByUserIdAndApproved(oldUser?.id as Integer, true, [max: 10, offset: (page - 1) * 10])
        def total = OldAdvertisement.countByUserIdAndApproved(oldUser?.id as Integer, true)
        if (user['remainingOldAds'] != total) {
            user['remainingOldAds'] = total
            user.save(flush: true)
        }
        total = total / 10 + (total % 10 > 0 ? 1 : 0)

        [ads: ads, currentPage: page, total: total]
    }

    @Secured([Roles.AUTHENTICATED])
    def delete() {
        def ad = OldAdvertisement.get(params.id)
        if (ad) {
            ad.approved = false
            ad.save(flush: true)
        }
        flash.info = message(code: 'oldAdds.delete.success', args: [ad.title])
        redirect(action: 'list')
    }

    @Secured([Roles.AUTHENTICATED])
    def add() {
        def ad = OldAdvertisement.get(params.id as Long)
        def location = OldLocation.get(ad?.locationId)
        def tags = OldTag.findAllByIdInListAndItemsCountGreaterThan(OldAdvertisementTag.findAllByAdvertisementId(ad?.id)?.collect {
            it?.tagId
        }, 4)?.collect { StringHelper.normalize(it.name) }
        tags.each { tag ->
            if (!Tag.findByName(tag))
                new Tag(name: tag).save(flush: true)
        }
        def city = StringHelper.normalize(location?.name ?: ' ')

        def oldCategory = OldCategory.findById(ad.categoryId)
//        def parentCategory = agahisaz.old.OldCategory.get(category.parentCategoryId)
        def query = StringHelper.normalize((oldCategory.name?.split(' ')?.findAll { it?.size() > 2 })?.join(' ') + tags)

        def category = Category.get(mongoService.getCollection("category").find([$text: [$search: query]])?.limit(1)?.findAll()?.find()?._id)

        [
                place: [
                        name    : StringHelper.normalize(ad?.title?.replace('-', ' ')?.replace('  ', ' ')),
                        address : StringHelper.normalize(ad?.address),
                        phone   : StringHelper.normalize(ad?.phoneNumber),
                        city    : city,
                        province: cityService.findProvinceByCity(city)?.name,
                        tags    : tags,
                        category: category
                ],
                ad   : [
                        id: ad?.id
                ]
        ]
    }

    @Secured([Roles.AUTHENTICATED])
    def save() {
        def ad = OldAdvertisement.get(params.adId)
        if (ad) {
            if (ad.approved) {
                def place = placeService.save(params)
                if (place) {

                    ad.approved = false
                    ad.save(flush: true)

                    actionService.doAction(Action.ADD_PLACE)

                    flash.info = message(code: 'oldAdds.add.success', args: [createLink(controller: 'place', action: 'view', params: [id: place?.id, name: place.name]), ad.title])
                    redirect(action: 'list')
                } else {
                    flash.error = place.errors
                    redirect(action: 'add', id: params.adId)
                }
            } else {
                flash.info = message(code: 'oldAdds.add.alreadyAdded', args: [ad.title])
                redirect(action: 'list')
            }
        } else {
            flash.info = message(code: 'oldAdds.add.notFound')
            redirect(action: 'list')
        }
    }
}
