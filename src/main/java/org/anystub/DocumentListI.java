package org.anystub;

import java.util.Iterator;
import java.util.Optional;

public interface DocumentListI {
    boolean isEmpty();

//    Iterator<Document> iterator();

    void add(Document document);

    void clear();

    Optional<Document> getDocument(String[] keys);
}
