package agahisaz

import com.mongodb.DBObject

class MongoService {
    private def db_
    def mongo

    private def getDb() {
        db_ = mongo.getDB("agahisaz")
    }

    def getCollection(String collectionName){
        db.getCollection(collectionName)
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
