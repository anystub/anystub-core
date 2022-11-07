package org.anystub;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import static java.util.Arrays.asList;

public class DocumentList {

    private final ConcurrentLinkedQueue<Document> documents = new ConcurrentLinkedQueue<>();
    private final ConcurrentHashMap<List<String>, Document> index = new ConcurrentHashMap<>();

    private final Object request2lock = new Object();

    public void add(Document document) {
        synchronized (request2lock) {
            documents.add(document);
            index.putIfAbsent(document.getKey(), document);
        }
    }

    public void clear() {
        documents.clear();
        index.clear();
    }

    public Optional<Document> getDocument(String[] keys) {
        Document document = index.get(new ArrayList<>(asList(keys)));
        return document != null ? Optional.of(document) : Optional.empty();
    }


}
