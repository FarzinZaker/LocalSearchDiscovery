package agahisaz

import com.mongodb.DBObject

class MongoService {
    private def db_
    def mongo
    def Map<String, Object> collections = [:]

    private def getDb() {
        if(!db)
            db_ = mongo.getDB("agahisaz")
        db_
    }

    def getCollection(String collectionName){
        if(!collections?.containsKey(collectionName))
           collections.put(collectionName, db.getCollection(collectionName))
        collections[collectionName]
    }

//    def query(String collectionName, Map criteria, projection = null) {
//        db.getCollection(collectionName).find(criteria, projection)
//    }
//
//    def count(String collectionName, criteria) {
//        db.getCollection(collectionName).count(criteria)
//    }
//
//    def createIndex(String collectionName, String name, Map fields, Map weights) {
//        db.getCollection(collectionName).createIndex(fields, [weights: weights, name: name])
//    }
}
