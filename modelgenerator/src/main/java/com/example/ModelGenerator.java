package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class ModelGenerator {

    public static void main(String[] args) throws Exception {

        // Making schema object with the version code (in this case 1) and package name passed to the method.
        Schema schema = new Schema(1,
                "android.todo.com.database");


        addSchema(schema);

        // This instance of generator generate the specified schema with all the tables added to it and put it on the specified path.
        new DaoGenerator().generateAll(schema, "app/src/main/java");

    }

    private static void addSchema(Schema schema) {

        // Log Entity basically a table named Log

        Entity todotask = schema.addEntity("TodoTask");
        todotask.addLongProperty("_id").primaryKey().autoincrement();
        todotask.addStringProperty("title");
        todotask.addStringProperty("description");
        todotask.addStringProperty("created_date");

    }


}
