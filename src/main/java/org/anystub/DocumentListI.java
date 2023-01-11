package org.anystub;

import java.util.Optional;

public interface DocumentListI {
    boolean isEmpty();

    void add(Document document);

    void clear();

    Optional<Document> getDocument(String[] keys);
}
