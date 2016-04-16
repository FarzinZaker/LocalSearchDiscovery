package agahisaz

class ImageController {
    def userService

    def index() {
        switch (params.type) {
            case 'profile':
                render userService.getProfileImage(params.id)
        }
    }
}
