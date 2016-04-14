package agahisaz

class MongoService {
    private def db_
    def mongo

    private def getDb() {
        db_ = mongo.getDB("agahisaz")
    }

    def query(String collectionName, criteria) {
        db.getCollection(collectionName).find(criteria)
    }

    def count(String collectionName, criteria) {
        db.getCollection(collectionName).count(criteria)
    }
}
