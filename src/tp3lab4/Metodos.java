/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp3lab4;

import com.mongodb.BasicDBObject;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Indexes;

import com.mongodb.client.result.DeleteResult;

import org.bson.Document;
import org.bson.conversions.Bson;


/**
 *
 * @author Leandro
 */
public class Metodos {


    public static void imprimeAmericas(MongoCollection collection) {
        System.out.println("\nPaíses con región Americas: ");

        BasicDBObject query = new BasicDBObject();
        query.put("region", "Americas");

        MongoCursor<Document> cursor = collection.find(query).iterator();

        while (cursor.hasNext()) {
            System.out.println(cursor.next().toJson());
        }

    }

    public static void regionPoblacion(MongoCollection collection) {
        System.out.println("\nPaises con región Americas y población mayor a 100000000");

        BasicDBObject criteria = new BasicDBObject();
        criteria.put("region", "Americas");
        criteria.put("poblacion", new Document("$gt", 50000000));

        MongoCursor<Document> cursor = collection.find(criteria).iterator();

        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }

    }

    public static void distintoAfrica(MongoCollection collection) {
        System.out.println("\nPaises con región distinta a Africa");

        BasicDBObject query = new BasicDBObject();
        query.append("region", new BasicDBObject("$ne", "Africa"));

        MongoCursor<Document> cursor = collection.find(query).iterator();

        while (cursor.hasNext()) {
            System.out.println(cursor.next().toJson());
        }
    }

    public static void updateEgypt(MongoCollection collection) {
        System.out.println("\nBuscando pais Egypt para actualizar...");

        BasicDBObject query = new BasicDBObject();
        query.put("nombrePais", "Egypt");

        Document found = (Document) collection.find(new Document("nombrePais", "Egypt")).first();

        if (found != null) {

            Bson update = new Document("nombrePais", "Egipto").append("poblacion", 95000000);
            Bson updateEgypt = new Document("$set", update);
            collection.updateOne(found, updateEgypt);
            System.out.println("Egypt actualizado");

        }

    }

    public static void deleteCod258(MongoCollection collection) {
        System.out.println("\nBuscando código 258");

        BasicDBObject query = new BasicDBObject();
        query.put("codigoPais", 258);
        DeleteResult result = collection.deleteOne(query);
        System.out.println("Resultado de la operación: " + result.getDeletedCount());

    }

    public static void poblacionMayorqueMenorque(MongoCollection collection) {
        System.out.println("\nPoblación mayor que 50000000 y menor que 150000000");

        FindIterable<Document> iterable = collection.find(
                new Document("poblacion", new Document("$gt", 50000000).append("$lt", 150000000)));

        MongoCursor<Document> cursor = iterable.iterator();

        while (cursor.hasNext()) {
            System.out.println(cursor.next().toJson());
        }

    }

    public static void ordenadoPorNombre(MongoCollection collection) {
        System.out.println("\nPaises ordenado por nombre Ascendente: ");

        MongoCursor<Document> cursor = collection.find().sort(new BasicDBObject("nombrePais", 1)).iterator();

        while (cursor.hasNext()) {
            System.out.println(cursor.next().toJson());
        }

    }
    public static void codigoAindex(MongoCollection collection){
              System.out.println("\nConvirtiendo a codigoPais en INDEX");
        try {
            collection.createIndex(Indexes.ascending("codigoPais"));

            System.out.println("Index creado para codigoPais");
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}
