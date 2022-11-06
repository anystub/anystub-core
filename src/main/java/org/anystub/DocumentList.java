package org.anystub;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import static java.util.Arrays.asList;

public class DocumentList {

    ConcurrentLinkedQueue<Document> documentList = new ConcurrentLinkedQueue<>();
    ConcurrentHashMap<List<String>, Document> x = new ConcurrentHashMap<>();

    private final Object request2lock = new Object();

    public boolean isEmpty() {
        return documentList.isEmpty();
    }

    public Iterator<Document> iterator() {
        return documentList.iterator();
    }

    public void add(Document document) {
        synchronized (request2lock) {
            documentList.add(document);
            x.putIfAbsent(document.getKey(), document);
        }
    }

    public void clear() {
        documentList.clear();
        x.clear();
    }

    public Optional<Document> getDocument(String[] keys) {
        Document document = x.get(new ArrayList<>(asList(keys)));
        return document != null ? Optional.of(document) : Optional.empty();
    }


}
