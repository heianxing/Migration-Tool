package org.axonframework.migration;

import org.axonframework.migration.eventstore.JpaEventStoreMigrator;
import org.axonframework.migration.sagas.JpaSagaRepositoryMigrator;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Allard Buijze
 */
public class Migrator {

    public static void main(String[] args) throws Exception {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "/META-INF/spring/migration-config.xml",
                "file:app-specific-context.xml");

        System.out.println("Getting ready to convert Saga Entries");
        new JpaSagaRepositoryMigrator(context).run();
        System.out.println("Finished converting Saga Entries");
        System.out.println();

        System.out.println("Starting to migrate the Event Store");
        new JpaEventStoreMigrator(context).run();

        System.out.println( "Event Store migrated.\n"
                                    + "A new table has been created (e.g. NewDomainEventEntries) with the new Event "
                                    + "Store. Rename tables when ready to finalize migration of your application.");
        System.out.println();
        context.stop();
        System.out.println("Done...");
    }
}
