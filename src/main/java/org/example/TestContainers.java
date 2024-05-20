package org.example;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Filters.eq;

public class TestContainers {

    public static void main(String[] args) {
        //String uri = "mongodb+srv://theuser:pwd@cluster0.k5dqp.mongodb.net";
        String uri = System.getenv("MONGODB_URI");
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("sample_mflix");
            MongoCollection<Document> collection = database.getCollection("movies");

            Bson filter = new Document("imdb.rating", new Document("$gt", 7));
            Bson projection = new Document("title", 1).append("_id", 0);
            int limit = 5;
            FindIterable<Document> documents = collection.find(filter).projection(projection).limit(limit);
            for (Document doc : documents) {
                System.out.println(doc.toJson());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
